package MasterMind;

import java.util.Comparator;
import java.util.Collections;

public class CompararUsuariosVictorias implements Comparator <Usuario>{

    public int compare(Usuario e1, Usuario e2){
        if(e1.getPG()>e2.getPG()){
            return -1;
        }else if(e1.getPG()>e2.getPG()){
            return 0;
        }else{
            return 1;
        }
    }    
}
