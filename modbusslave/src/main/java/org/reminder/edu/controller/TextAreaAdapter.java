package org.reminder.edu.controller;

import org.reminder.edu.modbusslave.MessageRenderer;

import javafx.scene.control.TextArea;

public class TextAreaAdapter implements MessageRenderer {

    private TextArea textArea;

    public TextAreaAdapter(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void info(String message) {
        // textArea.setText(message);
        textArea.appendText(message + "\n");
    }

    @Override
    public void error(String message) {
        // textArea.setText(message);
        textArea.appendText(message + "\n");
    }

}
