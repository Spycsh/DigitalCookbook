package PixivCookbook.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.LinkedList;

public class RecipeWindow extends Application {
    String name="Yang wang xin kong";
    String description="This is a good dish This is a good dish Tis is a good dish This is a good dish This is a good dish This is a good dish This is a good dish This is a good dish This is a good dish This is a good dish ";
    int number=5;
    LinkedList<String> ingredient = new LinkedList<String>();
    LinkedList<String> step = new LinkedList<String>();
    int posy=0;
    int lineheight=50;
    int center=550-160;
    @Override
    public void start(Stage primaryStage) throws Exception{
        for(int i=0;i<0;i++)
            ingredient.add("mian fen 1 gram");
        ingredient.add("yu");
        step.add("sha yu");
        step.add("hong pei");
        ScrollPane spane = new ScrollPane();
        Pane pane = new Pane();
        pane.setMinWidth(1375);
        pane.getStyleClass().add("root");
        primaryStage.setTitle("Hello World");
        spane.setContent(pane);
        Scene scene=new Scene(spane, 1400, 900);

        Label nameLabel = new Label();
        nameLabel.setText(name);
        nameLabel.setLayoutX(600);
        nameLabel.setLayoutY(posy+=lineheight);
        pane.getChildren().add(nameLabel);
        Button share = new Button("share");
        share.setLayoutX(900);
        share.setLayoutY(posy+=lineheight);
        Button star = new Button("star");
        star.setLayoutX(1050);
        star.setLayoutY(posy);
        Button delete = new Button("delete");
        delete.setLayoutX(1200);
        delete.setLayoutY(posy);
        pane.getChildren().add(share);
        pane.getChildren().add(star);
        pane.getChildren().add(delete);

        Image image = new Image("file:D:\\Work\\CookbookGui\\image\\ff.png");
        ImageView title = new ImageView(image);
        title.setFitHeight(180*2);
        title.setFitWidth(320*2);
        title.setLayoutX(center);
        title.setLayoutY(posy+=lineheight);
        pane.getChildren().add(title);
        posy+=310;

        Label descripLabel = new Label();
        descripLabel.setText("Description");
        descripLabel.setLayoutX(center);
        descripLabel.setLayoutY(posy+=lineheight);
        Button editDescription = new Button("edit");
        editDescription.setLayoutX(center+150);
        editDescription.setLayoutY(posy);
        pane.getChildren().add(editDescription);
        pane.getChildren().add(descripLabel);
        Label descrip = new Label();
        int line=0;
        for(int i=0;i<description.length();i++)
        {
            if(i%60==0&i>=60)
            {
                description = description.substring(0, i + 1) + "\n" + description.substring(i + 1, description.length());
                i++;
                line++;
            }
        }
        descrip.setText(description);
        descrip.setLayoutX(center);
        descrip.setLayoutY(posy+=lineheight);
        posy+=lineheight*(line-1);
        pane.getChildren().add(descrip);

        Label ingreLabel = new Label();
        ingreLabel.setText("Ingredient");
        ingreLabel.setLayoutX(center);
        ingreLabel.setLayoutY(posy+=lineheight);
        Button editIngredient = new Button("edit");
        editIngredient.setLayoutX(center+150);
        editIngredient.setLayoutY(posy);
        pane.getChildren().add(editIngredient);
        pane.getChildren().add(ingreLabel);
        for(int i=0;i<ingredient.size();i++)
        {
            if(ingredient.get(i)==null) continue;
            Label label = new Label();
            label.setText(ingredient.get(i));
            label.setLayoutX(center+20);
            label.setLayoutY(posy+=lineheight);
            pane.getChildren().add(label);
        }

        Label stepLabel = new Label();
        stepLabel.setText("Step");
        stepLabel.setLayoutX(center);
        stepLabel.setLayoutY(posy+=lineheight);
        Button editStep = new Button("edit");
        editStep.setLayoutX(center+150);
        editStep.setLayoutY(posy);
        pane.getChildren().add(editStep);
        pane.getChildren().add(stepLabel);
        for(int i=0;i<step.size();i++)
        {
            if(step.get(i)==null) continue;
            Label label = new Label();
            label.setText(step.get(i));
            label.setLayoutX(center+20);
            label.setLayoutY(posy+=lineheight);
            pane.getChildren().add(label);
        }

        //System.out.println(root.getChildrenUnmodifiable());
        //label.getStyleClass().add("pointer");
        scene.getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
