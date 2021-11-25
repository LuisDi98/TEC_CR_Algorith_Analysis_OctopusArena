package model;

import java.util.ArrayList;

public class Node <T>{
    
    private T TValue;
    private Node<T> previous;
    private boolean mark;
    private boolean visited;
    private ArrayList<Arc<T>> arcList;
    
    
    public Node(){
        previous = null;
        arcList = new ArrayList<Arc<T>>();
    }
    
    public Node(T pValue){
        previous = null;
        TValue = pValue;
        arcList = new ArrayList<Arc<T>>();
    }
    
    public T geTValue(){
        return TValue;
    }
    
    public void setValue(T pValue){
        TValue = pValue;
    }
    
    public Node<T> getPrevious(){
        return previous;
    }
    
    public void setPrevious(Node<T> pPrevious){
        previous = pPrevious;
    }
    
    public ArrayList<Arc<T>> getArcs(){
        return arcList;
    }
    
    public void addArc(Arc<T> pArc){
            arcList.add(pArc);
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean pMark) {
        this.mark = pMark;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean pVisited) {
        this.visited = pVisited;
    }
    
    public ArrayList<Arc<T>> getArcList(){
        return this.arcList;
    }
    
    @Override
    public String toString() {
        return "\nNode{" + "TValue=" + TValue + ", mark=" + mark + ", visited=" + visited + '}';
    }
    
}