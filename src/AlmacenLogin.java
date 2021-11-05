package MasterMind;

//Clase que permite guardar los usuarios que juegan o administran el videojuego

//Librerias autoimportadas necesarias para mantener tanto las excepciones como las distintas estructuras de datos
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

//Implementamos la clase serializable que nos permite serializar binariamente datos
public class AlmacenLogin implements Serializable{
    private ArrayList <Usuario> jugadores = new ArrayList(); //Creamos el ArrayList que tendrá todos los usuarios.
    private String nombre;
    
    public AlmacenLogin(){
        this.nombre="jugadoresBinario";//Nombre del objeto
    }
    
    public void setAdmin(String password){ //Metodo que crea un usuario administrador
        this.registrarUsuario("admin", password);
        Usuario admin = identificarUsuario("admin",password);
        admin.setAdmin();
    }
    
    public void setSerial(String nombre){//Metodo que cambia el nombre del objeto
        this.nombre=nombre;
    }
    
    public boolean registrarUsuario(String nombre,String password){ //Metodo que crea un nuevo usuario
        boolean existe=false; //Crea un booleano que nos servirá para ver si un usuario ya existe
        for(Usuario aux: this.jugadores){
            if(aux.getNombre().equals(nombre)){
                existe=true; //Comprobamos si existe dentro del ArrayList y marcamos existe=true
            }
        }
        if(!existe){
            jugadores.add(new Usuario(nombre,password)); //Si el usuario no existe, se añadirá al ArrayList de jugadores
        }
        return(existe);
    }
    
    public Usuario identificarUsuario(String nombre,String password){//Metodo que inicia sesion
        Usuario usuarioBuscar = null; 
        for(Usuario aux: this.jugadores){
            if(aux.getNombre().equals(nombre)){ //Si el nombre del usuario existe
                if(aux.getPassword().equals(password)){ //Y la contraseña del usuario es correcta
                    usuarioBuscar=aux;
                }
            }
        }
        return(usuarioBuscar); //Returnearemos o null si el usuario no existe o el usuarioBuscar con sus datos rellenos si existe dentro del ArrayList
    }
    
    public void remove(Usuario user){
        jugadores.remove(user); //Borra el usuario indicado
    }
    
    public ArrayList <Usuario> getArray(){
        return(this.jugadores); //Devuelve el ArrayList de jugadores
    }
    
    public void setArray(ArrayList users){
        this.jugadores=users; //Modifica el ArrayList con un ArrayList traido como parametro
    }
    
    public void serializar(String name) throws ClassNotFoundException{
        try{
            ObjectOutputStream entrada = new ObjectOutputStream(new FileOutputStream(name));

            entrada.writeObject(this.jugadores);

            entrada.close();
            System.out.println("Guardado correctamente");
        }catch (IOException e) {
            System.out.println("ERROR de escritura!!!"); 
        }
    }
    
    public void deserializar() throws ClassNotFoundException{
        try{
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombre));

            ArrayList<Usuario> objetoLeido = (ArrayList<Usuario>) entrada.readObject();
        
            this.jugadores.addAll(objetoLeido);
            System.out.println("Cargado correctamente");
            
        }catch(IOException e){
            System.out.println("ERROR de lectura!!!");
        }
    }
    
    public void verUsuarios(){
        for(int ii=0;ii<jugadores.size();ii++){
            System.out.println("\t" + (ii+1) + ".- " + jugadores.get(ii).getNombre());
        }                
    }
}
