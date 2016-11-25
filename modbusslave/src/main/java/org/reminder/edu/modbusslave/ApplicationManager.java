package org.reminder.edu.modbusslave;

import java.util.ArrayList;
import java.util.List;

import org.reminder.edu.configuration.ApplicationConfiguration;
import org.reminder.edu.modbusslave.comm.CoilSensorMapper;
import org.reminder.edu.modbusslave.comm.DataRegisterSensor;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.io.ModbusTransport;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.net.SerialConnection;
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

    private String portName;
    private int baudRate;
    private int dataBits;
    private String parity;
    private String stopBits;
    private String flowControl;
    private int slaveId;
    private Thread currentModBusListener;

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

        ApplicationConfiguration appConfig = ApplicationConfiguration
                .getInstance();

        this.portName = "COM1";
        this.baudRate = appConfig.getBaudRate();
        this.parity = appConfig.getParity();
        this.stopBits = appConfig.getStopBits();
        this.flowControl = "None";
        this.slaveId = appConfig.getSlaveUuid();

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
        SimpleProcessImage spi = buildSimpleProcessImage();

        ModbusCoupler.getReference().setProcessImage(spi);
        ModbusCoupler.getReference().setMaster(false);
        ModbusCoupler.getReference().setUnitID(slaveId);
        final SerialParameters params = new SerialParameters();
        params.setPortName(portName);
        params.setBaudRate(baudRate);
        params.setDatabits(dataBits);
        params.setParity(parity);
        params.setStopbits(stopBits);
        params.setFlowControlIn(flowControl);
        params.setEncoding("ascii");
        params.setEcho(false);

        currentModBusListener = new ModBusListener(params);
        currentModBusListener.start();
        /*
         * Thread t = new Thread(new Runnable() { public void run() {
         * setModbusSerialListener(new ModbusSerialListener(params)); } });
         * t.setDaemon(true); t.start();
         */
    }

    public void stopModbusListener() {
        if (currentModBusListener != null) {
            currentModBusListener.interrupt();
        }
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public void setParity(String parity) {
        this.parity = parity;
    }

    public void setStopBits(String stopBits) {
        this.stopBits = stopBits;
    }

    public void setFlowControl(String flowControl) {
        this.flowControl = flowControl;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    private static class ModBusListener extends Thread {

        private SerialConnection connection;

        public ModBusListener(SerialParameters params) {
            connection = new SerialConnection(params);
            this.setDaemon(true);
        }

        @Override
        public void run() {

            try {
                connection.open();
                ModbusTransport transport = connection.getModbusTransport();

                while (!this.isInterrupted()) {
                    ModbusRequest request = transport.readRequest();
                    ModbusResponse response = null;

                    if (ModbusCoupler.getReference()
                            .getProcessImage() == null) {
                        response = request.createExceptionResponse(
                                Modbus.ILLEGAL_FUNCTION_EXCEPTION);
                    } else {
                        response = request.createResponse();
                    }

                    System.out.println("Request:" + request.getHexMessage());
                    System.out.println("Response:" + response.getHexMessage());

                    transport.writeMessage(response);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                connection.close();
            }
        }
    }
}
