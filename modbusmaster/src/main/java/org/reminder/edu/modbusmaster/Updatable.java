package org.reminder.edu.modbusmaster;

public interface Updatable {

    void update() throws Exception;

    void commit();
}
