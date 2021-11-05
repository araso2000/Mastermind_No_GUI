package MasterMind;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Entrenamiento {
    private Usuario jugador;
    private int intentos;
    private ArrayList<String> historial;
    private char[] claveCorrecta = new char[4];
    private char[] claveIntento = new char[4];
    private boolean infinito;
    private Scanner scan = new Scanner(System.in);
    
    public Entrenamiento(Usuario jugador,int intentos){
        this.jugador=jugador;
        if(intentos==0){
            this.intentos=9999;
            infinito=true;
        }else{
            infinito=false;
            this.intentos=intentos;
        }
        this.claveCorrecta=this.generarCombinacion();
        this.historial=new ArrayList<>();
    }
    
    public void jugar(boolean adminCheat) throws InterruptedException{
        boolean ganar=false;
        int inte=this.intentos;
        for(int ii=0;ii<this.intentos && !ganar;ii++){
            
            if(ii!=0){
                this.impHistorial();
            }
            
            if(adminCheat){
                System.out.println("\n((Ayuda: " + this.imprimirCombinacion() + "))");
            }
            boolean correcto= false;
            String temporal="";
            while(!correcto){
                if(infinito){
                    System.out.println("\nTienes infinitos intentos\nIntroduce combinacion (posibles letras: b n a r v m):");
                }else{
                    System.out.println("\nTienes " + inte + " intentos\nIntroduce combinacion (posibles letras: b n a r v m):");
                }
                temporal = scan.nextLine();
                
                if(temporal.length()<4 || temporal.length()>4){
                    System.out.println("\nLas combinaciones son de 4 caracteres!!!");
                }else if(this.comprobar(this.convertir(temporal))==false){
                    System.out.println("\nLas combinaciones solo pueden tener los siguientes caracteres: b n a r v m");
                }else{
                    correcto=true;
                }
            }
            
            String temp="";
            for(int jj=0;jj<4;jj++){
                this.claveIntento[jj]=temporal.charAt(jj);
                temp=temp+temporal.charAt(jj)+" ";
            }
            
            historial.add(temp);
                                  
            if(combinacionCorrecta()){
                System.out.println("\nENHORABUENA! Has acertado la combinacion!\n" + this.imprimirCombinacion());
                jugador.guardarEntrenamiento(jugador.getNombre(),this.numeroAciertos(claveIntento),this.numeroColocados(claveIntento));
                ganar=true;
            }else if(ii==this.intentos-1){
                System.out.println("\nEs tu ultimo intento y no has acertado la combinacion...Vamos a guardar tu entrenamiento en el historial\nColocados (posiciones coincidentes): " + this.numeroColocados(claveIntento) + " - Aciertos (letras iguales): " + this.numeroAciertos(claveIntento) + "\n" + this.imprimirCombinacion());
                jugador.guardarEntrenamiento(jugador.getNombre(),this.numeroAciertos(claveIntento),this.numeroColocados(claveIntento));
                ganar=true;
            }else{
                System.out.println("\nCasi!!!\nColocados (posiciones coincidentes): " + this.numeroColocados(claveIntento) + " - Aciertos (letras iguales): " + this.numeroAciertos(claveIntento) + "\nSigue intentandolo!");
            }
            Thread.sleep(3000);
            inte--;
        }
    }
    
    public boolean comprobar(char[] clave){
        boolean correcto = false;
        int contador=0;
        for(int ii=0;ii<4;ii++){
            if(clave[ii]=='b' ){
                contador++;
            }else if(clave[ii]=='n'){
                contador++;;
            }else if(clave[ii]=='a'){
                contador++;
            }else if(clave[ii]=='r'){
                contador++;
            }else if(clave[ii]=='v'){
                contador++;
            }else if(clave[ii]=='m'){
                contador++;;
            }
        }
        if(contador==4){
            correcto=true;
        }
        return(correcto);
    }
    
    public char[] convertir(String combinacion){
        char[] temp = new char[4];
        for(int ii=0;ii<4;ii++){
            temp[ii]=combinacion.charAt(ii);
        }
        return(temp);
    }
    
    public void impHistorial(){
        System.out.println("\nHistorial de intentos:");
        String temp;
        char[] clav = new char[4];
        
        for(int ii=0;ii<historial.size();ii++){
            temp=historial.get(ii);
            temp=temp.replace(" ", "");
            for(int jj=0;jj<4;jj++){
                clav[jj]=temp.charAt(jj);
            }
            System.out.println(historial.get(ii) + " - Aciertos: " + this.numeroAciertos(clav));// + " - Colocados: " + this.numeroColocados(clav));
        }
        
        System.out.println();
    }
    
    private char[] generarCombinacion(){
        char[] clave = new char[4]; //Creamos un char[] temporal
        Random rnd = new Random();
        
        for(int i=0; i<4; i++){
           int temp = rnd.nextInt(5);
           switch(temp){
               case 0:
                   clave[i]='b';
                   break;
               case 1:
                   clave[i]='n';
                   break;
               case 2:
                   clave[i]='a';
                   break;
               case 3:
                   clave[i]='r';
                   break;
               case 4:
                   clave[i]='v';
                   break;
               case 5:
                   clave[i]='m';
                   break;
            }
        }
        return clave;
    }
    public boolean combinacionCorrecta(){
        if(this.numeroColocados(claveIntento)==4){ //Una combinación será correcta si el numero de colocados es 4, lo que significa que las 4 posiciones de la suposicion del usuario concuerdan perfectamente con las 4 posiciones de la clave
            return(true);
	}
	return(false);       
    }
    
    public int numeroColocados(char[] claveJugador){
        int colocados = 0;
        for(int ii=0; ii<4; ii++){
           if(claveJugador[ii] == claveCorrecta[ii]){
               colocados++;
           } 
        }
       return colocados;
    }
    
    public int numeroAciertos(char[] claveJugador){
        int aciertos = 0;
        char[] claveBuena = new char[4];
        
        for(int ii=0;ii<4;ii++){
            claveBuena[ii]=claveCorrecta[ii];
        }
        
        boolean hecho=false;
        
        for(int ii=0;ii<4;ii++){
            for(int jj=0;jj<4 && !hecho;jj++){
                if(claveJugador[ii]==claveBuena[jj] && claveJugador[ii]!='-' && claveBuena[jj]!='-'){
                    aciertos++;
                    claveJugador[ii]='-';
                    claveBuena[jj]='-';
                    hecho=true;
                }
            }
            hecho=false;
        }
        return aciertos;
    }   
    
    public String imprimirCombinacion(){
        String temp="Combinacion correcta:\t";
        for(int ii=0;ii<4;ii++){
            temp=temp+claveCorrecta[ii] + " ";
        }
        return(temp);
    }
}