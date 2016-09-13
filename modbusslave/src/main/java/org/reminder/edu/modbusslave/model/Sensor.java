package org.reminder.edu.modbusslave.model;

/**
 * Основная сущность эмулирующая действия сенсоров.
 * @author Dmitrii Olkhov <reminder63@gmail.com>
 * 
 */
public class Sensor {

    /**
     * Идентификатор сенсора
     */
    private static int newID = 0;

    protected final String[] states;
    private final String name;
    private final int id;
    private final String shortName;
    private boolean on = true;
    private int codeState;

    public Sensor(String name, String alarmMessage, String shortName) {
        states = new String[] { "НОРМА", alarmMessage, "НЕИСПРАВНОСТЬ" };
        this.id = ++newID;
        this.name = name + " " + id;
        this.shortName = shortName + id;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getState() {
        return states[codeState];
    }

    public int getStateCode() {
        return codeState;
    }

    public void setState(int codeState) {
        this.codeState = codeState;
    }

    public boolean isOn() {
        return on;
    }

    public void onSensor() {
        this.on = true;
    }

    public void offSensor() {
        this.on = false;
    }

    public void setDefectStatus() {
        this.codeState = 2;
    }

    public void setNormalStatus() {
        this.codeState = 0;
    }

    public void setAlarmStatus() {
        this.codeState = 1;
    }
}
