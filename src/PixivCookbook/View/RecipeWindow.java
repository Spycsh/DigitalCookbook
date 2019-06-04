package PixivCookbook.View;

import PixivCookbook.Ingredient;
import PixivCookbook.Step;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

public class RecipeWindow{
    boolean markDescription=false;
    boolean markIngredient=false;
    boolean markImage=false;
    boolean markStep=false;
    boolean markName=false;
    Pane pane;
    ScrollPane spane;
    public String name;
    public String description ;
    public String imgPath;

    public LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
    public LinkedList<Step> step = new LinkedList<Step>();
    int posy=0;
    int lineheight=50;
    int center=550-160;
    public RecipeWindow()
    {

    }

    public Scene getScene(){
        spane = new ScrollPane();
        pane = new Pane();
        pane.setMinWidth(1375);
        pane.getStyleClass().add("root");
        //primaryStage.setTitle("Hello World");
        spane.setContent(pane);
        Scene scene=new Scene(spane, 1400, 900);
        refresh();
        //System.out.println(root.getChildrenUnmodifiable());
        //label.getStyleClass().add("pointer");
        scene.getStylesheets().add(Main.class.getResource("index.css").toExternalForm());
        //primaryStage.setScene(scene);
        //primaryStage.show();
        return scene;
    }

    public void refresh()
    {
        posy=0;
        lineheight=50;
        center=550-160;
        pane.getChildren().clear();
        Label nameLabel = new Label();
        nameLabel.setText(name);
        nameLabel.getStyleClass().add("bigtitle");
        //pane.getChildren().add(nameLabel);
        if(markName)
        {
            TextField tf = new TextField(name);
            tf.setLayoutX(500);
            tf.setLayoutY(posy+=lineheight);
            posy+=lineheight/2;
            tf.setMinSize(400,0);
            pane.getChildren().add(tf);
            Button editTitle= new Button("");
            editTitle.setMinSize(36,36);
            editTitle.setMaxSize(36,36);
            editTitle.setLayoutX(916);
            editTitle.setLayoutY(posy-14);
            editTitle.getStyleClass().add("save");
            pane.getChildren().add(editTitle);
        }
        else
        {
            nameLabel.setLayoutX(500);
            nameLabel.setLayoutY(posy+=lineheight);
            posy+=lineheight/2;
            pane.getChildren().add(nameLabel);
            Button editTitle= new Button("");
            editTitle.setMinSize(36,36);
            editTitle.setMaxSize(36,36);
            editTitle.setLayoutX(916);
            editTitle.setLayoutY(posy-14);
            editTitle.getStyleClass().add("edit");
            pane.getChildren().add(editTitle);
        }
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
        star.setMinSize(30,30);
        star.setMaxSize(30,30);
        delete.getStyleClass().add("delete");
        delete.setMinSize(30,30);
        delete.setMaxSize(30,30);
        share.getStyleClass().add("share");
        share.setMinSize(30,30);
        share.setMaxSize(30,30);
        pane.getChildren().add(share);
        pane.getChildren().add(star);
        pane.getChildren().add(delete);
        posy+=20;

        if(markImage)
        {
        	Image image = new Image(ClassLoader.getSystemResource("")+"..\\img\\addimage.png");
            ImageView title = new ImageView(image);
            title.setFitHeight(180*2);
            title.setFitWidth(320*2);
            title.setLayoutX(center);
            title.setLayoutY(posy+=lineheight);
            pane.getChildren().add(title);
            posy+=330;
        }
        else
        {
        	Image image = new Image(ClassLoader.getSystemResource("")+"..\\"+imgPath);
            ImageView title = new ImageView(image);
            title.setFitHeight(180*2);
            title.setFitWidth(320*2);
            title.setLayoutX(center);
            title.setLayoutY(posy+=lineheight);
            pane.getChildren().add(title);
            posy+=330;
        }
//mark
        Label descripLabel = new Label();
        descripLabel.setText("Description");
        descripLabel.setLayoutX(center);
        descripLabel.setLayoutY(posy+=lineheight);
        Button editDescription = new Button("");
        editDescription.setLayoutX(center+144);
        editDescription.setLayoutY(posy);
        pane.getChildren().add(descripLabel);
        int line=0;
        String disDes="";
        for(int i=0;i<description.length();i++)
        {
            if(i%60==0)
            {
                disDes +=(description.substring(i,Math.min(description.length(),i+60))+'\n');
                i++;
                line++;
            }
        }
        if(markDescription)
        {
            editDescription.getStyleClass().add("save");
            editDescription.setMinSize(36,36);
            editDescription.setMaxSize(36,36);
            pane.getChildren().add(editDescription);
            TextArea tf = new TextArea(disDes);
            tf.setLayoutX(center);
            tf.setLayoutY(posy+=lineheight);
            posy+=140;
            tf.setMaxSize(900,140);
            pane.getChildren().add(tf);
        }
        else
        {
            editDescription.getStyleClass().add("edit");
            editDescription.setMinSize(36,36);
            editDescription.setMaxSize(36,36);
            pane.getChildren().add(editDescription);
            Label descrip = new Label();
            descrip.setText(disDes);
            descrip.setLayoutX(center);
            descrip.setLayoutY(posy+=lineheight);
            posy+=lineheight*(line-1);
            descrip.getStyleClass().add("content");
            pane.getChildren().add(descrip);
        }

        Label ingreLabel = new Label();
        ingreLabel.setText("Ingredient");
        ingreLabel.setLayoutX(center);
        ingreLabel.setLayoutY(posy+=lineheight);
        Button editIngredient = new Button("e");
        editIngredient.setLayoutX(center+144);
        editIngredient.setLayoutY(posy);
        editIngredient.setMinSize(36,36);
        editIngredient.setMaxSize(36,36);
        pane.getChildren().add(ingreLabel);
        if(markIngredient)
        {
            editIngredient.getStyleClass().add("save");
            pane.getChildren().add(editIngredient);
            Label numLabel = new Label("Number");
            Label unitLabel = new Label("Unit");
            Label ingrenameLabel = new Label("Name");
            numLabel.setLayoutX(center+20);
            numLabel.setLayoutY(posy+=lineheight);
            numLabel.getStyleClass().add("content");
            pane.getChildren().add(numLabel);
            unitLabel.setLayoutX(center+190);
            unitLabel.setLayoutY(posy);
            unitLabel.getStyleClass().add("content");
            pane.getChildren().add(unitLabel);
            ingrenameLabel.setLayoutX(center+360);
            ingrenameLabel.setLayoutY(posy);
            ingrenameLabel.getStyleClass().add("content");
            pane.getChildren().add(ingrenameLabel);
            int cnt=0;
            for(int i=0;i<ingredients.size();i++)
            {
                if(ingredients.get(i)==null) continue;
                cnt++;
                Label label = new Label();
                label.setText(Integer.toString(cnt));
                label.setLayoutX(center-10);
                posy+=lineheight;
                label.setLayoutY(posy+8);
                label.getStyleClass().add("content");
                pane.getChildren().add(label);
                TextField tf1 = new TextField(ingredients.get(i).getName().toString());
                tf1.setLayoutX(center+20);
                tf1.setLayoutY(posy);
                tf1.setMaxSize(150,200);
                tf1.getStyleClass().add("content");
                pane.getChildren().add(tf1);
                TextField tf2 = new TextField(ingredients.get(i).getUnit());
                tf2.setLayoutX(center+190);
                tf2.setLayoutY(posy);
                tf2.setMaxSize(150,200);
                tf2.getStyleClass().add("content");
                pane.getChildren().add(tf2);
                TextField tf3 = new TextField(ingredients.get(i).getName());
                tf3.setLayoutX(center+360);
                tf3.setLayoutY(posy);
                tf3.setMaxSize(150,200);
                tf3.getStyleClass().add("content");
                pane.getChildren().add(tf3);
                Button button = new Button();
                button.setLayoutX(center+530);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("addbutton");
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+570);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("deletebutton");
                pane.getChildren().add(button);
            }
        }
        else
        {
            editIngredient.getStyleClass().add("edit");
            pane.getChildren().add(editIngredient);
            for(int i=0;i<ingredients.size();i++)
            {
                if(ingredients.get(i)==null) continue;
                Label label = new Label();
                label.setText(ingredients.get(i).toString());
                label.setLayoutX(center+20);
                label.setLayoutY(posy+=lineheight);
                label.getStyleClass().add("content");
                pane.getChildren().add(label);
            }
        }

        Label stepLabel = new Label();
        stepLabel.setText("Step");
        stepLabel.setLayoutX(center);
        stepLabel.setLayoutY(posy+=lineheight);
        Button editStep = new Button("e");
        editStep.setLayoutX(center+144);
        editStep.setLayoutY(posy);
        editStep.setMinSize(36,36);
        editStep.setMaxSize(36,36);
        pane.getChildren().add(stepLabel);
        if(markStep)
        {
            editStep.getStyleClass().add("save");
            pane.getChildren().add(editStep);
            int cnt=0;
            for(int i=0;i<step.size();i++)
            {
                if(step.get(i)==null) continue;
                cnt++;
                Label label = new Label();
                label.setText(Integer.toString(cnt));
                label.setLayoutX(center-10);
                posy+=lineheight;
                label.setLayoutY(posy+8);
                label.getStyleClass().add("content");
                pane.getChildren().add(label);
                TextField tf = new TextField(step.get(i).getContent());
                tf.setLayoutX(center+20);
                tf.setLayoutY(posy);
                tf.getStyleClass().add("content");
                pane.getChildren().add(tf);
                Button button = new Button();
                button.setLayoutX(center+300);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("addbutton");
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+340);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("deletebutton");
                pane.getChildren().add(button);
            }
        }
        else
        {
            editStep.getStyleClass().add("edit");
            pane.getChildren().add(editStep);
            for(int i=0;i<step.size();i++)
            {
                if(step.get(i)==null) continue;
                Label label = new Label();
                label.setText(step.get(i).getContent());
                label.setLayoutX(center+20);
                label.setLayoutY(posy+=lineheight);
                label.getStyleClass().add("content");
                int haha=0;
                pane.getChildren().add(label);
            }
        }
        star.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                markDescription=!markDescription;
                markStep=!markStep;
                markName=!markName;
                markImage=!markImage;
                markIngredient=!markIngredient;
                refresh();
            }
        });
    }
}
