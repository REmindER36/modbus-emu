package org.reminder.edu.modbusslave.entity;

/**
 * Основная сущность эмулирующая действия сенсоров.
 * @author Dmitrii Olkhov <reminder63@gmail.com>
 * 
 */
public class SensorImpl implements Sensor {

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

    public SensorImpl(String name, String alarmMessage, String shortName) {
        states = new String[] { "НОРМА", alarmMessage, "НЕИСПРАВНОСТЬ" };
        this.id = ++newID;
        this.name = name + " " + id;
        this.shortName = shortName + id;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#getShortName()
     */
    @Override
    public String getShortName() {
        return this.shortName;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#getState()
     */
    @Override
    public String getState() {
        return states[codeState];
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#getStateCode()
     */
    @Override
    public int getStateCode() {
        return codeState;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#setState(int)
     */
    @Override
    public void setState(int codeState) {
        this.codeState = codeState;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#isOn()
     */
    @Override
    public boolean isOn() {
        return on;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#onSensor()
     */
    @Override
    public void onSensor() {
        this.on = true;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#offSensor()
     */
    @Override
    public void offSensor() {
        this.on = false;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#setDefectStatus()
     */
    @Override
    public void setDefectStatus() {
        this.codeState = 2;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#setNormalStatus()
     */
    @Override
    public void setNormalStatus() {
        this.codeState = 0;
    }

    /* (non-Javadoc)
     * @see org.reminder.edu.modbusslave.entity.ISensor#setAlarmStatus()
     */
    @Override
    public void setAlarmStatus() {
        this.codeState = 1;
    }
}
