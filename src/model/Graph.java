package model;

import java.util.ArrayList;

public class Graph <T>{
    
    private ArrayList<Node<T>> nodeList;
    private ArrayList<Arc<T>> arcList;
    
    public Graph() {
        nodeList = new ArrayList<Node<T>>();
        arcList = new ArrayList<Arc<T>>();
    }
    
    public Node<T> addNode(T pValue){       
        Node<T> newNode = new Node<T>(pValue);
        nodeList.add(newNode);
        return newNode;
    }
    
    public void addArc(Node<T> pOrigin, Node<T> pDestiny, char pWeight){
        Arc<T> newArc = new Arc<T>(pOrigin, pDestiny, pWeight);
        arcList.add(newArc);     
        pOrigin.addArc(newArc);
        newArc = new Arc<T>(pDestiny, pOrigin, pWeight);
        arcList.add(newArc);        
        pDestiny.addArc(newArc);  
    }
    
    public ArrayList<Node<T>> getNodes(){
        return this.nodeList;
    }

    public ArrayList<Arc<T>> getArcs(){
        return this.arcList;
    }

    @Override
    public String toString() {
        return "Graph{" + "NodeList=" + nodeList + ", \n\nArcList=" + arcList + '}';
    }
}