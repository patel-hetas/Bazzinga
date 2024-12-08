package com.bazzinga.ciphernet;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class TabManager {
	
	private TabActions actions;
	private String showUrl = "";

    public TabManager(String showurl) {
    	this.showUrl = showUrl;
    	actions = new TabActions();
    }
	
	Tab createNewTab(String showUrl) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final LocalDateTime[] now = { LocalDateTime.now() };
		final String DEFAULT_URL = showUrl.length() > 0? showUrl : "https://duckduckgo.com";
		final String DEFAULT_Search = "Search";
		
		final Tab tab = new Tab();
		
		final Button backward = new Button();
		final Button forward = new Button();
		final Button reload = new Button();
		final ComboBox<String> history = new ComboBox<>();
		final ArrayList<String> tempHistory = new ArrayList<>();
		final Button burn = new Button();
		
		final TextField urlBar = new TextField(DEFAULT_URL);
		final TextField searchBar = new TextField();
		
		final Button toggleJs = new Button();
		final Button goButton = new Button();
		final Button bookmark = new Button();
		final Button notePad = new Button();
		
		final ComboBox<String> menu = new ComboBox<>();
		final ProgressBar progressBar = new ProgressBar(0.3);
		
		//displays web content
		final WebView webView = new WebView();
		//provides the interface for loading and interacting with web pages
		final WebEngine webEngine = webView.getEngine();
		// represents a background worker for loading web pages.
		final Worker<Void> worker = webEngine.getLoadWorker();
		
		final double screenWidth = Screen.getPrimary().getBounds().getWidth();
		
		
		tab.setText("Duck Duck Go");
		urlBar.setMinHeight(30.0);
		urlBar.setMaxHeight(36.0);
		urlBar.setPrefHeight(30.0);
		
		searchBar.setPromptText(DEFAULT_Search);
		searchBar.setMinHeight(30.0);
		searchBar.setMaxHeight(36.0);
		searchBar.setPrefHeight(30.0);
		
		progressBar.setPrefWidth(screenWidth);
		progressBar.progressProperty().bind(worker.progressProperty());
		progressBar.setVisible(true);
			
	

		worker.stateProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == Worker.State.SUCCEEDED) {
				try {
					now[0] = LocalDateTime.now();
					EncryptionDecryption.encrypt(
							dateTimeFormatter.format(now[0]) + " " + webEngine.getLocation() + "\n",
							new File(System.getProperty("user.dir") + "//src//data//history"), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				progressBar.setVisible(false);
				tempHistory.add(webEngine.getLocation());
				
				String titleName = webEngine.getHistory().getEntries()
						.get(webEngine.getHistory().getEntries().size() - 1).getTitle();
				
				if (titleName != null && titleName.length() > 0) {
					tab.setText(titleName);
				}
				else {
					titleName = urlBar.getText();
					if (titleName.contains("https://"))
						titleName = titleName.replace("https://", "");
					else if (titleName.contains("http://"))
						titleName = titleName.replace("http://", "");
					if (titleName.contains("www."))
						titleName = titleName.replace("www.", "");
					if (titleName.contains("."))
						titleName = titleName.substring(0, titleName.indexOf('.'));
					titleName = Character.toTitleCase(titleName.charAt(0)) + titleName.substring(1, titleName.length());
					
					tab.setText(titleName);
				}
			}
		});
		
		
		// Event Handlers for different actions
		
		EventHandler<ActionEvent> goAction = event -> {
			Index.getLocalHistory().addHistory(urlBar.getText());
			webEngine.load(urlBar.getText());
		};
		

		EventHandler<ActionEvent> searchAction = event -> {
			Index.getLocalHistory().addHistory("https://www.google.com/search?q=" + searchBar.getText().replace(' ', '+')
					+ "&ie=utf-8&oe=utf-8");
			
			webEngine.load("https://www.google.com/search?q=" + searchBar.getText().replace(' ', '+')
			+ "&ie=utf-8&oe=utf-8");
		};
		
		
		EventHandler<ActionEvent> addBookmark = actions.addBookmark(webEngine);

		EventHandler<ActionEvent> reloadPage = actions.reloadPage(webEngine);
		
		/*
		 * Action handler for JS toggle button
		 */
		EventHandler<ActionEvent> toggleJS = actions.toggleJS(webEngine, toggleJs);

		/*
		 * Action handler for notePad button
		 */
		EventHandler<MouseEvent> takeNote = actions.takeNote(webEngine);

		EventHandler<ActionEvent> menuChoose = actions.menuChoose(webEngine, menu);

		/*
		 * Action handler for history button
		 */
		EventHandler<MouseEvent> showHistory = actions.showHistory(history, tempHistory);
				
				
		EventHandler<ActionEvent> chooseEntry = actions.chooseEntry(webEngine, history, urlBar, tempHistory);

		EventHandler<ActionEvent> goBackward = actions.goBackward(webEngine);

		EventHandler<ActionEvent> goForward = actions.goForward(webEngine);
		
		EventHandler<ActionEvent> burnActivity = actions.burnActivity();

		/*
		 * Defining button sizes and styles
		 */
		goButton.setPrefSize(30.0, 30.0);
		toggleJs.setPrefSize(30.0, 30.0);
		forward.setPrefSize(30.0, 30.0);
		backward.setPrefSize(30.0, 30.0);
		history.setPrefSize(30.0, 30.0);
		bookmark.setPrefSize(30.0, 30.0);
		notePad.setPrefSize(30.0, 30.0);
		menu.setPrefSize(30.0, 30.0);
		burn.setPrefSize(30.0, 30.0);
		reload.setPrefSize(36.0, 36.0);

		goButton.setMinSize(30.0, 30.0);
		toggleJs.setMinSize(30.0, 30.0);
		forward.setMinSize(30.0, 30.0);
		backward.setMinSize(30.0, 30.0);
		history.setMinSize(30.0, 30.0);
		bookmark.setMinSize(30.0, 30.0);
		notePad.setMinSize(30.0, 30.0);
		menu.setMinSize(30.0, 30.0);
		burn.setMinSize(30.0, 30.0);
		reload.setPrefSize(36.0, 36.0);

		goButton.setDefaultButton(true);
		toggleJs.setDefaultButton(true);
		forward.setDefaultButton(true);
		backward.setDefaultButton(true);
		bookmark.setDefaultButton(true);
		notePad.setDefaultButton(true);
		burn.setDefaultButton(true);
		reload.setDefaultButton(true);

		menu.getItems().addAll("History", "Bookmarks");

		toggleJs.getStylesheets().add("/stylesheets/ToggleJs.css");
		forward.getStylesheets().add("/stylesheets/Forward.css");
		backward.getStylesheets().add("/stylesheets/Backward.css");
		history.getStylesheets().add("/stylesheets/HistoryButton.css");
		urlBar.getStylesheets().add("/stylesheets/URLField.css");
		searchBar.getStylesheets().add("/stylesheets/SearchField.css");
		goButton.getStylesheets().add("/stylesheets/GoButton.css");
		bookmark.getStylesheets().add("/stylesheets/bookmark.css");
		notePad.getStylesheets().add("/stylesheets/notePad.css");
		menu.getStylesheets().add("/stylesheets/menuButton.css");
		progressBar.getStylesheets().add("/stylesheets/ProgressBar.css");
		burn.getStylesheets().add("/stylesheets/Burn.css");
		reload.getStylesheets().add("/stylesheets/Reload.css");
		/*
		 * Adding event handlers to buttons
		 */
		urlBar.setOnAction(goAction);
		goButton.setOnAction(goAction);
		toggleJs.setOnAction(toggleJS);
		notePad.setOnMouseClicked(takeNote);
		bookmark.setOnAction(addBookmark);
		history.setOnMouseClicked(showHistory);
		history.setOnAction(chooseEntry);
		backward.setOnAction(goBackward);
		forward.setOnAction(goForward);
		searchBar.setOnAction(searchAction);
		burn.setOnAction(burnActivity);
		menu.setOnAction(menuChoose);
		reload.setOnAction(reloadPage);

		HBox hBox = new HBox(10);
		hBox.getChildren().setAll(backward, forward, reload, history, burn, urlBar, searchBar, toggleJs, goButton,
				bookmark, notePad, menu);
		hBox.setPadding(new Insets(6, 12, 6, 12));
		
		hBox.setStyle("-fx-background-color: #F5F7FA");
		hBox.setMinHeight(48.0);
		hBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(urlBar, Priority.ALWAYS);
		
		final VBox vBox = new VBox();
		vBox.getChildren().setAll(hBox, progressBar, webView);
		vBox.setStyle("-fx-background-color: #F5F7FA");
		VBox.setVgrow(webView, Priority.ALWAYS);
		vBox.setMinHeight(40);
		
		tab.setContent(vBox);
		tab.setOnCloseRequest(event -> webEngine.load("https://www.google.com"));
		webEngine.load(DEFAULT_URL);
		
		Index.getLocalHistory().addHistory(DEFAULT_URL);
		
		return tab;
	}


}
