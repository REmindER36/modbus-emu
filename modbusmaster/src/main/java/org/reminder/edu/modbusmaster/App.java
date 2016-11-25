package org.reminder.edu.modbusmaster;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SerialConnection con;

        ModbusSerialTransaction trans = null;
        // ReadMultipleRegistersRequest req = null;
        // ReadMultipleRegistersResponse res = null;
        ReadInputRegistersRequest req = null;
        ReadInputRegistersResponse res = null;

        // 1. Setup the parameters
        String portname = "COM3";
        int unitid = 2;
        int ref = 0;
        int count = 1;

        // 2. Set master identifier
        ModbusCoupler.getReference().setUnitID(1);

        // 3. Setup serial parameters
        SerialParameters params = new SerialParameters();
        params.setPortName(portname);
        params.setBaudRate(9600);
        params.setDatabits(8);
        params.setParity("None");
        params.setStopbits(1);
        params.setEncoding("ascii");
        params.setEcho(false);

        // 4. Open the connection

        con = new SerialConnection(params);
        try {
            con.open();

            // 5. Prepare a request
            // req = new ReadMultipleRegistersRequest(ref, count);
            req = new ReadInputRegistersRequest(ref, count);
            req.setUnitID(unitid);
            req.setHeadless();

            // 6. Prepare a transaction
            trans = new ModbusSerialTransaction(con);
            trans.setRequest(req);

            // 7. Execute the transaction repeat times
            trans.execute();
            res = (ReadInputRegistersResponse) trans.getResponse();
            System.out.println("InputRegister[0] value = "
                    + res.getRegister(0).getValue());
            // res = (ReadMultipleRegistersResponse) trans.getResponse();
            // System.out.println("Register[0] value = " +
            // res.getRegister(0).getValue());
            // System.out.println("Register[1] value = " +
            // res.getRegister(1).getValue());

            WriteCoilRequest changeReq = new WriteCoilRequest(1, true);
            // ReadInputRegistersRequest changeReq = new
            // ReadInputRegistersRequest(0, 1);
            changeReq.setUnitID(2);
            changeReq.setHeadless();
            ModbusSerialTransaction tx = new ModbusSerialTransaction(con);
            tx.setRequest(changeReq);
            tx.execute();

            trans.setRequest(req);
            trans.execute();
            res = (ReadInputRegistersResponse) trans.getResponse();
            System.out.println("InputRegister[0] value = "
                    + res.getRegister(0).getValue());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 8. Close the connection
            System.out.println("Connection close.");
            con.close();
        }
    }
}
