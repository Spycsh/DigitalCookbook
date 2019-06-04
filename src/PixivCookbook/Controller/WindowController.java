package PixivCookbook.Controller;

import PixivCookbook.Model.SQL_test;
import PixivCookbook.Ingredient;
import PixivCookbook.Recipe;
import PixivCookbook.Step;
import PixivCookbook.View.Main;
import PixivCookbook.View.RecipeWindow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class WindowController extends Application {

    SQL_test model = new SQL_test();
    List<Recipe> recipe;
    Scene mainScene;
    Scene editScene;
    RecipeWindow recipeWindow = new RecipeWindow();
    Stage primaryStage = new Stage();
    Stage editStage = new Stage();
    Main main;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        this.model.run();
        primaryStage = this.primaryStage;
        recipe = this.model.getRecipesForMainPage();
        main = new Main();
        initMain(main);
        mainScene = main.getScene();
        primaryStage.setScene(mainScene);  //main interface
        primaryStage.show();
        recipeWindow= new RecipeWindow();
        editScene = recipeWindow.getScene();
        editStage.setScene(editScene);
        //scene = recipeWindow.getScene();
//        scene.getStylesheets().add(WindowController.class.getResource("index.css").toExternalForm());
//        //primaryStage.setScene(recipeWindow.getScene()); //edit interface
    }
    public void initMain(Main main)
    {
        if(main.temp!=null)
            main.dropAllImageviews();
        main.initializeMainPage(recipe);
        addRecommandButtonAction(main);
        addSearchButtonAction(main);
        addTempAction(recipe,main);
        //main.getScene().getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
    }
    public void initRecipeWindow(RecipeWindow rwin)
    {
        addHomeAction(rwin);
        addEditAction(rwin);
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
            	primaryStage.close();
                editStage.show();
                recipeWindow.refresh();
                initRecipeWindow(recipeWindow);
            }
        });
    	}
    }
    public void addHomeAction(RecipeWindow rmain) {
        rmain.home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editStage.close();
                primaryStage.show();
                initMain(main);
            }
        });
    }
    public void addEditAction(RecipeWindow rwin)
    {
        rwin.editTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rwin.markName=!rwin.markName;
                rwin.refresh();
                initRecipeWindow(rwin);
            }
        });
        rwin.editDescription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rwin.markDescription=!rwin.markDescription;
                rwin.refresh();
                initRecipeWindow(rwin);
            }
        });
        rwin.editIngredient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rwin.markIngredient=!rwin.markIngredient;
                rwin.refresh();
                initRecipeWindow(rwin);
            }
        });
        rwin.editStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rwin.markStep=!rwin.markStep;
                rwin.refresh();
                initRecipeWindow(rwin);
            }
        });
        for(int i=0;i<rwin.addStep.size();i++)
        {
            int mark=i;
            if(rwin.addStep!=null)
            rwin.addStep.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //rwin.step.add(new Step("",mark));
                    //rwin.refresh();
                    //initRecipeWindow(rwin);
                }
            });
//            if(rwin.deleteStep!=null)
//            rwin.deleteStep.get(i).setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    //rwin.step.remove(mark);
//                    //rwin.refresh();
//                    //initRecipeWindow(rwin);
//                }
//            });
        }
        for(int i=0;i<rwin.addIngredient.size();i++)
        {
            int mark=i;
            if(rwin.addIngredient!=null)
            rwin.addStep.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //rwin.ingredients.add(new Ingredient("",0,""));
                    //rwin.refresh();
                    //initRecipeWindow(rwin);
                }
            });
//            if(rwin.deleteIngredient!=null)
//            rwin.deleteIngredient.get(i).setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    //rwin.ingredients.remove(mark);
//                    //rwin.refresh();
//                    //initRecipeWindow(rwin);
//                }
//            });
        }
    }
}
