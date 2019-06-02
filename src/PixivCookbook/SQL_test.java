package PixivCookbook;
/**
 * 
 */


import java.sql.*;
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
	
	public void getAllRecipesfromDatabase(){
		Statement statement;
		try {
			statement = this.connect.createStatement();
			String sql = "select * from recipe";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Recipe contents are shown as followed");
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("recipe_id"+"\t"+"name"+"\t\t"+"servings"+"\t"+"preparationTime"+"\t"+"cookingTime"+"\t"+"description");
			
			int recipe_id = 0;
			String name = null;
			int servings = 0;
			int preparationTime = 0;
			int cookingTime = 0;
			String description = null;
			
			while(rs.next()) {
				recipe_id = rs.getInt("recipe_id");
				name = rs.getString("name");
				servings = rs.getInt("servings");
				preparationTime = rs.getInt("preparationTime");
				cookingTime = rs.getInt("cookingTime");
				description = rs.getString("description");
				System.out.println(recipe_id+"\t\t"+name+"\t\t"+servings+"\t\t"+preparationTime+"\t\t"+cookingTime+"\t"+description);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getRecipesBySearchfromDatabase(String search_name){
		Statement statement;
		String likerecipe_name = new String("%" + search_name + "%");
		try {
			statement = this.connect.createStatement();
			String sql = "select * from recipe where name LIKE '" + likerecipe_name + "'";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Recipe contents are shown as followed");
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("recipe_id"+"\t"+"name"+"\t\t"+"servings"+"\t"+"preparationTime"+"\t"+"cookingTime"+"\t"+"description");
			
			int recipe_id = 0;
			String name = null;
			int servings = 0;
			int preparationTime = 0;
			int cookingTime = 0;
			String description = null;
			
			while(rs.next()) {
				recipe_id = rs.getInt("recipe_id");
				name = rs.getString("name");
				servings = rs.getInt("servings");
				preparationTime = rs.getInt("preparationTime");
				cookingTime = rs.getInt("cookingTime");
				description = rs.getString("description");
				System.out.println(recipe_id+"\t\t"+name+"\t\t"+servings+"\t\t"+preparationTime+"\t\t"+cookingTime+"\t"+description);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getRecipefromDatabase(Recipe output, int id){
		 getIngredientsfromDatabase(output,id);
		 getStepsfromDatabase(output,id);
	}
	
	public void getRecipeBySearchfromDatabase(String recipe_name){
		 getIngredientsBySearchfromDatabase(recipe_name);
		 getStepsBySearchfromDatabase(recipe_name);
	}
	
	private void getIngredientsfromDatabase(Recipe output, int id){
		Statement statement;
		id += 1;
		try {
			statement = this.connect.createStatement();
			String sql = "select * from ingredient where recipe_id = '"+id+"'";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Ingredient contents of "+output.getRecipeName()+" are shown as followed");
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("recipe_id"+"\t"+"name"+"\t\t"+"quantity"+"\t"+"unit"+"\t"+"description");
			
			int recipe_id = 0;
			String name = null;
			double quantity = 0;
			String unit = null;
			String description = null;
			
			while(rs.next()) {
				recipe_id = rs.getInt("recipe_id");
				name = rs.getString("name");
				quantity = rs.getDouble("quantity");
				unit = rs.getString("unit");
				description = rs.getString("description");
				System.out.println(recipe_id+"\t\t"+name+"\t\t"+quantity+"\t\t"+unit+"\t\t"+description);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getStepsfromDatabase(Recipe output, int id){
		Statement statement;
		id += 1;
		try {
			statement = this.connect.createStatement();
			String sql = "select * from preparation_step where recipe_id = '"+id+"'";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Step contents of "+output.getRecipeName()+" are shown as followed");
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("recipe_id"+"\t"+"step"+"\t\t"+"description");
			
			int recipe_id = 0;
			int step = 0;
			String description = null;
			
			while(rs.next()) {
				recipe_id = rs.getInt("recipe_id");
				step = rs.getInt("step");
				description = rs.getString("description");
				System.out.println(recipe_id+"\t\t"+step+"\t\t"+description);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void getIngredientsBySearchfromDatabase(String search_name) {
		Statement statement;
		String likerecipe_name = new String("%" + search_name + "%");
		try {
			statement = this.connect.createStatement();
			String sql = "SELECT * FROM cookbook.ingredient as A right Join cookbook.recipe as B on A.recipe_id = B.recipe_id where B.name LIKE '"
					+ likerecipe_name + "'";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Ingredient contents of the search name are shown as followed");
			System.out.println("-----------------------------------------------------------------------");
			System.out
					.println("recipe_id" + "\t" + "name" + "\t\t" + "quantity" + "\t" + "unit" + "\t" + "description");

			int recipe_id = 0;
			String name = null;
			double quantity = 0;
			String unit = null;
			String description = null;

			while (rs.next()) {
				recipe_id = rs.getInt("recipe_id");
				name = rs.getString("name");
				quantity = rs.getDouble("quantity");
				unit = rs.getString("unit");
				description = rs.getString("description");
				System.out
						.println(recipe_id + "\t\t" + name + "\t\t" + quantity + "\t\t" + unit + "\t\t" + description);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getStepsBySearchfromDatabase(String search_name) {
		Statement statement;
		String likerecipe_name = new String("%" + search_name + "%");
		try {
			statement = this.connect.createStatement();
			String sql = "SELECT * FROM cookbook.preparation_step as A right Join cookbook.recipe as B on A.recipe_id = B.recipe_id where B.name LIKE '"
					+ likerecipe_name + "'";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Step contents of the search name are shown as followed");
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("recipe_id"+"\t"+"step"+"\t\t"+"description");
			
			int recipe_id = 0;
			int step = 0;
			String description = null;
			
			while(rs.next()) {
				recipe_id = rs.getInt("recipe_id");
				step = rs.getInt("step");
				description = rs.getString("description");
				System.out.println(recipe_id+"\t\t"+step+"\t\t"+description);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
