package MasterMind;

import java.util.Collections;
import java.util.Comparator;

public class CompararUsuariosPorcentaje implements Comparator <Usuario>{

    public int compare(Usuario e1, Usuario e2){
        if(e1.calcularPorcentajeVictorias()>e2.calcularPorcentajeVictorias()){
            return -1;
        }else if(e1.calcularPorcentajeVictorias()>e2.calcularPorcentajeVictorias()){
            return 0;
        }else{
            return 1;
        }
    }    
}