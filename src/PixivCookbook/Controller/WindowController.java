package PixivCookbook.Controller;

import PixivCookbook.Ingredient;
import PixivCookbook.Model.SQL_test;
import PixivCookbook.Recipe;
import PixivCookbook.Step;
import PixivCookbook.View.Main;
import PixivCookbook.View.RecipeWindow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    static int id;
    static int alertSwitch = 0;
    static Alert alert;
    
    public static void alertBoxDuplicate() {
    	alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Such recipe has already existed");
    	alert.show();
    	alertSwitch = 1;
    }
    
    public static void alertBoxDefault() {
    	alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Please enter another name!");
    	alert.show();
    	alertSwitch = 1;
    }
    
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
        
        addAddAction(recipe,main );
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
                        main.search.setText("");
                        addAddAction(recipe,main);
                    }
                });
        main.favoriteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(main.favorite);
                main.favorite=!main.favorite;
                initMain(main);
            }
        });
    }

	public void addSearchButtonAction(Main main) {
		main.getSearchButton().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (!main.search.getText().isEmpty()) {
					if (main.backButton.getText() != "back") {
						main.backButton.setText("back");
						main.backButton.setLayoutX(600);
						main.backButton.setLayoutY(150);
						main.backButton.setMinSize(20, 20);
						main.pane.getChildren().add(main.backButton);
						addBackButtonAction(main);
					}
					String searchName = main.search.getText();
					main.dropAllImageviews();
					List<Recipe> result = model.searchAllMatchedRecipes(searchName);
					main.initializeMainPage(result);
					addTempAction(result, main);
					addAddAction(result,main);
				}
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
//                        List<Recipe> showRecipe = new LinkedList<Recipe>();
//                        for(int i=0;i<recipe.size();i++)
//                        {
//                            int temp=i+1;
//                            for(int j=0;j<10;j++)
//                                temp=(temp*131+temp)%recipe.size();
//                            showRecipe.add(recipe.get(temp-1));
//                        }
                        main.pane.getChildren().remove(main.backButton);
                        main.backButton.setText("");
                        main.initializeMainPage(recipe);
                        addTempAction(recipe,main);
                        addAddAction(recipe,main);
                        main.search.setText("");
                    }
                });
    }
    
    public void addTempAction(List<Recipe> recipe, Main main) {
    	for(int i = 0; i< Math.min(7,recipe.size());i++) {
    		int tempCount = i;
    		main.temp[i].addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	int id = model.searchAllMatchedID(recipe.get(tempCount).getRecipeName()).get(0);  
            	recipeWindow.description = model.getRecipeBySearchfromDatabase(id).getCuisineName();
            	recipeWindow.ingredients = (LinkedList<Ingredient>) model.getIngredientsfromDatabase(id);
            	recipeWindow.step = (LinkedList<Step>) model.getStepsfromDatabase(id);
            	recipeWindow.preparationTime=model.getRecipeBySearchfromDatabase(id).getPreparationTime();
            	recipeWindow.servings=model.getRecipeBySearchfromDatabase(id).getNumberOfEaters();
            	recipeWindow.cookingTime=model.getRecipeBySearchfromDatabase(id).getCookingTime();
            	recipeWindow.name = model.getRecipeBySearchfromDatabase(id).getRecipeName();
            	recipeWindow.imgPath = model.getRecipeBySearchfromDatabase(id).getImgAddress();
            	recipeWindow.markDescription = false;
            	recipeWindow.markName = false;
            	recipeWindow.markImage = false;
            	recipeWindow.markStep = false;
            	recipeWindow.markIngredient = false;
//            	System.out.print(recipeWindow.imgPath);
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
                String searchName = main.search.getText();
                main.dropAllImageviews();
                List<Recipe> result = model.searchAllMatchedRecipes(searchName);
                main.initializeMainPage(result);
                addTempAction(result,main);
                addAddAction(result,main);
            }
        });
        rmain.star.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rmain.favorite=!rmain.favorite;
                rmain.saveData();
                rmain.refresh();
                initRecipeWindow(rmain);
            }
        });
    }
    
    public void addEditAction(RecipeWindow rwin)
    {
    	
    	if(rwin.name != "Default") {
    	id = model.searchAllMatchedID(rwin.name).get(0);
    	}else if(rwin.name =="Default") {
    		id = model.assignID();
    	}
        rwin.editTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(rwin.markName == false) {
                double posx=editStage.getX();
                double posy=editStage.getY();
                rwin.markName=!rwin.markName;
                rwin.saveData();
                rwin.refresh();
                initRecipeWindow(rwin);
                editStage.setX(posx);;
                editStage.setY(posy);
            	}else {
            		double posx=editStage.getX();
                    double posy=editStage.getY();
            		String newName=rwin.tf_RecipeName.getText();
            		
            		if(!rwin.name.matches("Default")) {
            			if(newName.matches("Default")) {
            				alertBoxDefault();
            			}else {
            			model.saveRecipName(id, newName);
            			}
            		}else if(rwin.name.matches("Default")) {
            			if(newName.matches("Default")) {
            				alertBoxDefault();
            			}else {
            			//description by default is null, and serving is 4
                		Recipe recipe = new Recipe(newName,"",4);
                		model.addRecipetoDatabase(recipe,id);
                		model.saveImagePath(id+1, "");
            			}
                	}
            		
            		if(alertSwitch == 1) {
            			alertSwitch = 0;
            			rwin.markName=!rwin.markName;
            			rwin.saveData();
                        rwin.refresh();
                        initRecipeWindow(rwin);
                        editStage.setX(posx);;
                        editStage.setY(posy);
            		}else{
	            		rwin.name =newName;
	            		rwin.markName=!rwin.markName;
	            		rwin.saveData();
	                    rwin.refresh();
	                    initRecipeWindow(rwin);
	                    editStage.setX(posx);;
	                    editStage.setY(posy);
            		}
            	}
            }
        });
        
        
        // image
    	rwin.title.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	System.out.println("click img");
            	
            	JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You choose to open this image: " + chooser.getSelectedFile().getPath());
						rwin.editImgPath = chooser.getSelectedFile().getPath();
					model.saveImagePath(id, rwin.editImgPath);
					String fileName = chooser.getSelectedFile().getName();
					
	                double posx=editStage.getX();
	                double posy=editStage.getY();
//                    rwin.markImage = false;
                    rwin.saveData();
                    rwin.refresh();
                    initRecipeWindow(rwin);
                    editStage.setX(posx);;
                    editStage.setY(posy);
//					System.out.println(fileName);
//					System.out.println(filePath);

				}
            }
            
    	});
        
        rwin.editDescription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(rwin.markDescription == false) {
                double posx=editStage.getX();
                double posy=editStage.getY();
                
                rwin.markDescription=!rwin.markDescription;
                rwin.saveData();
                rwin.refresh();
                initRecipeWindow(rwin);
                editStage.setX(posx);;
                editStage.setY(posy);             
            	}else {
            	    rwin.saveData();
            		double posx=editStage.getX();
                    double posy=editStage.getY();
                    rwin.description = rwin.tf_Description.getText();
                    model.saveDescription(id, rwin.description);
                    model.saveCooktime(id,rwin.cookingTime);
                    model.savePreparationtime(id,rwin.preparationTime);
                    model.saveServings(id,rwin.servings);
                    rwin.markDescription=!rwin.markDescription;
                    rwin.saveData();
                    rwin.refresh();
                    initRecipeWindow(rwin);
                    editStage.setX(posx);;
                    editStage.setY(posy);
            	}
            }
        });
        rwin.editIngredient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double posx=editStage.getX();
                double posy=editStage.getY();
                
                rwin.markIngredient=!rwin.markIngredient;
                rwin.saveData();
                rwin.refresh();
                initRecipeWindow(rwin);
                editStage.setX(posx);;
                editStage.setY(posy);
            }
        });
        
        rwin.editStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double posx=editStage.getX();
                double posy=editStage.getY();
                //rwin.saveData();
                rwin.markStep=!rwin.markStep;
                rwin.saveData();
                rwin.refresh();
                initRecipeWindow(rwin);
                editStage.setX(posx);;
                editStage.setY(posy);
                              
            }
        });
        for(int i=0;i<rwin.addStep.size();i++)
        {
            int mark=i;
            rwin.addStep.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double posx=editStage.getX();
                    double posy=editStage.getY();
                    rwin.saveData();
                    rwin.step.add(mark,new Step("",mark));
                    rwin.refresh();
                    initRecipeWindow(rwin);
                    editStage.setX(posx);;
                    editStage.setY(posy);
                }
            });
            rwin.deleteStep.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double posx=editStage.getX();
                    double posy=editStage.getY();
                    rwin.saveData();
                    rwin.step.remove(mark);
                    if(rwin.step.size()==0)
                        rwin.step.add(new Step("default",0));
                    rwin.refresh();
                    initRecipeWindow(rwin);
                    editStage.setX(posx);;
                    editStage.setY(posy);
                    
                }
            });
            rwin.upStep.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double posx=editStage.getX();
                    double posy=editStage.getY();
                    rwin.saveData();
                    if(mark>0)
                    {
                        Step step1=rwin.step.get(mark);
                        Step step2=rwin.step.get(mark-1);
                        rwin.step.set(mark-1,step1);
                        rwin.step.set(mark,step2);
                    }
                    rwin.refresh();
                    initRecipeWindow(rwin);
                    editStage.setX(posx);;
                    editStage.setY(posy);

                }
            });
            rwin.downStep.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double posx=editStage.getX();
                    double posy=editStage.getY();
                    rwin.saveData();
                    if(mark<rwin.addStep.size()-1)
                    {
                        Step step1=rwin.step.get(mark);
                        Step step2=rwin.step.get(mark+1);
                        rwin.step.set(mark+1,step1);
                        rwin.step.set(mark,step2);
                    }
                    rwin.refresh();
                    initRecipeWindow(rwin);
                    editStage.setX(posx);;
                    editStage.setY(posy);

                }
            });
        }
        for(int i=0;i<rwin.addIngredient.size();i++)
        {
            int mark=i;
            rwin.addIngredient.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double posx=editStage.getX();
                    double posy=editStage.getY();
                    rwin.saveData();
                    rwin.ingredients.add(mark,new Ingredient("",0,""));
                    rwin.refresh();
                    initRecipeWindow(rwin);
                    editStage.setX(posx);;
                    editStage.setY(posy);
                    
                }
            });
            rwin.deleteIngredient.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double posx=editStage.getX();
                    double posy=editStage.getY();
                    rwin.saveData();
                    rwin.ingredients.remove(mark);
                    if(rwin.ingredients.size()==0)
                        rwin.ingredients.add(new Ingredient("default",0,""));
                    rwin.refresh();
                    initRecipeWindow(rwin);
                    editStage.setX(posx);;
                    editStage.setY(posy);
                    
                }
            });
        }
    }

    public void addAddAction(List<Recipe> recipe, Main main) {
    	 main.temp[Math.min(7,recipe.size())].addEventHandler(MouseEvent.MOUSE_CLICKED,
                 new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent e) {
                    	 // after add recipe, all attributes are default
                    	 recipeWindow.description = "";

						Ingredient defaultIngredient = new Ingredient("Default", 0.0, "0");
						recipeWindow.ingredients = new LinkedList<Ingredient>() {{
							add(defaultIngredient);
						}};
						Step defaultStep = new Step(" ",0);
						recipeWindow.step = new LinkedList<Step>() {{
							add(defaultStep);
						}};
						recipeWindow.name = "Default";
						
						recipeWindow.markImage = true; // wait for add image
						recipeWindow.imgPath = "img\\addImage.png";  // set default image
						
						primaryStage.close();
						editStage.show();
						recipeWindow.refresh();
						initRecipeWindow(recipeWindow);
                     }
                     
                 });
    }
    
    public void addEditImg(RecipeWindow rwin) {

    }
}
