package util;

import Item.OrderListItem;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;

import java.io.IOException;
import java.io.InputStream;

public class CustomCell extends ListCell<OrderListItem> {
    public void updateItem(OrderListItem o, boolean e) {
        super.updateItem(o, e);
        if (o != null && !e) {
            FXMLLoader loader = new FXMLLoader();
            InputStream in = Main.class.getResourceAsStream("../ui/item.fxml");
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("../ui/item.fxml"));
            AnchorPane page = null;
            try {
                page = (AnchorPane) loader.load(in);

                Label labelId = new Label(o.getId());
                labelId.setTextFill(Color.valueOf("#0d2f9e"));
                labelId.setFont(Font.font(18));
                labelId.setLayoutY(0);
                labelId.setLayoutX(50);
                labelId.setPrefHeight(80);

                Label labelS = new Label(o.getStart());
                labelS.setTextFill(Color.valueOf("#000000"));
                labelS.setFont(Font.font("Cambria",FontWeight.BOLD,18));
                labelS.setLayoutY(10);
                labelS.setLayoutX(150);
                labelS.setPrefHeight(30);

                Label labelE = new Label(o.getEnd());
                labelE.setTextFill(Color.valueOf("#000000"));
                labelE.setFont(Font.font("Cambria",FontWeight.BOLD,18));
                labelE.setLayoutY(10);
                labelE.setLayoutX(250);
                labelE.setPrefHeight(30);

                Label labelTime = new Label(o.getTime());
                labelTime.setTextFill(Color.valueOf("#0d2f9e"));
                labelTime.setFont(Font.font(18));
                labelTime.setLayoutY(0);
                labelTime.setLayoutX(340);
                labelTime.setPrefHeight(80);

                Label labelLeave = new Label("经济舱剩余: "+o.getFjcur()+" / 头等舱剩余: "+o.getFtcur());
                labelLeave.setTextFill(Color.valueOf("#7c7878"));
                labelLeave.setFont(Font.font(14));
                labelLeave.setLayoutY(40);
                labelLeave.setLayoutX(470);
                labelLeave.setPrefHeight(30);

                Label labelMoney = new Label(o.getFjmon()+" / "+o.getFtmon());
                labelMoney.setTextFill(Color.valueOf("#fc781b"));
                labelMoney.setFont(Font.font(24));
                labelMoney.setLayoutY(10);
                labelMoney.setLayoutX(510);
                labelMoney.setPrefHeight(30);

                Label labelSair = new Label(o.getStartAir());
                labelSair.setTextFill(Color.valueOf("#7c7878"));
                labelSair.setFont(Font.font(14));
                labelSair.setLayoutY(40);
                labelSair.setLayoutX(140);
                labelSair.setPrefHeight(30);

                Label labelEair = new Label(o.getEndAir());
                labelEair.setTextFill(Color.valueOf("#7c7878"));
                labelEair.setFont(Font.font(14));
                labelEair.setLayoutY(40);
                labelEair.setLayoutX(240);
                labelEair.setPrefHeight(30);

                page.getChildren().addAll(labelId, labelS, labelE, labelLeave, labelTime, labelMoney,labelSair,labelEair);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            setGraphic(page);
        }else if(e) setGraphic(null);
    }
}
