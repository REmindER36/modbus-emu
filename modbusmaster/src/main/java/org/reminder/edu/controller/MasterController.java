package org.reminder.edu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.reminder.edu.model.MasterModel;

import javafx.fxml.Initializable;

public class MasterController implements Initializable {
   
    private MasterModel model;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MasterModel();
    }    

}
