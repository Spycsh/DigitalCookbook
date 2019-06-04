package PixivCookbook.Controller;

import PixivCookbook.View.RecipeWindow;
import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class WindowController extends Application {

    ScrollPane spane;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        RecipeWindow recipeWindow= new RecipeWindow();
        primaryStage.setScene(recipeWindow.getScene());
        primaryStage.show();
    }
}
