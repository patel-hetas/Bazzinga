package com.bazzinga.ciphernet;

import javafx.application.Application;
import javafx.stage.Stage;


public class Index extends Application {
	
	static Stage stage = new Stage();
	BrowserStage browserStage = new BrowserStage();
    private static History localHistory = new History();
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        
        BrowserStage.init(Index.stage);
        Index.stage.show();
       
    }

	public static History getLocalHistory() {
		return localHistory;
	}

}