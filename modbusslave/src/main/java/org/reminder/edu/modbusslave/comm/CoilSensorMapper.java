package org.reminder.edu.modbusslave.comm;

import java.util.List;

import org.reminder.edu.modbusslave.entity.Sensor;

import net.wimpi.modbus.procimg.ObservableDigitalOut;
import net.wimpi.modbus.util.Observable;
import net.wimpi.modbus.util.Observer;

/**
 * @author Dmitrii Olkhov <reminder63@gmail.com> Задача класса привязать
 *         конкретный сенсор к конкретным цифровым портам.
 */
public class CoilSensorMapper implements Observer {

    private Sensor sensor;
    private List<ObservableDigitalOut> digOuts;

    public CoilSensorMapper(Sensor sensor, List<ObservableDigitalOut> digOuts) {
        this.sensor = sensor;
        this.digOuts = digOuts;
        for (ObservableDigitalOut out : digOuts) {
            out.addObserver(this);
            out.set(false);
        }

    }

    public void update(Observable o, Object arg) {
        // System.out.println("Sensor: " + sensor.getName() + " updated! (" +
        // arg.getClass() + ";" + o.getClass() + ")" );
        if (!(o.getClass().equals(ObservableDigitalOut.class)))
            return;

        ObservableDigitalOut out = (ObservableDigitalOut) o;

        switch (digOuts.indexOf(out)) {
        case 0:
            if (out.isSet()) {
                sensor.onSensor();
            } else {
                sensor.offSensor();
            }
            break;
        case 1:
            if (out.isSet()) {
                sensor.setNormalStatus();
            }
            break;
        case 2:
            if (out.isSet()) {
                sensor.setAlarmStatus();
            }
            break;
        case 3:
            if (out.isSet()) {
                sensor.setDefectStatus();
            }
            break;
        default:
            break;
        }
    }

    public Sensor getSensor() {
        return sensor;
    }

    public List<ObservableDigitalOut> getDigitalOuts() {
        return digOuts;
    }
}
