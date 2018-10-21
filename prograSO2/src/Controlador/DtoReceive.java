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
public class DtoReceive {
    private int fuente;
    private int mensaje;

    public DtoReceive(int fuente, int mensaje) {
        this.fuente = fuente;
        this.mensaje = mensaje;
    }

    public int getFuente() {
        return fuente;
    }

    public void setFuente(int fuente) {
        this.fuente = fuente;
    }

    public int getMensaje() {
        return mensaje;
    }

    public void setMensaje(int mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
