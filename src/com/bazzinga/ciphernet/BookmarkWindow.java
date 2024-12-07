package com.bazzinga.ciphernet;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class BookmarkWindow extends SceneGenerator {
	
	private static Scene lastScene = Index.stage.getScene();
	private static double scWidth = Screen.getPrimary().getBounds().getWidth();
	private static double scHeight = Screen.getPrimary().getBounds().getHeight();
	static BookmarkWindow bookmarkWindow = new BookmarkWindow();

	static void start() throws Exception {
		
		lastScene = Index.stage.getScene();
		Index.stage.setScene(bookmarkWindow.showScene());
		
	}


	@Override
	Scene showScene() throws Exception {
		
		HBox hBox = bookmarkWindow.generateHBox();
		VBox vBox = bookmarkWindow.generateVBox();
		
		Button back = generateBackButton(event -> Index.stage.setScene(lastScene));
			
		 // Create TableView and columns
        TableView<List<String>> tableView = bookmarkWindow.generateTableView();
        TableColumn<List<String>, String> lineNumberColumn = new TableColumn<>("Number");
        TableColumn<List<String>, String> urlColumn = new TableColumn<>("Urls");
        
        lineNumberColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(0)));
        urlColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(1)));
        urlColumn.setMinWidth(scWidth);
        
        
		addData(tableView);
		bookmarkWindow.rowClicked(tableView, back, 1);
		tableView.getColumns().addAll(lineNumberColumn, urlColumn);
		
		hBox.getChildren().addAll(tableView);
		vBox.getChildren().addAll(back, hBox);
		
		return new Scene(bookmarkWindow.generateWindow(vBox), scWidth, scHeight);
	}
	
	@Override
	void addData(TableView<List<String>> tableView) throws Exception {
		
		createFile();
	  
     String content = EncryptionDecryption.decrypt(new File(System.getProperty("user.dir") + "//src//data//sv//cache"));
     String[] lines = content.split("\n");
     
     // Prepare table data
     ObservableList<List<String>> tableData = FXCollections.observableArrayList();
     
     for (int i = 0; i < lines.length; i++) {
         List<String> lineData = new ArrayList<>();
         lineData.add(String.valueOf(i + 1)); 
         lineData.add(lines[i]);
         tableData.add(lineData);
     }

     tableView.setItems(tableData);
	}
	
	private static void createFile() {
		
		Path path = Paths.get(System.getProperty("user.dir") + "//src//data//sv//cache");
		System.out.println(path);
		if (!Files.exists(path)) {
			 try {
	                Files.createFile(path);
	                
	            } catch (IOException e) {
	            
	                e.printStackTrace();
	            }
		}
		
	}
}
