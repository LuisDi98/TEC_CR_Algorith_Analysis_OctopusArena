package logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Graph;
import model.Arena;
import model.Node;
import model.Player;
import utils.ArenasUtil;
import utils.RandomUtil;


//Probabilistic Algorithm tecnique
public class GraphGenerator{
    private final int attempts = 1000;      //Stands for the quantity of randoms to use in the table

    public GraphGenerator() {
    }

    private boolean findTicket(ArrayList<Integer> tickets, int day){
        for(int ticket: tickets){
            if(ticket == day) return true;
        }
        return false;
    }
    
    // donde 0 es el mejor y 5 el peor
    private int fitDemand(Player player, Arena arena){
        boolean fit, exp, capacity, bet, timeZone, day;
        int weight = 0;
        
        exp = 5 >= Math.abs(player.getExp() - arena.getExperience());
        capacity = (player.getFightRange()[0] <= arena.getCapacity()) && (arena.getCapacity() <= player.getFightRange()[1]);
        bet = (player.getBetRange()[0] <= arena.getBet()) && (arena.getBet()<= player.getBetRange()[1]);
        timeZone = 3 >= Math.abs(player.getTimeZone() - arena.getTimeZone());
        day = findTicket(player.getTickets(), arena.getTicketDay());
        
        ArrayList<Boolean> parameters = new ArrayList(List.of(exp, capacity, bet, timeZone, day));
        for(boolean paramt: parameters){
            weight = paramt ? weight+1 : weight;
        }
        
        if(!timeZone){weight = 0;}
        
        return parameters.size() - weight;              //nos devuelve el peso para ese usario
    }
    
    public GraphGenerator(Player player){
        
        Graph grafo = ArenasUtil.getInstance().getArenasGraph();            
        ArrayList<Node> weight = new ArrayList<>();
        ArrayList<ArrayList<Node>> whole = new ArrayList<ArrayList<Node>>(List.of(weight,weight, weight, weight,weight, weight));
        Graph arenas = ArenasUtil.getInstance().getArenasGraph();
        ArrayList<Node<Arena>> nodos = arenas.getNodes();
        if(!nodos.isEmpty()){

            int manyNodes =  (int)Math.ceil((double)nodos.size()/30.00); //30%
            System.out.println(manyNodes);
            for (int node = 0; node < manyNodes; node++){
                int randomNode = RandomUtil.getRandom(0, nodos.size()-1);
                int review = fitDemand(player ,nodos.get(randomNode).geTValue());
                whole.get(review).add(nodos.get(randomNode));
                System.out.println("fit que no es fit " + review);
            }
            Map<Integer, Double> probMap = new HashMap<Integer,Double>();
            double prob;

            for(int i = 0; i < whole.size(); i++){
                prob = whole.get(i).size()/manyNodes;
                probMap.put(i, prob);
            }

            RandomUtil newRandomUtil = new RandomUtil();
            newRandomUtil.Distribution(probMap);
//            for(int i =0; i < 50; i++){
//                System.out.println(probableRandom);
//            }
            int probableRandom = newRandomUtil.sample();
            System.out.println("Probabilistic Random: " +probableRandom);

            if (probableRandom >=3){
                String name = "Another awesome name for an Arena, like 'Existencial Complex' or something like that";
                int players  = 0;
                int capacity = player.getFightRange()[1];
                int bet = player.getBetRange()[1];
                int timeZone = player.getTimeZone();       //PST, CST, 
                int TicketDay = player.getTickets().get(0);      // From 0 to 6
                int experience = player.getExp();     //Diff should be between 0 and 3 to satisfy player demands
                Arena newArena = new Arena(name,players,capacity,bet,timeZone,TicketDay,experience);
                arenas.addNode(newArena);
                System.out.println(newArena.getName());
            }

            
            ////search random  times in nodos
            
        }
        else{
            String name = "A greate Name for the arena like 'Selfdestructive Path of the TEC' or something like that";
            int players  = 0;
            int capacity = player.getFightRange()[1];
            int bet = player.getBetRange()[1];
            int timeZone = player.getTimeZone();       //PST, CST, 
            int TicketDay = player.getTickets().get(0);      // From 0 to 6
            int experience = player.getExp();     //Diff should be between 0 and 3 to satisfy player demands
            Arena newArena = new Arena(name,players,capacity,bet,timeZone,TicketDay,experience);
            arenas.addNode(newArena);
            System.out.println(newArena.getName());
        }
    }  

}