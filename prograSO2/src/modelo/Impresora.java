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
public class Impresora {
    private Proceso proceso;
    private CasilleroMensajes casilleroMensajes;

    public Impresora(Proceso proceso, CasilleroMensajes casilleroMensajes) {
        this.proceso = proceso;
        this.casilleroMensajes = casilleroMensajes;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public CasilleroMensajes getCasilleroMensajes() {
        return casilleroMensajes;
    }

    public void setCasilleroMensajes(CasilleroMensajes casilleroMensajes) {
        this.casilleroMensajes = casilleroMensajes;
    }
    
}
