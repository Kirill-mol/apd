package org.apd;

import javafx.scene.control.TextArea;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ApplicationHandler extends Handler {

    private TextArea textArea;

    public ApplicationHandler(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void publish(LogRecord logRecord) {
        textArea.appendText(logRecord.getLoggerName() + " " + logRecord.getSourceMethodName() + "\n" + logRecord.getLevel() + ":" + logRecord.getMessage() + "\n");
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
