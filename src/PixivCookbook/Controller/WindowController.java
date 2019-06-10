package PixivCookbook.Controller;

import PixivCookbook.ForbiddenPair;
import PixivCookbook.Ingredient;
import PixivCookbook.Model.SQL_test;
import PixivCookbook.Recipe;
import PixivCookbook.Step;
import PixivCookbook.View.ForbiddenEditWindow;
import PixivCookbook.View.Main;
import PixivCookbook.View.RecipeWindow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WindowController extends Application {

    SQL_test model = new SQL_test();
    List<Recipe> recipe;
    Scene mainScene;
    Scene editScene;
    RecipeWindow recipeWindow = new RecipeWindow();
    Stage primaryStage = new Stage();
    Stage editStage = new Stage();
    Stage forbiddenStage = new Stage();
    Main main;
    
    
    static int id;
    static int alertSwitch = 0;
    static int alertDelete = 0;
    static int alertForbid = 0;
    static StringBuffer alertWrongType_SB = new StringBuffer("");
    static Alert alert;
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
        ForbiddenEditWindow forbidden = new ForbiddenEditWindow();
        Scene forbiddenScene= forbidden.getScene();
        forbiddenStage.setScene(forbiddenScene);
        initEditForbidden(forbidden,forbiddenStage,model);
        //primaryStage.close();
        //forbiddenStage.show();
    }
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
    	alert.setContentText("Please enter another recipe name!");
    	alert.show();
    	alertSwitch = 1;
    }
    
    public static void alertBoxDelete() {
    	alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Warning");
    	alert.setContentText("Are you sure you want to delete this recipe?");
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		alertDelete = 1;
    	}else{

    	}
    }
    
//    public static void alertImgAddress() {
//    	alert = new Alert(AlertType.ERROR);
//    	alert.setTitle("Warning");
//    	alert.setContentText("Image path illegal, maybe the path contains some illegal brackets!");
//    	alert.show();
//    	alertSwitch = 1;
//    }
    
    public static void alertInputMainInfo() {
    	alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Input illegal, Please check the main infomation format!");
    	alert.show();
    	alertSwitch = 1;
    }
    
    public static void alertInputIngredientInfo() {
    	alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Input illegal, Please check the Ingredient infomation format!");
    	alert.show();
    	alertSwitch = 1;
    }
    
    // click save ingredient
    public static void alertBoxForbidPair(String forbid1, String forbid2) {
    	alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Warning");
    	alert.setContentText("Your ingredient list contains something not compatible with each other,("+
    							forbid1+":"+forbid2+"), are you sure to save them?");
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		alertForbid = 0;
    	}else {
    		
    	}
    }
    
    // display forbid ingredient when view the recipe
    public static void alertBoxForbidPairView(List<String> forbidInfo) {
    	alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Warning");
    	alert.setContentText("Your ingredient list contains something not compatible with each other:"
    							+"\n"+forbidInfo+" \n"+"please check them");
    	
    	alert.show();
    }
    
    public static void alertBoxWrongtypeDES() {
    	alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText(alertWrongType_SB.toString());
    	alert.show();
    }
    public static boolean isDouble(String input){
    	Matcher mer = Pattern.compile("^[+-]?[0-9.]+$").matcher(input);
    	return mer.find();
   	}
    
    public static boolean isInteger(String input){
    	Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
    	return mer.find();
    	}
    
    public static void main(String[] args) {
        launch(args);
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
        addForbidAction(rwin);

    }
    public void addRecommandButtonAction(Main main) {
    	//List<Recipe> recipe = model.getRecipesForMainPage();
        main.getRecommendButton().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        List<Recipe> recipe = model.getRecipesForMainPage();
                        main.littleTitle.setText("");
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
                //System.out.println(main.favorite);
                //main.favorite=!main.favorite;
                forbiddenStage.show();
                initMain(main);
            }
        });
        main.favoriteButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println(main.favorite);
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
						main.backButton.setLayoutX(400);
						main.backButton.setLayoutY(150);
						main.backButton.setMinSize(20, 20);
						main.pane.getChildren().add(main.backButton);
						addBackButtonAction(main);
					}
					main.littleTitle.setText("Search Results");
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
                        main.littleTitle.setText("");
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
            	int id = model.getIDbyName(recipe.get(tempCount).getRecipeName());  
            	recipeWindow.description = model.getRecipeBySearchfromDatabase(id).getCuisineName();
            	recipeWindow.ingredients = (LinkedList<Ingredient>) model.getIngredientsfromDatabase(id);
            	recipeWindow.step = (LinkedList<Step>) model.getStepsfromDatabase(id);
            	recipeWindow.preparationTime=model.getRecipeBySearchfromDatabase(id).getPreparationTime();
            	recipeWindow.servings=model.getRecipeBySearchfromDatabase(id).getNumberOfEaters();
            	recipeWindow.cookingTime=model.getRecipeBySearchfromDatabase(id).getCookingTime();
            	recipeWindow.name = model.getRecipeBySearchfromDatabase(id).getRecipeName();
            	recipeWindow.editImgPath = null;
            	recipeWindow.imgPath = model.getRecipeBySearchfromDatabase(id).getImgAddress();
            	recipeWindow.markDescription = false;
            	recipeWindow.markName = false;
            	recipeWindow.markImage = false;
            	recipeWindow.markStep = false;
            	recipeWindow.markIngredient = false;
            	recipeWindow.stepText= new LinkedList<TextField>();
                recipeWindow.ingredientText1= new LinkedList<TextField>();
                recipeWindow.ingredientText2= new LinkedList<TextField>();
                recipeWindow.ingredientText3= new LinkedList<TextField>();
                recipeWindow.spane.setVvalue(0);
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
    
    public void addForbidAction(RecipeWindow rwin) {
    	List<String> forbidInfo = new LinkedList<String>();
    	// forbid pair info
    	List<String> aList = new LinkedList<String>();
        for(int i=0;i<rwin.ingredients.size();i++)
        {
        	String currentIngredient = rwin.ingredients.get(i).getName();
        	aList.add(currentIngredient);
        	List<String> opponentList =  model.getForbiddenPair(currentIngredient);
        	for(String e:aList) {
        		if(opponentList.contains(e)) {
        			System.out.println("forbidden pair exists:("+e+":"+currentIngredient+")");
        			forbidInfo.add(e+" and "+currentIngredient);
//        			alertBoxForbidPair(e,currentIngredient);
        			break;
        		}
        	}
        }
        if(alertForbid == 0)
	        if((forbidInfo.size()!=0)) {
	  
	        	alertBoxForbidPairView(forbidInfo);
	        }
    }
    
    public void addEditAction(RecipeWindow rwin)
    {

    		
    	
    	if(rwin.name != "Default") {
        	id = model.getIDbyName(rwin.name);
        	}else if(rwin.name =="Default") {
        		id = model.assignID();
        	}
        rwin.editTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(rwin.markName == false) {
	                double posx=editStage.getX();
	                double posy=editStage.getY();
	                rwin.saveData();
	                rwin.markName=!rwin.markName;
	                rwin.refresh();
	                initRecipeWindow(rwin);
	                editStage.setX(posx);;
	                editStage.setY(posy);
            	}else {
            		double posx=editStage.getX();
                    double posy=editStage.getY();
                    rwin.saveData();
            		String newName=rwin.tf_RecipeName.getText();
            		
            		if(!rwin.name.matches("Default")) {
            			if(newName.matches("Default")) {
            				alertBoxDefault();
            			}else {
            			System.out.println(newName);
            			model.saveRecipName(id, newName);
            			}
            		}else if(rwin.name.matches("Default")) {
            			if(newName.matches("Default")) {
            				alertBoxDefault();
            			}else {
	            			//description by default is null, and serving is 4
	                		Recipe recipe = new Recipe(newName,"",4);
	                		rwin.name = newName;
	                		model.addRecipetoDatabase(recipe,id);
	                		model.saveImagePath(id+1, "");
            			}
                	}
            		
            		if(alertSwitch == 1) {
            			alertSwitch = 0;
                        rwin.saveData();
            			rwin.markName=!rwin.markName;
                        rwin.refresh();
                        initRecipeWindow(rwin);
                        editStage.setX(posx);;
                        editStage.setY(posy);
            		}else{
	            		rwin.name = newName;
                        rwin.saveData();
	            		rwin.markName=!rwin.markName;
	                    rwin.refresh();
	                    initRecipeWindow(rwin);
	                    editStage.setX(posx);;
	                    editStage.setY(posy);
            		}
            	}
            }
        });
        
        rwin.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(rwin.name.matches("Default")) {

            	}else {

	            	alertBoxDelete();
	            	if(alertDelete == 1) {
	            		model.deleteRecipefromDatabase(id);
	            		editStage.close();
	                    primaryStage.show();
	                    List<Recipe> recipe = model.getRecipesForMainPage();
	                    main.littleTitle.setText("");
	                    main.dropAllImageviews();
	                    main.initializeMainPage(recipe);
	                    addTempAction(recipe,main);
	                    if(main.backButton.getText() == "back") {
	                        main.pane.getChildren().remove(main.backButton);
	                        main.backButton.setText("");
	                    }
	                    main.search.setText("");
	                    addAddAction(recipe,main);
	            		alertDelete = 0;
	            	}else {
	            		alertDelete = 0;
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
					if(rwin.name != "Default") {
						rwin.editImgPath = chooser.getSelectedFile().getPath();
						model.saveImagePath(id, rwin.editImgPath);
					}else {
						alertBoxDefault();
					}
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
        
    	//  edit main information
        rwin.editDescription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(rwin.markDescription == false) {
	                double posx=editStage.getX();
	                double posy=editStage.getY();
	                
	                rwin.markDescription=!rwin.markDescription;
	                rwin.refresh();
	                initRecipeWindow(rwin);
	                editStage.setX(posx);;
	                editStage.setY(posy);             
            	}else { 	
            		if(isInteger(rwin.tf_Serveing.getText()) && Integer.parseInt(rwin.tf_Serveing.getText()) == 0) {
        
            				alertWrongType_SB.append("Servings can't be 0!\n");
      
            			if(!isDouble(rwin.tf_Cookingtime.getText())|!isDouble(rwin.tf_Preparation.getText())) {
	            			if(!isDouble(rwin.tf_Preparation.getText())) {
	            				alertWrongType_SB.append("Wrong type for preparation time!\n");
	            			}
	            			if(!isDouble(rwin.tf_Cookingtime.getText())) {
	            				alertWrongType_SB.append("Wrong type for cooking time\n");
	            			}
	            			if(!isInteger(rwin.tf_Serveing.getText())) {
	            				alertWrongType_SB.append("Wrong type for servings!\n");
	            			}
            			}	
	            			alertBoxWrongtypeDES();
	            			alertWrongType_SB =new StringBuffer("");
	            			double posx=editStage.getX();
		                    double posy=editStage.getY();
		                    rwin.refresh();
		                    initRecipeWindow(rwin);
		                    editStage.setX(posx);;
		                    editStage.setY(posy);
	            		}else if(!isDouble(rwin.tf_Cookingtime.getText())|!isDouble(rwin.tf_Preparation.getText())|!isInteger(rwin.tf_Serveing.getText())) {
	            			if(!isDouble(rwin.tf_Preparation.getText())) {
	            				alertWrongType_SB.append("Wrong type for preparation time!\n");
	            			}
	            			if(!isDouble(rwin.tf_Cookingtime.getText())) {
	            				alertWrongType_SB.append("Wrong type for cooking time\n");
	            			}
	            			if(!isInteger(rwin.tf_Serveing.getText())) {
	            				alertWrongType_SB.append("Wrong type for servings!\n");
	            			}
	            			alertBoxWrongtypeDES();
	            			alertWrongType_SB =new StringBuffer("");
	            			double posx=editStage.getX();
		                    double posy=editStage.getY();
		                    rwin.refresh();
		                    initRecipeWindow(rwin);
		                    editStage.setX(posx);;
		                    editStage.setY(posy);
	            		}else{
		            	    rwin.saveData();
		            		double posx=editStage.getX();
		                    double posy=editStage.getY();
		                    model.saveDescription(id, rwin.description);
		                    model.saveCooktime(id,rwin.cookingTime);
		                    model.savePreparationtime(id,rwin.preparationTime);
		                    model.saveServings(id,rwin.servings);
		                    rwin.markDescription=!rwin.markDescription;
		                    try {
								model.addIngredientstoDatabase(rwin.ingredients, id);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                    rwin.refresh();
		                    initRecipeWindow(rwin);
		                    editStage.setX(posx);;
		                    editStage.setY(posy);
	            		}
	            	}
            }
        });
        
        // every eidt and save click will cause warning
        // if forbidden pair exists
        rwin.editIngredient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double posx=editStage.getX();
                double posy=editStage.getY();
                rwin.saveData();
                rwin.markIngredient=!rwin.markIngredient;
                rwin.refresh();
                
                rwin.saveData();
                if(rwin.markIngredient == false) {
	                List<String> aList = new LinkedList<String>();
	                for(int i=0;i<rwin.ingredientText3.size();i++)
	                {
	                	aList.add(rwin.ingredientText3.get(i).getText());
	                	String currentIngredient = rwin.ingredientText3.get(i).getText();
	                	List<String> opponentList =  model.getForbiddenPair(currentIngredient);
	                	for(String e:aList) {
	                		if(opponentList.contains(e)) {
	                			System.out.println("forbidden pair exists:("+e+":"+currentIngredient+")");
	                			alertBoxForbidPair(e,currentIngredient);
	                			break;
	                		}
	                	}
	                }
                }
                if(alertForbid == 1) { 
                	
                	alertForbid = 0;

                initRecipeWindow(rwin);
                editStage.setX(posx);;
                editStage.setY(posy);
                }
                else {
                	try {
 						model.addIngredientstoDatabase(rwin.ingredients, id);
 					} catch (SQLException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					}
 	                initRecipeWindow(rwin);
 	                editStage.setX(posx);;
 	                editStage.setY(posy);
                }
                
                
            }
        });
        
        rwin.editStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double posx=editStage.getX();
                double posy=editStage.getY();
                rwin.saveData();
                rwin.markStep=!rwin.markStep;
                rwin.saveData();
                rwin.refresh();
                try {
					model.addStepstoDatabase(rwin.step, id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
                    //System.out.println(mark);
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
                    	recipeWindow.preparationTime=0;
                     	recipeWindow.servings=0;
                     	recipeWindow.cookingTime=0;
						Ingredient defaultIngredient = new Ingredient("Default", 0.0, "0");
						recipeWindow.ingredients = new LinkedList<Ingredient>() {{
							add(defaultIngredient);
						}};
						Step defaultStep = new Step(" ",0);
						recipeWindow.step = new LinkedList<Step>() {{
							add(defaultStep);
						}};
						recipeWindow.name = "Default";
						
						recipeWindow.markImage = false; // wait for add image
						recipeWindow.imgPath = "img\\addImage.png";  // set default image
						
						recipeWindow.markDescription = false;
		            	recipeWindow.markName = false;
		            	recipeWindow.markImage = false;
		            	recipeWindow.markStep = false;
		            	recipeWindow.markIngredient = false;
                        recipeWindow.ingredientText1= new LinkedList<TextField>();
                        recipeWindow.ingredientText2= new LinkedList<TextField>();
                        recipeWindow.ingredientText3= new LinkedList<TextField>();
						primaryStage.close();
						editStage.show();
						recipeWindow.editImgPath = null;
						recipeWindow.refresh();
						initRecipeWindow(recipeWindow);
                     }
                     
                 });
    }
    public void initEditForbidden(ForbiddenEditWindow forw,Stage forbiddenStage,SQL_test model)
    {
        LinkedList<ForbiddenPair> data;
        data=model.getAllForbiddenPair();
        EditForbiddenEvent editFor = new EditForbiddenEvent();
        forw.data=data;
        forw.refresh();
        editFor.addForbiddenEvent(forw,forbiddenStage,model);
    }

    public void addEditImg(RecipeWindow rwin) {

    }
}
