package model;

public class Arc <T> {
    
    private Node<T> origin;
    private Node<T> destiny;
    private char weight;
    
    public Arc(Node<T> pOrigin, Node<T> pDestiny, char pWeight){
        origin = pOrigin;
        destiny = pDestiny;
        weight = pWeight;
    }
    
    public Node<T> getOrigin(){
        return origin;
    }
    
    public void setOrigin(Node<T> pOrigin){
        origin = pOrigin;
    }
    
    public Node<T> getDestiny(){
        return destiny;
    }
    
    public void setDestiny(Node<T> pDestiny){
        destiny = pDestiny;
    }
    
    public int getWeight(){
        return weight;
    }
    
    public void setWeight(char pWeight){
        weight = pWeight;
    }

    @Override
    public String toString() {
        return "\nArc{" + "origin=" + origin + ", destiny=" + destiny + ", weight=" + Character.toString(weight) + '}';
    }

}