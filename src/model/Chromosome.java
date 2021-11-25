package model;

public class Chromosome implements Comparable<Chromosome>{
    
    private int genotype;       //From 0 to 255
    private int action;         //0 = ([0, 86] : DEF), 1 = ([87, 171] : ATK), 2 = ([172, 255] : MOVE)
    private String binary;
    private int fit;

    public Chromosome(int pGenotype){
        this.genotype = pGenotype;
        if((0 < this.genotype) && (this.genotype < 87)){
            this.action = 1;
        }
        if((86 < this.genotype) && (this.genotype < 172)){
            this.action = 2;
        }
        if((171 < this.genotype) && (this.genotype < 256)){
            this.action = 3;
        }
        this.binary = Integer.toBinaryString(pGenotype);
        //Integer.parseInt("10101", 2); works like a dream to make it an int
        while(binary.length() < 8){
            binary = Character.toString('0') + binary;
        }
        this.fit = 0;
    }

    public int getGenotype() {
        return genotype;
    }

    public int getAction() {
        return action;
    }

    public String getBinary() {
        return binary;
    }

    public int getFit() {
        return fit;
    }

    public void setBinary(String pBinary) {
        this.binary = pBinary;
        this.genotype = Integer.parseInt(pBinary, 2);
    }

    public void setFit(int pFit) {
        this.fit = pFit;
    }

    public String bitwiseAnd(String pBinMom, String pBinDad){
        String result = "";
        for(int bin = 0; bin < pBinDad.length() ; bin++){
            if((pBinDad.charAt(bin) == pBinMom.charAt(bin)) && (pBinDad.charAt(bin) == '1')){
                result += Character.toString('0');
            } else{
                result += Character.toString('1');
            }
        }
        return result;
    }

    public String bitwiseOr(String pBinMom, String pBinDad){
        String result = "";
        for(int bin = 0; bin < pBinDad.length() ; bin++){
            if((pBinDad.charAt(bin) == pBinMom.charAt(bin)) && (pBinDad.charAt(bin) == '0')){
                result += Character.toString('0');
            } else{
                result += Character.toString('1');
            }
        }
        return result;
    }

    public String bitwiseXor(String pBinMom, String pBinDad){
        String result = "";
        for(int bin = 0; bin < pBinDad.length() ; bin++){
            if(pBinDad.charAt(bin) == pBinMom.charAt(bin)){
                result += Character.toString('0');
            } else{
                result += Character.toString('1');
            }
        }
        return result;
    }

    public Chromosome cross(Chromosome pDad) {
        String firstHalf = this.getBinary().substring(4);   ///0011 \ 0000
        String secondHalf = pDad.getBinary().substring(0, 4);
        while(firstHalf.length() < 8){  //Fill with 0's
            firstHalf = Character.toString('0') + firstHalf;
            secondHalf = secondHalf + Character.toString('0');
        }
        int newGenotype = Integer.parseInt(bitwiseOr(firstHalf, secondHalf), 2);
        return new Chromosome(newGenotype);
    }

    @Override
    public int compareTo(Chromosome pChromosome) {
        if(this.genotype == pChromosome.getFit())  
            return 0;  
        else if(this.genotype > pChromosome.getFit())  
            return 1;  
        else  
            return -1;
    }

}
