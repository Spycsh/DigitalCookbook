package PixivCookbook.Controller;

import PixivCookbook.Model.ForbiddenPair;
import PixivCookbook.Controller.DBController;
import PixivCookbook.View.ForbiddenEditWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.Arrays;

public class EditForbiddenPairController
{
    /**

     * @param forW forbidden edit window
     * @param forbiddenStage forbidden stage
     * @param model	 the functions to operate the database
     * bind the forbidden pair window
     * with edit, add and delete function
     * and the home button to go back to shut down the stage

     */
    public void addForbiddenEvent(ForbiddenEditWindow forW, Stage forbiddenStage, DBController model)
    {
        forW.home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                forW.savedata();
                model.deleteAllForbiddenPair();
                for(int k=0;k<forW.data.size();k++)
                    model.addForbiddenPair(forW.data.get(k).getKey(),forW.data.get(k).getValue());
                forbiddenStage.close();
            }
        });
        
        // each line has three buttons: edit, add and delete
        for(int i=0;i<forW.data.size();i++)
        {
            if(forW.deleteForbidden.size()==0) break;
            if(i>=forW.editForbidden.size()) break;
            int temp=i;
            
            // edit button
            forW.editForbidden.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(forW.mark.get(temp).equals(1)&&temp<forW.forbidenText1.size())
                    {
                        //System.out.println(forW.forbidenText1.size());
                        String s1 ="";
                        String s2 ="";
                        int j=temp;
                        //for(int j=0;j<forW.forbidenText1.size();j++)
                        {
                            int id = forW.data.get(j).getId();
                            s1=forW.forbidenText1.get(j).getText();
                            s2=forW.forbidenText2.get(j).getText();
                            model.deleteAllForbiddenPair();
                            forW.data.set(j,new ForbiddenPair(s1,s2,forW.data.get(j).getId()));
                            for(int k=0;k<forW.data.size();k++)
                                model.addForbiddenPair(forW.data.get(k).getKey(),forW.data.get(k).getValue());
                        }
                    }
                    forW.mark.set(temp,1-forW.mark.get(temp));
                    forW.refresh();
                    addForbiddenEvent(forW,forbiddenStage,model);
                }
            });
            
            // delete button
            forW.deleteForbidden.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    forW.savedata();
                    forW.data.remove(temp);
                    forW.mark.remove(temp);
                    forW.mark.add(1);
                    if(forW.data.size()==0) forW.data.add(new ForbiddenPair("a","b",0));
                    forW.refresh();
                    addForbiddenEvent(forW,forbiddenStage,model);
                }
            });
            
            // add button
            forW.addForbidden.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int id=0;
                    Integer a[]=new Integer[20000];
                    int cnt=0;
                    for(int j=0;j<forW.data.size();j++)
                    {
                        a[cnt++]=forW.data.get(j).getId();
                    }
                    Arrays.sort(a,0,cnt);
                    for(int j=0;j<cnt;j++)
                    {
                        if(id!=a[j]) break;
                        id++;
                    }
                    //System.out.println(id);
                    forW.savedata();
                    forW.data.add(temp,new ForbiddenPair("Default","Default",id));
                    forW.mark.add(temp,1);
                    forW.refresh();
                    addForbiddenEvent(forW,forbiddenStage,model);
                }
            });
        }
    }
}
