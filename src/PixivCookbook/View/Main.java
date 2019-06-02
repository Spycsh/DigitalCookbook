package PixivCookbook.View;
import java.util.List;

import com.sun.javafx.binding.SelectBinding.AsDouble;

import PixivCookbook.Recipe;
import PixivCookbook.Controller.DBController;
import PixivCookbook.Model.SQL_test;

//import com.sun.corba.se.pept.transport.EventHandler;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
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
	
	private SQL_test model = new SQL_test();
	
	
	
    String imageAdd[] ={"","","","","","",""};
    String name[] = {"","","","","","",""};
    ImageView images[]= new ImageView[8];
    Label labels[] = new Label[8];
    ImageView addView= new ImageView();
    Label addLabel=new Label();
    private Button recommendButton =new Button();



	private Pane pane;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
    	model.run();
        int posx=100,posy=200;
        pane = new Pane();
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene=new Scene(pane, 1400, 900);
        TextField search = new TextField();
        //search.setMaxSize(100,10);
        
        //relative path
        Image image = new Image(ClassLoader.getSystemResource("")+"..\\img\\title2.png");
        ImageView title = new ImageView(image);
        title.setFitHeight(120);
        title.setFitWidth(600);
        title.setLayoutX(400);
        title.setLayoutY(0);
        pane.getChildren().add(title);
        Button searchButton = new Button();
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
        
        addRecommandButtonAction();
               
        pane.getChildren().add(recommendButton);
        //System.out.println(root.getChildrenUnmodifiable());
        
        // initialize the mainpage pictures
        setMainPage();
        
        for(int i=0;i<imageAdd.length;i++)
        {
        	System.out.println(ClassLoader.getSystemResource("")+"../"+this.imageAdd[i]);
            image = new Image(ClassLoader.getSystemResource("")+"../"+this.imageAdd[i]);
            ImageView temp = new ImageView(image);
            temp.setFitHeight(200);
            temp.setFitWidth(200);
            temp.setLayoutX(i%4*300+posx);
            temp.setLayoutY(i/4*350+posy);
            temp.getStyleClass().add("pointer");
            images[i]=temp;
            pane.getChildren().add(temp);
            System.out.println(this.name[i]);
            
            Label label = new Label(name[i]);  // 注意imageAdd和name数组大小必须相同
          

            label.setId("img");
            label.setLayoutX(i%4*300+posx);
            label.setLayoutY(i/4*350+200+posy);
            label.getStyleClass().add("flower");
            label.getStyleClass().add("pointer");
            labels[i]=label;
            pane.getChildren().add(label);
        }
        image = new Image(ClassLoader.getSystemResource("")+"..\\img\\add.png");
        ImageView temp = new ImageView(image);
        temp.setFitHeight(150);
        temp.setFitWidth(150);
        temp.setLayoutX(imageAdd.length%4*300+posx+25);
        temp.setLayoutY(imageAdd.length/4*350+posy+25);
        temp.getStyleClass().add("pointer");
        pane.getChildren().add(temp);
        Label label = new Label("NEW RECIPE");
        label.setLayoutX(imageAdd.length%4*300+posx+30);
        label.setLayoutY(imageAdd.length/4*350+200+posy);
        label.getStyleClass().add("pointer");
        addView=temp;
        addLabel=label;
        pane.getChildren().add(label);
        scene.getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void reload() {
    	for(int i=0;i<imageAdd.length;i++) {
            pane.getChildren().remove(images[i]);
            pane.getChildren().remove(labels[i]);
            pane.getChildren().remove(addView);
            pane.getChildren().remove(addLabel);
        }
        Image image;
        int posx=100,posy=200;
        for(int i=0;i<imageAdd.length;i++)
        {
        	image = new Image(ClassLoader.getSystemResource("")+"../"+this.imageAdd[i]);
            ImageView temp = new ImageView(image);
            images[i]=temp;
            images[i].setFitHeight(200);
            images[i].setFitWidth(200);
            images[i].setLayoutX(i%4*300+posx);
            images[i].setLayoutY(i/4*350+posy);
            images[i].getStyleClass().add("pointer");
            pane.getChildren().add(images[i]);
            Label label = new Label(name[i]);
            label.setId("img");
            label.setLayoutX(i%4*300+posx);
            label.setLayoutY(i/4*350+200+posy);
            label.getStyleClass().add("flower");
            label.getStyleClass().add("pointer");
            labels[i]=label;
            pane.getChildren().add(label);
        }
        image = new Image(ClassLoader.getSystemResource("")+"..\\img\\add.png");
        ImageView temp = new ImageView(image);
        temp.setFitHeight(150);
        temp.setFitWidth(150);
        temp.setLayoutX(imageAdd.length%4*300+posx+25);
        temp.setLayoutY(imageAdd.length/4*350+posy+25);
        temp.getStyleClass().add("pointer");
        pane.getChildren().add(temp);
        Label label = new Label("NEW RECIPE");
        label.setLayoutX(imageAdd.length%4*300+posx+30);
        label.setLayoutY(imageAdd.length/4*350+200+posy);
        label.getStyleClass().add("pointer");
        pane.getChildren().add(label);
//        scene.getStylesheets().add(Main.class.getResource("index.css").toExternalForm());

    }
    
//	public void createMainPage() {
//			
//		}
    public Button getRecommendButton() {
		return recommendButton;
	}

	public void setRecommendButton(Button recommendButton) {
		this.recommendButton = recommendButton;
	}
	
	// for MainPage Pictures and names
	public void setMainPage() {
		int i=0;
		List<Recipe> alist = this.model.getRecipesForMainPage();
		for(Recipe r:alist) {
			this.name[i] = r.getRecipeName();
			this.imageAdd[i]= r.getImgAddress();
			i=i+1;
		}
	}
	
	
	// action of flush button for MainPage Pictures and names
	public void addRecommandButtonAction() {
		List<Recipe> aa = this.model.getRecipesForMainPage();
		this.getRecommendButton().addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() { 
			        @Override 
			        public void handle(MouseEvent e) { 
			           System.out.println("flushing..."+aa.toString());
			           flushMainPage(aa);
			           reload();
			        }
			    });
	}
	
	public void flushMainPage(List<Recipe> aa) {
		int i = 0;
		for(Recipe r:aa) {
     	    System.out.println(r.getRecipeName()+":"+r.getImgAddress());
			this.name[i] = r.getRecipeName();
			this.imageAdd[i] = r.getImgAddress();
			i=i+1;
		}
	}
	
	public static void main(String[] args) {
        launch(args);
    }


}
