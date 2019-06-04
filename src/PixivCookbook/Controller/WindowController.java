package PixivCookbook.Controller;

import PixivCookbook.Model.SQL_test;
import PixivCookbook.Ingredient;
import PixivCookbook.Recipe;
import PixivCookbook.Step;
import PixivCookbook.View.Main;
import PixivCookbook.View.RecipeWindow;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class WindowController extends Application {

    SQL_test model = new SQL_test();
    List<Recipe> recipe;
    Scene scene;
    RecipeWindow recipeWindow = new RecipeWindow();
    Stage primaryStage = new Stage();
    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        this.model.run();
        primaryStage = this.primaryStage;
        recipe = this.model.getRecipesForMainPage();
        recipeWindow= new RecipeWindow();
        Main main = new Main();
        initMain(main);
        scene = main.getScene();
        //scene = recipeWindow.getScene();
//        scene.getStylesheets().add(WindowController.class.getResource("index.css").toExternalForm());
//        //primaryStage.setScene(recipeWindow.getScene()); //edit interface
        primaryStage.setScene(scene);  //main interface
        primaryStage.show();
    }
    public void initMain(Main main)
    {
        main.initializeMainPage(recipe);
        addRecommandButtonAction(main);
        addSearchButtonAction(main);
        addTempAction(recipe,main);
        //main.getScene().getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
    }

    public void addRecommandButtonAction(Main main) {
    	List<Recipe> recipe = model.getRecipesForMainPage();
        main.getRecommendButton().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        main.dropAllImageviews();
                        main.initializeMainPage(recipe);
                        addTempAction(recipe,main);
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
                        addTempAction(result,main);
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
    
    public void addTempAction(List<Recipe> recipe, Main main) {
    	for(int i = 0; i< recipe.size();i++) {
    		int tempCount = i;
    		main.temp[i].addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	int id = model.searchAllMatchedID(recipe.get(tempCount).getRecipeName()).get(0);  
            	recipeWindow.description = model.getRecipeBySearchfromDatabase(id).getCuisineName();
            	recipeWindow.ingredients = (LinkedList<Ingredient>) model.getIngredientsfromDatabase(id);
            	recipeWindow.step = (LinkedList<Step>) model.getStepsfromDatabase(id);
            	recipeWindow.name = model.getRecipeBySearchfromDatabase(id).getRecipeName();
            	recipeWindow.imgPath = model.getRecipeBySearchfromDatabase(id).getImgAddress();
            	scene = recipeWindow.getScene();
            	primaryStage.setScene(scene);
            	primaryStage.show();
            }
        });
    	}
    }
}
