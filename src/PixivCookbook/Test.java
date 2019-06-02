package PixivCookbook;
import PixivCookbook.Controller.*;
import PixivCookbook.Model.*;
import PixivCookbook.View.*;
public class Test {
	public static void main(String[]args) {
		SQL_test model = new SQL_test();
		Main viewMain = new Main();
		DBController controller = new DBController(model, viewMain);
		
		
		
	}

}
