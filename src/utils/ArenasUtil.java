package utils;

import model.Arena;
import model.Graph;

public class ArenasUtil {
    
    private static ArenasUtil arenaUtil = new ArenasUtil();
    private Graph arenasGraph;

    private ArenasUtil(){
        this.arenasGraph = new Graph<Arena>();
    }

    public static ArenasUtil getInstance(){
        return ArenasUtil.arenaUtil;
    }

    public Graph getArenasGraph() {
        return arenasGraph;
    }

}
