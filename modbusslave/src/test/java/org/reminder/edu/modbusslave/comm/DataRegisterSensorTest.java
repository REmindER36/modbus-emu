package org.reminder.edu.modbusslave.comm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleRegister;

public class DataRegisterSensorTest {

    @Test
    public void testOnSensor() {
        Register register = new SimpleRegister((int) (Math.random() * 0xFFFF));
        DataRegisterSensor sensor = new DataRegisterSensor("", "", "",
                register);
        int oldValue = sensor.getRegister().getValue();
        sensor.onSensor();
        int newValue = register.getValue();
        assertEquals(oldValue | 1, newValue);

        // System.out.println("Old value: " + Integer.toBinaryString(oldValue));
        // System.out.println("New value: " + Integer.toBinaryString(newValue));
    }

    @Test
    public void testOffSensor() {
        Register register = new SimpleRegister((int) (Math.random() * 0xFFFF));
        DataRegisterSensor sensor = new DataRegisterSensor("", "", "",
                register);
        int oldValue = sensor.getRegister().getValue();
        sensor.offSensor();
        int newValue = register.getValue();
        assertEquals(oldValue & ~1, newValue);
    }

    @Test
    public void testSetNormalStatus() {
        Register register = new SimpleRegister(0xF);

        DataRegisterSensor sensor = new DataRegisterSensor("", "", "",
                register);
        int oldValue = sensor.getRegister().getValue();
        sensor.setNormalStatus();
        int newValue = register.getValue();
        assertEquals((oldValue | 2) & ~0xC, newValue);
    }

    @Test
    public void testSetAlarmStatus() {
        Register register = new SimpleRegister(0xF);
        DataRegisterSensor sensor = new DataRegisterSensor("", "", "",
                register);
        int oldValue = sensor.getRegister().getValue();
        sensor.setAlarmStatus();
        int newValue = register.getValue();
        assertEquals((oldValue | 4) & ~0xA, newValue);
    }

    @Test
    public void testSetDefectStatus() {
        Register register = new SimpleRegister(0xF);
        DataRegisterSensor sensor = new DataRegisterSensor("", "", "",
                register);
        int oldValue = sensor.getRegister().getValue();
        sensor.setDefectStatus();
        int newValue = register.getValue();
        assertEquals((oldValue | 8) & ~0x6, newValue);
    }
}
