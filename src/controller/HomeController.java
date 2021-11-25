package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.ConfigUtil;
import utils.PlayerUtil;

public class HomeController {
    
    //attributes for the Controller
    private ConfigUtil configUtil = ConfigUtil.getInstance();
    private PlayerUtil playerUtil = PlayerUtil.getInstance();

    //attributes for Home.fxml Visual components
    @FXML
    private TabPane mainLayout;

    @FXML
    private TextField secsXHourInput;

    @FXML
    private TextField alphaCapacityInput;

    @FXML
    private TextField alphaBetInput;

    @FXML
    private TextField maxPlayersInput;

    @FXML
    private TextField usernameInput;

    //methods

    //Methods for FXML

    @FXML
	void onLoginClicked(MouseEvent pMouseEvent) throws IOException {
        //Login logic here
        try {
            String[] userNames = usernameInput.getText().split("_");
            String word1 = userNames[0];
            userNames = userNames[1].split("#");
            String word2 = userNames[0];
            int index = Integer.valueOf(userNames[1]) - 1;
            if(this.playerUtil.getUserMap().get(word1).get(word2).get(index) != null){
                playerUtil.setLastPlayerLoggedIn(this.playerUtil.getUserMap().get(word1).get(word2).get(index));
                Parent playerView = FXMLLoader.load(ClassLoader
                        .getSystemClassLoader().getResource("view/Player.fxml"));
                Scene playerScene = new Scene(playerView);
                Stage window = new Stage();
                window.setScene(playerScene);
                window.show();
            }
        } catch (Exception e) {
            System.err.println(e);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error on Login");
            alert.setHeaderText("Authentication Failed");
            alert.setContentText("Please, enter your credentials again");
            alert.showAndWait();
        }
	}

    @FXML
	void onSaveClicked(MouseEvent mouseEvent) throws IOException {
        System.out.println("\nSaving Configurations...\n");
        this.configUtil.setSecondsPerHour(Integer.parseInt(this.secsXHourInput.getText()));
        this.configUtil.setAlphaPlayers(Integer.parseInt(this.alphaCapacityInput.getText()));
        this.configUtil.setAlphaBet(Integer.parseInt(this.alphaBetInput.getText()));
        this.configUtil.setMaxPlayersPerDay(Integer.parseInt(this.maxPlayersInput.getText()));
        System.out.println("\nSaved!\n");
	}

}
