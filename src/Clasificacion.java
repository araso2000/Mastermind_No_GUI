package MasterMind;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Clasificacion{
    
    public String imprimirClasificacionGeneralVictorias(AlmacenLogin jugadores){
        ArrayList<Usuario> array = (ArrayList<Usuario>)jugadores.getArray().clone();
        Collections.sort(array,new CompararUsuariosVictorias()); 
        String temp = "";
        for(int ii=0;ii<array.size();ii++){
            temp = temp + (ii+1) + ".- Nombre: " + array.get(ii).getNombre() + "\tPartidasJugadas: " + array.get(ii).getPJ() + "\tPartidasGanadas: " + array.get(ii).getPG() + "\tPartidasPerdidas: " + array.get(ii).getPP() + "\tPorcentajeVictorias: " + array.get(ii).calcularPorcentajeVictorias() + "%\n";
        }
        return temp;
    }
    
    public String imprimirClasificacionGeneralPorcentaje(AlmacenLogin jugadores){
        ArrayList<Usuario> array = (ArrayList<Usuario>)jugadores.getArray().clone();
        Collections.sort(array,new CompararUsuariosPorcentaje()); 
        String temp = "";
        for(int ii=0;ii<array.size();ii++){
            temp = temp + (ii+1) + ".- Nombre: " + array.get(ii).getNombre() + "\tPartidasJugadas: " + array.get(ii).getPJ() + "\tPartidasGanadas: " + array.get(ii).getPG() + "\tPartidasPerdidas: " + array.get(ii).getPP() + "\tPorcentajeVictorias: " + array.get(ii).calcularPorcentajeVictorias() + "%\n";
        }
        return(temp);
    }
    
    public void guardarClasificacionGeneralVictorias(String nombre,AlmacenLogin jugadores) throws IOException{
         try {
            FileWriter fw = new FileWriter(nombre);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            
            salida.print(this.imprimirClasificacionGeneralVictorias(jugadores));
            salida.close();
        }
        catch(java.io.IOException ioex) { 
            System.out.println("ERROR al guardar");
        }
    }
    
    public void guardarClasificacionGeneralPorcentaje(String nombre,AlmacenLogin jugadores) throws IOException{
        try {
            FileWriter fw = new FileWriter(nombre);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            
            salida.print(this.imprimirClasificacionGeneralPorcentaje(jugadores));
            salida.close();
        }
        catch(java.io.IOException ioex) {
            System.out.println("ERROR al guardar");
        }
    }
}
