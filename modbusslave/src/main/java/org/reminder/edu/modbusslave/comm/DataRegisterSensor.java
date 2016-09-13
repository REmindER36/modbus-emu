package org.reminder.edu.modbusslave.comm;

import org.reminder.edu.modbusslave.model.Sensor;

import net.wimpi.modbus.procimg.Register;

public class DataRegisterSensor extends Sensor {

    private Register register;

    public DataRegisterSensor(String name, String alarmMessage,
            String shortName, Register register) {
        super(name, alarmMessage, shortName);
        this.register = register;
        onSensor();
        setNormalStatus();
    }

    @Override
    public void onSensor() {
        super.onSensor();
        byte[] bytes = register.toBytes();
        bytes[1] = (byte) (bytes[1] | 1);
        register.setValue(bytes);
        // System.out.println("Sensor: " + this.getName() + " on!");
    }

    @Override
    public void offSensor() {
        super.offSensor();
        byte[] bytes = register.toBytes();
        bytes[1] = (byte) (bytes[1] & ~1);
        // System.out.println("Sensor: " + this.getName() + " off!");
    }

    @Override
    public void setNormalStatus() {
        super.setNormalStatus();
        byte[] bytes = register.toBytes();
        bytes[1] = (byte) ((bytes[1] | 0b10) & ~0b1100);
    }

    @Override
    public void setAlarmStatus() {
        super.setAlarmStatus();
        byte[] bytes = register.toBytes();
        bytes[1] = (byte) ((bytes[1] | 0b100) & ~0b1010);
    }

    @Override
    public void setDefectStatus() {
        super.setDefectStatus();
        byte[] bytes = register.toBytes();
        bytes[1] = (byte) ((bytes[1] | 0b1000) & ~0b0110);
    }

    public Register getRegister() {
        return this.register;
    }
}
