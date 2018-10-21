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
public class Solicitudes {
    
    private String mensaje;
    private int proceso;
    private String detalle;
    private boolean estado;

    public Solicitudes(String mensaje, int proceso, String detalle, boolean estado) {
        this.mensaje = mensaje;
        this.proceso = proceso;
        this.detalle = detalle;
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getProceso() {
        return proceso;
    }

    public void setProceso(int proceso) {
        this.proceso = proceso;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Solicitudes{" + "mensaje=" + mensaje + ", proceso=" + proceso + ", detalle=" + detalle + ", estado=" + estado + '}';
    }
    
}
