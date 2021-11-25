package model;

public class Arena {
    
    private String name;        //This is hust to display the name on the GUI ListView
    private int players;
    private int capacity;
    private int bet;
    private int timeZone;       //PST, CST, 
    private int TicketDay;      // From 0 to 6
    private int experience;     //Diff should be betwween 0 and 3 to satisfy player demands

    public Arena(String pName, int pPlayers, int pCap, int pBet,
                int pTimeZone, int pTicketDay, int pExp){
        this.name = pName;
        this.players = pPlayers;
        this.capacity = pCap;
        this.bet = pBet;
        this.timeZone = pTimeZone;
        this.TicketDay = pTicketDay;
        this.experience = pExp;
    }

    public String getName() {
        return name;
    }

    public int getPlayers() {
        return players;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBet() {
        return bet;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public int getTicketDay() {
        return TicketDay;
    }

    public int getExperience() {
        return experience;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public void setBet(int bet) {
        this.bet = bet;
    }
    
    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }
    
    public void setTicketDay(int ticketDay) {
        TicketDay = ticketDay;
    }
    
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString(){
        return this.name;
    }

}
