package PixivCookbook.Model;
import PixivCookbook.*;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * @author Ling Wei
 *
 */
public class SQL_test {
	private Connection connect;	
	public void run() {
		try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/CookBook?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
						"root", "Fuckyou741@ttg");
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
	
	public void addRecipetoDatabase(Recipe input,int id) {
		PreparedStatement psql;
		try {
			psql = this.connect.prepareStatement("insert into recipe (recipe_id,name,servings,preparationTime,cookingTime,description)"+ "values(?,?,?,?,?,?)");
			psql.setInt(1, id+1);
			psql.setString(2,input.getRecipeName());
			psql.setInt(3,input.getNumberOfEaters());
			psql.setInt(4,input.getPreparationTime());
			psql.setInt(5,input.getCookingTime());
			psql.setString(6,input.getCuisineName());
			
			psql.executeUpdate();

			addIngredientstoDatabase(input,id);
			addStepstoDatabase(input,id);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	private void addStepstoDatabase(Recipe input,int id) throws SQLException{
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
	 * @param id the id contained by the recipe that we are looking for
	 * @return a recipe
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
	 * @param RecipeName
	 * @return the list of id
	 * @author csh
	 * search the matched recipe
	 * fuzzy query
	 * capitalization insensitive
	 */
	public List<Integer> searchAllMatchedRecipes(String RecipeName){
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
	 * @author Spycsh
	 * @return the latest 12 recipes name for the main page
	 * if less than 12 return all
	 */
	public List<String> getRecipesForMainPage() {
		Statement statement;
		List<String> nameList = new LinkedList<String>();
		try {
			statement = this.connect.createStatement();
			String sql = "select * from recipe order by recipe_id desc limit 12";
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				nameList.add(rs.getString("name"));
			}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return nameList;
	}
	
	/**
	 * @author Spycsh
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
					"(select a.ForbidIngredient1, a.ForbidIngredient2 from \r\n" + 
					"(select* from defaultnotallowedpair a\r\n" + 
					"group by ForbidIngredient1,ForbidIngredient2\r\n" + 
					"having count(*)>1\r\n" + 
					")a\r\n" + 
					")\r\n" + 
					"and DefaultNotAllowedPair_id not in\r\n" + 
					"(select  min(DefaultNotAllowedPair_id) from\r\n" + 
					"(select * from defaultnotallowedpair b\r\n" + 
					"group by ForbidIngredient1,ForbidIngredient2 having count(*)>1\r\n" + 
					")b\r\n" + 
					")";
			statement.executeUpdate(sqlDel);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Spycsh
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
}

