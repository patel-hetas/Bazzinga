package com.bazzinga.ciphernet;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class HistoryScene extends SceneGenerator {
	private static Scene lastScene = Index.stage.getScene();
	private static double scWidth = Screen.getPrimary().getBounds().getWidth();
	private static double scHeight = Screen.getPrimary().getBounds().getHeight();
	
	static HistoryScene hs = new HistoryScene();

	static void start() throws Exception {
		
		lastScene = Index.stage.getScene();
		Index.stage.setScene(hs.showScene());
	}

	static void addHash() throws Exception {
		EncryptionDecryption.encrypt("", new File(System.getProperty("user.dir") + "//src//data//history"),
				true);
	}
	
	
	@Override
	Scene showScene() throws Exception {
			
		HBox hBox = hs.generateHBox();
        VBox vBox = hs.generateVBox();
        Button back = generateBackButton(event -> Index.stage.setScene(lastScene));

        // Create TableView and columns
        TableView<List<String>> tableView = hs.generateTableView();
        TableColumn<List<String>, String> lineNumberColumn = new TableColumn<>("Number");
        TableColumn<List<String>, String> dateTimeColumn = new TableColumn<>("DateTime");
        TableColumn<List<String>, String> urlColumn = new TableColumn<>("URL");
        
        // when row is clicked it opens new tab
        hs.rowClicked(tableView, back, 2);

        lineNumberColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(0)));
        dateTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(1)));
        urlColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(2)));
        urlColumn.setMinWidth(scWidth);
        
        // add data to table
        addData(tableView);
        
        tableView.getColumns().addAll(lineNumberColumn, dateTimeColumn, urlColumn);
       

        hBox.getChildren().addAll(tableView);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(back, hBox);

        Group window = hs.generateWindow(vBox);
        Scene scene = new Scene(window, scWidth, scHeight);

        return scene;
		
	}
	
	@Override
	void addData(TableView<List<String>> tableView) throws Exception {
	       // Get text content and split by line
        String content = EncryptionDecryption.decrypt(new File(System.getProperty("user.dir") + "//src//data//history"));
        String[] lines = content.split("\n");

        // Prepare table data
        ObservableList<List<String>> tableData = FXCollections.observableArrayList();
        
        for (int i = 1; i < lines.length; i++) {
        	String[] parts = lines[i].split(" ", 3);
            List<String> lineData = new ArrayList<>();
            lineData.add(String.valueOf(i + 1));  // Line number
            lineData.add((parts[0] + "-" + parts[1]).replaceAll("[^0-9/:-]", "")); // DateTime
            lineData.add(parts.length > 2 ? parts[2] : ""); // URL
            tableData.add(lineData);
        }

        tableView.setItems(tableData);
	}
	

}
