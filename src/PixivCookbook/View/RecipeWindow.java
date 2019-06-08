package PixivCookbook.View;

import PixivCookbook.Ingredient;
import PixivCookbook.Step;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeWindow{
    public boolean markDescription=false;
    public boolean markIngredient=false;
    public boolean markImage=false;
    public boolean markStep=false;
    public boolean markName=false;
    Pane pane = new Pane();
    public ScrollPane spane;
    public String name = "Default";
    public String description;
    public String imgPath;
    public Button home= new Button();
    public Button star = new Button();
    public Button delete= new Button();
    public Button share =new Button();
    public Button editTitle;
    public Button editDescription;
    public Button editIngredient;
    public Button editStep;
    public TextField tf_RecipeName;
    public TextField tf_Description;
    public TextField tf_Preparation;
    public TextField tf_Cookingtime;
    public TextField tf_Serveing;
    public ImageView title;
    public String editImgPath;  // here is the editImg
    
    public LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
    public LinkedList<Step> step = new LinkedList<Step>();
    public double preparationTime=0;
    public double cookingTime=0;
    public double servings=0;
    public boolean favorite = false;

    public LinkedList<Button> addIngredient;
    public LinkedList<Button> addStep;
    public LinkedList<Button> deleteIngredient;
    public LinkedList<Button> deleteStep;
    public LinkedList<Button> upStep;
    public LinkedList<Button> downStep;
    public LinkedList<TextField> ingredientText3= new LinkedList<TextField>();
    public LinkedList<TextField> ingredientText2= new LinkedList<TextField>();
    public LinkedList<TextField> ingredientText1= new LinkedList<TextField>();
    public LinkedList<TextField> stepText= new LinkedList<TextField>();
    int posy=0;
    int lineheight=50;
    int center=550-160;
    public RecipeWindow()
    {

    }

    public Scene getScene(){
        pane.setMinWidth(1375);
        pane.getStyleClass().add("root");
        //primaryStage.setTitle("Hello World");
        System.out.println();
        spane = new ScrollPane();
        spane.setMinHeight(900);
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
        double posv=spane.getVvalue();
        //System.out.println("pre"+spane.getVvalue());
        //double posv=spane.getVvalue();
        //spane.setVvalue(posv);
        pane.getChildren().clear();
        addIngredient = new LinkedList<Button>();
        addStep = new LinkedList<Button>();
        deleteIngredient = new LinkedList<Button>();
        deleteStep = new LinkedList<Button>();
        upStep=new LinkedList<Button>();
        downStep=new LinkedList<Button>();
        posy=0;
        lineheight=50;
        center=550-160;
        pane.getChildren().clear();
        Label nameLabel = new Label();
        nameLabel.setText(name);
        nameLabel.getStyleClass().add("bigtitle");
        //pane.getChildren().add(nameLabel);
        pane.layout();
        pane.applyCss();
        spane.applyCss();
        spane.layout();
        spane.setVvalue(posv);
        if(markName)
        {
            tf_RecipeName = new TextField(name);
            tf_RecipeName.setLayoutX(500);
            tf_RecipeName.setLayoutY(posy+=lineheight);
            posy+=lineheight/2;
            tf_RecipeName.setMinSize(400,0);
            pane.getChildren().add(tf_RecipeName);
            editTitle= new Button("");
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
            editTitle= new Button("");
            editTitle.setMinSize(36,36);
            editTitle.setMaxSize(36,36);
            editTitle.setLayoutX(916);
            editTitle.setLayoutY(posy-14);
            editTitle.getStyleClass().add("edit");
            pane.getChildren().add(editTitle);
        }
        home = new Button("s");
        home.setLayoutX(820);
        home.setLayoutY(posy+=lineheight);
        home.setMinSize(30,30);
        home.setMaxSize(30,30);
        home.getStyleClass().add("home");
        share = new Button("s");
        share.setLayoutX(900);
        share.setLayoutY(posy);
        star = new Button("s");
        star.setLayoutX(980);
        star.setLayoutY(posy);
        delete = new Button("d");
        delete.setLayoutX(1060);
        delete.setLayoutY(posy);
        if(favorite) star.getStyleClass().add("yellowstar");
        else star.getStyleClass().add("star");
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
        pane.getChildren().add(home);
        posy+=20;

        if(markImage)
        {
        	Image image = new Image(ClassLoader.getSystemResource("")+"..\\img\\addimage.png");
            title = new ImageView(image);
            title.setFitHeight(180*2);
            title.setFitWidth(320*2);
            title.setLayoutX(center);
            title.setLayoutY(posy+=lineheight);
            pane.getChildren().add(title);
            posy+=330;
        }
        else
        {
        	Image image;
        	if(this.editImgPath!=null) {
//        		System.out.println(editImgPath);
        		image = new Image("file:/"+editImgPath);   // "file:/" is important!
        		title = new ImageView(image);
	            title.setFitHeight(180*2);
	            title.setFitWidth(320*2);
	            title.setLayoutX(center);
	            title.setLayoutY(posy+=lineheight);
	            pane.getChildren().add(title);
	            posy+=330;
        	}
        	else if(imgPath == null && editImgPath == null) {
        				
        	}
        	else {
	        		String patternstr = "([A-Z]{1}:)";
	    			Pattern p = Pattern.compile(patternstr);
	    			Matcher matcher = p.matcher(imgPath);
	    			if(matcher.find()) {
	    				image = new Image("file:\\"+imgPath);
	    				title = new ImageView(image);
			            title.setFitHeight(180*2);
			            title.setFitWidth(320*2);
			            title.setLayoutX(center);
			            title.setLayoutY(posy+=lineheight);
			            pane.getChildren().add(title);
			            posy+=330;
	    			}else {
	    				image = new Image(ClassLoader.getSystemResource("")+"..\\"+imgPath);
	    				title = new ImageView(image);
	    		        title.setFitHeight(180*2);
	    		        title.setFitWidth(320*2);
	    		        title.setLayoutX(center);
	    		        title.setLayoutY(posy+=lineheight);
	    		        pane.getChildren().add(title);
	    		        posy+=330;
	    			}
        	}
    }

//mark
        Label descripLabel = new Label();
        descripLabel.setText("Main Information");
        descripLabel.setLayoutX(center);
        descripLabel.setLayoutY(posy+=lineheight);
        editDescription = new Button("");
        editDescription.setLayoutX(center+244);
        editDescription.setLayoutY(posy);
        pane.getChildren().add(descripLabel);
        int line=1;
        String disDes=description;
        if(markDescription)
        {
            center+=20;
            editDescription.getStyleClass().add("save");
            editDescription.setMinSize(36,36);
            editDescription.setMaxSize(36,36);
            pane.getChildren().add(editDescription);
            Label desLabel = new Label("Descriptoon:");
            tf_Description = new TextField(disDes);
            desLabel.setLayoutX(center);
            posy+=lineheight;
            desLabel.setLayoutY(posy+8);
            desLabel.getStyleClass().add("content");
            pane.getChildren().add(desLabel);
            tf_Description.setLayoutX(center+200);
            tf_Description.setLayoutY(posy);
            pane.getChildren().add(tf_Description);
            Label prepaLable = new Label("Preparation Time:");
            tf_Preparation = new TextField(Double.toString(preparationTime));
            prepaLable.setLayoutX(center);
            posy+=lineheight;
            prepaLable.setLayoutY(posy+8);
            prepaLable.getStyleClass().add("content");
            pane.getChildren().add(prepaLable);
            tf_Preparation.setLayoutX(center+200);
            tf_Preparation.setLayoutY(posy);
            pane.getChildren().add(tf_Preparation);
            Label cookLabel = new Label("Cook Time:");
            tf_Cookingtime = new TextField(Double.toString(cookingTime));
            cookLabel.setLayoutX(center);
            posy+=lineheight;
            cookLabel.setLayoutY(posy+8);
            cookLabel.getStyleClass().add("content");
            pane.getChildren().add(cookLabel);
            tf_Cookingtime.setLayoutX(center+200);
            tf_Cookingtime.setLayoutY(posy);
            pane.getChildren().add(tf_Cookingtime);
            Label serveLabel = new Label("Servings:");
            tf_Serveing = new TextField(Double.toString(servings));
            serveLabel.setLayoutX(center);
            posy+=lineheight;
            serveLabel.setLayoutY(posy+8);
            serveLabel.getStyleClass().add("content");
            pane.getChildren().add(serveLabel);
            tf_Serveing.setLayoutX(center+200);
            tf_Serveing.setLayoutY(posy);
            pane.getChildren().add(tf_Serveing);
            center-=20;
        }
        else
        {
            center+=20;
            editDescription.getStyleClass().add("edit");
            editDescription.setMinSize(36,36);
            editDescription.setMaxSize(36,36);
            pane.getChildren().add(editDescription);
            Label desLabel = new Label("Descriptoon:");
            desLabel.setLayoutX(center);
            posy+=lineheight;
            desLabel.setLayoutY(posy);
            desLabel.getStyleClass().add("content");
            pane.getChildren().add(desLabel);
            Label descLabel = new Label();
            descLabel.setText(description);
            descLabel.setLayoutX(center+200);
            descLabel.setLayoutY(posy);
            descLabel.getStyleClass().add("content");
            pane.getChildren().add(descLabel);
            Label prepaLable = new Label("Preparation Time:");
            prepaLable.setLayoutX(center);
            posy+=lineheight;
            prepaLable.setLayoutY(posy);
            prepaLable.getStyleClass().add("content");
            pane.getChildren().add(prepaLable);
            Label prepaLabel = new Label();
            prepaLabel.setText(Double.toString(preparationTime));
            prepaLabel.setLayoutX(center+200);
            prepaLabel.setLayoutY(posy);
            prepaLabel.getStyleClass().add("content");
            pane.getChildren().add(prepaLabel);
            Label cookLabel = new Label("Cook Time:");
            cookLabel.setLayoutX(center);
            posy+=lineheight;
            cookLabel.setLayoutY(posy);
            cookLabel.getStyleClass().add("content");
            pane.getChildren().add(cookLabel);
            Label cooLabel = new Label();
            cooLabel.setText(Double.toString(cookingTime));
            cooLabel.setLayoutX(center+200);
            cooLabel.setLayoutY(posy);
            cooLabel.getStyleClass().add("content");
            pane.getChildren().add(cooLabel);
            Label serveLabel = new Label("Servings:");
            serveLabel.setLayoutX(center);
            posy+=lineheight;
            serveLabel.setLayoutY(posy);
            serveLabel.getStyleClass().add("content");
            pane.getChildren().add(serveLabel);
            Label servLabel = new Label();
            servLabel.setText(Double.toString(servings));
            servLabel.setLayoutX(center+200);
            servLabel.setLayoutY(posy);
            servLabel.getStyleClass().add("content");
            pane.getChildren().add(servLabel);
            center-=20;
        }

        Label ingreLabel = new Label();
        ingreLabel.setText("Ingredient");
        ingreLabel.setLayoutX(center);
        ingreLabel.setLayoutY(posy+=lineheight);
        editIngredient = new Button("e");
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
                TextField tf1 = new TextField(Double.toString(ingredients.get(i).getNum()));
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
                addIngredient.add(button);
                button.getStyleClass().add("addbutton");
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+570);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                deleteIngredient.add(button);
                button.getStyleClass().add("deletebutton");
                pane.getChildren().add(button);
                if(i>=ingredientText1.size())
                {
                    ingredientText1.add(tf1);
                    ingredientText2.add(tf2);
                    ingredientText3.add(tf3);
                }
                else
                {
                    ingredientText1.set(i,tf1);
                    ingredientText2.set(i,tf2);
                    ingredientText3.set(i,tf3);
                }
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
        editStep = new Button("e");
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
                addStep.add(button);
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+340);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("deletebutton");
                deleteStep.add(button);
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+260);
                button.setLayoutY(posy);
                button.setMinSize(36,18);
                button.setMaxSize(36,18);
                button.getStyleClass().add("upbutton");
                upStep.add(button);
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+260);
                button.setLayoutY(posy+18);
                button.setMinSize(36,18);
                button.setMaxSize(36,18);
                button.getStyleClass().add("downbutton");
                downStep.add(button);
                pane.getChildren().add(button);
                if(i>=stepText.size())
                    stepText.add(tf);
                else stepText.set(i,tf);
            }
        }
        else
        {
            editStep.getStyleClass().add("edit");
            pane.getChildren().add(editStep);
            int cnt=0;
            for(int i=0;i<step.size();i++)
            {
                if(step.get(i)==null) continue;
                cnt++;
                Label label = new Label();
                label.setText(Integer.toString(i+1)+".");
                label.setLayoutX(center-10);
                posy+=lineheight;
                label.setLayoutY(posy);
                label.getStyleClass().add("content");
                pane.getChildren().add(label);
                label = new Label();
                label.setText(step.get(i).getContent());
                label.setLayoutX(center+20);
                label.setLayoutY(posy);
                label.getStyleClass().add("content");
                int haha=0;
                pane.getChildren().add(label);
            }
        }
//        star.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                markDescription=!markDescription;
//                markStep=!markStep;
//                markName=!markName;
//                markImage=!markImage;
//                markIngredient=!markIngredient;
//                refresh();
//            }
//        });
    }
    public void saveData()
    {
        if(markDescription&&tf_Preparation!=null)
        {
            this.servings=Double.parseDouble(tf_Serveing.getText());
            this.cookingTime=Double.parseDouble(tf_Cookingtime.getText());
            this.preparationTime=Double.parseDouble(tf_Preparation.getText());
            this.description=tf_Description.getText();
        }
        if(ingredientText3.size()>0)
            for(int i=0;i<ingredientText3.size();i++)
            {
                if(ingredients.size()<=i) break;
                ingredients.get(i).setName(ingredientText3.get(i).getText());
                ingredients.get(i).setNum(Double.parseDouble(ingredientText1.get(i).getText()));
                ingredients.get(i).setUnit(ingredientText2.get(i).getText());
            }
        if(stepText.size()>0)
            for(int i=0;i<stepText.size();i++)
            {
                if(step.size()<=i) break;
                step.get(i).setContent(stepText.get(i).getText());
            }
    }
}
