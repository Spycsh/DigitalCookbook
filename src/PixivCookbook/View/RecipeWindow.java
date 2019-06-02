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
        for(int i=0;i<10;i++)
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
        nameLabel.setLayoutX(450);
        nameLabel.setLayoutY(posy+=lineheight);
        posy+=lineheight;
        nameLabel.getStyleClass().add("bigtitle");
        pane.getChildren().add(nameLabel);
        Button share = new Button("s");
        share.setLayoutX(900);
        share.setLayoutY(posy+=lineheight);
        Button star = new Button("s");
        star.setLayoutX(980);
        star.setLayoutY(posy);
        Button delete = new Button("d");
        delete.setLayoutX(1060);
        delete.setLayoutY(posy);
        star.getStyleClass().add("star");
        star.setMinSize(48,48);
        delete.getStyleClass().add("delete");
        delete.setMinSize(48,48);
        share.getStyleClass().add("share");
        share.setMinSize(48,48);
        pane.getChildren().add(share);
        pane.getChildren().add(star);
        pane.getChildren().add(delete);
        posy+=20;

        Image image = new Image("file:D:\\Work\\CookbookGui\\image\\ff.png");
        ImageView title = new ImageView(image);
        title.setFitHeight(180*2);
        title.setFitWidth(320*2);
        title.setLayoutX(center);
        title.setLayoutY(posy+=lineheight);
        pane.getChildren().add(title);
        posy+=330;

        Label descripLabel = new Label();
        descripLabel.setText("Description");
        descripLabel.setLayoutX(center);
        descripLabel.setLayoutY(posy+=lineheight);
        Button editDescription = new Button("");
        editDescription.setLayoutX(center+144);
        editDescription.setLayoutY(posy);
        editDescription.getStyleClass().add("edit");
        editDescription.setMinSize(36,36);
        editDescription.setMaxSize(36,36);
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
        descrip.getStyleClass().add("content");
        pane.getChildren().add(descrip);

        Label ingreLabel = new Label();
        ingreLabel.setText("Ingredient");
        ingreLabel.setLayoutX(center);
        ingreLabel.setLayoutY(posy+=lineheight);
        Button editIngredient = new Button("e");
        editIngredient.setLayoutX(center+144);
        editIngredient.setLayoutY(posy);
        editIngredient.getStyleClass().add("edit");
        editIngredient.setMinSize(36,36);
        editIngredient.setMaxSize(36,36);
        pane.getChildren().add(editIngredient);
        pane.getChildren().add(ingreLabel);
        for(int i=0;i<ingredient.size();i++)
        {
            if(ingredient.get(i)==null) continue;
            Label label = new Label();
            label.setText(ingredient.get(i));
            label.setLayoutX(center+20);
            label.setLayoutY(posy+=lineheight);
            label.getStyleClass().add("content");
            pane.getChildren().add(label);
        }

        Label stepLabel = new Label();
        stepLabel.setText("Step");
        stepLabel.setLayoutX(center);
        stepLabel.setLayoutY(posy+=lineheight);
        Button editStep = new Button("e");
        editStep.setLayoutX(center+144);
        editStep.setLayoutY(posy);
        editStep.getStyleClass().add("edit");
        editStep.setMinSize(36,36);
        editStep.setMaxSize(36,36);
        pane.getChildren().add(editStep);
        pane.getChildren().add(stepLabel);
        for(int i=0;i<step.size();i++)
        {
            if(step.get(i)==null) continue;
            Label label = new Label();
            label.setText(step.get(i));
            label.setLayoutX(center+20);
            label.setLayoutY(posy+=lineheight);
            label.getStyleClass().add("content");
            int haha=0;
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
