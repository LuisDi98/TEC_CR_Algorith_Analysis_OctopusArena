package utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class RandomUtil {
    
    public static int getRandom(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}

    public static int getProbabilityRandom(int min, int max,int prob) {
		int range = (max - min) + 1;
                Random rand = new Random();
		return (int)(rand.nextInt(range) * prob) + min;
	}

    List<Double> probs = new ArrayList<>();
    List<Integer> events = new ArrayList<>();
    Double sumProb = 0.0;
    Random rand = new Random();

    public void Distribution(Map<Integer,Double> hashProb){
        for(Integer event : hashProb.keySet()){
            sumProb += hashProb.get(event);
            events.add(event);
            this.probs.add(hashProb.get(event));
        }
    }

    public int sample(){
        int value;
        double prob = rand.nextDouble()*sumProb;
        int i;
        for(i=0; prob>0; i++){
            prob-= probs.get(i);
        }
        return events.get(i-1);
    }
}