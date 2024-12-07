package com.bazzinga.ciphernet;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;

public class TabActions implements TabActionsInterface {



	@Override
	public EventHandler<ActionEvent> addBookmark(WebEngine webEngine) {
		
		return event -> {
			File file = new File(System.getProperty("user.dir") + "//src//data//sv//cache");
			try {
				EncryptionDecryption.encrypt(webEngine.getLocation() + "\n", file, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

	@Override
	public EventHandler<ActionEvent> reloadPage(WebEngine webEngine) {
		
		return event -> webEngine.reload();
	}

	@Override
	public EventHandler<ActionEvent> toggleJS(WebEngine webEngine, Button toggleJs) {
		
		return event -> {
			if (!webEngine.isJavaScriptEnabled()) {
				webEngine.setJavaScriptEnabled(true);
				toggleJs.getStylesheets().removeAll(toggleJs.getStylesheets());
				toggleJs.getStylesheets().add("/stylesheets/ToggleJs.css");
				webEngine.reload();
			} else {
				webEngine.setJavaScriptEnabled(false);
				toggleJs.getStylesheets().removeAll(toggleJs.getStylesheets());
				toggleJs.getStylesheets().add("/stylesheets/notToggleJs.css");
				webEngine.reload();
			}
		};
	}

	@Override
	public EventHandler<ActionEvent> menuChoose(WebEngine webEngine, ComboBox<String> menu) {
		
		return event -> {
			switch (menu.getValue()) {
			case "History":
				try {
					HistoryScene.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "Bookmarks":
				try {
					BookmarkWindow.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		};
	}

	@Override
	public EventHandler<ActionEvent> chooseEntry(WebEngine webEngine, ComboBox<String> history, TextField urlBar, ArrayList<String> tempHistory) {

		return event -> {
			if (history.getValue() != null) {
				webEngine.load(history.getValue());
				urlBar.setText(webEngine.getLocation());
				tempHistory.add(webEngine.getLocation());
				history.getItems().removeAll(history.getItems());
				for (int i = tempHistory.size() - 1; i >= 0; i--) {
					history.getItems().add(tempHistory.get(i));
				}
			}
		};
	}

	@Override
	public EventHandler<ActionEvent> goBackward(WebEngine webEngine) {
		

		return event -> {
			String url = Index.getLocalHistory().backward();
			if (url != null) {
				webEngine.load(url);
			}
		};
	}

	@Override
	public EventHandler<ActionEvent> goForward(WebEngine webEngine) {

		return  event -> {
			String url = Index.getLocalHistory().forward();
			if (url != null) {
				webEngine.load(url);
			}
		};
	}

	@Override
	public EventHandler<ActionEvent> burnActivity() {
		
		return event -> {
			File dir = new File(System.getProperty("user.dir") + "//src//data//sv");
			if (dir.isDirectory()) {
				File[] listOfFiles = dir.listFiles();
				if (listOfFiles != null) {
					for (File file : listOfFiles) {
						if (file.isFile()) {
							file.delete();
						}
					}
				}
			}
			Index.stage.close();
			BrowserStage.init(Index.stage);
			Index.stage.show();
		};
	}

	@Override
	public EventHandler<MouseEvent> takeNote(WebEngine webEngine) {
		
		return event -> {
			try {
				NoteScene.takeNote();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

	@Override
	public EventHandler<MouseEvent> showHistory(ComboBox<String> history, ArrayList<String> tempHistory) {

		return event -> {
			history.getItems().removeAll(history.getItems());
			for (int i = tempHistory.size() - 1; i >= 0; i--) {
				history.getItems().add(tempHistory.get(i));
			}
		};
	}

}
