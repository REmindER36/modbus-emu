package org.reminder.edu.modbusslave;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.net.ModbusSerialListener;
import net.wimpi.modbus.procimg.SimpleDigitalIn;
import net.wimpi.modbus.procimg.SimpleDigitalOut;
import net.wimpi.modbus.procimg.SimpleInputRegister;
import net.wimpi.modbus.procimg.SimpleProcessImage;
import net.wimpi.modbus.procimg.SimpleRegister;
import net.wimpi.modbus.util.SerialParameters;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        try {
            // final ModbusSerialListener listener = null;
            SimpleProcessImage spi = null;
            String portname = "COM2";

            // 1. Prepare a process image
            spi = new SimpleProcessImage();
            spi.addDigitalOut(new SimpleDigitalOut(true));
            spi.addDigitalOut(new SimpleDigitalOut(false));
            spi.addDigitalIn(new SimpleDigitalIn(false));
            spi.addDigitalIn(new SimpleDigitalIn(true));
            spi.addDigitalIn(new SimpleDigitalIn(false));
            spi.addDigitalIn(new SimpleDigitalIn(true));
            spi.addRegister(new SimpleRegister(251));
            spi.addRegister(new SimpleRegister(127));
            spi.addInputRegister(new SimpleInputRegister(45));
            spi.addInputRegister(new SimpleRegister(567));
            // spi.getRegister(0).setValue(456);

            // 2. Create the coupler and set the slave identity
            ModbusCoupler.getReference().setProcessImage(spi);
            ModbusCoupler.getReference().setMaster(false);
            ModbusCoupler.getReference().setUnitID(2);

            final SerialParameters params = new SerialParameters();
            params.setPortName(portname);
            params.setBaudRate(9600);
            params.setDatabits(8);
            params.setParity("None");
            params.setStopbits(1);
            params.setEncoding("ascii");
            params.setEcho(false);
            params.setFlowControlIn("");

            Thread t = new Thread(new Runnable() {

                public void run() {
                    // listener =
                    new ModbusSerialListener(params);
                }
            });
            t.start();

            // spi.setInputRegister(0, new SimpleInputRegister(68));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
