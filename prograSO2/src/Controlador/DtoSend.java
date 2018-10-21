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
public class DtoSend {
    private int destino;
    private int mensaje;

    public DtoSend(int destino, int mensaje) {
        this.destino = destino;
        this.mensaje = mensaje;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public int getMensaje() {
        return mensaje;
    }

    public void setMensaje(int mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
