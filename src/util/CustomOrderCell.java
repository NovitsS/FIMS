package util;

import Item.OrderListItem;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import data.Optable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import ui.OrderController;
import java.io.IOException;

public class CustomOrderCell extends ListCell<OrderListItem> {

    public void updateItem(OrderListItem o, boolean e) {
        super.updateItem(o, e);
        if (o != null && !e) {
            AnchorPane page = new AnchorPane();
            page.setPrefHeight(110);
            page.setPrefWidth(670);
            Label labelId = new Label(o.getId()+"次航班 "+o.getStart()+"->"+o.getEnd());
            labelId.setTextFill(Color.valueOf("#000000"));
            labelId.setFont(Font.font(18));
            labelId.setLayoutY(5);
            labelId.setLayoutX(40);

            Label labelS = new Label("起飞时间 : "+o.getTime());
            labelS.setTextFill(Color.valueOf("#7c7878"));
            labelS.setFont(Font.font(14));
            labelS.setLayoutY(50);
            labelS.setLayoutX(40);

            Label labelZ = new Label("座次 : "+o.getFtcur());
            labelZ.setTextFill(Color.valueOf("#7c7878"));
            labelZ.setFont(Font.font(14));
            labelZ.setLayoutY(50);
            labelZ.setLayoutX(210);
            Label labelC=null;
            if(o.getItem()==null){
                labelC= new Label("乘客 : "+Main.user.getUname());
            }else labelC= new Label("乘客 : "+o.getItem().getUname());
            labelC.setTextFill(Color.valueOf("#7c7878"));
            labelC.setFont(Font.font(14));
            labelC.setLayoutY(80);
            labelC.setLayoutX(40);

            Label labelZj=null;
            if(o.getItem()==null){
                labelZj= new Label("证件号 : "+Main.user.getUiden());
            }else labelZj= new Label("证件号 : "+o.getItem().getUiden());
            labelZj.setTextFill(Color.valueOf("#7c7878"));
            labelZj.setFont(Font.font(14));
            labelZj.setLayoutY(80);
            labelZj.setLayoutX(210);


            Label labelKind = new Label();
            labelKind.setTextFill(Color.valueOf("#000000"));
            labelKind.setFont(Font.font(18));
            labelKind.setLayoutY(35);
            labelKind.setLayoutX(600);
            if(o.getKind().equals("1"))
                labelKind.setText("头等舱");
            else labelKind.setText("经济舱");

            JFXButton button1=new JFXButton("打印订单");
            button1.setTextFill(Color.valueOf("#f53711"));
            button1.setFont(Font.font(12));
            button1.setLayoutY(80);
            button1.setLayoutX(570);
            button1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        TxtExport.creatTxtFile(o.getId()+o.getTime()+Main.user.getUname());
                        TxtExport.writeTxtFile(o.getId()+"\n"+
                                                o.getStart()+"\n"+
                                                o.getEnd()+"\n"+
                                                o.getStartAir()+"\n"+
                                                o.getEndAir()+"\n"+
                                                o.getTime()+"\n"+
                                                o.getHour()+"\n");
                        o.setIsprint(1);
                        Optable.setPrint(o.getNum());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            JFXButton button2=new JFXButton("退订");
            button2.setTextFill(Color.valueOf("#f53711"));
            button2.setFont(Font.font(12));
            button2.setLayoutY(80);
            button2.setLayoutX(640);
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(o.getIsprint()==1){
                        showMessage("已打印过订单，无法退订");
                    }else {
                        if (o.getKind().equals("1"))
                            o.setFtcur((Integer.parseInt(o.getFtcur()) + 1) + "");
                        else o.setFjcur((Integer.parseInt(o.getFjcur()) + 1) + "");
                        OrderController.orderItems.remove(o);
                        Optable.reFlight(o);
                    }
                }
            });

            page.getChildren().addAll(labelId, labelS, labelZ,labelC,labelZj,button1,button2,labelKind);
            setGraphic(page);
        }else if(e){
            setGraphic(null);
        }
    }

    private void showMessage(String s){
        JFXAlert alert=new JFXAlert((Stage)Main.stage.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setLayoutX(10);
        layout.setLayoutY(100);
        layout.setMaxWidth(300);
        layout.setMaxHeight(200);
        layout.setHeading(new Label("Message:"));

        Label label1 = new Label(s);
        layout.setBody(label1);
        JFXButton closeButton = new JFXButton("取消");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }
}
