/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Rigo-PC
 */
public class Sincronizacion {
    private String send;
    private String receive;

    public Sincronizacion(String send, String receive) {
        this.send = send;
        this.receive = receive;
    }
    
    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    @Override
    public String toString() {
        return "Sincronizacion{" + "send=" + send + ", receive=" + receive + '}';
    }
    
    
    
}
