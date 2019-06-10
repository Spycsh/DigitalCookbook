package PixivCookbook.View;

import PixivCookbook.ForbiddenPair;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

public class ForbiddenEditWindow{
    int posy=0;
    int lineheight=50;
    int center=550-160;
    public LinkedList<ForbiddenPair> data = new LinkedList<ForbiddenPair>();
    public Pane pane = new Pane();
    public ScrollPane spane = new ScrollPane();
    public LinkedList<Button> editForbidden = new LinkedList<Button>();
    public LinkedList<Button> addForbidden = new LinkedList<Button>();
    public LinkedList<Button> deleteForbidden = new LinkedList<Button>();
    public LinkedList<TextField> forbidenText1 = new LinkedList<TextField>();
    public LinkedList<TextField> forbidenText2 = new LinkedList<TextField>();
    public LinkedList<Integer> mark= new LinkedList<>();
    public Button home = new Button();

    public Scene getScene(){
        pane.setMinWidth(1375);
        pane.getStyleClass().add("root");
        //primaryStage.setTitle("Hello World");
        //System.out.println();
        spane = new ScrollPane();
        spane.setMinHeight(900);
        spane.setContent(pane);
        Scene scene=new Scene(spane, 1400, 900);
        for(int i=0;i<2000;i++)
        {
            mark.add(0);
        }
        refresh();
        scene.getStylesheets().add(ForbiddenEditWindow.class.getResource("index.css").toExternalForm());
        return scene;
    }
    public ForbiddenEditWindow()
    {

    }
    public void refresh()
    {
        double posv=spane.getVvalue();
        pane.getChildren().clear();
        addForbidden=new LinkedList<Button>();
        editForbidden=new LinkedList<Button>();
        deleteForbidden=new LinkedList<Button>();
        addForbidden = new LinkedList<Button>();
        posy=0;
        lineheight=50;
        center=620-160;
        pane.getChildren().clear();
        Label nameLabel = new Label();
        nameLabel.setText("Edit Forbidden Pairs");
        nameLabel.getStyleClass().add("bigtitle");
        nameLabel.setLayoutX(500);
        nameLabel.setLayoutY(posy+=lineheight);
        posy+=lineheight/2;
        pane.layout();
        pane.applyCss();
        spane.applyCss();
        spane.layout();
        spane.setVvalue(posv);
        pane.getChildren().add(nameLabel);
        home = new Button("s");
        home.setLayoutX(920);
        home.setLayoutY(posy+=lineheight);
        home.setMinSize(30,30);
        home.setMaxSize(30,30);
        home.getStyleClass().add("home");
        pane.getChildren().add(home);
        posy+=20;
        for(int i=0;i<data.size();i++)
        {
            if(data.get(i)==null) continue;
            Label label = new Label();
            label.setText(Integer.toString(i+1));
            label.setLayoutX(center-10);
            posy+=lineheight;
            label.setLayoutY(posy+4);
            label.getStyleClass().add("content");
            pane.getChildren().add(label);
            if(mark.get(i)==1)
            {
                TextField tf1 = new TextField(data.get(i).getKey());
                tf1.setLayoutX(center + 20);
                tf1.setLayoutY(posy);
                tf1.setMaxSize(150, 200);
                tf1.getStyleClass().add("content");
                pane.getChildren().add(tf1);
                TextField tf2 = new TextField(data.get(i).getValue());
                tf2.setLayoutX(center + 190);
                tf2.setLayoutY(posy);
                tf2.setMaxSize(150, 200);
                tf2.getStyleClass().add("content");
                pane.getChildren().add(tf2);
                if(i>=forbidenText1.size())
                    forbidenText1.add(tf1);
                else forbidenText1.set(i,tf1);
                if(i>=forbidenText2.size())
                    forbidenText2.add(tf2);
                else forbidenText2.set(i,tf2);
                Button button = new Button();
                button.setLayoutX(center+380);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("save");
                pane.getChildren().add(button);
                editForbidden.add(button);
            }
            else
            {
                Label label2 = new Label();
                label2.setText(data.get(i).getKey()+" can't stay with "+data.get(i).getValue());
                label2.setLayoutX(center+20);
                label2.setLayoutY(posy);
                label2.getStyleClass().add("content");
                label2.setStyle("-fx-font-size: 20px; -fx-font-weight: 100;");
                pane.getChildren().add(label2);
                Button button = new Button();
                button.setLayoutX(center+380);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("edit");
                pane.getChildren().add(button);
                editForbidden.add(button);
            }
            Button button = new Button();
            button.setLayoutX(center+420);
            button.setLayoutY(posy);
            button.setMinSize(36,36);
            button.setMaxSize(36,36);
            button.getStyleClass().add("addbutton");
            addForbidden.add(button);
            pane.getChildren().add(button);
            button = new Button();
            button.setLayoutX(center+460);
            button.setLayoutY(posy);
            button.setMinSize(36,36);
            button.setMaxSize(36,36);
            button.getStyleClass().add("deletebutton");
            deleteForbidden.add(button);
            pane.getChildren().add(button);
            posv=spane.getVvalue();
        }
    }
    public void savedata()
    {
        for(int i=0;i<forbidenText1.size();i++)
        {
            if(i>=data.size()) break;
            data.get(i).setKey(forbidenText1.get(i).getText());
            data.get(i).setValue(forbidenText2.get(i).getText());
        }
    }

}
