
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.OctopusMind;
import model.Chromosome;
import model.Player;
import provider.DataProvider;
import utils.PlayerUtil;
 
public class App extends Application {

    private PlayerUtil playerUtils = PlayerUtil.getInstance();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.generatePlayers();
        playerUtils.initHashMap();      //Loads the hashmap from serialization.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemClassLoader().getResource("view/Home.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Octopus Arena Game~");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void generatePlayers(){
        for(int i = 0; i <15; i++){
            Player newUser = new Player();
            this.playerUtils.newUserName(newUser);
            this.playerUtils.paragraphGenerator(newUser);
            DataProvider.serialize("D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\usersData\\",
                                    newUser);
            Player des = DataProvider.deserialize("D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\usersData\\",
                                                  newUser.getUserName());
            System.out.println(des.getUserName());
            System.out.println(des.getParagraph());
        }

    }

    public static void main(String[] args) {
        OctopusMind ga = new OctopusMind(100, 600, 600); //energy, playerBet, maxBet
        
        int index =10;
        while(index > 0){
            System.out.println("Attempt #" + Math.abs(11-(index)));
            Chromosome test = ga.selection();
            System.out.println("Selection: " + test.getGenotype());
            if((0 < test.getGenotype()) && (test.getGenotype() < 87)){
                System.out.print("\tDEF");
                System.out.println("\tDEF");
            }
            else{
                if((86 < test.getGenotype()) && (test.getGenotype() < 172)){
                    System.out.print("\tATK");
                }
                else{
                    System.out.print("\tMOV");
                }
            }
        index--;
        }
    launch(args);
    }
}