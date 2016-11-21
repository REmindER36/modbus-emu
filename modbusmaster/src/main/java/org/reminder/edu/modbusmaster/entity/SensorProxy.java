package org.reminder.edu.modbusmaster.entity;

import java.net.ConnectException;

import org.reminder.edu.modbusmaster.Updatable;
import org.reminder.edu.modbusslave.entity.Sensor;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.InputRegister;

public class SensorProxy implements Sensor, Updatable {
    
    private SerialConnection connection;
    private Sensor sensor;
    private ModbusSerialTransaction tx;
    
    public SensorProxy(SerialConnection connection, Sensor sensor) throws ConnectException {
        if (!connection.isOpen())
            throw new ConnectException("Соединение закрыто.");
        this.connection = connection;
        this.sensor = sensor;
        this.tx = new ModbusSerialTransaction(connection);
    }

    @Override
    public String getName() {
        return sensor.getName();
    }
    
    @Override
    public int getId() {
        return sensor.getId();
    }

    @Override
    public String getShortName() {
        return sensor.getShortName();
    }

    @Override
    public String getState() {
        return sensor.getState();
    }

    @Override
    public int getStateCode() {
        return sensor.getStateCode();
    }

    @Override
    public void setState(int codeState) {
        sensor.setState(codeState);
    }

    @Override
    public boolean isOn() {
        return sensor.isOn();
    }

    @Override
    public void onSensor() throws Exception {
        sensor.onSensor();
    }

    @Override
    public void offSensor() throws Exception {
        sensor.offSensor();
    }

    @Override
    public void setDefectStatus() throws Exception {
        sensor.setDefectStatus();
    }

    @Override
    public void setNormalStatus() throws Exception {
        sensor.setNormalStatus();
    }

    @Override
    public void setAlarmStatus() throws Exception {
        sensor.setAlarmStatus();
    }

    @Override
    public void update() throws Exception {
        ReadInputRegistersRequest requset = new ReadInputRegistersRequest(sensor.getId(), 1);
        ReadInputRegistersResponse response = null;
        tx.setRequest(requset);
        tx.execute();
        response = (ReadInputRegistersResponse) tx.getResponse();
        InputRegister register = response.getRegister(0);
        char[] value = String.format("%8s", Integer.toBinaryString(register.toBytes()[0] & 0xFF)).replace(' ', '0').toCharArray();
        assert value.length == 8;
        if ("1".equals(value[7])) {
            sensor.onSensor();
        } else if ("0".equals(value[7])) {
            sensor.offSensor();
        }
        
        if ("1".equals(value[6])) {
            sensor.setNormalStatus();
        } 
        else if ("1".equals(value[5])) {
            sensor.setAlarmStatus();
        }
        else if ("1".equals(value[4])) {
            sensor.setDefectStatus();
        }
    }

    @Override
    public void commit() {
    }

}
