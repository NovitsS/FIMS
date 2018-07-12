package ui;

import Item.OrderListItem;
import com.jfoenix.controls.*;
import data.Optable;
import data.ServerData;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXButton forgetButton;
    @FXML
    private JFXTextField accountTextField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXCheckBox box;

    public void onLoginButtonClick(Event event){
        String account = accountTextField.getText();
        String password = passwordField.getText();
        String upassword = null;
        if(!box.isSelected()) {
            try {
                upassword = ServerData.getUserPassword(account);
                if (password.equals(upassword)) {
                    Main.replaceSceneContent("../ui/order.fxml", 700, 500);
                    System.out.println("login succeed");
                } else {
                    showMessage();
                    System.out.println("login failed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                if(account.equals(Main.admin)&&password.equals(Main.aPassword))
                    Main.replaceSceneContentA("../ui/admin.fxml", 600, 500);
                else showMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("login succeed");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onRegisterButtonClick(Event event){
        try {
            Main.replaceSceneContent("../ui/register.fxml",300,400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onForgetButtonClick(Event event){
        Main.closeStage();
    }

    private void showMessage(){
        JFXAlert alert=new JFXAlert((Stage)loginButton.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setLayoutX(10);
        layout.setLayoutY(100);
        layout.setMaxWidth(300);
        layout.setMaxHeight(200);
        layout.setHeading(new Label("Message:"));

        Label label1 = new Label("          账户密码不匹配");
        layout.setBody(label1);
        JFXButton closeButton = new JFXButton("取消");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

}
