package org.reminder.edu.controller;

import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

import gnu.io.CommPortIdentifier;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class MainViewController implements Initializable {

    @FXML
    private ComboBox<String> portNames;
    
    @FXML
    private ComboBox<Byte> dataBits;
    
    @FXML
    private ComboBox<Integer> baudRate;
        
    public MainViewController() {       
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ObservableList<String> portNamesItems = portNames.getItems();
        Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList
                    .nextElement();
            portNamesItems.add(portId.getName());
        }
        
        final ObservableList<Byte> dataBitsItems = dataBits.getItems();
        dataBitsItems.add(new Byte((byte)8));
        
        final ObservableList<Integer> baudRateItems = baudRate.getItems();
        baudRateItems.add(new Integer(9600));
    }
}
