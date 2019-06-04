package PixivCookbook.View;

import PixivCookbook.Recipe;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

//import com.sun.corba.se.pept.transport.EventHandler;

public class Main{

    public ImageView temp[],title;
    public Pane pane = new Pane();
    Image image;
    Scene scene;
    Label label[];
    public TextField search;
    Stage primaryStage;
    public Button addButton = new Button();
    public Button recommendButton =new Button();
    public Button searchButton = new Button();
    public Button backButton = new Button();
    
    public Main()
    {

    }
    public Scene getScene(){
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        //this.primaryStage.setTitle("Hello World");
        scene=new Scene(pane, 1400, 900);
        initializeOtherParts();
        scene.getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
        return scene;
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
            //System.out.println(recipe.get(i).getImgAddress());
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
    }
    
    public void clearAll() {
    	pane.getChildren().clear();
    }

    public void dropAllImageviews() {
        for(int i = 0; i<temp.length;i++) {
            pane.getChildren().remove(temp[i]);
            pane.getChildren().remove(label[i]);
        }
    }
	
	 public ImageView[] getTemp() {
			return temp;
		}
		public void setTemp(ImageView[] temp) {
			this.temp = temp;
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
}
