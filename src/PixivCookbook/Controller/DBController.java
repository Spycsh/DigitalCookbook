package PixivCookbook.Controller;

import PixivCookbook.Model.ForbiddenPair;
import PixivCookbook.Model.Ingredient;
import PixivCookbook.Model.Recipe;
import PixivCookbook.Model.Step;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

/**
 * this is the interface class dealing with database
 * 
 * @author Ling Wei
 * @author Shen Yu
 * @author Chen Sihan
 * 
 */
public class DBController {
	private Connection connect;
	private final static DBController INSTANCE = new DBController();
	private DBController()
	{

	}
	public static DBController getInstance(){
		return INSTANCE;
	}
	public Connection getConnect() {
		return connect;
	}

	public void setConnect(Connection connect) {
		this.connect = connect;
	}

	public void run() {
		try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/CookBook?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true",
						"root", "Fuckyou741@ttg"); //Fuckyou741@ttg
				System.out.println(this.connect);
				System.out.println("You have successfully connected the server!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error loading MySQL driver!");
				e.printStackTrace();
			}
	}
	
	public void close() throws SQLException {
		this.connect.close();
	}
	
	/**
	 * insert a given recipe to database
	 * always use with assinID function
	 * which given the max id of recipes in database
	 * and insert the recipe as the (maxID+1) recipe
	 * @param input
	 * @param id

	 * 
	 */
	public void addRecipetoDatabase(Recipe input,int id){
		PreparedStatement psql;
		try {
			System.out.println(input.getRecipeName());
			psql = this.connect.prepareStatement("insert into recipe (recipe_id,name,servings,preparationTime,cookingTime,description,imgAddress)"+ "values(?,?,?,?,?,?,?)");
			psql.setInt(1, id+1);
			psql.setString(2,input.getRecipeName());
			psql.setInt(3,input.getNumberOfEaters());
			psql.setInt(4,input.getPreparationTime());
			psql.setInt(5,input.getCookingTime());
			psql.setString(6,input.getCuisineName());
			psql.setString(7,input.getImgAddress());
			try {
				psql.executeUpdate();
			}
			catch (SQLException e) {
			    if (e instanceof SQLIntegrityConstraintViolationException) {
			        //WindowController.alertBoxDuplicate();
			    } else {
			        // Other SQL Exception
			    }
			}
			try {
			addIngredientstoDatabase(input,id);
			addStepstoDatabase(input,id);
			}catch (SQLException e) {
			    if (e instanceof SQLIntegrityConstraintViolationException) {
			    	// Duplicate entry
			    } else {
			        // Other SQL Exception
			    }
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	/**
	 * assign ID for new recipe
	 * @return the largest current ID 
	 */
	public int assignID() {
		PreparedStatement psql;
		try {
			int cnt=0;
			psql = this.connect.prepareStatement("select recipe_id from recipe");
			ResultSet rs = psql.executeQuery();
			//System.out.println("haha");
			while(rs.next()) {
			cnt=max(cnt, rs.getInt("recipe_id"));
			//System.out.println(cnt+1);
			}
			return cnt+1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return -1;
	}
	
	/**
	 * delete recipe by id
	 * @param id
	 * 
	 */
	public void deleteRecipefromDatabase(int id) {
		PreparedStatement psql;
		try {
			psql = this.connect.prepareStatement("DELETE FROM recipe WHERE recipe_id = '"+id+"'");
			psql.executeUpdate();
			psql = this.connect.prepareStatement("UPDATE recipe SET recipe_id = recipe_id-1 WHERE recipe_id > '"+id+"'");
			psql.executeUpdate();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * add ingredients by recipe id
	 * @param input		 the recipe that the ingredient belongs to
	 * @param id the ingredients
	 * @throws SQLException sequel exception
	 * 
	 * 
	 */
	private void addIngredientstoDatabase(Recipe input,int id) throws SQLException {
		PreparedStatement psql;
		for(int i = 0; i<input.getIngredients().size();i++) {
			psql = this.connect.prepareStatement("insert into ingredient (recipe_id,name,quantity,unit,description)"+ "values(?,?,?,?,?)");
			psql.setInt(1, id+1);
			psql.setString(2,input.getIngredients().get(i).getName());
			psql.setDouble(3,input.getIngredients().get(i).getNum());
			psql.setString(4,input.getIngredients().get(i).getUnit());
			psql.setString(5,input.getIngredients().get(i).getPreparation());			
			psql.executeUpdate();
		}
	}
	
	/**
	 * 
	 * delete all ingredients first and insert into database a new one
	 * @param input  	 the list of ingredient
	 * @param id  		 the recipe id
	 * @throws SQLException
	 * 
	 * 
	 * 
	 */
	public void addIngredientstoDatabase(List<Ingredient> input,int id) throws SQLException {
		PreparedStatement psql;
		psql = this.connect.prepareStatement("delete from ingredient where recipe_id ='"+id+"'");
		psql.executeUpdate();
		for(int i = 0; i<input.size();i++) {
			psql = this.connect.prepareStatement("insert into ingredient (recipe_id,name,quantity,unit,description)"+ "values(?,?,?,?,?)");
			psql.setInt(1, id);
			psql.setString(2,input.get(i).getName());
			psql.setDouble(3,input.get(i).getNum());
			psql.setString(4,input.get(i).getUnit());
			psql.setString(5,input.get(i).getPreparation());			
			psql.executeUpdate();
		}
	}
	
	/**
	 * 
	 * add steps by recipe id
	 * @param input		// the recipe the steps belong to
	 * @param id		// the recipe id
	 * @throws SQLException
	 * @throws SQLIntegrityConstraintViolationException
	 * 
	 */
	private void addStepstoDatabase(Recipe input,int id) throws SQLException,SQLIntegrityConstraintViolationException{
		PreparedStatement psql;
		for(int i = 0; i<input.getSteps().size();i++) {
			psql = this.connect.prepareStatement("insert into preparation_step (recipe_id,step,description)"+ "values(?,?,?)");
			psql.setInt(1, id+1);
			psql.setInt(2,input.getSteps().get(i).getStepNumber());
			psql.setString(3,input.getSteps().get(i).getContent());			
			psql.executeUpdate();
		}
	}
	
	/**
	 * add steps to the given recipe
	 * @param input		// the list of step of the recipe
	 * @param id		// the recpie id
	 * @throws SQLException
	 * @throws SQLIntegrityConstraintViolationException
	 * 
	 */
	public void addStepstoDatabase(List<Step> input,int id) throws SQLException,SQLIntegrityConstraintViolationException{
		PreparedStatement psql;
		psql = this.connect.prepareStatement("delete from preparation_step where recipe_id ='"+id+"'");
		psql.executeUpdate();
		for(int i = 0; i<input.size();i++) {
			psql = this.connect.prepareStatement("insert into preparation_step (recipe_id,step,description)"+ "values(?,?,?)");
			psql.setInt(1, id);
			psql.setInt(2,i+1);
			psql.setString(3,input.get(i).getContent());			
			psql.executeUpdate();
		}
	}
	
	/**
	 * 
	 * update the recipe name to be the given one
	 * @param id		 the recipe id
	 * @param newName	 the new name
	 * 
	 */
	public void saveRecipName(int id, String newName) {
		Statement statement;
		try {
			statement = this.connect.createStatement();
			String sql = "UPDATE cookbook.recipe SET name ='"+newName+"'"+"WHERE recipe_id ='"+id+"'";
			try {
			statement.execute(sql);
			}catch (SQLException e) {
			    if (e instanceof SQLIntegrityConstraintViolationException) {
			        //WindowController.alertBoxDuplicate();
			    } else {
			        // Other SQL Exception
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *
	 * update the recipe name to be the given one
	 * @param newName	 the new name
	 *
	 */
	public boolean judgeRecipName(String newName) {
		Statement statement;
		try {
			statement = this.connect.createStatement();
			int cnt=0;
			String sql ="select * from recipe where name = '" + newName + "'";
			try {
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					cnt++;
				}
				if(cnt==0) return true;
				else return false;
			}catch (SQLException e) {

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * update the description of the given recipe
	 * @param id				 recipe id
	 * @param newDescription	 the new descrition of recipe
	 * 
	 */
	public void saveDescription(int id, String newDescription) {
		Statement statement;
		try {
			statement = this.connect.createStatement();
			String sql = "UPDATE cookbook.recipe SET description ='"+newDescription+"'"+"WHERE recipe_id ='"+id+"'";
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * update the serving of the given recipe
	 * @param id			 the recipe id
	 * @param newserving	 the new serving
	 * 
	 */
	public void saveServings(int id, double newserving) {
		Statement statement;
		try {
			statement = this.connect.createStatement();
			String sql = "UPDATE cookbook.recipe SET servings ='"+(int)newserving+"'"+"WHERE recipe_id ='"+id+"'";
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * update the time of the given recipe
	 * @param id					 the recipe id
	 * @param newPreparationtime	 the new Preparation time
	 * 
	 */
	public void savePreparationtime(int id, int newPreparationtime) {
		Statement statement;
		try {
			statement = this.connect.createStatement();
			String sql = "UPDATE cookbook.recipe SET preparationTime ='"+(int)newPreparationtime+"'"+"WHERE recipe_id ='"+id+"'";
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param id			 the recipe id
	 * @param newCooktime	 the new cook time
	 * 
	 */
	public void saveCooktime(int id, int newCooktime) {
		Statement statement;
		try {
			statement = this.connect.createStatement();
			String sql = "UPDATE cookbook.recipe SET cookingTime ='"+(int)newCooktime+"'"+"WHERE recipe_id ='"+id+"'";
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param id		 the recipe id
	 * @param newPath	 the new path of the recipe
	 */
	public void saveImagePath(int id, String newPath) {
		PreparedStatement psql;
		try {
			if(newPath != "") {
				String sql = "UPDATE cookbook.recipe SET imgAddress =? WHERE recipe_id = ?";
				psql = this.connect.prepareStatement(sql);
				psql.setString(1, newPath);
				psql.setInt(2, id);
				psql.executeUpdate();
			}else {
				newPath = new String("img"+"\\\\"+"Default.jpg");
				String sql = "UPDATE cookbook.recipe SET imgAddress ='"+newPath+"'"+"WHERE recipe_id ='"+id+"'";
				psql = this.connect.prepareStatement(sql);
				psql.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return 	 all the recipe names in the database as a list
	 * 
	 */
	public List<String> getAllRecipeNamesfromDatabase(){
		List<String> names = new LinkedList<String>();
		Statement statement;
		try {
			statement = this.connect.createStatement();
			String sql = "select name from recipe";
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				names.add(rs.getString("name"));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
		
	}
	/**
	 * @param id the id contained by the recipe that we are looking for
	 * @return the recipe
	 */
	public Recipe getRecipeBySearchfromDatabase(int id){
		Statement statement;
		Recipe recipe = new Recipe("","",0);
		try {
			statement = this.connect.createStatement();
			String sql = "select * from recipe where recipe_id = '" + id + "'";
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				recipe = new Recipe(rs.getString("name"),rs.getString("description"),rs.getInt("servings"));
				recipe.setCookingTime(rs.getInt("cookingTime"));
				recipe.setPreparationTime(rs.getInt("preparationTime"));
                recipe.setImgAddress(rs.getString("imgAddress"));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recipe;
	}
	
	
	/**
	 * @param id the id contained by ingredients that we are looking for
	 * @return a list of ingredients
	 */
	public List<Ingredient> getIngredientsfromDatabase(int id){
		Statement statement;
		List<Ingredient> ingredientList = new LinkedList<Ingredient>();
		try {
			statement = this.connect.createStatement();
			String sql = "select * from ingredient where recipe_id = '"+id+"'";
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				Ingredient ingredient = new Ingredient(rs.getString("name"),rs.getDouble("quantity"),rs.getString("unit"),rs.getString("description"));
				ingredientList.add(ingredient);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredientList;
	}
	
	
	/**
	 * @param id the id contained by steps that we are looking for
	 * @return a list of steps
	 */
	public List<Step> getStepsfromDatabase(int id){
		Statement statement;
		List<Step> stepList = new LinkedList<Step>();
		try {
			statement = this.connect.createStatement();
			String sql = "select * from preparation_step where recipe_id = '"+id+"'";
			ResultSet rs = statement.executeQuery(sql);			
			
			while(rs.next()) {
				Step step = new Step(rs.getString("description"),rs.getInt("step"));
				stepList.add(step);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stepList;
	}
	

	
	/**
	 * fuzzy query
	 * search the matched recipe
	 * capitalization insensitive
	 * @param RecipeName
	 * @return the list of id
	 * @author csh
	 * 
	 */
	public List<Recipe> searchAllMatchedRecipes(String RecipeName) throws IndexOutOfBoundsException{
		List<Recipe> matchedRecipeIdList = new LinkedList<Recipe>();
		Recipe recipe = new Recipe("","",0);
		PreparedStatement psql;
		try {
			psql = this.connect.prepareStatement("select * from Recipe where name like '%"+RecipeName+"%'");
		
			ResultSet rs = psql.executeQuery();
			while(rs.next()) {
				recipe = new Recipe(rs.getString("name"),rs.getString("description"),rs.getInt("servings"));
				recipe.setCookingTime(rs.getInt("cookingTime"));
				recipe.setPreparationTime(rs.getInt("preparationTime"));
                recipe.setImgAddress(rs.getString("imgAddress"));
                matchedRecipeIdList.add(recipe);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchedRecipeIdList;
	}
	
	/**
	 * @param RecipeName
	 * @return the id the recipe name matches to
	 */
	public int getIDbyName(String RecipeName){
		int id = 0;
		PreparedStatement psql;
		try {
			psql = this.connect.prepareStatement("select recipe_id from Recipe where name = '"+RecipeName+"'");
		
			ResultSet resultSet = psql.executeQuery();
			while(resultSet.next()) {
				id = resultSet.getInt("recipe_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * @param RecipeName
	 * @return a list of matched recipes with given names
	 * @throws IndexOutOfBoundsException
	 */
	public List<Integer> searchAllMatchedID(String RecipeName) throws IndexOutOfBoundsException{
		List<Integer> matchedRecipeIdList = new LinkedList<Integer>();
		PreparedStatement psql;
		try {
			psql = this.connect.prepareStatement("select * from Recipe where name like '%"+RecipeName+"%'");
		
			ResultSet resultSet = psql.executeQuery();
			while(resultSet.next()) {
				int r = resultSet.getInt("recipe_id");
				matchedRecipeIdList.add(r);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchedRecipeIdList;
	}
	
	/**
	 * if less than 7 return all
	 * return the random 7 recipes name for the main page
	 * @return the random list
	 * 
	 */
	public List<Recipe> getRecipesForMainPage() {
		Statement statement;
		List<Recipe> nameList = new LinkedList<Recipe>();
		try {
			statement = this.connect.createStatement();
			String sql = "select * from recipe order by rand() desc limit 7";
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				Recipe recipe = new Recipe(rs.getString("name"),rs.getString("description"),rs.getInt("servings"));
				recipe.setCookingTime(rs.getInt("cookingTime"));
				recipe.setPreparationTime(rs.getInt("preparationTime"));
                recipe.setImgAddress(rs.getString("imgAddress"));
                nameList.add(recipe);
			}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return nameList;
	}
	
	/**
	 * @param p1 ForbidIngredient1
	 * @param p2 ForbidIngredient2
	 */
	public void addForbiddenPair(String p1, String p2){
		PreparedStatement psql;
		int pairId = 0;
		try {
			
			
			// find the latest insert pair id
			Statement statement = this.connect.createStatement();
			String sql = "select * from DefaultNotAllowedPair order by defaultNotAllowedPair_id desc limit 1";
			ResultSet rs =  statement.executeQuery(sql);
			while(rs.next()) {
				pairId =rs.getInt("DefaultNotAllowedPair_id");
			}
			psql = this.connect.prepareStatement("insert into DefaultNotAllowedPair (DefaultNotAllowedPair_id,ForbidIngredient1,ForbidIngredient2)"+ "values(?,?,?)");
			psql.setInt(1, pairId+1);
			psql.setString(2,p1);
			psql.setString(3,p2);
			psql.executeUpdate();
			
			// delete the repeated record
			Statement statementDeleteToOne = this.connect.createStatement();
			String sqlDel = "delete from defaultnotallowedpair where (defaultnotallowedpair.ForbidIngredient1,defaultnotallowedpair.ForbidIngredient2) in \r\n" + 
					"(select ForbidIngredient1,ForbidIngredient2 from\r\n" + 
					"(select a.ForbidIngredient1,a.ForbidIngredient2  from defaultnotallowedpair a\r\n" + 
					"group by ForbidIngredient1,ForbidIngredient2\r\n" + 
					"having count(*)>1)\r\n" + 
					"a)\r\n" + 
					"and defaultnotallowedpair.DefaultNotAllowedPair_id not in\r\n" + 
					"(SELECT DefaultNotAllowedPair_id from\r\n" + 
					"(select min(DefaultNotAllowedPair_id) as DefaultNotAllowedPair_id from defaultnotallowedpair b\r\n" + 
					"group by ForbidIngredient1,ForbidIngredient2\r\n" + 
					"having count(*)>1)\r\n" + 
					"b)";
			statement.executeUpdate(sqlDel);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param p1
	 * @return the list that the given ingredient cannot match
	 */
	public List<String> getForbiddenPair(String p1) {
		List<String> matchedRecipeIdList = new LinkedList<String>();
		PreparedStatement psql;
		try {
			psql = this.connect.prepareStatement("select ForbidIngredient1 from DefaultNotAllowedPair where ForbidIngredient2='"+
					p1+"'UNION"+" "+"select ForbidIngredient2 from defaultnotallowedpair where ForbidIngredient1='"+p1+"'"							
					);
		
			ResultSet resultSet = psql.executeQuery();
			while(resultSet.next()) {
				String opponentIngredient = resultSet.getString("ForbidIngredient1");
				matchedRecipeIdList.add(opponentIngredient);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchedRecipeIdList;
	}

	/**
	 * @return a list of all forbidden pairs
	 */
	public LinkedList<ForbiddenPair> getAllForbiddenPair() {
		LinkedList<ForbiddenPair> matchedRecipeIdList = new LinkedList<ForbiddenPair>();
		PreparedStatement psql;
		try {
			psql = this.connect.prepareStatement("select * from DefaultNotAllowedPair"
			);

			ResultSet resultSet = psql.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("DefaultNotAllowedPair_id");
				String first = resultSet.getString("ForbidIngredient1");
				String second = resultSet.getString("ForbidIngredient2");
				matchedRecipeIdList.add(new ForbiddenPair(first,second,id));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchedRecipeIdList;
	}

	/**
	 * delete all forbidden pairs
	 */
	public void deleteAllForbiddenPair() {
		PreparedStatement psql;
		try {
			psql = this.connect.prepareStatement("delete from defaultnotallowedpair "
			);

			psql.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

