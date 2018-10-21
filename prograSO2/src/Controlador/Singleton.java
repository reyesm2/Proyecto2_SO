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
    
    private int cantidadAplicaciones = 0;  // Usado como identificador
    private int cantidadImpresoras = 0;  // Usado como identificador
    private static Singleton INSTANCE = null;
    private Controlador controlador;

    private Singleton () {
        controlador = new Controlador();
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setCantidadImpresoras(int cantidadImpresoras){
        this.cantidadImpresoras = cantidadImpresoras;
        
    }
    public int getCantidadImpresoras(){
        return this.cantidadImpresoras;
    }
    public int getCantidadAplicaciones() {
        return cantidadAplicaciones;
    }

    public void setCantidadAplicaciones(int cantidadAplicaciones) {
        this.cantidadAplicaciones = cantidadAplicaciones;
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
