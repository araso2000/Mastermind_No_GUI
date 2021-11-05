package MasterMind;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
        //Escaner de entrada de consola
        Scanner scan = new Scanner(System.in);
        
        //Objetos de clases aparte que añaden la funcionalidad al programa
        AlmacenLogin almacen = new AlmacenLogin();
        Usuario j1,j2;
        j2=new Usuario();
        Clasificacion clasif = new Clasificacion();
        
        //Variables de control del programa
        int rondasPartida=3;
        int intentosRondaPartida=10;
        
        System.out.println("Juego MasterMind");
        
        //Bloque archivo serializado
        boolean correct = false;
        while(!correct){
            System.out.println("\t1.Introducir archivo serializado personal\n\t2.Cargar archivo serializado por defecto (jugadoresBinario)\n\t3.Empezar de cero sin cargar nada");
            int o = scan.nextInt();
            scan.nextLine();
            if(o<1 && o>3){
                System.out.println("Opcion incorrecta!");
            }else{
                correct=true;
                switch(o){
                    case 1:
                        System.out.println("Introduce el nombre del archivo:");
                        String name = scan.nextLine();
                        System.out.println("El archivo tiene un usuario administrador ya creado? 1.Si - OTRO.No");
                        int a=scan.nextInt();
                        scan.nextLine();
                        
                        if(a!=1){
                            System.out.println("Password para el usuario administrador:");
                            String passAdmin = scan.nextLine();
                            System.out.println("\t Usuario administrador: 'admin' Pass: '" + passAdmin +"'");
                            almacen.setAdmin(passAdmin);
                        }
                        almacen.setSerial(name);
                        almacen.deserializar();
                        break;
                    case 2:
                        almacen.deserializar();
                        System.out.println("\t Usuario administrador: 'admin' Pass: 'admin'");
                        break;
                    case 3:
                        System.out.println("Password para el usuario administrador:");
                        String passAdmin = scan.nextLine();
                        System.out.println("\t User: 'admin' Pass: '" + passAdmin +"'");
                        almacen.setAdmin(passAdmin);
                        break;
                }
            }
        }
        //Menu principal
        int opt=1;
        while(opt!=0){
            System.out.println("\n1.Iniciar sesión - 2.Registrar nuevo usuario - 3.Ver usuarios - 0.SALIR");
            opt = scan.nextInt();
            scan.nextLine();
            
            if(opt<0 && opt>3){
                System.out.println("Opcion incorrecta");
            }else{
                //Inicio de sesion
                if(opt==1){
                    System.out.println("\nIniciar sesión. Introduzca usuario:");
                    String user1 = scan.nextLine();
                    System.out.println("Introduzca contraseña:");
                    String pass1 = scan.nextLine();
                    
                    j1 = almacen.identificarUsuario(user1,pass1);
                    
                    if(j1==null){
                        System.out.println("No existe ese usuario!");
                    }else{
                        //Usuario ADMIN
                        if(j1.getAdmin()){
                            System.out.println("\nMenu Administrador\n¿Deseas 1.JUGAR o OTRO.ADMINISTRAR?");
                            int opt4 = scan.nextInt();
                            scan.nextLine();
                            
                            if(opt4==1){
                                    int opt10=1;
                                    while(opt10!=0){
                                        //Modo juego admin
                                        System.out.println("\nJUGAR (Usuario Administrador)");
                                        System.out.println("1.Entrenamiento - 2.Partida - 3.Estadisticas personales - 4.Clasificacion general - 5.Serializar - 0.SALIR");
                                        opt10=scan.nextInt();
                                        scan.nextLine();
                                        if(opt10<0 && opt10>5){
                                            System.out.println("Opcion incorrecta!");
                                        }else{
                                            switch(opt10){
                                                case 1:
                                                    System.out.println("\nJugar entrenamiento.\nIntroduzca numero de intentos (0 para ilimitados):");
                                                    int intentosEntrena=scan.nextInt();
                                                    scan.nextLine();
                                                    if(intentosEntrena<0){
                                                        intentosEntrena=0;
                                                    }
                                                    System.out.println("Quieres mostrar la solucion mientras juegas? 1.SI - OTRO.NO");
                                                    Entrenamiento entrena = new Entrenamiento(j1,intentosEntrena);
                                                    int opt6=scan.nextInt();
                                                    scan.nextLine();
                                                    if(opt6==1){
                                                        entrena.jugar(true); 
                                                    }else{
                                                        entrena.jugar(false);
                                                    }
                                                    break;
                                                case 2:
                                                    System.out.println("\nJugar partida");
                                                    int op=-1;
                                                    
                                                    boolean ayuda = false;
                                                    System.out.println("\nAdministrador: ¿Quieres ayuda durante la partida? 1.Si - OTRO.No");
                                                    int b = scan.nextInt();
                                                    scan.nextLine();
                                                    if(b==1){
                                                        ayuda=true;
                                                    }
                                                    
                                                    boolean existe = false;
                                                        while(!existe){
                                                            System.out.println("Inicie sesion el segundo jugador.Usuario:");
                                                            String user2 = scan.nextLine();
                                                            System.out.println("Introduzca contraseña:");
                                                            String pass2 = scan.nextLine();
                                                
                                                            j2 = almacen.identificarUsuario(user2,pass2);
                                                
                                                            if(j2==null){
                                                                System.out.println("\nEl usuario no existe!!!");
                                                            }else if(j1.getNombre().equals(j2.getNombre())){
                                                                System.out.println("\nNo se puede jugar contigo mismo. Usa la funcion ENTRENAMIENTO");
                                                            }else{
                                                                System.out.println("\nSegundo jugador ha iniciado sesion correctamente");
                                                                existe=true;
                                                            }
                                                        }
                                            
                                                        Date date = new Date();
                                                        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                                                        String fechaHora = hourdateFormat.format(date);
                    
                                                        Partida p = new Partida(j1,j2,fechaHora,intentosRondaPartida,rondasPartida,ayuda);
                                                        p.jugar();
                                                    break;
                                                case 3:
                                                    System.out.println("\nEstadisticas personales:\n\n" + j1.clasificacionPersonal());
                                                    int opt3=1;
                                                    while(opt3!=0){
                                                        System.out.println("\nDeseas buscar alguna partida concreta?\n1.SI-Por usuario - 2.SI.Por numero - 0.NO");
                                                        opt3=scan.nextInt();
                                                        scan.nextLine();
                                                        if(opt3<1 && opt3>2){
                                                            System.out.println("Opcion incorrecta!");
                                                        }else{
                                                            if(opt3==1){
                                                                System.out.println("Por usuario. Introduzca usuario:");
                                                                String secondUser = scan.nextLine();
                                                                System.out.println(j1.buscarPorContrincante(secondUser));
                                                            }else if(opt3==2){
                                                                System.out.println("Por numero. Introduzca numero:");
                                                                int num = scan.nextInt();
                                                                scan.nextLine();
                                                                System.out.println(j1.buscarPorNumero(num));
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 4:
                                                    int opt2=0;
                                                    System.out.println("\nClasificacion general:\n\t1.Imprimir ordenado por porcentaje de victorias.\n\t2.Imprimir ordenado por numero de partidas ganadas.");
                                                    opt2=scan.nextInt();
                                                    scan.nextLine();
                                    
                                                    if(opt2>2 && opt<1){
                                                        System.out.println("Opcion errónea!");
                                                    }else{
                                                        if(opt2==1){
                                                            System.out.println("Clasificacion general por porcentaje de victorias\n" + clasif.imprimirClasificacionGeneralPorcentaje(almacen));
                                                        }else{
                                                            System.out.println("Clasificacion general por numero de victorias\n" + clasif.imprimirClasificacionGeneralVictorias(almacen));
                                                        }
                                                    }
                                                    break;
                                                case 5:
                                                    System.out.println("\nIntroduce nombre para guardar el archivo:");
                                                    String na = scan.nextLine();
                                                    almacen.serializar(na);
                                                    break;
                                            }
                                        }
                                    }
                            }else{
                                //Modo administrar admin
                                    int opt5=-1;
                                    while(opt5!=0){
                                        System.out.println("\nADMINISTRAR.\n1.Imprimir clasificacion - 2.Cambiar numero de rondas/partida - 3.Cambiar numero de intentos/ronda - 4.Serializar los jugadores - 0.SALIR");
                                        opt5=scan.nextInt();
                                        scan.nextLine();
                                        if(opt5<1 && opt5>4){
                                            System.out.println("Opcion incorrecta");
                                        }else{
                                            switch(opt5){
                                                case 1:
                                                    System.out.println("\nIntroduzca nombre para guardar la clasificacion:");
                                                    String temp = scan.nextLine();
                                                    clasif.guardarClasificacionGeneralVictorias(temp+"NumVictoras.txt", almacen);
                                                    clasif.guardarClasificacionGeneralVictorias(temp+"Porcentaje.txt", almacen);
                                                    System.out.println("Guardado correctamente");
                                                    break;
                                                case 2:
                                                    System.out.println("\nIntroduzca numero de rondas por partida:");
                                                    int num=scan.nextInt();
                                                    scan.nextLine();
                                                    if(num>0){
                                                        rondasPartida=num;
                                                    }
                                                    break;
                                                case 3:
                                                    System.out.println("\nIntroduzca intentos por cada ronda en partida:");
                                                    num=scan.nextInt();
                                                    scan.nextLine();
                                                    if(num>0){
                                                        intentosRondaPartida=num;
                                                    }
                                                    break;
                                                case 4:
                                                    System.out.println("\nIntroduce nombre para guardar el archivo:");
                                                    String na = scan.nextLine();
                                                    almacen.serializar(na);
                                                    break;
                                            } 
                                        } 
                                    }
                                }
                        }else{
                            //Modo jugar normal
                            int opt1=-1;
                            while(opt1!=0){
                                System.out.println("\nJUGAR (Usuario normal)");
                                System.out.println("1.Entrenamiento - 2.Partida - 3.Estadisticas personales - 4.Clasificacion general - 5.Serializar - 0.SALIR");
                                opt1=scan.nextInt();
                                scan.nextLine();
                                if(opt1<0 && opt1>5){
                                    System.out.println("Opcion incorrecta");
                                }else{
                                    switch(opt1){
                                    case 1:
                                        System.out.println("\nJugar entrenamiento.\nIntroduzca numero de intentos (0 para ilimitados):");
                                        int intentosEntrena=scan.nextInt();
                                        scan.nextLine();
                                        Entrenamiento entrena = new Entrenamiento(j1,intentosEntrena);
                                        entrena.jugar(false);                                    
                                        break;
                                    case 2:
                                        System.out.println("\nJugar partida");
                                        boolean existe = false;
                                            while(!existe){
                                                System.out.println("Inicie sesion el segundo jugador.Usuario:");
                                                String user2 = scan.nextLine();
                                                System.out.println("Introduzca contraseña:");
                                                String pass2 = scan.nextLine();
                                                
                                                j2 = almacen.identificarUsuario(user2,pass2);
                                                
                                                if(j2==null){
                                                    System.out.println("\nEl usuario no existe!!!");
                                                }else if(j1.getNombre().equals(j2.getNombre())){
                                                    System.out.println("\nNo se puede jugar contigo mismo. Usa la funcion ENTRENAMIENTO");
                                                }else{
                                                    System.out.println("\nSegundo jugador ha iniciado sesion correctamente");
                                                    existe=true;
                                                }
                                            }
                                            boolean ayuda = false;
                                            if(j2.getAdmin()){
                                                System.out.println("\nJ2: Administrador: ¿Quieres ayuda durante la partida? 1.Si - OTRO.No");
                                                int b = scan.nextInt();
                                                scan.nextLine();
                                                if(b==1){
                                                    ayuda=true;
                                                }
                                            }
                                            
                                            Date date = new Date();
                                            DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                                            String fechaHora = hourdateFormat.format(date);
                    
                                            Partida p = new Partida(j1,j2,fechaHora,intentosRondaPartida,rondasPartida,ayuda);
                                            p.jugar();
                                        break;
                                    case 3:
                                        System.out.println("\nEstadisticas personales:\n\n" + j1.clasificacionPersonal());
                                        int opt3=1;
                                        while(opt3!=0){
                                            System.out.println("Deseas buscar alguna partida concreta?\n1.SI-Por usuario - 2.SI.Por numero - 0.NO");
                                            opt3=scan.nextInt();
                                            scan.nextLine();
                                            if(opt3<0 && opt3>2){
                                                System.out.println("Opcion incorrecta!");
                                            }else{
                                                if(opt3==1){
                                                    System.out.println("Por usuario. Introduzca usuario:");
                                                    String secondUser = scan.nextLine();
                                                    System.out.println(j1.buscarPorContrincante(secondUser));
                                                }else if(opt3==2){
                                                    System.out.println("Por numero. Introduzca numero:");
                                                    int num = scan.nextInt();
                                                    scan.nextLine();
                                                    System.out.println(j1.buscarPorNumero(num));
                                                }
                                            }
                                        }
                                        break;
                                    case 4:
                                        int opt2=0;
                                        System.out.println("\nClasificacion general:\n\t1.Imprimir ordenado por porcentaje de victorias.\n\t2.Imprimir ordenado por numero de partidas ganadas.");
                                        opt2=scan.nextInt();
                                        scan.nextLine();
                                    
                                        if(opt2>2 && opt<1){
                                            System.out.println("Opcion errónea!");
                                        }else{
                                            if(opt2==1){
                                                System.out.println("Clasificacion general por porcentaje de victorias\n" + clasif.imprimirClasificacionGeneralPorcentaje(almacen));
                                            }else{
                                                System.out.println("Clasificacion general por numero de victorias\n" + clasif.imprimirClasificacionGeneralVictorias(almacen));
                                            }
                                        }
                                        break;
                                    case 5:
                                        System.out.println("\nIntroduce nombre para guardar el archivo:");
                                        String na = scan.nextLine();
                                        almacen.serializar(na);
                                        break;
                                    }
                                }
                            }
                        }
                    }      
                }else if(opt==2){
                    //registrar nuevo usuario
                    System.out.println("\nRegistrar nuevo usuario. Introduzca usuario:");
                    String user = scan.nextLine();
                    System.out.println("Introduzca contraseña:");
                    String pass = scan.nextLine();
                    
                    if(almacen.registrarUsuario(user,pass)){
                        System.out.println("\nEl usuario ya existe!");
                    }else{
                        System.out.println("\nUsuario creado correctamente");
                    }
                }else if(opt==3){
                    almacen.verUsuarios();
                }
            }
        }
    }
}