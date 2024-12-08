package  com.bazzinga.ciphernet;

import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;


abstract class SceneGenerator {
    protected static double scWidth = Screen.getPrimary().getBounds().getWidth();
    protected static double scHeight = Screen.getPrimary().getBounds().getHeight();
    protected static Scene lastScene;

 
    abstract Scene showScene() throws Exception;
    abstract void addData(TableView<List<String>> tableView) throws Exception;

    protected static Button generateBackButton(EventHandler<ActionEvent> backToLastScene) {
        Button back = new Button("Back");
        back.setOnAction(backToLastScene);
        back.getStylesheets().add("/stylesheets/NotePadButton.css");
        return back;
    }

    protected VBox generateVBox(){
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #fafafa;");
        return vBox;
    }

    protected HBox generateHBox(){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-background-color: #fafafa;");
        return hBox;
    }

    protected Group generateWindow(VBox vBox) {
        Group window = new Group();
        window.getChildren().add(vBox);
        return window;
    }
    
    protected TableView<List<String>> generateTableView() {
        TableView<List<String>> tableView = new TableView<>();
        tableView.setPlaceholder(new Label(""));
        tableView.setMinWidth(scWidth);
        tableView.setMaxWidth(scWidth);
        tableView.setMinHeight(scHeight);
        tableView.setMaxHeight(scHeight);
        tableView.getStylesheets().add("/stylesheets/tableStyles.css");

       
        return tableView;
    }
    
	protected static  void rowClicked(TableView<List<String>> tableView, Button back, int index){
		
	      tableView.setRowFactory(tv -> {
	            TableRow<List<String>> row = new TableRow<>();
	            row.setOnMouseClicked(event -> {
	                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
	                        && event.getClickCount() == 2) {

	                    List<String> clickedRow = row.getItem();
	                    String clickedURL = clickedRow.get(index); // Store the clicked URL in the variable

	                    
	                    if (clickedURL != null && !clickedURL.isEmpty()) {
	                        TabManager tabManager = new TabManager(clickedURL);
	                        Tab newTab = tabManager.createNewTab(clickedURL);
	                        BrowserStage.getTabPane().getTabs().add(newTab);
	                        BrowserStage.getTabPane().getSelectionModel().select(newTab);
	                        
	                       back.fire();
	                    }
	                }
	            });
	            return row;
	        });
	}
}