package org.reminder.edu.modbusslave.entity;

public interface Sensor {

    String getName();

    int getId();

    String getShortName();

    String getState();

    int getStateCode();

    void setState(int codeState);

    boolean isOn();

    void onSensor();

    void offSensor();

    void setDefectStatus();

    void setNormalStatus();

    void setAlarmStatus();

}
