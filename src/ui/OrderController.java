package ui;

import Item.OrderListItem;
import com.jfoenix.controls.*;
import data.LocalData;
import data.Optable;
import data.ServerData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import util.AutoCompleteComboBoxListener;
import util.CustomCell;
import util.CustomOrderCell;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private JFXButton searchButton,close;
    @FXML
    private JFXComboBox startComboBox;
    @FXML
    private JFXComboBox endComboBox;
    @FXML
    private JFXDatePicker dateTextField;
    @FXML
    private JFXListView jfxListView,jfxListView1;
    @FXML
    private JFXTextField name,iden;
    @FXML
    private JFXButton addUser;
    @FXML
    private Label l1,l2,l3;
    @FXML
    private JFXChipView<String> chipView;
    public static ObservableList<OrderListItem> items=null;
    ObservableList<String> startItems=null;
    ObservableList<String> endItems=null;
    public static ObservableList<OrderListItem> orderItems=null;
    private static String[] s1=new String[]{"经济舱"};
    private static String[] s2=new String[]{"头等舱"};
    private static String[] s3 = new String[]{"经济舱", "头等舱"};



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        l1.setText("name :  "+Main.user.getUname());
        l2.setText("iden : "+Main.user.getUiden());
        l3.setText("tel : "+Main.user.getUtel());
        items=jfxListView.getItems();
        orderItems = jfxListView1.getItems();
        startItems = startComboBox.getItems();
        endItems = endComboBox.getItems();
        ServerData.getCityItem(startItems, endItems);
        ServerData.getSecondItem(orderItems,Main.user.getUnum());
        jfxListView.setCellFactory((e) ->new CustomCell());
        jfxListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        jfxListView.getSelectionModel().selectFirst();
        jfxListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                OrderListItem newValue=(OrderListItem) jfxListView.getSelectionModel().getSelectedItem();
                if(newValue!=null) {
                    OrderListItem item = (OrderListItem) newValue;
                    showMessage(item);
                }
            }
        });
        jfxListView1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        jfxListView1.getSelectionModel().selectFirst();
        jfxListView1.setCellFactory((e)->new CustomOrderCell());
        AutoCompleteComboBoxListener<String> kslistener = new AutoCompleteComboBoxListener<>(startComboBox);
        AutoCompleteComboBoxListener<String> yslistener = new AutoCompleteComboBoxListener<>(endComboBox);
        jfxListView.depthProperty().set(1);
        jfxListView.expandedProperty().set(true);
        jfxListView1.depthProperty().set(30);
        jfxListView1.expandedProperty().set(true);
    }

    @FXML
    public void onSearchButtonClick(Event e){
        String startCity = (String) startComboBox.getValue();
        String endCity = (String) endComboBox.getValue();
        LocalDate ldate = dateTextField.getValue();
        String date=null;
        if(ldate!=null)
            date = ldate.toString();
        if(startCity==null||startCity.equals("")){
            return ;
        }
        if(endCity==null||endCity.equals("")){
            return ;
        }
        if(date==null||date.equals(""))
            ServerData.getOrderListItem(items,startCity,endCity,null);
        else ServerData.getOrderListItem(items,startCity,endCity,date);
    }

    private void showMessage(OrderListItem item){
        int ftcur = Integer.parseInt(item.getFtcur());
        int fjcur = Integer.parseInt(item.getFjcur());
        if(ftcur==0&&fjcur==0){
                showFailed(item.getId()+ "航班机票已售完");
        }else{
            JFXAlert alert=new JFXAlert((Stage)searchButton.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setOverlayClose(false);
            AnchorPane anchorPane=new AnchorPane();
            anchorPane.setPrefWidth(600);
            anchorPane.setPrefHeight(200);
            Label label1 = new Label(item.getId() + " " + item.getStart() + " -> " + item.getEnd());
            Label label2 = new Label(item.getStartAir() + " -> " + item.getEndAir());
            Label label3 = new Label(item.getTime());
            Label label4 = new Label(item.getHour());
            label1.setFont(Font.font(18));
            label1.setLayoutY(33);
            label1.setLayoutX(63);
            label2.setFont(Font.font(18));
            label2.setLayoutY(68);
            label2.setLayoutX(60);
            label3.setFont(Font.font(18));
            label3.setLayoutY(106);
            label3.setLayoutX(98);
            label4.setFont(Font.font(18));
            label4.setLayoutY(140);
            label4.setLayoutX(122);
            JFXComboBox box1=null;
            if(fjcur==0) {
                box1 = new JFXComboBox<String>();
                box1.getItems().addAll(s2);
            }
            else {
                if(ftcur==0) {
                    box1 = new JFXComboBox<String>();
                    box1.getItems().addAll(s1);
                }
                else {
                    box1 = new JFXComboBox<String>();
                    box1.getItems().addAll(s3);
                }
            }
            box1.setLayoutX(496);
            box1.setLayoutY(34);
            box1.setEditable(false);
            box1.setMaxWidth(90);
            box1.setValue(box1.getItems().get(0));
            JFXComboBox box2=new JFXComboBox<String>();
            box2.setLayoutX(496);
            box2.setLayoutY(81);
            box2.setEditable(false);
            box2.setMaxWidth(90);
            box2.setPrefWidth(90);
            box2.getItems().add(Main.user.getUname());
            for(int i=0;i<chipView.getChips().size();i++){
                box2.getItems().add(chipView.getChips().get(i));
            }
            box2.setValue(box2.getItems().get(0));
            JFXButton button1 = new JFXButton("预订");
            button1.setTextFill(Color.valueOf("#000000"));
            button1.setLayoutX(486);
            button1.setLayoutY(153);
            button1.getStyleClass().add("dialog-accept");
            JFXComboBox finalBox = box1;
            button1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    alert.hideWithAnimation();
                    if(item.getNum()!=null&&box2.getValue().toString().equals(Main.user.getUname())){
                        showFailed("您已预订过本次航班");
                    }else{
                        if(!box2.getValue().toString().equals(Main.user.getUname())){
                            item.setItem(LocalData.getuser(box2.getValue().toString()));
                        }
                        String kind = finalBox.getValue().toString();
                        if(kind.equals("头等舱")){
                            int i=ftcur;
                            i--;
                            item.setKind("1");
                            orderItems.add(item);
                            item.setFtcur("" + i);
                            jfxListView.setItems(null);
                            jfxListView.setItems(items);
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Optable.orderFlight(item,box2.getValue().toString(),1);
                             }
                             });
                            thread.start();
                        }else{
                            int i=fjcur;
                            i--;
                            item.setKind("0");
                            orderItems.add(item);
                            item.setFjcur("" + i);
                            jfxListView.setItems(null);
                            jfxListView.setItems(items);
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Optable.orderFlight(item,box2.getValue().toString(),0);
                                }
                            });
                            thread.start();
                        }
                    }
                }
            });
            JFXButton button2 = new JFXButton("取消");
            button2.setTextFill(Color.valueOf("#000000"));
            button2.setLayoutX(541);
            button2.setLayoutY(153);
            button2.getStyleClass().add("dialog-accept");
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    alert.hideWithAnimation();
                }
            });
            anchorPane.getChildren().addAll(label1,label2,label3,label4,box1,box2,button1,button2);
            alert.setContent(anchorPane);
            alert.show();
        }
    }

    private void showFailed(String s){
        JFXAlert alert=new JFXAlert((Stage)searchButton.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        AnchorPane anchorPane=new AnchorPane();
        anchorPane.setPrefWidth(350);
        anchorPane.setPrefHeight(180);
        Label label1 = new Label(s);
        label1.setFont(Font.font(18));
        label1.setLayoutY(80);
        label1.setLayoutX(94);
        JFXButton button2 = new JFXButton("取消");
        button2.setTextFill(Color.valueOf("#000000"));
        button2.setLayoutX(281);
        button2.setLayoutY(150);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.hideWithAnimation();
            }
        });
        button2.getStyleClass().add("dialog-accept");
        anchorPane.getChildren().addAll(label1,button2);
        alert.setContent(anchorPane);
        alert.show();
    }

    @FXML
    public void onCloseClick(Event e){
        Main.closeStage();
    }

    @FXML
    public void onAddUserClick(Event e){
        String n=name.getText();
        String ide = iden.getText();
        if(n==null||n.equals("")){
            showFailed("          姓名不能为空");
            return;
        }

        if(ide==null||ide.equals("")){
            showFailed("          身份证不能为空");
            return;
        }

        String re = ServerData.getUser(n, ide);
        if(re==null)
            showFailed("姓名身份证不匹配");
        else chipView.getChips().add(re);
    }

    private void showMessage(String s){
        JFXAlert alert=new JFXAlert((Stage)close.getScene().getWindow());
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


