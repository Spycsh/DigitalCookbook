package PixivCookbook.Controller;

import PixivCookbook.Model.SQL_test;
import PixivCookbook.View.Main;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DBController {
	private SQL_test modelSql_test;
	private Main viewMain;
	
	public DBController(SQL_test viewSql_test, Main viewMain) {
		this.modelSql_test = modelSql_test;
		this.viewMain = viewMain;
		
		this.viewMain.getRecommendButton().addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() { 
			        @Override 
			        public void handle(MouseEvent e) { 
			           System.out.println("flushing...");
			        }
			    });
	}
}
