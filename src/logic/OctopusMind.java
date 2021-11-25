package logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import utils.RandomUtil;
import java.lang.*;

import model.Chromosome;

//Genetic Algorithm tecnique
public class OctopusMind {

    private final int INITIAL_POPULATION = 100;
    private final int N_GENERATIONS = 5;
    private final double ACCEPTED_PERCENTAGE = 0.6;
    private int octopusEnergy;
    private int playerBet;
    private int maxBet;

    public OctopusMind(int pEnergy, int pPlayerBet, int pMaxBet){
        this.octopusEnergy = pEnergy;
        this.playerBet = pPlayerBet;
        this.maxBet = pMaxBet;
    }

    public ArrayList<Chromosome> fitness(ArrayList<Chromosome> pPopulation, int pEnergy){
        ArrayList<Chromosome> grades = new ArrayList<Chromosome>();
        for(Chromosome guy : pPopulation){
            int fitEnergia = (int) Math.round((pEnergy/100.0)*
                            (255*(1-(100*guy.getGenotype()/255.0)/100.0)) +
                            (((100.0-pEnergy)/100.0)*
                            (255*((100*guy.getGenotype()/255.0)/100.0))));
            int finalFit = (int) Math.round(fitEnergia * ((double)this.playerBet / this.maxBet));
            guy.setFit(finalFit);
            grades.add(guy);
        }
        ArrayList<Chromosome> result = new ArrayList<Chromosome>(
            grades.subList(0, (int)Math.ceil(grades.size()* this.ACCEPTED_PERCENTAGE))
        );
        return result;
    }

    public ArrayList<Chromosome> crossOver(ArrayList<Chromosome> pPopulation){
        ArrayList<Chromosome> nextGeneration = new ArrayList<Chromosome>();
        for(int chromosome = 0; chromosome < pPopulation.size(); chromosome++){
            int father = RandomUtil.getRandom(0, pPopulation.size() - 1);
            int mother = RandomUtil.getRandom(0, pPopulation.size() - 1);
            nextGeneration.add(pPopulation.get(mother).cross(pPopulation.get(father)));
        }
        return nextGeneration;
    }

    //Probabilistic mutation with 15% chance to be mutated
    public String mutate(Chromosome pChoosen){
        int mutationAttempt = RandomUtil.getRandom(0,100);
        if(mutationAttempt < 15){
            mutationAttempt = RandomUtil.getRandom(1 , pChoosen.getBinary().length() - 1); //  111 1 1100
            char bit = pChoosen.getBinary().charAt(mutationAttempt);
            bit = (bit== '1') ? '0' : '1';
            String part1 = pChoosen.getBinary().substring(0, mutationAttempt - 1);
            String part2 = pChoosen.getBinary().substring(mutationAttempt, pChoosen.getBinary().length());
            return (part1 + Character.toString(bit) + part2);
        }
        return pChoosen.getBinary();

    }

    public ArrayList<Chromosome> getGenerations(){
        ArrayList<Chromosome> octopusIdeas = new ArrayList<Chromosome>();
        for(int index = 0; index < this.INITIAL_POPULATION; index++){
            octopusIdeas.add(new Chromosome(RandomUtil.getRandom(0, 256)));
        }
        for(int generation = 0; generation < this.N_GENERATIONS; generation++){
            octopusIdeas = this.fitness(octopusIdeas, octopusEnergy);
            octopusIdeas = this.crossOver(octopusIdeas);
            for(Chromosome gen : octopusIdeas){     //Mutate with 15% chance of success mutation
                gen.setBinary(this.mutate(gen));
            }
        }
        return octopusIdeas;
    }

    public Chromosome selection(){
        ArrayList<Chromosome> lastGen = getGenerations();
        Collections.sort(lastGen, Collections.reverseOrder());
        for(Chromosome p : lastGen){
            System.out.println(p.getGenotype());
        }
        System.out.println(lastGen.get(0));
        return lastGen.get(0);
    }

}
