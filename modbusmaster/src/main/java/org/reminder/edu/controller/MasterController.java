package org.reminder.edu.controller;

import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.reminder.edu.modbusslave.MessageRenderer;
import org.reminder.edu.model.MasterModel;

import gnu.io.CommPortIdentifier;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.wimpi.modbus.util.SerialParameters;

public class MasterController implements Initializable {

    @FXML
    private TextField slaveId;
    
    @FXML
    private ComboBox<String> portNames;

    @FXML
    private ComboBox<Integer> dataBits;

    @FXML
    private ComboBox<Integer> baudRate;

    @FXML
    private ComboBox<String> parity;

    @FXML
    private ComboBox<String> stopBits;

    @FXML
    private ComboBox<String> flowControl;
    
    @FXML
    private Button btnOpen;

    @FXML
    private Button btnClose;
    
    @FXML
    private Button td1;
    
    @FXML
    private Button td2;
    
    @FXML
    private Button td3;
    
    @FXML
    private Button dd4;
    
    @FXML
    private Button dd5;
    
    @FXML
    private Button tk6;
    
    @FXML
    private Button tk7;
    
    @FXML
    private Button dv8;
    
    @FXML
    private Button dv9;
    
    @FXML
    private Button ds10;
    
    @FXML
    private Button ds11;

    @FXML
    private Button ds12;
    
    @FXML
    private Button do13;
    
    @FXML
    private Button do14;
    
    @FXML
    private Button do15;
    
    @FXML
    private TextArea logArea;
    
    private MessageRenderer messageRenderer;
    
    private MasterModel model;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MasterModel();
        
        final ObservableList<String> portNamesItems = portNames.getItems();
        Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList
                    .nextElement();
            portNamesItems.add(portId.getName());
        }
        
        final ObservableList<Integer> dataBitsItems = dataBits.getItems();
        dataBitsItems.add(4);
        dataBitsItems.add(5);
        dataBitsItems.add(6);
        dataBitsItems.add(7);
        dataBitsItems.add(8);
        dataBits.getSelectionModel().select(4);

        final ObservableList<Integer> baudRateItems = baudRate.getItems();
        baudRateItems.add(2400);
        baudRateItems.add(4800);
        baudRateItems.add(7200);
        baudRateItems.add(9600);
        baudRateItems.add(14400);
        baudRate.getSelectionModel().select(3);

        final ObservableList<String> parityItems = parity.getItems();
        parityItems.add("None");
        parityItems.add("Even");
        parityItems.add("Odd");
        parity.getSelectionModel().select(0);

        final ObservableList<String> stopBitsItems = stopBits.getItems();
        stopBitsItems.add("1");
        stopBitsItems.add("1.5");
        stopBitsItems.add("2");
        stopBits.getSelectionModel().select(0);

        final ObservableList<String> flowControlItems = flowControl.getItems();
        flowControlItems.add("None");
        // flowControlItems.add("xon/xoff out");
        flowControlItems.add("xon/xoff in");
        flowControlItems.add("rts/cts in");
        // flowControlItems.add("rts/cts out");
        flowControl.getSelectionModel().select(0);
        
        this.messageRenderer = new TextAreaAdapter(logArea);
        this.model.setMessageRenderer(messageRenderer);
    }    

    @FXML
    private void handleOpenConnection(ActionEvent event) {
        
        SerialParameters params = new SerialParameters();

        params.setPortName(portNames.getValue());
        params.setBaudRate(baudRate.getValue());
        params.setDatabits(dataBits.getValue());
        params.setParity(parity.getValue());
        params.setStopbits(stopBits.getValue());
        params.setFlowControlIn(flowControl.getValue());

        try {
            model.openConnection(params, new Integer(slaveId.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCloseConnection(ActionEvent event) {
        model.closeConnection();
    }
    
    @FXML
    private void handleSensorStateRequest(ActionEvent event) {
        
    }
}
