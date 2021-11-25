package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Arena;
import model.Player;
import provider.DataProvider;
import utils.ArenasUtil;
import utils.ConfigUtil;
import utils.CryptoUtil;
import utils.PlayerUtil;
import utils.ThreadUtil;

public class PlayerController implements Initializable {
    
    //attributes for the Controller
    private ConfigUtil configUtil = ConfigUtil.getInstance();
    private ArenasUtil arenasUtil = ArenasUtil.getInstance();
    private CryptoUtil cryptoUtil = CryptoUtil.getInstance();
    private PlayerUtil playerUtil = PlayerUtil.getInstance();
    private Player player;
    private ThreadUtil timer;
    private Arena selectedArena;

    //attributes for Home.fxml Visual components

    @FXML
    private Pane listPane;

    @FXML
    private ListView<Arena> arenasListView;

    @FXML
    private Pane arenasPane;

    @FXML
    private Group board;

    @FXML
    private Pane finishPane;

    @FXML
    private Label earnedLabel;

    @FXML
    private Label ticketLabel;

    //@FXML methods

    @FXML
    void onSelectedArena(MouseEvent pEvent) {
        //Here we got the arena and timer ran off. Time to change to the arena and start the fight!

        

    }

    @FXML
    void onArenaClicked(MouseEvent pMouseEvent){
        System.out.println("Checking times on GUI");
        Arena arena = this.arenasListView.getSelectionModel().getSelectedItem();
        this.selectedArena = arena;
        if(arena != null){
            System.out.println("Selected one is: " + arena.getName());
            //if si ya la publickey existe, si no existe, hace request en userPublicKeys
            if(this.player.getPublicKey() == 0){
                //request it and assign later
                DataProvider.writeFile("D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\userPublicKeys\\" + this.player.getUserName() + ".txt", 
                "Gimme my key");
            }


            TextInputDialog betDialog = new TextInputDialog("");
            betDialog.setTitle("Check-In into " + arena.getName());
            betDialog.setHeaderText("Get Ready for the battle!\nSuggested Bet: " + arena.getBet());
            betDialog.setContentText("Please enter your bet:");
            Optional<String> actualBet = betDialog.showAndWait();
            if (actualBet.isPresent()){
                System.out.println("Actual bet: " + actualBet.get());
                this.player.setActualBet(Double.parseDouble(actualBet.get()));
            }
            TextInputDialog energyDialog = new TextInputDialog("");
            energyDialog.setTitle("Check-In into " + arena.getName());
            energyDialog.setHeaderText("Get Ready for the battle!\nEnergy between 100 and 20");
            energyDialog.setContentText("Please enter your energy:");
            Optional<String> actualEnergy = energyDialog.showAndWait();
            if (actualEnergy.isPresent()){
                System.out.println("Actual Energy: " + actualEnergy.get());
                this.player.setActualEnergy(Double.parseDouble(actualEnergy.get()));
            }

            

            //Here should be ready the publickey, search by the username and get the public Key
            ArrayList<String> publicKeyList = DataProvider.readFile("D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\userPublicKeys\\" + this.player.getUserName() + ".txt");
            this.player.setPublicKey(Double.parseDouble(publicKeyList.get(0)));
            //Encrypt bet and energy, get encryptedBet and encryptedEnergy
            double encryptedBet = cryptoUtil.encrypt(this.player.getActualBet(), this.player.getPublicKey());
            double encryptedEnergy = cryptoUtil.encrypt(this.player.getActualEnergy(), this.player.getPublicKey());
            //For Encryption, info Format should be "EncryptedBet \n EncryptedEnergy", send it to userEncryption
            DataProvider.writeFile("D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\userEncryption\\" + this.player.getUserName() + ".txt", 
                                    Double.toString(encryptedBet) + "\n" + Double.toString(encryptedEnergy));
            //Search by username on same folder the desencrypted info with format ["bet", "energy"]  using readFile from DataProvider
            ArrayList<String> desencryptedList = DataProvider.readFile("D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\userDecryption\\" + this.player.getUserName() + ".txt");
            //and wait for the arena to start
            
        }
    }

    @FXML
    void onExitPressed(MouseEvent pMouseEvent) {
        this.timer.setAlive(false);
        Stage window = (Stage)((Node)pMouseEvent.getSource()).getScene().getWindow();
        window.close();
    }

    //Controller Methods

    public ListView<Arena> getArenasListView() {
        return arenasListView;
    }

    //This method should add all the arenas for a player and its adjacents, loading the arenasListView on JavaFX
    void loadArenasList(){
        this.arenasListView.getItems().add(new Arena("Arena 1", 2, 10, 
                                                        500, 1, 3, 5));
        this.arenasListView.getItems().add(new Arena("Arena 2", 0, 10, 
                                                        500, 1, 3, 5));
    }

    void earnBalancer(ArrayList<Player> pPlayers){
        double maxBet = 0;
        double minBet = 0;
        double commission = 0.05;
        double minPerPlayer = 0.0;
        double totalBet = 0.0;
        double totalMins = 0.0;
        double totalCommission = 0.0;
        double subNetDist = 0.0;        //To pay after paying mins and commission
        for(Player player : pPlayers){
            if(player.getActualBet() < minBet){
                minBet = player.getActualBet();
            }
            if(player.getActualBet() < maxBet){
                maxBet = player.getActualBet();
            }
            totalBet += player.getActualBet();
        }
        minPerPlayer = minBet - (minBet * commission);
        totalMins = minPerPlayer * pPlayers.size();
        totalCommission = (minBet * commission) * pPlayers.size();
        subNetDist = totalBet - totalMins - totalCommission;
    }

    @Override
	public void initialize(URL url, ResourceBundle rb) {
        this.player = playerUtil.getLastPlayerLoggedIn();
        this.arenasListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.timer = new ThreadUtil(this.arenasListView, this.player);
        this.timer.start();
        loadArenasList();   //Populate ListView
    }

}
