package PixivCookbook.Controller;

import PixivCookbook.Model.ForbiddenPair;
import PixivCookbook.Model.Ingredient;
import PixivCookbook.Model.Recipe;
import PixivCookbook.Model.Step;
import PixivCookbook.View.ForbiddenEditWindow;
import PixivCookbook.View.MainWindow;
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


/**
 * This is the controller class for
 * main window class
 *
 */
public class WindowController extends Application {
    DBController model = DBController.getInstance();
    List<Recipe> recipe;
    Scene mainScene;
    Scene editScene;
    RecipeWindow recipeWindow;
    Stage primaryStage = new Stage();
    Stage editStage = new Stage();
    Stage forbiddenStage = new Stage();
    MainWindow main;
	private boolean displayForbidInfo = true;

    static int id;
    static int alertSwitch = 0;
    static int alertDelete = 0;
    static int alertForbid = 0;
    static int alertIngredient = 0;
    static StringBuffer alertWrongType_SB = new StringBuffer("");
    static Alert alert;


    /**
     * start the stage by get recipes data 
     * from database and show them on the screen
     */
    @Override
    public void start(Stage primaryStage) {
        this.model.run();
        primaryStage = this.primaryStage;
        recipe = this.model.getRecipesForMainPage();
        main = MainWindow.getInstance();
        initMain(main);
        mainScene = main.getScene();
        primaryStage.setScene(mainScene);  //main interface
        primaryStage.show();
        recipeWindow= RecipeWindow.getInstance();
        editScene = recipeWindow.getScene();
        editStage.setScene(editScene);
        ForbiddenEditWindow forbidden = ForbiddenEditWindow.getInstance();
        Scene forbiddenScene= forbidden.getScene();
        forbiddenStage.setScene(forbiddenScene);
        initEditForbidden(forbidden,forbiddenStage,model);
        primaryStage.setTitle("Pixiv Cookbook");
        editStage.setTitle("Pixiv Cookbook");
        forbiddenStage.setTitle("Pixiv Cookbook");
        //primaryStage.close();
        //forbiddenStage.show();
    }


    /**
     *  alert box:
     *  duplicate recipe name
     *
     */
    public static void alertBoxDuplicate() {
    	alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Such recipe has already existed");
    	alert.show();
    	alertSwitch = 1;
    }

    public static void alertBoxDuplicateIngredient() {
    	alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Such ingredient has already existed");
    	alert.show();
    	alertIngredient = 1;
    }
    /**
     *  alert box
     *  recipe name cannot be default
     */
    public static void alertBoxDefault() {
    	alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Please enter another recipe name!");
    	alert.show();
    	alertSwitch = 1;
    }

    /**
     *  alert box
     *  recipe name first
     */
    public static void alertBoxFirst() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setContentText("Please enter recipe name and save it at first!");
        alert.show();
        alertSwitch = 1;
    }

    /**
     * alert box
     * delete acknowledgement
     */
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

    /**
     * alert box
     * illegal input in main Information
     */
    public static void alertInputMainInfo() {
    	alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Input illegal, Please check the main infomation format!");
    	alert.show();
    	alertSwitch = 1;
    }

    /**
     * alert box
     * illegal input in ingredient
     */
    public static void alertInputIngredientInfo() {
    	alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText("Input illegal, Please check the Ingredient infomation format!");
    	alert.show();
    	alertSwitch = 1;
    }

    /**
     * @param forbid1 one of the forbidden pair
     * @param forbid2 the other of the forbidden pair
     * alert box
     * ask whether user want to save ingredients in the database
     * although they may collide with each other
     *
     */
    public static void alertBoxForbidPair(String forbid1, String forbid2) {
    	alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Warning");
    	alert.setContentText("Your ingredient list contains something not compatible with each other,("+
    							forbid1+":"+forbid2+"), are you sure to save them?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		alertForbid = 0;
    	}else {
    		alertForbid =1;
    	}
    }

    /**
     * @param forbidInfo the forbidden ingredient pair information
     *
     * alert box
     * user enter in some ingredients and 
     * offense the forbidden ingredient pair in database
     * this will alert user about which is the pair
     *
     * displayed when view the recipe
     *
     */
    public static void alertBoxForbidPairView(List<String> forbidInfo) {
    	alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Warning");
    	alert.setContentText("Your ingredient list contains something not compatible with each other:"
    							+"\n"+forbidInfo+" \n"+"please check them");
    	alert.show();
    }


    /**
     *  check if the image path is valid
     */
    public static void alertImgPathNotValid() {
    	alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Warning");
    	alert.setContentText("Please upload the right image format");
    	alert.show();
    }


    /**
     * alert box
     * show which has a wrong input
     *
     */
    public static void alertBoxWrongtypeDES() {
    	alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Warning");
    	alert.setContentText(alertWrongType_SB.toString());
    	alert.show();
    }


    /**
     * @param input	 given input string
     * @return true if denotes a double 
     *
     */
    public static boolean isDouble(String input){
    	Matcher mer = Pattern.compile("^[+]?[0-9.]+$").matcher(input);
    	return mer.find();
   	}


    /**
     * @param input  given input string
     * @return	 true if denotes an integer
     */
    public static boolean isInteger(String input){
    	Matcher mer = Pattern.compile("^[+]?[0-9]+$").matcher(input);
    	return mer.find();
    	}

    /**
     * @param input
     * @return if the image suffix is valid
     */
    public static boolean imgValid(String input) {
    	Matcher mer = Pattern.compile("^.*(.jpg|.JPG|.JPEG|.jpeg|.png|.PNG)$").matcher(input);
    	return mer.find();
    }


    /**
     * @param main
     * initialize the main window
     */
    public void initMain(MainWindow main)
    {
        if(main.temp!=null)
            main.dropAllImageviews();
        main.initializeMainPage(recipe);
        addRecommendButtonAction(main);
        addSearchButtonAction(main);
        addBackButtonAction(main);
        addTempAction(recipe,main);
        addAddAction(recipe,main );
        //main.getScene().getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
    }


    /**
     * @param rwin
     * initialize the recipe window
     */
    public void initRecipeWindow(RecipeWindow rwin)
    {
        addHomeAction(rwin);
        addEditAction(rwin);
    }


    /**
     * bind actions on recommend button
     * flush, get 7 recipes by random from database
     * if in a search state
     * then eliminate the back button
     * clear the search box
     *
     * @param main

     */
    public void addRecommendButtonAction(MainWindow main) {
    	//List<Recipe> recipe = model.getRecipesForMainPage();
        main.getRecommendButton().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        List<Recipe> recipe = model.getRecipesForMainPage();
                        main.littleTitle.setText("");   // search Results
                        main.dropAllImageviews();
                        main.initializeMainPage(recipe);
                        addTempAction(recipe,main);
                        main.searchButton.setVisible(true);
                        main.backButton.setVisible(false);
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
//                forbiddenStage.show();
//                initMain(main);
//                editStage.close();
//                primaryStage.show();
//                String searchName = main.search.getText();
//                main.dropAllImageviews();
//                List<Recipe> result = model.searchAllMatchedRecipes(searchName);
//                main.initializeMainPage(result);
//                addTempAction(result,main);
//                addAddAction(result,main);
            }
        });
        main.sticky.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println(main.favorite);
                //main.favorite=!main.favorite;
                forbiddenStage.show();
                initMain(main);
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
        main.favoriteButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println(main.favorite);
                main.favorite=!main.favorite;
                initMain(main);
            }
        });
    }

	public void addSearchButtonAction(MainWindow main) {
		main.getSearchButton().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (!main.search.getText().isEmpty()) {
				    main.backButton.setVisible(true);
				    //main.searchButton.setVisible(false);
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

    public void addBackButtonAction(MainWindow main) {
        main.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        main.backButton.setVisible(false);
                        //main.searchButton.setVisible(true);
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
                        main.initializeMainPage(recipe);
                        addTempAction(recipe,main);
                        addAddAction(recipe,main);
                        main.search.setText("");
                    }
                });
    }

    /**
     * set the 7 recipes on the main window with action
     * and prepare the information for display on the
     * recipe window
     *
     * @param recipe  a list of recipes displayed
     * @param main main window
     *

     *
     */
    public void addTempAction(List<Recipe> recipe, MainWindow main) {
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
                        recipeWindow.illegal=false;
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
    /**
     * home action
     * @param rmain

     */
    public void addHomeAction(RecipeWindow rmain) {
        rmain.home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	LinkedList<Ingredient> temp = new LinkedList<Ingredient>();
            	for(int i=0;i<rmain.ingredients.size();i++)
            	{
            		temp.add(new Ingredient(rmain.ingredients.get(i)));
            	}
                rmain.saveData();
                if(!rmain.illegal)
                try {
                    if(rmain.markIngredient)
                    {
                        model.addIngredientstoDatabase(rmain.ingredients, id);
                        if(alertIngredient==1)
                        {
                        	alertIngredient=0;
                        	rmain.ingredients=temp;
                        	model.addIngredientstoDatabase(rmain.ingredients, id);
                        }
                    }
                    if(rmain.markName)
                        model.saveRecipName(id, rmain.name);
                    if(rmain.markImage)
                        model.saveImagePath(id, rmain.imgPath);
                    if(rmain.markDescription) {
                        model.saveDescription(id, rmain.description);
                        model.saveCooktime(id, rmain.cookingTime);
                        model.savePreparationtime(id, rmain.preparationTime);
                        model.saveServings(id, rmain.servings);
                    }
                    if(rmain.markStep)
                        model.addStepstoDatabase(rmain.step, id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                editStage.close();
                primaryStage.show();
                String searchName = main.search.getText();
                main.dropAllImageviews();
                List<Recipe> result = model.searchAllMatchedRecipes(searchName);
                main.initializeMainPage(result);
                addTempAction(result,main);
                addAddAction(result,main);
                displayForbidInfo = true;
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

    /**
     *
     * when view a recipe
     * it will check if the recipe contains a forbidden pair
     *
     * @param rwin recipe window
     *
     */
    public void addForbidAction(RecipeWindow rwin) {
    	List<String> forbidInfo = new LinkedList<String>();
    	// forbid pair info
    	List<String> aList = new LinkedList<String>();
        for(int i=0;i<rwin.ingredientText3.size();i++)
        {
        	if(i>rwin.ingredients.size()) break;
        	String currentIngredient = rwin.ingredientText3.get(i).getText();
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

    /**
     * control the actions and display
     * when edit a recipe
     *
     * @param rwin
     *
     *
     */
    public void addEditAction(RecipeWindow rwin)
    {
    	if(rwin.illegal) {
            id = model.assignID();
            System.out.println("asi"+id);
        	}else{
                id = model.getIDbyName(rwin.name);
                System.out.println("all"+rwin.name+" "+id);
        	}
    	//System.out.println(id);
    	// edit the title
    	editTitle(rwin);
    	// delete Button
    	editDeleteButton(rwin);
    	// image
        editImage(rwin);
        // edit main information
        editMainInfo(rwin);
        // edit ingredient
        editIngredient(rwin);
        // edit step
        editStep(rwin);

    }


    /**
     * bind title part with action
     * should be renamed
     * @param rwin
     */
    public void editTitle(RecipeWindow rwin) {
    	rwin.editTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("now"+id);
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
            		String newName=rwin.tf_RecipeName.getText();

            		if(!rwin.illegal) {
            			if(newName.matches("Default")|newName.matches("")) {
            				alertBoxDefault();
            			}
            			else {
                            //System.out.println(newName);
                            if(model.judgeRecipName(newName)||newName.equals(rwin.name)) {
                                rwin.saveData();
                                model.saveRecipName(id, rwin.name);
                                //System.out.println("save : "+id+""+rwin.name);
                                //rwin.name = newName;
                                rwin.markName = !rwin.markName;
                                rwin.refresh();
                                initRecipeWindow(rwin);
                                editStage.setX(posx);
                                editStage.setY(posy);
                            }
                            else
                            {
                                alertBoxDuplicate();
                                rwin.tf_RecipeName.setText(rwin.name);
                                rwin.saveData();
                            }
            			}
            		}else{
            			if(newName.matches("Default")|newName.matches("")) {
            				alertBoxDefault();
            			}else {
            			    rwin.saveData();
	            			//description by default is null, and serving is 4
	                		if(model.judgeRecipName(rwin.name)) {
	                		    rwin.illegal=false;
                                rwin.markName=!rwin.markName;
                                rwin.refresh();
                                editStage.setX(posx);
                                editStage.setY(posy);
                                //System.out.println("haha"+id);
                                //System.out.println(newName);
                                //System.out.println(id);
                                Recipe recipe = new Recipe(rwin.name,"",4);
                                System.out.println("save : "+id+""+rwin.name);
                                model.addRecipetoDatabase(recipe, id);
                                model.saveImagePath(id , "");
                                initRecipeWindow(rwin);
                            }
	                		else alertBoxDuplicate();
            			}
                	}
            	}
            }
        });
	}

    /**
     * bind delete button action
     * @param rwin
     *
     */
    public void editDeleteButton(RecipeWindow rwin) {
    	rwin.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rwin.illegal) {
                    alertBoxFirst();
                    return;
                }
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
	}

    /**
     *
     *
     * first check the title and make sure title valid
     * then bind image part with action
     * choose the image you want
     * add it to the recipe
     * if not in a valid image format
     * then not flush
     *
     * @param rwin
     */
    public void editImage(RecipeWindow rwin) {
        //System.out.println("sss");
    	rwin.title.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(rwin.illegal) {
                    alertBoxFirst();
                    return;
                }
            	JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG or PNG file", "jpg", "jpeg","png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.print(rwin.imgPath);
					if(rwin.name != "Default") {
						rwin.editImgPath = chooser.getSelectedFile().getPath();
						if(imgValid(rwin.editImgPath)) {
						    rwin.imgPath=rwin.editImgPath;
						    System.out.println(rwin.imgPath);
							model.saveImagePath(id, rwin.editImgPath);
						}
						else {
							alertImgPathNotValid();
							if(rwin.imgPath!="img\\addImage.png") {
								rwin.editImgPath = rwin.imgPath;		//for edit,if there exists a image
																	// using the current image
							}
							else {
							    rwin.editImgPath = "";					// for add, if there does not exist a image
							}
							//System.out.println(rwin.editImgPath);
						}
					}else {
						alertBoxDefault();
					}
                // if not invalid image path, then flush
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
    }


	/**
	 *
	 *
	 * bind main information part with action
	 * with different illegal input warning
	 * @param rwin
	 */
	public void editMainInfo(RecipeWindow rwin) {
    	rwin.editDescription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rwin.illegal) {
                    alertBoxFirst();
                    return;
                }
            	if(rwin.markDescription == false) {
	                double posx=editStage.getX();
	                double posy=editStage.getY();

	                rwin.markDescription=!rwin.markDescription;
	                rwin.refresh();
	                initRecipeWindow(rwin);
	                editStage.setX(posx);;
	                editStage.setY(posy);
            	}else {
            	    rwin.saveData();
            		if(isInteger(rwin.tf_Serveing.getText()) && Integer.parseInt(rwin.tf_Serveing.getText()) <= 0) {

            				alertWrongType_SB.append("Servings must be a positive integer!\n");

            			if(!isDouble(rwin.tf_Cookingtime.getText())|!isInteger(rwin.tf_Preparation.getText())) {
	            			if(!isInteger(rwin.tf_Preparation.getText())) {
	            				alertWrongType_SB.append("Wrong type for preparation time!\n");
	            			}
	            			if(!isInteger(rwin.tf_Cookingtime.getText())) {
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
	            		}else if(!isInteger(rwin.tf_Cookingtime.getText())|!isInteger(rwin.tf_Preparation.getText())|!isInteger(rwin.tf_Serveing.getText())) {
	            			if(!isInteger(rwin.tf_Preparation.getText())) {
	            				alertWrongType_SB.append("Wrong type for preparation time!\n");
	            			}
	            			if(!isInteger(rwin.tf_Cookingtime.getText())) {
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
    }

    /**
     * bind action to the ingredients
     * @param rwin
     *
     *
     */
    public void editIngredient(RecipeWindow rwin) {
    	rwin.editIngredient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rwin.illegal) {
                    alertBoxFirst();
                    return;
                }
                double posx=editStage.getX();
                double posy=editStage.getY();
                if(rwin.markIngredient == false) {
                	System.out.println("bbb");
                    rwin.markIngredient=!rwin.markIngredient;
	                rwin.refresh();
	                initRecipeWindow(rwin);
                }
                else 
                {
                	System.out.println("aaa");
                	LinkedList<Ingredient> temp = new LinkedList<Ingredient>();
                	for(int i=0;i<rwin.ingredients.size();i++)
                	{
                		temp.add(new Ingredient(rwin.ingredients.get(i)));
                	}
                	rwin.saveData();
                	try {
 						model.addIngredientstoDatabase(rwin.ingredients, id);
 					} catch (SQLException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					}
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
                	if(alertForbid == 1|alertIngredient == 1){
                		rwin.ingredients=temp;
                    	alertForbid = 0;
                    	alertIngredient = 0;
                    	rwin.refresh();
    	                initRecipeWindow(rwin);
    	                editStage.setX(posx);;
    	                editStage.setY(posy);
                    }
                	else
                	{
                		rwin.markIngredient=!rwin.markIngredient;
                		System.out.println("asd");
                		rwin.saveData();
                		rwin.refresh();
	 	                initRecipeWindow(rwin);
	 	                editStage.setX(posx);
	 	                editStage.setY(posy);
                	}
                }
            }
        });
    }

    /**
     * bind action to step part
     * @param rwin
     *
     */
    public void editStep(RecipeWindow rwin) {
    	rwin.editStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rwin.illegal) {
                    alertBoxFirst();
                    return;
                }
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




    /**
     * bind action to add recipe buttton on main window
     * initialization for the recipe window
     * @param recipe
     * @param main
     *

     *
     */
    public void addAddAction(List<Recipe> recipe, MainWindow main) {
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
		            	recipeWindow.markName = true;
		            	recipeWindow.markImage = false;
		            	recipeWindow.markStep = false;
		            	recipeWindow.servings=2;
		            	recipeWindow.favorite=false;
		            	recipeWindow.markIngredient = false;
		            	recipeWindow.illegal=true;
                        recipeWindow.ingredientText1= new LinkedList<TextField>();
                        recipeWindow.ingredientText2= new LinkedList<TextField>();
                        recipeWindow.ingredientText3= new LinkedList<TextField>();
                         recipeWindow.stepText= new LinkedList<TextField>();
						primaryStage.close();
						editStage.show();
						recipeWindow.editImgPath = null;
						recipeWindow.refresh();
						initRecipeWindow(recipeWindow);
                     }

                 });
    }
    /**
     * initialize the forbidden window
     * @param forw
     * @param forbiddenStage
     * @param model
     *
     *
     *
     */
    public void initEditForbidden(ForbiddenEditWindow forw,Stage forbiddenStage,DBController model)
    {
        LinkedList<ForbiddenPair> data;
        data=model.getAllForbiddenPair();
        EditForbiddenPairController editFor = new EditForbiddenPairController();
        forw.data=data;
        forw.refresh();
        editFor.addForbiddenEvent(forw,forbiddenStage,model);
    }

    /**
     * launch the PixivCookbook here
     * @param args
     *
     */
    public static void main(String[] args) {
        launch(args);
    }
}
