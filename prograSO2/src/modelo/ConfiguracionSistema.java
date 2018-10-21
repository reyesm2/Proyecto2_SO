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
public class ConfiguracionSistema {
    private int numeroProcesos;
    private int tamanoCola;
    private Sincronizacion sincronizacion;
    private Direccionamiento direccionamiento;
    private Formato formato;
    private ManejoColas manejoColas;

    public ConfiguracionSistema(int numeroProcesos, int tamanoCola, Sincronizacion sincronizacion, Direccionamiento direccionamiento, Formato formato, ManejoColas manejoColas) {
        this.numeroProcesos = numeroProcesos;
        this.tamanoCola = tamanoCola;
        this.sincronizacion = sincronizacion;
        this.direccionamiento = direccionamiento;
        this.formato = formato;
        this.manejoColas = manejoColas;
    }

    public int getNumeroProcesos() {
        return numeroProcesos;
    }

    public void setNumeroProcesos(int numeroProcesos) {
        this.numeroProcesos = numeroProcesos;
    }

    public int getTamanoCola() {
        return tamanoCola;
    }

    public void setTamanoCola(int tamanoCola) {
        this.tamanoCola = tamanoCola;
    }

    public Sincronizacion getSincronizacion() {
        return sincronizacion;
    }

    public void setSincronizacion(Sincronizacion sincronizacion) {
        this.sincronizacion = sincronizacion;
    }

    public Direccionamiento getDireccionamiento() {
        return direccionamiento;
    }

    public void setDireccionamiento(Direccionamiento direccionamiento) {
        this.direccionamiento = direccionamiento;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public ManejoColas getManejoColas() {
        return manejoColas;
    }

    public void setManejoColas(ManejoColas manejoColas) {
        this.manejoColas = manejoColas;
    }

    @Override
    public String toString() {
        return "ConfiguracionSistema{" + "numeroProcesos=" + numeroProcesos + ", tamanoCola=" + tamanoCola + ", sincronizacion=" + sincronizacion + ", direccionamiento=" + direccionamiento + ", formato=" + formato + ", manejoColas=" + manejoColas + '}';
    }
    
    
    
}
