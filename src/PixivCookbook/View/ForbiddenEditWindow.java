package PixivCookbook.View;

import PixivCookbook.Model.ForbiddenPair;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

/**
 * this is the forbidden edit window
 * @author Shen yu
 * 
 */
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

    /**
     * @return the scene with given style
     */
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
        scene.getStylesheets().add(ForbiddenEditWindow.class.getResource("source/index.css").toExternalForm());
        return scene;
    }

    /**
     * refresh the forbidden edit window
     */
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
		Button lay = new Button();
		lay.setText("a");
		lay.setLayoutX(920);
		lay.setLayoutY(900);
		lay.setMinSize(1,1);
		lay.setMaxSize(1,1);
		lay.getStyleClass().add("home");
		pane.getChildren().add(lay);
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
    /**
     * save all the ingredient pair content displayed on the screen
     */
    public void savedata()
    {
        for(int i=0;i<forbidenText1.size();i++)
        {
            if(i>=data.size()) break;
            data.get(i).setKey(forbidenText1.get(i).getText());
            data.get(i).setValue(forbidenText2.get(i).getText());
        }
    }

	/**
	 * get the y position
	 * @return the posy
	 */
	public int getPosy() {
		return posy;
	}

	/**
	 * set the y position
	 * @param posy the posy to set
	 */
	public void setPosy(int posy) {
		this.posy = posy;
	}

	/**
	 * @return the lineheight
	 */
	public int getLineheight() {
		return lineheight;
	}

	/**
	 * @param lineheight the lineheight to set
	 */
	public void setLineheight(int lineheight) {
		this.lineheight = lineheight;
	}

	/**
	 * @return the center
	 */
	public int getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(int center) {
		this.center = center;
	}

	/**
	 * @return the data
	 */
	public LinkedList<ForbiddenPair> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LinkedList<ForbiddenPair> data) {
		this.data = data;
	}

	/**
	 * @return the pane
	 */
	public Pane getPane() {
		return pane;
	}

	/**
	 * @param pane the pane to set
	 */
	public void setPane(Pane pane) {
		this.pane = pane;
	}

	/**
	 * @return the spane
	 */
	public ScrollPane getSpane() {
		return spane;
	}

	/**
	 * @param spane the spane to set
	 */
	public void setSpane(ScrollPane spane) {
		this.spane = spane;
	}

	/**
	 * @return the editForbidden
	 */
	public LinkedList<Button> getEditForbidden() {
		return editForbidden;
	}

	/**
	 * @param editForbidden the editForbidden to set
	 */
	public void setEditForbidden(LinkedList<Button> editForbidden) {
		this.editForbidden = editForbidden;
	}

	/**
	 * @return the addForbidden
	 */
	public LinkedList<Button> getAddForbidden() {
		return addForbidden;
	}

	/**
	 * @param addForbidden the addForbidden to set
	 */
	public void setAddForbidden(LinkedList<Button> addForbidden) {
		this.addForbidden = addForbidden;
	}

	/**
	 * @return the deleteForbidden
	 */
	public LinkedList<Button> getDeleteForbidden() {
		return deleteForbidden;
	}

	/**
	 * @param deleteForbidden the deleteForbidden to set
	 */
	public void setDeleteForbidden(LinkedList<Button> deleteForbidden) {
		this.deleteForbidden = deleteForbidden;
	}

	/**
	 * @return the forbidenText1
	 */
	public LinkedList<TextField> getForbidenText1() {
		return forbidenText1;
	}

	/**
	 * @param forbidenText1 the forbidenText1 to set
	 */
	public void setForbidenText1(LinkedList<TextField> forbidenText1) {
		this.forbidenText1 = forbidenText1;
	}

	/**
	 * @return the forbidenText2
	 */
	public LinkedList<TextField> getForbidenText2() {
		return forbidenText2;
	}

	/**
	 * @param forbidenText2 the forbidenText2 to set
	 */
	public void setForbidenText2(LinkedList<TextField> forbidenText2) {
		this.forbidenText2 = forbidenText2;
	}

	/**
	 * @return the mark
	 */
	public LinkedList<Integer> getMark() {
		return mark;
	}

	/**
	 * @param mark the mark to set
	 */
	public void setMark(LinkedList<Integer> mark) {
		this.mark = mark;
	}

	/**
	 * @return the home
	 */
	public Button getHome() {
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public void setHome(Button home) {
		this.home = home;
	}

}
