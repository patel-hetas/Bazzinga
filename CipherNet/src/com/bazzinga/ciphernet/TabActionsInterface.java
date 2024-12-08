package com.bazzinga.ciphernet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import java.util.ArrayList;

public interface TabActionsInterface {
	

    
    EventHandler<ActionEvent> addBookmark(WebEngine webEngine);
    
    EventHandler<ActionEvent> reloadPage(WebEngine webEngine);
    
    EventHandler<ActionEvent> toggleJS(WebEngine webEngine, Button toggleJs);
    
    EventHandler<MouseEvent> takeNote(WebEngine webEngine);
    
    EventHandler<ActionEvent> menuChoose(WebEngine webEngine,ComboBox<String> menu);
    
    EventHandler<MouseEvent> showHistory(ComboBox<String> history,  ArrayList<String> tempHistory);
    
    EventHandler<ActionEvent> chooseEntry(WebEngine webEngine, ComboBox<String> history, TextField urlBar, ArrayList<String> tempHistory);
    
    EventHandler<ActionEvent> goBackward(WebEngine webEngine);
    
    EventHandler<ActionEvent> goForward(WebEngine webEngine);
    
    EventHandler<ActionEvent> burnActivity();
}
