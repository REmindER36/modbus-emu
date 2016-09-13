package org.reminder.edu.modbusslave;

import java.util.ArrayList;
import java.util.List;

import org.reminder.edu.configuration.ApplicationConfiguration;
import org.reminder.edu.modbusslave.comm.DataRegisterSensor;

import net.wimpi.modbus.procimg.ObservableDigitalOut;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleRegister;

public class Helper {

    public static List<DataRegisterSensor> createSensorsFromConfiguration() {
        List<DataRegisterSensor> sensors = new ArrayList<DataRegisterSensor>();

        ApplicationConfiguration config = ApplicationConfiguration
                .getInstance();

        for (int i = 0; i < config.getThermalSensorCount(); i++) {
            sensors.add(createThermalSensor());
        }

        for (int i = 0; i < config.getSmokeSensorCount(); i++) {
            sensors.add(createSmokeSensor());
        }

        for (int i = 0; i < config.getFireButtonCount(); i++) {
            sensors.add(createFireButton());
        }

        for (int i = 0; i < config.getAlarmButtonCount(); i++) {
            sensors.add(createAlarmButton());
        }

        for (int i = 0; i < config.getCrackDoorSensorCount(); i++) {
            sensors.add(createCrackDoorSensor());
        }

        for (int i = 0; i < config.getGlassBreakSensorCount(); i++) {
            sensors.add(createGlassBreakSensor());
        }

        for (int i = 0; i < config.getVolumeChangeSensorCount(); i++) {
            sensors.add(createVolumeChangeSensor());
        }
        return sensors;
    }

    public static List<Register> createRegisters(int count) {
        List<Register> registers = new ArrayList<Register>();

        for (int i = 0; i < count; i++) {
            registers.add(new SimpleRegister());
        }
        return registers;
    }

    public static Register createRegister() {
        return new SimpleRegister(0);
    }

    public static DataRegisterSensor createThermalSensor() {
        return new DataRegisterSensor("Тепловой датчик", "ТЕПЛО", "ТД",
                createRegister());
    }

    public static DataRegisterSensor createSmokeSensor() {
        return new DataRegisterSensor("Дымовой датчик", "ДЫМ", "ДД",
                createRegister());
    }

    public static DataRegisterSensor createFireButton() {
        return new DataRegisterSensor("Пожарная кнопка", "ПОЖАР", "ПК",
                createRegister());
    }

    public static DataRegisterSensor createAlarmButton() {
        return new DataRegisterSensor("Тревожная кнопка", "ТРЕВОГА", "ТК",
                createRegister());
    }

    public static DataRegisterSensor createCrackDoorSensor() {
        return new DataRegisterSensor("Датчик взлома двери", "ВЗЛОМ", "ДВ",
                createRegister());
    }

    public static DataRegisterSensor createGlassBreakSensor() {
        return new DataRegisterSensor("Датчик разбития стекла", "СТЕКЛО", "ДС",
                createRegister());
    }

    public static DataRegisterSensor createVolumeChangeSensor() {
        return new DataRegisterSensor("Датчик изменения объема", "ОБЪЁМ", "ДО",
                createRegister());
    }

    public static List<ObservableDigitalOut> createFourObservableDigitalOut() {
        List<ObservableDigitalOut> digs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            digs.add(new ObservableDigitalOut());
        }
        return digs;
    }
}
