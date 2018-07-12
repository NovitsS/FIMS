package ui;

import Item.Flight;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import util.CustomCell;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private JFXButton addButton,addButton1,close;
    @FXML
    private JFXTextField fname,fjmax,ftmax,fjmon,ftmon;
    @FXML
    private JFXTextField city,air;
    @FXML
    private JFXComboBox<String> start,end;

    @FXML
    private JFXListView<Flight> listView;
    ObservableList<String> startItems=null;
    ObservableList<String> endItems=null;
    ObservableList<Flight> flights=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startItems = start.getItems();
        endItems = end.getItems();
        ServerData.getCityItem(startItems,endItems);
        listView.setItems(LocalData.flights);
        listView.setCellFactory((e) ->new ListCell<Flight>(){
            public void updateItem(Flight o, boolean e){
                super.updateItem(o, e);
                if (o != null && !e) {
                    AnchorPane page = new AnchorPane();
                    page.setPrefHeight(60);
                    page.setPrefWidth(580);
                    page.setMaxWidth(600);
                    Label labelId = new Label(o.getFname());
                    labelId.setTextFill(Color.valueOf("#000000"));
                    labelId.setFont(Font.font(18));
                    labelId.setLayoutY(18);
                    labelId.setLayoutX(20);

                    Label labelS = new Label(o.getFtime());
                    labelS.setTextFill(Color.valueOf("#7c7878"));
                    labelS.setFont(Font.font(18));
                    labelS.setLayoutY(18);
                    labelS.setLayoutX(115);

                    Label labelZ = new Label(o.getFcur()+"/"+o.getFmax());
                    labelZ.setTextFill(Color.valueOf("#7c7878"));
                    labelZ.setFont(Font.font(14));
                    labelZ.setLayoutY(21);
                    labelZ.setLayoutX(250);

                    Label labelM = new Label("满座率 : ");
                    labelM.setTextFill(Color.valueOf("#7c7878"));
                    labelM.setFont(Font.font(14));
                    labelM.setLayoutY(21);
                    labelM.setLayoutX(412);

                    double i =(Double.parseDouble(o.getFmax())- Double.parseDouble(o.getFcur())) / Double.parseDouble(o.getFmax());
                    int s=(int)(i*100);
                    Label labelC = new Label(s+"%");
                    labelC.setTextFill(Color.valueOf("#000000"));
                    labelC.setFont(Font.font(18));
                    labelC.setLayoutY(18);
                    labelC.setLayoutX(500);

                    page.getChildren().addAll(labelId, labelS, labelZ,labelM,labelC);
                    setGraphic(page);
                }else if(e) setGraphic(null);
            }
        });
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectFirst();
    }
   @FXML
    public void onAddButtonClick(Event e){
        String fn=fname.getText();
       String ftma = ftmax.getText();
       String ftmo = ftmon.getText();
       String fjma = fjmax.getText();
       String fjmo = fjmon.getText();
        String st=start.getValue();
        String en=end.getValue();
        if(fn==null||fn.equals(""))
        {
            showMessage("航班编号");
            return;
        }
       if(st==null||st.equals(""))
       {
           showMessage("起始地");
           return;
       }
       if(en==null||en.equals(""))
       {
           showMessage("目的地");
           return;
       }
       if(ftmo==null||ftmo.equals(""))
       {
           showMessage("头等舱价格");
           return;
       }
       if(ftma==null||ftma.equals(""))
       {
           showMessage("头等舱位");
           return;
       }
       if(fjmo==null||fjmo.equals(""))
       {
           showMessage("经济舱价格");
           return;
       }
       if(fjma==null||fjma.equals(""))
       {
           showMessage("经济舱位");
           return;
       }
               Optable.addFlight(fn,st,en,ftma,ftmo,fjma,fjmo);
               ServerData.getFlights(LocalData.flights);
//       Thread thread=new Thread(new Runnable() {
//           @Override
//           public void run() {
//               Optable.addFlight(fn,st,en,ftma,ftmo,fjma,fjmo);
//               ServerData.getFlights(LocalData.flights);
//           }
//       });
//        thread.start();

    }
    @FXML
    public void onCloseClick(Event e){
        Main.closeStage();
    }

    @FXML
    public void onAddCityClick(Event e){
        String c=city.getText();
        String a=air.getText();
        if(c==null||c.equals(""))
        {
            showMessage("城市");
            return;
        }
        if(a==null||a.equals(""))
        {
            showMessage("机场");
            return;
        }
        Optable.addCity(c,a);
        startItems.clear();
        endItems.clear();
        ServerData.getCityItem(startItems,endItems);
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

        Label label1 = new Label("          "+s+"不能为空");
        layout.setBody(label1);
        JFXButton closeButton = new JFXButton("取消");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }
}
