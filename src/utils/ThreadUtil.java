package utils;

import java.util.ArrayList;
import java.util.Optional;

import controller.PlayerController;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import model.Arena;
import model.Player;
import provider.DataProvider;

public class ThreadUtil extends Thread {

    private ListView<Arena> target;
    private Player actualPlayer;
    
    private boolean alive;

    public ThreadUtil(ListView<Arena> pTarget, Player pActualPlayer){
        this.alive = true;
        this.target = pTarget;
        this.actualPlayer = pActualPlayer;
    }

    @Override
    public void run(){  
        
    }
    
    
    public void setAlive(boolean pAlive) {
        this.alive = pAlive;
    }


}
