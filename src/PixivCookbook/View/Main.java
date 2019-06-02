package PixivCookbook.View;

import java.util.List;

import PixivCookbook.Recipe;
import PixivCookbook.Model.SQL_test;

//import com.sun.corba.se.pept.transport.EventHandler;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent; 

public class Main extends Application {

	SQL_test model = new SQL_test();
    ImageView temp[],title;
    Pane pane;
    Image image;
    Scene scene;
    Label label[];
    TextField search;
    Stage primaryStage;
    
    private Button recommendButton =new Button();
    private Button searchButton = new Button();
    
    
    public void start(Stage primaryStage) throws Exception{
    	this.model.run();
    	this.primaryStage =primaryStage;
    	pane = new Pane();
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        this.primaryStage.setTitle("Hello World");
        scene=new Scene(pane, 1400, 900);
        List<Recipe> recipe = this.model.getRecipesForMainPage();   
        addRecommandButtonAction(recipe);
        addSearchButtonAction();
        initializeOtherParts();
        initializeMainPage(recipe);
    }
    
    public void initializeOtherParts() {
    	search = new TextField();
        //search.setMaxSize(100,10);
        
        //relative path
        image = new Image(ClassLoader.getSystemResource("")+"..\\img\\title2.png");
        title = new ImageView(image);
        title.setFitHeight(120);
        title.setFitWidth(600);
        title.setLayoutX(400);
        title.setLayoutY(0);
        pane.getChildren().add(title);
        searchButton.setText("sa");
        searchButton.setLayoutX(400);
        searchButton.setLayoutY(150);
        search.setLayoutX(100);
        search.setLayoutY(150);
        searchButton.getStyleClass().add("search");
        pane.getChildren().add(search);
        pane.getChildren().add(searchButton);
//        Button recommendButton = new Button();
        recommendButton.setText("C");
        recommendButton.setLayoutX(1000);
        recommendButton.setLayoutY(150);
        recommendButton.setMinSize(20,20);
        recommendButton.getStyleClass().add("border");      
        pane.getChildren().add(recommendButton);
        //System.out.println(root.getChildrenUnmodifiable());   	
    }
    
    public void initializeMainPage(List<Recipe> recipe) { 

    	int posx=100,posy=200;
        temp = new ImageView[recipe.size()+1];
        label = new Label[recipe.size()+1];
        for(int i=0;i<recipe.size();i++)
        {
            image = new Image(ClassLoader.getSystemResource("")+"..\\"+recipe.get(i).getImgAddress());
            temp[i] = new ImageView(image);
            temp[i].setFitHeight(200);
            temp[i].setFitWidth(200);
            temp[i].setLayoutX(i%4*300+posx);
            temp[i].setLayoutY(i/4*350+posy);
            temp[i].getStyleClass().add("pointer");
            pane.getChildren().add(temp[i]);
            label[i] = new Label(recipe.get(i).getRecipeName());
            label[i].setId("img");
            label[i].setLayoutX(i%4*300+posx);
            label[i].setLayoutY(i/4*350+200+posy);
            label[i].getStyleClass().add("flower");
            label[i].getStyleClass().add("pointer");
            pane.getChildren().add(label[i]);
        }
        image = new Image(ClassLoader.getSystemResource("")+"..\\img\\add.png");
        temp[recipe.size()] = new ImageView(image);
        temp[recipe.size()].setFitHeight(150);
        temp[recipe.size()].setFitWidth(150);
        temp[recipe.size()].setLayoutX(recipe.size()%4*300+posx+25);
        temp[recipe.size()].setLayoutY(recipe.size()/4*350+posy+25);
        temp[recipe.size()].getStyleClass().add("pointer");
        pane.getChildren().add(temp[recipe.size()]);
        label[recipe.size()] = new Label("NEW RECIPE");
        label[recipe.size()].setLayoutX(recipe.size()%4*300+posx+30);
        label[recipe.size()].setLayoutY(recipe.size()/4*350+200+posy);
        label[recipe.size()].getStyleClass().add("pointer");
        pane.getChildren().add(label[recipe.size()]);
        scene.getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        }

    public void dropAllImageviews() {
    	for(int i = 0; i<temp.length;i++) {
    	pane.getChildren().remove(temp[i]);
    	pane.getChildren().remove(label[i]);
    	}
    }
    
    
    
    public Button getRecommendButton() {
		return recommendButton;
	}
    
    public Button getSearchButton() {
    	return searchButton;
    }
    public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public void setRecommendButton(Button recommendButton) {
		this.recommendButton = recommendButton;
	}
	
	public void addRecommandButtonAction(List<Recipe> recipe) {
		this.getRecommendButton().addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() { 
			        @Override 
			        public void handle(MouseEvent e) { 
			        	dropAllImageviews();
			        	initializeMainPage(recipe);
			        }
			    });
	}
	
	public void addSearchButtonAction() {
		this.getSearchButton().addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() { 
			        @Override 
			        public void handle(MouseEvent e) { 
			        	Button backButton = new Button();
			        	backButton.setText("back");
			        	backButton.setLayoutX(600);
			        	backButton.setLayoutY(150);
			        	backButton.setMinSize(20,20);
			        	pane.getChildren().add(backButton);
			        	addBackButtonAction(backButton);
			        	String searchName = search.getText();
			        	dropAllImageviews();
			        	List<Recipe> result = model.searchAllMatchedRecipes(searchName);
			        	initializeMainPage(result);
			        }
			    });
	}
	
	public void addBackButtonAction(Button backButton) {
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() { 
			        @Override 
			        public void handle(MouseEvent e) { 
			        	dropAllImageviews();
			        	List<Recipe> recipe = model.getRecipesForMainPage();
			        	pane.getChildren().remove(backButton);
			        	initializeMainPage(recipe);
			        }
			    });
	}

	public static void main(String[] args) {
        launch(args);
    }


}
