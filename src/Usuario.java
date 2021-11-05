package MasterMind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Usuario implements Serializable{
    private String nombre,password;
    private int numPJ,numPG,numPP,pAnotados;
    private boolean admin;
    
    Random rnd = new Random();
    
    private ArrayList <String> partidasGuardadas = new ArrayList();

    public Usuario(String nombre,String password){
        this.nombre=nombre;
        this.password=password;
        this.admin=false;
        numPJ=numPG=numPP=pAnotados=0;
    }
    
    public Usuario(String password){
        this.nombre="admin";
        this.password=password;
        this.admin=true;
        numPJ=numPG=numPP=pAnotados=0;
    }
    
    public Usuario(){
        
    }
    
    public String getNombre(){
        return(this.nombre);
    }
    
    public String getPassword(){
        return(this.password);
    }

    public float calcularPorcentajeVictorias(){
        if(numPJ==0){
            return(0);
        }else{
            return(((((float)this.numPG/this.numPJ)*100)));
        }
    }
    
    public boolean getAdmin(){
        return(this.admin);
    }
    
    public int getPJ(){
        return(this.numPJ);
    }
    public int getPG(){
        return(this.numPG);
    }
    public int getPP(){
        return(this.numPP);
    }

    public void guardarPartida(String j1,String j2,int p1,int p2,String fecha){
	String temp = "";
        this.pAnotados=this.pAnotados+p1;
        if(p1>p2){
            numPG=numPG+1;
            temp="Partida: -Jugador 1(WIN): " + j1 + " -Puntuacion J1: " + p1 + " -Jugador 2: " + j2 + " -Puntuacion J2: " + p2 + " -Fecha y hora: " + fecha;
        }else if(p1<p2){
            numPP=numPP+1;
            temp="Partida: -Jugador 1: " + j1 + " -Puntuacion J1: " + p1 + " -Jugador 2(WIN): " + j2 + " -Puntuacion J2: " + p2 + " -Fecha y hora: " + fecha;
        }else{
            temp="Partida (EMPATE): -Jugador 1: " + j1 + " -Puntuacion J1: " + p1 + " -Jugador 2: " + j2 + " -Puntuacion J2: " + p2 + " -Fecha y hora: " + fecha;
        }
        this.partidasGuardadas.add(temp);
        numPJ=numPJ+1;
    }
    
    public void guardarEntrenamiento(String nombre,int aciertos,int colocados){
        String temp = "Entrenamiento: -Jugador: " + nombre + " -Aciertos: " + aciertos + " -Colocados: " + colocados;
        this.partidasGuardadas.add(temp);
    }
    
    public String buscarPorNumero(int posicion){
        int temp=0;
        String temp1="";
        Iterator <String> iter  = partidasGuardadas.iterator();
        while(iter.hasNext()){
            temp++;
            if(temp==posicion){
                temp1=iter.next();
            }
        }
        return(temp1);
    }
    
    public String buscarPorContrincante(String j2){
        String temp = "";
        for(int ii=0;ii<partidasGuardadas.size();ii++){
            if(partidasGuardadas.get(ii).contains(j2)){
                temp=temp+partidasGuardadas.get(ii) + "\n";
            }
        }
        return(temp);
    }
    
    public String clasificacionPersonal(){
        String temp = "";
        int num=0;
        Iterator <String> iter = partidasGuardadas.iterator();
        while(iter.hasNext()){
            temp=temp + (num++) + ".- " +iter.next() + "\n";
        }
        return temp;
    }
    
    public void setAdmin(){
        this.admin=true;
    }
}
