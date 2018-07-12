package ui;

import com.jfoenix.controls.*;
import data.Optable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class RegisterController implements Initializable {

    @FXML
    private JFXTextField account;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField iden;
    @FXML
    private JFXTextField tel;
    @FXML
    private JFXButton button;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onRegisterButtonClick(Event event) {
        String acc=account.getText();
        if(acc.equals("")){
            return ;
        }
        String pas=password.getText();
        if(pas.equals("")){
            return ;
        }
        String nam = name.getText();
        if(nam.equals("")){
            return ;
        }
        String ide = iden.getText();
        if(ide.equals("")){
            return ;
        }
        String te = tel.getText();
        if(te.equals("")){
            return ;
        }
        String  s=Optable.registerUser(acc,pas,nam,ide,te);
        if(s.equals("s"))
        {
            //TODO:返回注册页
        }else{
            //TODO:提示错误信息
        }
    }

    private void showError(String s){
        JFXAlert alert=new JFXAlert((Stage)button.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setLayoutX(10);
        layout.setLayoutY(100);
        layout.setMaxWidth(280);
        layout.setMaxHeight(200);
        layout.setHeading(new Label("注册失败"));
        layout.setBody(new Label(s));

        JFXButton closeButton = new JFXButton("确定");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }
}
