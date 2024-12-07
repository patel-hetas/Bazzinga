package com.bazzinga.ciphernet;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class BrowserStage {

    private static double screenWidth = Screen.getPrimary().getBounds().getWidth();
    private static double screenHeight = Screen.getPrimary().getBounds().getHeight();

    private static TabPane tabPane;


    public static void init(Stage stage) {
    	
        Group root = new Group();
        BorderPane borderPane = new BorderPane();
        
        tabPane = new TabPane();
        TabManager tabManager = new TabManager("");

        try {
            HistoryScene.addHash();
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.setTitle("CipherNet");
        stage.setMaximized(true);
        stage.setScene(new Scene(root, screenWidth, screenHeight));
        stage.getIcons().add(new Image("Icons/icon.png"));

        getTabPane().setSide(Side.TOP);
        getTabPane().setPrefSize(screenWidth, screenHeight);
        getTabPane().getStylesheets().add("/stylesheets/tab.css");
        getTabPane().setMinHeight(44.0);

        Tab newTab = createNewTab(tabManager);
        
        getTabPane().getTabs().add(newTab);
        createHomeTab(getTabPane());

        getTabPane().getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedTab, newSelectedTab) -> {
            if (newSelectedTab == newTab) {
                Tab tab = tabManager.createNewTab("");
                final ObservableList<Tab> tabs = getTabPane().getTabs();
                tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));
                tabs.add(tabs.size() - 1, tab);
                getTabPane().getSelectionModel().select(tab);
            }
        });

        borderPane.setCenter(getTabPane());
        root.getChildren().add(borderPane);
    }

    private static Tab createNewTab(TabManager tabManager) {
        Tab newTab = new Tab();
        newTab.setText("+");
        newTab.setStyle("-fx-font-size: 14pt;");
        newTab.setClosable(false);
        return newTab;
    }

    private static void createHomeTab(final TabPane tabPane) {
        Tab tab = new Tab("Home");
        tab.setStyle("-fx-border-width: 0 0 0 0");
        
        final VBox vBox = new VBox();
        ImageView imageView = new ImageView(new Image("/Icons/ood.jpg"));
        imageView.setFitWidth(screenWidth);
        imageView.setFitHeight(screenHeight - 10);
        vBox.getChildren().setAll(imageView);
        
        vBox.setMinSize(screenWidth, screenHeight);
        vBox.setPrefSize(screenWidth, screenHeight);
        vBox.setMaxSize(screenWidth, screenHeight);
        tab.setContent(vBox);
        
        final ObservableList<Tab> tabs = tabPane.getTabs();
        tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));
        tabs.add(tabs.size() - 1, tab);
        tabPane.getSelectionModel().select(tab);
    }
    

	static TabPane getTabPane() {
		return tabPane;
	}

}

