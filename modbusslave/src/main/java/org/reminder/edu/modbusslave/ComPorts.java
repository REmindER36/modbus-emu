package org.reminder.edu.modbusslave;

import java.util.Enumeration;

import gnu.io.CommPortIdentifier;

public class ComPorts {

    public static void main(String[] args) {
        Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList
                    .nextElement();
            System.out.println(portId.getName());
        }
    }

}
