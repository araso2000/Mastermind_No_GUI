package MasterMind;

import java.util.Scanner;

public class Partida {
    private int intentos,rondas,puntosJ1,puntosJ2;
    private char[] claveJ1,claveJ2,intentoJ1,intentoJ2;
    private String fechaHora;
    private Usuario j1,j2;
    private boolean ayudaJ1,ayudaJ2;
    private Scanner scan = new Scanner(System.in);
    
    public Partida(Usuario j1,Usuario j2,String fecha,int intentos,int rondas,boolean ayuda){
        this.j1=j1;
        this.j2=j2;
        this.fechaHora=fecha;
        this.intentos=intentos;
        this.rondas=rondas;
        
        this.puntosJ1=this.puntosJ2=0;
        this.claveJ1=new char[4];
        this.claveJ2=new char[4];
        this.intentoJ1=new char[4];
        this.intentoJ2=new char[4];
        
        if(j1.getAdmin() && ayuda){
            ayudaJ1=true;
            ayudaJ2=false;
        }
        if(j2.getAdmin() && ayuda){
            ayudaJ2=true;
            ayudaJ1=false;
        }
    }
    
    public void jugar() throws InterruptedException{
        System.out.println("\nPartida");
        if(intentos!=10){
            System.out.println("\nConfiguracion por defecto de intentos cambiada (por el administrador)");
        }
        if(rondas!=3){
            System.out.println("\nConfiguracion por defecto de rondas cambiada (por el administrador)");
        }
        for(int ii=0;ii<rondas;ii++){
            System.out.println("\nRonda " + (ii+1) + " de " + rondas + " rondas totales\nTeneis cada jugador " + intentos + " intentos.");
            boolean correcto=false;
            while(correcto!=true){
                System.out.println("Jugador 1 (" + j1.getNombre() + ") introduce la combinación que el Jugador 2 (" + j2.getNombre() + ") deberá adivinar (solo 4 caracteres) (posibles letras: b n a r v m):");
                String temp = scan.nextLine();
                if(temp.length()<4 || temp.length()>4){
                    System.out.println("La combinacion debe ser de 4 caracteres!!!");
                }else if(!comprobar(convertir(temp))){
                    System.out.println("\nLas combinaciones solo pueden tener los siguientes caracteres: b n a r v m");
                }else{
                    claveJ2=this.convertir(temp);
                    correcto=true;
                }
            }
            correcto=false;
            while(correcto!=true){
                System.out.println("\nJugador 2 (" + j2.getNombre() + ") introduce la combinación que el Jugador 1 (" + j1.getNombre() + ") deberá adivinar (solo 4 caracteres) (posibles letras: b n a r v m):");
                String temp = scan.nextLine();
                if(temp.length()<4 || temp.length()>4){
                    System.out.println("La combinacion debe ser de 4 caracteres!!!");
                }else if(!comprobar(convertir(temp))){
                    System.out.println("\nLas combinaciones solo pueden tener los siguientes caracteres: b n a r v m");
                }else{
                    claveJ1=this.convertir(temp);
                    correcto=true;
                }
            }
            int puntuacion = intentos;
            boolean ganar=false;
            for(int jj=0;jj<intentos && !ganar;jj++){
                System.out.println("\nIntento " + (jj+1) + " de " + intentos + " intentos totales");
                
                correcto = false;
                while(!correcto){
                    if(ayudaJ1){
                        System.out.println("\n((Ayuda para J1: " + this.imprimirCombinacion(claveJ1) + "))\n");
                    }
                    System.out.println("Jugador 1 introduzca su clave (posibles letras: b n a r v m):");
                    String temp = scan.nextLine();
                    if(temp.length()<4 || temp.length()>4){
                        System.out.println("La combinacion debe ser de 4 caracteres!!!");
                    }else if(!comprobar(convertir(temp))){
                        System.out.println("\nLas combinaciones solo pueden tener los siguientes caracteres: b n a r v m");
                    }else{
                        correcto=true;
                        intentoJ1=convertir(temp);
                    }
                }
                
                correcto = false;
                while(!correcto){
                    if(ayudaJ2){
                        System.out.println("\n((Ayuda para J2: " + this.imprimirCombinacion(claveJ2) + "))\n");
                    }
                    System.out.println("Jugador 2 introduzca su clave (posibles letras: b n a r v m):");
                    String temp = scan.nextLine();
                    if(temp.length()<4 || temp.length()>4){
                        System.out.println("La combinacion debe ser de 4 caracteres!!!");
                    }else if(!comprobar(convertir(temp))){
                        System.out.println("\nLas combinaciones solo pueden tener los siguientes caracteres: b n a r v m");
                    }else{
                        correcto=true;
                        intentoJ2=convertir(temp);
                    }
                }
                int puntosRondaJ1,puntosRondaJ2;
                puntosRondaJ1=puntosRondaJ2=0;
                System.out.print("\nResultados Jugador 1: ");
                if(combinacionCorrecta(intentoJ1,claveJ1)){
                    System.out.println("ENHORABUENA! Has acertado la combinacion!\n" + this.imprimirCombinacion(claveJ1));
                    puntosRondaJ1=puntuacion;
                    puntosJ1=puntosJ1+puntuacion;
                    ganar=true;
                }else if(ii==this.intentos-1){
                    System.out.println("Es tu ultimo intento y no has acertado la combinacion...\nColocados (posiciones coincidentes): " + this.numeroColocados(intentoJ1,claveJ1) + " - Aciertos (letras iguales): " + this.numeroAciertos(intentoJ1,claveJ1) + "\n" + this.imprimirCombinacion(claveJ1));
                    ganar=true;
                }else{
                    System.out.println("Casi!!!\nColocados (posiciones coincidentes): " + this.numeroColocados(intentoJ1,claveJ1) + " - Aciertos (letras iguales): " + this.numeroAciertos(intentoJ1,claveJ1) + "\nSigue intentandolo!");
                }
                Thread.sleep(3000);
                System.out.print("\nResultados Jugador 2: ");
                if(combinacionCorrecta(intentoJ2,claveJ2)){
                    System.out.println("ENHORABUENA! Has acertado la combinacion!\n" + this.imprimirCombinacion(claveJ2));
                    puntosRondaJ2=puntuacion;
                    puntosJ2=puntosJ2+puntuacion;
                    ganar=true;
                }else if(ii==this.intentos-1){
                    System.out.println("Es tu ultimo intento y no has acertado la combinacion...\nColocados (posiciones coincidentes): " + this.numeroColocados(intentoJ2,claveJ2) + " - Aciertos (letras iguales): " + this.numeroAciertos(intentoJ2,claveJ2) + "\n" + this.imprimirCombinacion(claveJ2));
                    ganar=true;
                }else{
                    System.out.println("Casi!!!\nColocados (posiciones coincidentes): " + this.numeroColocados(intentoJ2,claveJ2) + " - Aciertos (letras iguales): " + this.numeroAciertos(intentoJ2,claveJ2) + "\nSigue intentandolo!");
                }
                
                if(ganar){
                    System.out.println("\nResumen de la ronda:");
                    if(puntosRondaJ1<puntosRondaJ2){
                        System.out.println("Enhorabuena " + j2.getNombre() + ", has ganado la ronda!!!");
                    }else if(puntosRondaJ1>puntosRondaJ2){
                        System.out.println("Enhorabuena " + j1.getNombre() + ", has ganado la ronda!!!");
                    }else{
                        System.out.println("Empate");
                    }
                }
                
                System.out.println("\nPuntuacion J1: " + puntosRondaJ1 + "\nPuntuacion J2: " + puntosRondaJ2); 
                Thread.sleep(3000);
                puntuacion--;
            }
        }
        
        System.out.println("\nResumen de la partida:");
        if(puntosJ1<puntosJ2){
            System.out.println("Enhorabuena " + j2.getNombre() + ", has ganado la partida!!!");
        }else if(puntosJ1>puntosJ2){
            System.out.println("Enhorabuena " + j1.getNombre() + ", has ganado la partida!!!");
        }else{
            System.out.println("Empate");
        }
        System.out.println("\nPuntuacion J1: " + puntosJ1 + "\nPuntuacion J2: " + puntosJ2); 
        Thread.sleep(3000);
               
        j1.guardarPartida(j1.getNombre(),j2.getNombre(),this.puntosJ1,this.puntosJ2,fechaHora);
        j2.guardarPartida(j2.getNombre(),j1.getNombre(),this.puntosJ2,this.puntosJ1,fechaHora);
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
    
    public boolean combinacionCorrecta(char[] claveIntento,char[] claveCorrecta){
        if(this.numeroColocados(claveIntento,claveCorrecta)==4){ //Una combinación será correcta si el numero de colocados es 4, lo que significa que las 4 posiciones de la suposicion del usuario concuerdan perfectamente con las 4 posiciones de la clave
            return(true);
	}
	return(false);       
    }
    
    public int numeroColocados(char[] claveIntento,char[] claveCorrecta){
        int colocados = 0;
        for(int ii=0; ii<4; ii++){
           if(claveIntento[ii] == claveCorrecta[ii]){
               colocados++;
           } 
        }
       return colocados;
    }
    
    public int numeroAciertos(char[] claveIntento,char[] claveCorrecta){
        int aciertos = 0;
        char[] claveJugador = new char[4];
        char[] claveBuena = new char[4];
        
        for(int ii=0;ii<4;ii++){
            claveJugador[ii]=claveIntento[ii];
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
    
    public String imprimirCombinacion(char[] claveCorrecta){
        String temp="Combinacion correcta:\t";
        for(int ii=0;ii<4;ii++){
            temp=temp+claveCorrecta[ii] + " ";
        }
        return(temp);
    }
}