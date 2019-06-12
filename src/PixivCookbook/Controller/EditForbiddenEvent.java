package PixivCookbook.Controller;

import PixivCookbook.ForbiddenPair;
import PixivCookbook.Model.SQL_test;
import PixivCookbook.View.ForbiddenEditWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.Arrays;

public class EditForbiddenEvent
{
    /**
     * @param forW
     * @param forbiddenStage
     * @param model
     */
    public void addForbiddenEvent(ForbiddenEditWindow forW, Stage forbiddenStage, SQL_test model)
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
        for(int i=0;i<forW.data.size();i++)
        {
            if(forW.deleteForbidden.size()==0) break;
            if(i>=forW.editForbidden.size()) break;
            int temp=i;
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
                    forW.data.add(temp,new ForbiddenPair("Deafault","Deafault",id));
                    forW.mark.add(temp,1);
                    forW.refresh();
                    addForbiddenEvent(forW,forbiddenStage,model);
                }
            });
        }
    }
}
