package utils;

import java.util.HashMap; // import the HashMap class
import java.util.ArrayList; // import the ArrayList class
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import model.Player;
import provider.DataProvider;

public class PlayerUtil {
    
    private static PlayerUtil userUtil = new PlayerUtil();
    private Player lastPlayerLoggedIn;

    //Todos los usuarios registrados
    HashMap<String, HashMap<String, ArrayList<Player>>> userMap = new HashMap<>();
    
    public Player getLastPlayerLoggedIn() {
        return lastPlayerLoggedIn;
    }

    public void setLastPlayerLoggedIn(Player pLastPlayerLoggedIn) {
        this.lastPlayerLoggedIn = pLastPlayerLoggedIn;
    }

    public static PlayerUtil getInstance(){
        return PlayerUtil.userUtil;
    }

    private PlayerUtil(){
        this.userMap = new HashMap<String, HashMap<String, ArrayList<Player>>>();
    }

    public HashMap<String, HashMap<String, ArrayList<Player>>> getUserMap() {
        return userMap;
    }

    // obtener numero random con un tango ej: de 0 a 100
    public int getRandomInRange(int min, int max){
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }
    
    //registro de un nuevo usuario, crea el nombre aleatorio y asigna su numero, tambien lo agrega al hash
    public void newUserName(Player newUser){
        String path1 = "D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\src\\resources\\userNames.txt";
        int rand = getRandomInRange(0,90);
        String word2 = DataProvider.getLine(path1, rand);
        int rand2 = getRandomInRange(91,142);
        String word1 = DataProvider.getLine(path1, rand2);
        String userName = word1 + "_" + word2;
        if(userMap.get(word1)!= null){
            if(userMap.get(word1).get(word2)== null){
                userMap.get(word1).put(word2,new ArrayList<Player>());
            }
        }
        else{
            userMap.put(word1, new HashMap<>());
            userMap.get(word1).put(word2,new ArrayList<Player>());

        }
        userMap.get(word1).get(word2).add(newUser);
        newUser.setName(userName);
        newUser.setNumber(userMap.get(word1).get(word2).size());
    }
    
    public void paragraphGenerator(Player user){
        
        String data = "";
        String message;
        
        Random rand = new Random();
        int maxOctopus = 14;
        int maxBet = 999;
        int messageCuantity = 5;        // Swich cases

        int octopus = rand.nextInt(maxOctopus)+1;
        int octopus2 = rand.nextInt(maxOctopus)+1;

        int bet = rand.nextInt(maxBet)+1;
        int bet2 = rand.nextInt(maxBet)+1;

        int messagePosition = getRandomInRange(0, messageCuantity);
        switch(messagePosition)
        {
           case 0: {
               message = "I would like to spend a maximum of $%s dollars per fight and that are fights of 1 to 1 octopuses";
               data = String.format(message, bet);
                // Declaraciones
                // break es opcional
                break;
            }
           case 1: {
               message = "I would like to spend from $%s to $%s dollars per fight and that are fights of 1 to 1 octopuses";
               data = String.format(message, bet, bet2);
                // break es opcional
                break;
            }
           case 2: {
               message = "I want to play in octopus fights of %s to %s octopuses and bet from $%s to $%s";
               data = String.format(message, octopus, octopus2, bet, bet2);
                // break es opcional
                break;
            }
           case 3: {
               message = "I would like to spend a maximum of $%s dollars per fight in fights with %s octopus";
               data = String.format(message, bet, octopus);
                // break es opcional
                break;
            }
           case 4: {
               message = "I want to play in %s octopus fights and bet from $%s to $%s";
               data = String.format(message, octopus,  bet, bet2);
                // break es opcional
                break;
            }
                
           case 5: {
               message = "I would like to bet between $%s and $%s per fight and that are fights of %s octopuses";
               data = String.format(message, bet, bet2, octopus );
                // break es opcional
                break;
            }
        }
        user.setParagraph(data);
        paragraphReader(data, user);
    }
    
    public void initHashMap(){
        String userDataPath = "D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\usersData\\";
        ArrayList<String> users = DataProvider.getFileNames(userDataPath);
        Collections.sort(users);
        for(String user : users){
            String userName =  user.replace(".ser", "");
            Player loadedUser = DataProvider.deserialize(userDataPath, userName);
            String[] userNames = userName.split("_");
            String word1 = userNames[0];
            userNames = userNames[1].split("#");
            String word2 = userNames[0];
            int number = Integer.valueOf(userNames[1]) - 1;
            if(userMap.get(word1)!= null){
                if(userMap.get(word1).get(word2)== null){
                    userMap.get(word1).put(word2,new ArrayList<Player>());
                }
            }
            else{
                userMap.put(word1, new HashMap<>());
                userMap.get(word1).put(word2,new ArrayList<Player>());
            }
            userMap.get(word1).get(word2).add(loadedUser);
        }
    }
    
    //lectura del parrafo, de momento solo separa los numeros en una lista
    public void paragraphReader(String paragraph, Player user){
        String str = paragraph;  
        //expresion regular
        String numbers = str.replaceAll("[^$.?0-9]+", " ");
        List<String> ranges = Arrays.asList(numbers.trim().split(" "));
        ArrayList<Integer> dollars = new ArrayList<Integer>();
        ArrayList<Integer> octopus = new ArrayList<Integer>();
        for(String number: ranges){
            if(number.contains("$")){
                if(!number.equals("$")){
                    number = number.replace("$", "");
                    System.out.println("Bet  " + number);
                    try{
                        int dollar = (int)Double.parseDouble(number); 
                        dollars.add(dollar);
                    }catch(Exception e){
                        System.out.println("Not a number in ParagraphReader dollars");
                    }
                }
            }
            else{
                if(!number.equals(".")){
                    try{
                        int octo = Integer.parseInt(number); 
                        octopus.add(octo);
                    }catch(Exception e){
                        System.out.println("Not a number in ParagraphReader octopus");
                    }
                }
            }
        }
        if(dollars.size() < 2){
            dollars.add(0);
        }
        if(octopus.size() < 2){
            octopus.add(0);
        }
        Integer[] bet = new Integer[dollars.size()];
        bet = dollars.toArray(bet); 
        user.setBetRange(bet);
        Integer[] fight = new Integer[octopus.size()];
        fight = octopus.toArray(fight);
        user.setFightRange(fight);
        System.out.println("Bet Range size = " + user.getBetRange()[0] + " - " + user.getBetRange()[1]);      
        System.out.println("Octopus Range size = " + user.getFightRange()[0] + " - " + user.getFightRange()[1]);
    }


}
