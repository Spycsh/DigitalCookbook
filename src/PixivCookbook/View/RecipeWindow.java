package PixivCookbook.View;

import PixivCookbook.Model.Ingredient;
import PixivCookbook.Model.Step;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this is the recipe window
 * @author Shen Yu
 *
 */
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
    public int preparationTime=0;
    public int cookingTime=0;
    public int servings=0;
    private int formerServings=0;
    public boolean favorite = false;
    public boolean illegal;
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

    private final static RecipeWindow INSTANCE = new RecipeWindow();
    private  RecipeWindow()
    {

    }
    public static RecipeWindow getInstance(){
        return INSTANCE;
    }
    /**
     * initialize the scene
     * @return
     */
    public Scene getScene(){
        pane.setMinWidth(1375);
        pane.getStyleClass().add("root");
        //primaryStage.setTitle("Hello World");
        spane = new ScrollPane();
        spane.setMinHeight(900);
        spane.setContent(pane);
        Scene scene=new Scene(spane, 1400, 900);
        refresh();
        //System.out.println(root.getChildrenUnmodifiable());
        //label.getStyleClass().add("pointer");
        scene.getStylesheets().add(MainWindow.class.getResource("source/index.css").toExternalForm());
        //primaryStage.setScene(scene);
        //primaryStage.show();
        return scene;
    }

    /**
     * refresh the whole window
     */
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
        home.setLayoutX(400);
        home.setLayoutY(posy+=lineheight);
        home.setMinSize(30,30);
        home.setMaxSize(30,30);
        home.getStyleClass().add("home");
//        share = new Button("s");
//        share.setLayoutX(900);
//        share.setLayoutY(posy);
//        star = new Button("s");
//        star.setLayoutX(900);
//        star.setLayoutY(posy);
        delete = new Button("d");
        delete.setLayoutX(980);
        delete.setLayoutY(posy);
//        if(favorite) star.getStyleClass().add("yellowstar");
//        else star.getStyleClass().add("star");
//        star.setMinSize(30,30);
//        star.setMaxSize(30,30);
        delete.getStyleClass().add("delete");
        delete.setMinSize(30,30);
        delete.setMaxSize(30,30);
//        share.getStyleClass().add("share");
//        share.setMinSize(30,30);
//        share.setMaxSize(30,30);
//        pane.getChildren().add(share);
//        pane.getChildren().add(star);
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
        	if(imgPath==null) imgPath="img\\addImage.png";
            String patternstr = "([A-Z]{1}:)";
            Pattern p = Pattern.compile(patternstr);
            Matcher matcher = p.matcher(imgPath);
            //System.out.println(imgPath);
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
            Label desLabel = new Label("Description:");
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
            tf_Preparation = new TextField(Integer.toString(preparationTime));
            prepaLable.setLayoutX(center);
            posy+=lineheight;
            prepaLable.setLayoutY(posy+8);
            prepaLable.getStyleClass().add("content");
            pane.getChildren().add(prepaLable);
            tf_Preparation.setLayoutX(center+200);
            tf_Preparation.setLayoutY(posy);
            pane.getChildren().add(tf_Preparation);
            Label cookLabel = new Label("Cook Time:");
            tf_Cookingtime = new TextField(Integer.toString(cookingTime));
            cookLabel.setLayoutX(center);
            posy+=lineheight;
            cookLabel.setLayoutY(posy+8);
            cookLabel.getStyleClass().add("content");
            pane.getChildren().add(cookLabel);
            tf_Cookingtime.setLayoutX(center+200);
            tf_Cookingtime.setLayoutY(posy);
            pane.getChildren().add(tf_Cookingtime);
            Label serveLabel = new Label("Servings:");
            tf_Serveing = new TextField(Integer.toString(servings));
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
            Label desLabel = new Label("Description:");
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
            prepaLabel.setText(Integer.toString(preparationTime));
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
            cooLabel.setText(Integer.toString(cookingTime));
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
            servLabel.setText(Integer.toString(servings));
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
        if(ingredients.size()==0) ingredients.add(new Ingredient("Default",0,""));
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
                TextField tf1 = new TextField(Double.toString(ingredients.get(i).getNum()));
                TextField tf2 = new TextField(ingredients.get(i).getUnit());
                TextField tf3 = new TextField(ingredients.get(i).getName());
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
        if(step.size()==0) step.add(new Step("Default",0));
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
                tf.setMinWidth(620);
                tf.getStyleClass().add("content");
                pane.getChildren().add(tf);
                Button button = new Button();
                button.setLayoutX(center+700);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("addbutton");
                addStep.add(button);
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+740);
                button.setLayoutY(posy);
                button.setMinSize(36,36);
                button.setMaxSize(36,36);
                button.getStyleClass().add("deletebutton");
                deleteStep.add(button);
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+660);
                button.setLayoutY(posy);
                button.setMinSize(36,18);
                button.setMaxSize(36,18);
                button.getStyleClass().add("upbutton");
                upStep.add(button);
                pane.getChildren().add(button);
                button = new Button();
                button.setLayoutX(center+660);
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
    }
    public void saveData()
    {
        formVerify();

        if(markDescription&&tf_Preparation!=null)
        {
        	formerServings = this.servings;
            this.servings=Integer.parseInt(tf_Serveing.getText());
            for(int i = 0;i<ingredientText1.size();i++) {
            	if(i>=ingredients.size())
            		break;
            	DecimalFormat df1=new DecimalFormat("0.0000");
            	DecimalFormat df2=new DecimalFormat("0.0");
            	ingredients.get(i).setNum(Double.parseDouble(df2.format(ingredients.get(i).getNum()*
            			Double.parseDouble(df1.format((float)this.servings/(float)this.formerServings)))));
            }
            formerServings = this.servings;
            this.cookingTime=Integer.parseInt(tf_Cookingtime.getText());
            this.preparationTime=Integer.parseInt(tf_Preparation.getText());
            this.description=tf_Description.getText();
        }
        if(ingredientText3!=null)
            for(int i=0;i<ingredientText3.size();i++)
            {
                if(ingredients.size()<=i) break;
                ingredients.get(i).setName(ingredientText3.get(i).getText());
                ingredients.get(i).setUnit(ingredientText2.get(i).getText());
            }
        if(stepText!=null)
            for(int i=0;i<stepText.size();i++)
            {
                if(step.size()<=i) break;
                step.get(i).setContent(stepText.get(i).getText());
            }
    }

    /**
     * alert when input length
     * exceed the default size
     */
    public void formVerify()
    {
        boolean longNmae = false;
        boolean wrongIngredientnum[];
        boolean longDescription=false;
        boolean longIngredientname[]=null;
        boolean longIngredientunit[]=null;
        boolean longStep[]=null;
        LinkedList<String> message = new LinkedList<String>();
        if(tf_RecipeName!=null)
            if(tf_RecipeName.getText().length()>20)
            {
                longDescription = true;
                message.add("Recipe Name is too long!");
                tf_RecipeName.setText(tf_RecipeName.getText().substring(0,19));
                this.name=tf_RecipeName.getText().substring(0,19);
            }
        if(tf_Cookingtime!=null)
            if(!isInt(tf_Cookingtime.getText()))
            {
                tf_Cookingtime.setText(""+this.cookingTime);
                message.add("Cooking time can only be integer");
            }
        if(tf_Preparation!=null)
            if(!isInt(tf_Preparation.getText()))
            {
                tf_Preparation.setText(""+this.preparationTime);
                message.add("Preparation time can only be integer");
            }
        if(tf_Serveing!=null)
            if(!isInt(tf_Serveing.getText()))
            {
                tf_Serveing.setText(""+this.servings);
                message.add("Serveing can only be integer");
            }
        if(markDescription&&tf_Description!=null) {
            if (tf_Description.getText().length() > 30) {
                longDescription = true;
                message.add("Description Name is too long!");
                tf_Description.setText(tf_Description.getText().substring(0, 29));
            }
        }
        if(markIngredient&&ingredientText1!=null)
        {
            wrongIngredientnum=new boolean[ingredients.size()];
            longIngredientname=new boolean[ingredients.size()];
            longIngredientunit=new boolean[ingredients.size()];
            int cnt=0;
            for(int i=0;i<ingredients.size();i++)
            {
            	if(i>=ingredientText1.size()) break;
            	cnt++;
                wrongIngredientnum[i] = false;
                if (!isDouble(ingredientText1.get(i).getText())) {
                	int position = i+1;
                    message.add("Ingredient "+cnt+": Ingredient number can only be rational number!");
                    wrongIngredientnum[i] = true;
                    ingredientText1.get(i).setText(Double.toString(ingredients.get(i).getNum()));
                }
                if(!wrongIngredientnum[i]) {
                	ingredients.get(i).setNum(Double.parseDouble(ingredientText1.get(i).getText()));
                }
            }
            cnt=0;
            for(int i=0;i<ingredients.size();i++)
            {
            	if(i>=ingredientText2.size()) break;
            	cnt++;
                longIngredientunit[i]=false;
                if(ingredientText2.get(i).getText().length()>15)
                {
                    message.add("Ingredient "+cnt+": Ingredient unit is too long!");
                    longIngredientunit[i] = true;
                    ingredientText2.get(i).setText(ingredientText2.get(i).getText().substring(0,14));
                }
            }
            cnt=0;
            for(int i=0;i<ingredients.size();i++)
            {
            	if(i>=ingredientText3.size()) break;
                longIngredientname[i]=false;
                cnt++;
                if(ingredientText3.get(i).getText().length()>30)
                {
                    message.add("Ingredient "+cnt+": Ingredient name is too long!");
                    longIngredientname[i] = true;
                    ingredientText3.get(i).setText(ingredientText3.get(i).getText().substring(0,29));
                }
            }
        }
        if(markStep&&stepText!=null)
        {
            longStep=new boolean[step.size()];
            int cnt=0;
            for(int i=0;i<step.size();i++)
            {
            	if(i>=stepText.size()) break;
            	cnt++;
                if(stepText.get(i).getText().length()>90)
                {
                    message.add("Step "+cnt+" is too long");
                    stepText.get(i).setText(stepText.get(i).getText().substring(0,89));
                    longStep[i]=true;
                }
            }
        }
        if(message.size()>0)
            alertForm(message);
    }

    /**
     * a substring of the message of error
     * @param message error 
     */
    public void alertForm(LinkedList<String> message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Form verify");
        alert.setHeaderText("The data you type in have some errors.");
        String s="";
        for(int i=0;i<message.size();i++)
        {
            s+="";
            if(i==message.size()-1)
                s+=(message.get(i));
            else s+=(message.get(i)+"\n");
        }
        alert.setContentText(s);
        alert.showAndWait();
    }

    /**
     * check if the string denotes a integer
     * @param s the string need to check
     * @return true or false
     */
    public boolean isInt(String s)
    {
        if(s.length()>9) return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(s).matches();
    }

    /**
     * check if the string denotes a double
     * @param s the string need to check
     * @return
     */
    public boolean isDouble(String s)
    {
        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]*");
        return pattern.matcher(s).matches();
    }

	/**
	 * @return the markDescription
	 */
	public boolean isMarkDescription() {
		return markDescription;
	}

	/**
	 * @param markDescription the markDescription to set
	 */
	public void setMarkDescription(boolean markDescription) {
		this.markDescription = markDescription;
	}

	/**
	 * @return the markIngredient
	 */
	public boolean isMarkIngredient() {
		return markIngredient;
	}

	/**
	 * @param markIngredient the markIngredient to set
	 */
	public void setMarkIngredient(boolean markIngredient) {
		this.markIngredient = markIngredient;
	}

	/**
	 * @return the markImage
	 */
	public boolean isMarkImage() {
		return markImage;
	}

	/**
	 * @param markImage the markImage to set
	 */
	public void setMarkImage(boolean markImage) {
		this.markImage = markImage;
	}

	/**
	 * @return the markStep
	 */
	public boolean isMarkStep() {
		return markStep;
	}

	/**
	 * @param markStep the markStep to set
	 */
	public void setMarkStep(boolean markStep) {
		this.markStep = markStep;
	}

	/**
	 * @return the markName
	 */
	public boolean isMarkName() {
		return markName;
	}

	/**
	 * @param markName the markName to set
	 */
	public void setMarkName(boolean markName) {
		this.markName = markName;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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

	/**
	 * @return the star
	 */
	public Button getStar() {
		return star;
	}

	/**
	 * @param star the star to set
	 */
	public void setStar(Button star) {
		this.star = star;
	}

	/**
	 * @return the delete
	 */
	public Button getDelete() {
		return delete;
	}

	/**
	 * @param delete the delete to set
	 */
	public void setDelete(Button delete) {
		this.delete = delete;
	}

	/**
	 * @return the share
	 */
	public Button getShare() {
		return share;
	}

	/**
	 * @param share the share to set
	 */
	public void setShare(Button share) {
		this.share = share;
	}

	/**
	 * @return the editTitle
	 */
	public Button getEditTitle() {
		return editTitle;
	}

	/**
	 * @param editTitle the editTitle to set
	 */
	public void setEditTitle(Button editTitle) {
		this.editTitle = editTitle;
	}

	/**
	 * @return the editDescription
	 */
	public Button getEditDescription() {
		return editDescription;
	}

	/**
	 * @param editDescription the editDescription to set
	 */
	public void setEditDescription(Button editDescription) {
		this.editDescription = editDescription;
	}

	/**
	 * @return the editIngredient
	 */
	public Button getEditIngredient() {
		return editIngredient;
	}

	/**
	 * @param editIngredient the editIngredient to set
	 */
	public void setEditIngredient(Button editIngredient) {
		this.editIngredient = editIngredient;
	}

	/**
	 * @return the editStep
	 */
	public Button getEditStep() {
		return editStep;
	}

	/**
	 * @param editStep the editStep to set
	 */
	public void setEditStep(Button editStep) {
		this.editStep = editStep;
	}

	/**
	 * @return the tf_RecipeName
	 */
	public TextField getTf_RecipeName() {
		return tf_RecipeName;
	}

	/**
	 * @param tf_RecipeName the tf_RecipeName to set
	 */
	public void setTf_RecipeName(TextField tf_RecipeName) {
		this.tf_RecipeName = tf_RecipeName;
	}

	/**
	 * @return the tf_Description
	 */
	public TextField getTf_Description() {
		return tf_Description;
	}

	/**
	 * @param tf_Description the tf_Description to set
	 */
	public void setTf_Description(TextField tf_Description) {
		this.tf_Description = tf_Description;
	}

	/**
	 * @return the tf_Preparation
	 */
	public TextField getTf_Preparation() {
		return tf_Preparation;
	}

	/**
	 * @param tf_Preparation the tf_Preparation to set
	 */
	public void setTf_Preparation(TextField tf_Preparation) {
		this.tf_Preparation = tf_Preparation;
	}

	/**
	 * @return the tf_Cookingtime
	 */
	public TextField getTf_Cookingtime() {
		return tf_Cookingtime;
	}

	/**
	 * @param tf_Cookingtime the tf_Cookingtime to set
	 */
	public void setTf_Cookingtime(TextField tf_Cookingtime) {
		this.tf_Cookingtime = tf_Cookingtime;
	}

	/**
	 * @return the tf_Serveing
	 */
	public TextField getTf_Serveing() {
		return tf_Serveing;
	}

	/**
	 * @param tf_Serveing the tf_Serveing to set
	 */
	public void setTf_Serveing(TextField tf_Serveing) {
		this.tf_Serveing = tf_Serveing;
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
	 * @return the editImgPath
	 */
	public String getEditImgPath() {
		return editImgPath;
	}

	/**
	 * @param editImgPath the editImgPath to set
	 */
	public void setEditImgPath(String editImgPath) {
		this.editImgPath = editImgPath;
	}

	/**
	 * @return the ingredients
	 */
	public LinkedList<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredients(LinkedList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @return the step
	 */
	public LinkedList<Step> getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(LinkedList<Step> step) {
		this.step = step;
	}

	/**
	 * @return the preparationTime
	 */
	public int getPreparationTime() {
		return preparationTime;
	}

	/**
	 * @param preparationTime the preparationTime to set
	 */
	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	/**
	 * @return the cookingTime
	 */
	public int getCookingTime() {
		return cookingTime;
	}

	/**
	 * @param cookingTime the cookingTime to set
	 */
	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	/**
	 * @return the servings
	 */
	public int getServings() {
		return servings;
	}

	/**
	 * @param servings the servings to set
	 */
	public void setServings(int servings) {
		this.servings = servings;
	}

	/**
	 * @return the formerServings
	 */
	public int getFormerServings() {
		return formerServings;
	}

	/**
	 * @param formerServings the formerServings to set
	 */
	public void setFormerServings(int formerServings) {
		this.formerServings = formerServings;
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
	 * @return the addIngredient
	 */
	public LinkedList<Button> getAddIngredient() {
		return addIngredient;
	}

	/**
	 * @param addIngredient the addIngredient to set
	 */
	public void setAddIngredient(LinkedList<Button> addIngredient) {
		this.addIngredient = addIngredient;
	}

	/**
	 * @return the addStep
	 */
	public LinkedList<Button> getAddStep() {
		return addStep;
	}

	/**
	 * @param addStep the addStep to set
	 */
	public void setAddStep(LinkedList<Button> addStep) {
		this.addStep = addStep;
	}

	/**
	 * @return the deleteIngredient
	 */
	public LinkedList<Button> getDeleteIngredient() {
		return deleteIngredient;
	}

	/**
	 * @param deleteIngredient the deleteIngredient to set
	 */
	public void setDeleteIngredient(LinkedList<Button> deleteIngredient) {
		this.deleteIngredient = deleteIngredient;
	}

	/**
	 * @return the deleteStep
	 */
	public LinkedList<Button> getDeleteStep() {
		return deleteStep;
	}

	/**
	 * @param deleteStep the deleteStep to set
	 */
	public void setDeleteStep(LinkedList<Button> deleteStep) {
		this.deleteStep = deleteStep;
	}

	/**
	 * @return the upStep
	 */
	public LinkedList<Button> getUpStep() {
		return upStep;
	}

	/**
	 * @param upStep the upStep to set
	 */
	public void setUpStep(LinkedList<Button> upStep) {
		this.upStep = upStep;
	}

	/**
	 * @return the downStep
	 */
	public LinkedList<Button> getDownStep() {
		return downStep;
	}

	/**
	 * @param downStep the downStep to set
	 */
	public void setDownStep(LinkedList<Button> downStep) {
		this.downStep = downStep;
	}

	/**
	 * @return the ingredientText3
	 */
	public LinkedList<TextField> getIngredientText3() {
		return ingredientText3;
	}

	/**
	 * @param ingredientText3 the ingredientText3 to set
	 */
	public void setIngredientText3(LinkedList<TextField> ingredientText3) {
		this.ingredientText3 = ingredientText3;
	}

	/**
	 * @return the ingredientText2
	 */
	public LinkedList<TextField> getIngredientText2() {
		return ingredientText2;
	}

	/**
	 * @param ingredientText2 the ingredientText2 to set
	 */
	public void setIngredientText2(LinkedList<TextField> ingredientText2) {
		this.ingredientText2 = ingredientText2;
	}

	/**
	 * @return the ingredientText1
	 */
	public LinkedList<TextField> getIngredientText1() {
		return ingredientText1;
	}

	/**
	 * @param ingredientText1 the ingredientText1 to set
	 */
	public void setIngredientText1(LinkedList<TextField> ingredientText1) {
		this.ingredientText1 = ingredientText1;
	}

	/**
	 * @return the stepText
	 */
	public LinkedList<TextField> getStepText() {
		return stepText;
	}

	/**
	 * @param stepText the stepText to set
	 */
	public void setStepText(LinkedList<TextField> stepText) {
		this.stepText = stepText;
	}

	/**
	 * @return the posy
	 */
	public int getPosy() {
		return posy;
	}

	/**
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

    
}
