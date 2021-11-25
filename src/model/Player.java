package model;

import java.util.ArrayList;
import java.util.Collections;
import utils.RandomUtil;

public class Player implements java.io.Serializable{
    String Name;
    Integer Number;
    Integer TimeZone;
    Integer Exp;
    String Paragraph;
    double actualBet;
    double actualEnergy;
    double publicKey;
    Integer[] BetRange;
    Integer[] FightRange;
    ArrayList<Integer> Tickets;
    
    public Player() {
        int timeZone = RandomUtil.getRandom(1, 6);
        this.setTimeZone(timeZone);
        this.setExp(0);
        this.randomTickets();
    }
    
    
    public String getUserName() {
        return Name+ "#" +Number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Integer getTimeZone() {
        return TimeZone;
    }

    public void setTimeZone(Integer TimeZone) {
        this.TimeZone = TimeZone;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer UserNumber) {
        this.Number = UserNumber;
    }

    public String getParagraph() {
        return Paragraph;
    }

    public void setParagraph(String Paragraph) {
        this.Paragraph = Paragraph;
    }

    public Integer getExp() {
        return Exp;
    }

    public void setExp(Integer Exp) {
        this.Exp = Exp;
    }

    public ArrayList<Integer> getTickets() {
        return Tickets;
    }

    private void randomTickets(){
        ArrayList<Integer> tick = new ArrayList<Integer>();
        int many = RandomUtil.getRandom(2, 6);
        
        for(int ticket = 0; ticket < many; ticket++){
            tick.add(RandomUtil.getRandom(0, 6));
        }        
        this.setTickets(tick);
    }
    
    public void usedTicket(int ticket){
         ArrayList<Integer> ticks = this.getTickets();
         ticks.remove(ticks.indexOf(ticket));
         ticks.add(RandomUtil.getRandom(0, 6));
         this.setTickets(ticks);
    }
    
    public void setTickets(ArrayList<Integer> tick) {
        Collections.sort(tick);
        this.Tickets = tick;
    }

  
    public double getActualEnergy() {
        return actualEnergy;
    }

    public void setActualEnergy(double actualEnergy) {
        this.actualEnergy = actualEnergy;
    }

    public double getActualBet() {
        return actualBet;
    }

    public void setActualBet(double pActualBet) {
        this.actualBet = pActualBet;
    }

    public Integer[] getBetRange() {
        return BetRange;
    }

    public void setBetRange(Integer[] BetRange) {
        Integer[] order = new Integer[2];
        if(BetRange[0] >= BetRange[1]){
            order[0] = BetRange[1];
            order[1] = BetRange[0];
        }
        else{
            order[0] = BetRange[0];
            order[1] = BetRange[1];
        }
        this.BetRange = order;
    }

    public Integer[] getFightRange() {
        return FightRange;
    }

    public void setFightRange(Integer[] FightRange) {
        Integer[] order = new Integer[2];
        if(FightRange[0] >= FightRange[1]){
            order[0] = FightRange[1];
            order[1] = FightRange[0];
        }
        else{
            order[0] = FightRange[0];
            order[1] = FightRange[1];
        }
        this.FightRange = order;
    }

    public double getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(double pPublicKey) {
        this.publicKey = pPublicKey;
    }

}

