package PixivCookbook.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    String imageAdd[] ={"asdf","asdf","asfd","a","a","s","s"};
    String name[] = {"asd","asdf","asdf","a","a","s","s"};
    ImageView images[];
    @Override
    public void start(Stage primaryStage) throws Exception{
        int posx=100,posy=200;
        Pane pane = new Pane();
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene=new Scene(pane, 1400, 900);
        TextField search = new TextField();
        //search.setMaxSize(100,10);
        Image image = new Image("file:D:\\Work\\CookbookGui\\image\\title2.png");
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
        Button recommendButton = new Button();
        recommendButton.setText("C");
        recommendButton.setLayoutX(1000);
        recommendButton.setLayoutY(150);
        recommendButton.setMinSize(20,20);
        recommendButton.getStyleClass().add("border");
        pane.getChildren().add(recommendButton);
        //System.out.println(root.getChildrenUnmodifiable());
        for(int i=0;i<imageAdd.length;i++)
        {
            image = new Image("file:D:\\Work\\CookbookGui\\image\\ff.jpg");
            ImageView temp = new ImageView(image);
            temp.setFitHeight(200);
            temp.setFitWidth(200);
            temp.setLayoutX(i%4*300+posx);
            temp.setLayoutY(i/4*350+posy);
            temp.getStyleClass().add("pointer");
            pane.getChildren().add(temp);
            Label label = new Label("asd");
            label.setId("img");
            label.setLayoutX(i%4*300+posx);
            label.setLayoutY(i/4*350+200+posy);
            label.getStyleClass().add("flower");
            label.getStyleClass().add("pointer");
            pane.getChildren().add(label);
        }
        image = new Image("file:D:\\Work\\CookbookGui\\image\\add.png");
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
        scene.getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
