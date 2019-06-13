package PixivCookbook.View;

import PixivCookbook.Model.Recipe;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.sun.corba.se.pept.transport.EventHandler;

public class Main{
    public ImageView temp[],title;
    public Pane pane = new Pane();
    public Image image;
    Scene scene;
    Label label[];
    public TextField search;
    Stage primaryStage;
    public Button addButton = new Button();
    public Button recommendButton =new Button();
    public Button favoriteButton = new Button();
    public Button favoriteButton2 = new Button();
    public Button searchButton = new Button();
    public Button backButton = new Button();
    public Button sticky = new Button();
    public boolean favorite=false;
    public Label littleTitle;

    /**
     * @return 	 scene of the main window
     */
    public Scene getScene(){
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        //this.primaryStage.setTitle("Hello World");
        scene=new Scene(pane, 1400, 900);
        initializeOtherParts();
        scene.getStylesheets().add(Main.class.getResource("source/index.css").toExternalForm());
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
        littleTitle = new Label();
        littleTitle.setLayoutX(600);
        littleTitle.setLayoutY(150);
        pane.getChildren().add(littleTitle);
//        Button recommendButton = new Button();
        recommendButton.setText("C");
        recommendButton.setLayoutX(1000);
        recommendButton.setLayoutY(150);
        recommendButton.setMinSize(20,20);
        recommendButton.getStyleClass().add("border");
        pane.getChildren().add(recommendButton);
		sticky.setText("C");
		sticky.setLayoutX(1200);
		sticky.setLayoutY(30);
		sticky.setMinSize(200,200);
		sticky.getStyleClass().add("sticky");
		pane.getChildren().add(sticky);
        //System.out.println(root.getChildrenUnmodifiable());
        favoriteButton.setText("C");
        favoriteButton.setLayoutX(1050);
        favoriteButton.setLayoutY(150);
        favoriteButton.setMinSize(20,20);
        favoriteButton.getStyleClass().add("bigstar");
        pane.getChildren().add(favoriteButton);
        favoriteButton2.setText("C");
        favoriteButton2.setLayoutX(1050);
        favoriteButton2.setLayoutY(150);
        favoriteButton2.setMinSize(20,20);
        favoriteButton2.getStyleClass().add("yellowbigstar");
        pane.getChildren().add(favoriteButton2);
    }

    public void initializeMainPage(List<Recipe> recipe) {
        if(favorite)
        {
            favoriteButton.setVisible(false);
            favoriteButton2.setVisible(true);
        }
        else
        {
            favoriteButton2.setVisible(false);
            favoriteButton.setVisible(true);
        }
        int posx=100,posy=200;
        temp = new ImageView[recipe.size()+1];
        label = new Label[recipe.size()+1];
        for(int i=0;i<Math.min(7,recipe.size());i++)
        {
        	String patternstr = "([A-Z]{1}:)";
			Pattern p = Pattern.compile(patternstr);
			Matcher matcher = p.matcher(recipe.get(i).getImgAddress());
			if(matcher.find()) {
			image = new Image("file:\\"+recipe.get(i).getImgAddress());
			}else {
            image = new Image(ClassLoader.getSystemResource("")+"..\\"+recipe.get(i).getImgAddress());
            //System.out.println(recipe.get(i).getImgAddress());
			}
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
        temp[Math.min(7,recipe.size())] = new ImageView(image);
        temp[Math.min(7,recipe.size())].setFitHeight(150);
        temp[Math.min(7,recipe.size())].setFitWidth(150);
        temp[Math.min(7,recipe.size())].setLayoutX(Math.min(7,recipe.size())%4*300+posx+25);
        temp[Math.min(7,recipe.size())].setLayoutY(Math.min(7,recipe.size())/4*350+posy+25);
        temp[Math.min(7,recipe.size())].getStyleClass().add("pointer");
        pane.getChildren().add(temp[Math.min(7,recipe.size())]);
        label[Math.min(7,recipe.size())] = new Label("NEW RECIPE");
        label[Math.min(7,recipe.size())].setLayoutX(Math.min(7,recipe.size())%4*300+posx+30);
        label[Math.min(7,recipe.size())].setLayoutY(Math.min(7,recipe.size())/4*350+200+posy);
        label[Math.min(7,recipe.size())].getStyleClass().add("pointer");
        pane.getChildren().add(label[Math.min(7,recipe.size())]);
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


	/**
	 * @return the temp
	 */
	public ImageView[] getTemp() {
		return temp;
	}


	/**
	 * @param temp the temp to set
	 */
	public void setTemp(ImageView[] temp) {
		this.temp = temp;
	}


	/**
	 * @return the title
	 */
	public ImageView getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(ImageView title) {
		this.title = title;
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
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}


	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}


	/**
	 * @return the label
	 */
	public Label[] getLabel() {
		return label;
	}


	/**
	 * @param label the label to set
	 */
	public void setLabel(Label[] label) {
		this.label = label;
	}


	/**
	 * @return the search
	 */
	public TextField getSearch() {
		return search;
	}


	/**
	 * @param search the search to set
	 */
	public void setSearch(TextField search) {
		this.search = search;
	}


	/**
	 * @return the primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}


	/**
	 * @param primaryStage the primaryStage to set
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	/**
	 * @return the addButton
	 */
	public Button getAddButton() {
		return addButton;
	}


	/**
	 * @param addButton the addButton to set
	 */
	public void setAddButton(Button addButton) {
		this.addButton = addButton;
	}


	/**
	 * @return the recommendButton
	 */
	public Button getRecommendButton() {
		return recommendButton;
	}


	/**
	 * @param recommendButton the recommendButton to set
	 */
	public void setRecommendButton(Button recommendButton) {
		this.recommendButton = recommendButton;
	}


	/**
	 * @return the favoriteButton
	 */
	public Button getFavoriteButton() {
		return favoriteButton;
	}


	/**
	 * @param favoriteButton the favoriteButton to set
	 */
	public void setFavoriteButton(Button favoriteButton) {
		this.favoriteButton = favoriteButton;
	}


	/**
	 * @return the favoriteButton2
	 */
	public Button getFavoriteButton2() {
		return favoriteButton2;
	}


	/**
	 * @param favoriteButton2 the favoriteButton2 to set
	 */
	public void setFavoriteButton2(Button favoriteButton2) {
		this.favoriteButton2 = favoriteButton2;
	}


	/**
	 * @return the searchButton
	 */
	public Button getSearchButton() {
		return searchButton;
	}


	/**
	 * @param searchButton the searchButton to set
	 */
	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}


	/**
	 * @return the backButton
	 */
	public Button getBackButton() {
		return backButton;
	}


	/**
	 * @param backButton the backButton to set
	 */
	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}


	/**
	 * @return the favorite
	 */
	public boolean isFavorite() {
		return favorite;
	}


	/**
	 * @param favorite the favorite to set
	 */
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}


	/**
	 * @return the littleTitle
	 */
	public Label getLittleTitle() {
		return littleTitle;
	}


	/**
	 * @param littleTitle the littleTitle to set
	 */
	public void setLittleTitle(Label littleTitle) {
		this.littleTitle = littleTitle;
	}


	/**
	 * @param scene the scene to set
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	


}
