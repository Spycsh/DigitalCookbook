package PixivCookbook.Controller;

import PixivCookbook.Model.SQL_test;
import PixivCookbook.Recipe;
import PixivCookbook.View.Main;
import PixivCookbook.View.RecipeWindow;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class WindowController extends Application {

    SQL_test model = new SQL_test();
    List<Recipe> recipe;
    Scene scene;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        this.model.run();
        recipe = this.model.getRecipesForMainPage();
        RecipeWindow recipeWindow= new RecipeWindow();
        Main main = new Main();
        initMain(main);
        scene = main.getScene();
        scene.getStylesheets().add(WindowController.class.getResource("index.css").toExternalForm());
        //primaryStage.setScene(recipeWindow.getScene()); //edit interface
        primaryStage.setScene(scene);  //main interface
        primaryStage.show();
    }
    public void initMain(Main main)
    {
        main.initializeMainPage(recipe);
        addRecommandButtonAction(recipe,main);
        addSearchButtonAction(main);
        //main.getScene().getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
    }

    public void addRecommandButtonAction(List<Recipe> recipe,Main main) {
        main.getRecommendButton().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        main.dropAllImageviews();
                        main.initializeMainPage(recipe);
                        if(main.backButton.getText() == "back") {
                            main.pane.getChildren().remove(main.backButton);
                            main.backButton.setText("");
                        }
                    }
                });
    }

    public void addSearchButtonAction(Main main) {
        main.getSearchButton().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if(main.backButton.getText() != "back") {
                            main.backButton.setText("back");
                            main.backButton.setLayoutX(600);
                            main.backButton.setLayoutY(150);
                            main.backButton.setMinSize(20,20);
                            main.pane.getChildren().add(main.backButton);
                            addBackButtonAction(main);
                        }
                        String searchName = main.search.getText();
                        main.dropAllImageviews();
                        List<Recipe> result = model.searchAllMatchedRecipes(searchName);
                        main.initializeMainPage(result);
                    }
                });
    }

    public void addBackButtonAction(Main main) {
        main.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        main.dropAllImageviews();
                        List<Recipe> recipe = model.getRecipesForMainPage();
                        main.pane.getChildren().remove(main.backButton);
                        main.backButton.setText("");
                        main.initializeMainPage(recipe);
                    }
                });
    }
}
