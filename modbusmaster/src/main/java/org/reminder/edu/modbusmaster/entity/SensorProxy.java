package org.reminder.edu.modbusmaster.entity;

import org.reminder.edu.Updatable;
import org.reminder.edu.modbusslave.entity.Sensor;

import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.InputRegister;

public class SensorProxy implements Sensor, Updatable {

    private Sensor sensor;
    private SerialConnection connection;
    private ModbusSerialTransaction tx;
    private int slaveId;

    public SensorProxy(Sensor sensor) {
        this.sensor = sensor;
        this.slaveId = slaveId;
        this.tx = new ModbusSerialTransaction(connection);
    }

    public void setConnection(SerialConnection connection) {
        this.connection = connection;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
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
    public void onSensor() {
        sensor.onSensor();
    }

    @Override
    public void offSensor() {
        sensor.offSensor();
    }

    @Override
    public void setDefectStatus() {
        sensor.setDefectStatus();
    }

    @Override
    public void setNormalStatus() {
        sensor.setNormalStatus();
    }

    @Override
    public void setAlarmStatus() {
        sensor.setAlarmStatus();
    }

    @Override
    public void update() throws Exception {
        ReadInputRegistersRequest requset = new ReadInputRegistersRequest(
                sensor.getId(), 1);
        requset.setUnitID(slaveId);
        ReadInputRegistersResponse response = null;
        tx.setRequest(requset);
        tx.execute();
        response = (ReadInputRegistersResponse) tx.getResponse();
        InputRegister register = response.getRegister(0);
        char[] value = String
                .format("%8s",
                        Integer.toBinaryString(register.toBytes()[0] & 0xFF))
                .replace(' ', '0').toCharArray();
        assert value.length == 8;
        if ("1".equals(String.valueOf(value[7]))) {
            sensor.onSensor();
        } else if ("0".equals(String.valueOf(value[7]))) {
            sensor.offSensor();
        }

        if ("1".equals(String.valueOf(value[6]))) {
            sensor.setNormalStatus();
        } else if ("1".equals(String.valueOf(value[5]))) {
            sensor.setAlarmStatus();
        } else if ("1".equals(String.valueOf(value[4]))) {
            sensor.setDefectStatus();
        }
    }

    @Override
    public void commit() {
    }

}
