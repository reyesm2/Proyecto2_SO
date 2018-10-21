/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author Rigo-PC
 */
public class Singleton {
    
    private int cantidadProcesosCreados = 0;  // Usado como identificador
    private int cantidadMensajesCreados = 0;  // Usado como identificador
    private static Singleton INSTANCE = null;
    private Controlador controlador;

    private Singleton () {
        controlador = new Controlador();
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setCantidadMensajesCreados(int cantidadMensajesCreados){
        this.cantidadMensajesCreados = cantidadMensajesCreados;
        
    }
    public int getCantidadMensajesCreados(){
        return this.cantidadMensajesCreados;
    }
    public int getCantidadProcesosCreados() {
        return cantidadProcesosCreados;
    }

    public void setCantidadProcesosCreados(int cantidadProcesosCreados) {
        this.cantidadProcesosCreados = cantidadProcesosCreados;
    }
    
    
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private synchronized  static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }
}
