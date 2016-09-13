package org.reminder.edu.modbusslave;

import java.util.ArrayList;
import java.util.List;

import org.reminder.edu.configuration.ApplicationConfiguration;
import org.reminder.edu.modbusslave.comm.CoilSensorMapper;
import org.reminder.edu.modbusslave.comm.DataRegisterSensor;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.net.ModbusSerialListener;
import net.wimpi.modbus.procimg.DigitalOut;
import net.wimpi.modbus.procimg.ObservableDigitalOut;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleProcessImage;
import net.wimpi.modbus.util.SerialParameters;

public class ApplicationManager {

    private List<DataRegisterSensor> sensors;
    private List<DigitalOut> digOuts;
    private List<Register> registers;
    private List<CoilSensorMapper> mappers;
    private ModbusSerialListener listener;

    public ApplicationManager() {
        sensors = Helper.createSensorsFromConfiguration();
        digOuts = new ArrayList<>();
        registers = new ArrayList<>();
        mappers = new ArrayList<>();

        for (DataRegisterSensor sensor : sensors) {
            registers.add(sensor.getRegister());

            List<ObservableDigitalOut> digs = Helper
                    .createFourObservableDigitalOut();
            mappers.add(new CoilSensorMapper(sensor, digs));
            digOuts.addAll(digs);
        }
    }

    protected SimpleProcessImage buildSimpleProcessImage() {
        SimpleProcessImage spi = new SimpleProcessImage();

        for (DigitalOut digIut : digOuts) {
            spi.addDigitalOut(digIut);
        }

        for (Register register : registers) {
            spi.addInputRegister(register);
        }

        return spi;
    }

    public void startModbusListener() {
        ApplicationConfiguration appConfig = ApplicationConfiguration
                .getInstance();
        SimpleProcessImage spi = buildSimpleProcessImage();

        ModbusCoupler.getReference().setProcessImage(spi);
        ModbusCoupler.getReference().setMaster(false);
        ModbusCoupler.getReference().setUnitID(appConfig.getSlaveUuid());
        final SerialParameters params = new SerialParameters();
        params.setPortName("COM2");
        params.setBaudRate(appConfig.getBaudRate());
        params.setDatabits(appConfig.getDataBits());
        params.setParity(appConfig.getParity());
        params.setStopbits(appConfig.getStopBits());
        params.setEncoding("ascii");
        params.setEcho(false);

        Thread t = new Thread(new Runnable() {

            public void run() {
                setModbusSerialListener(new ModbusSerialListener(params));
            }
        });
        t.setDaemon(false);
        t.start();
    }

    private void setModbusSerialListener(ModbusSerialListener listener) {
        this.listener = listener;
    }

    public void stopModbusListener() {
        if (listener == null)
            return;
        listener.setListening(false);
    }
}
