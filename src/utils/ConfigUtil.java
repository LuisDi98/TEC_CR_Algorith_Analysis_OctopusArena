package utils;

public class ConfigUtil {
    
    private static ConfigUtil configUtil = new ConfigUtil();
    private int alphaPlayers;
    private int alphaBet;
    private int secondsPerHour;
    private int maxPlayersPerDay;

    private ConfigUtil(){
        this.alphaPlayers = 10;
        this.alphaBet = 100;
        this.secondsPerHour = 2;
    }

    public static ConfigUtil getInstance(){
        System.out.println("ConfigUtil Singleton Instance");
        return ConfigUtil.configUtil;
    }

    public int getAlphaPlayers(){
        return this.alphaPlayers;
    }

    public int getAlphaBet(){
        return this.alphaBet;
    }

    public int getSecondsPerHour(){
        return this.secondsPerHour;
    }

    public int getMaxPlayersPerDay(){
        return this.maxPlayersPerDay;
    }

    public void setAlphaPlayers(int pAlphaPlayer){
        this.alphaPlayers = pAlphaPlayer;
    }

    public void setAlphaBet(int pAlphaBet){
        this.alphaBet = pAlphaBet;
    }

    public void setSecondsPerHour(int pSecs){
        this.secondsPerHour = pSecs;
    }

    public void setMaxPlayersPerDay(int pMaxPlayersPerDay){
        this.maxPlayersPerDay = pMaxPlayersPerDay;
    }

    @Override
    public String toString(){
        return "ConfigUtil = {\n\tSecondPerHour: " + this.secondsPerHour +
                                "\n\tAlphaCapacity: " + this.alphaPlayers +
                                "\n\tAlphaBet: " + this.alphaBet +
                                "\n\tMaxPlayerPerDay: " + this.maxPlayersPerDay + " }";
    }

}
