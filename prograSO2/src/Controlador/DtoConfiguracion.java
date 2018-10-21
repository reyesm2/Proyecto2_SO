/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import modelo.Direccionamiento;
import modelo.Formato;
import modelo.ManejoColas;
import modelo.Sincronizacion;

/**
 *
 * @author Rigo-PC
 */
public class DtoConfiguracion {
    private int tamanoCola;
    private ManejoColas manejoColas;
    private Formato formato;
    private Direccionamiento direccionamiento;
    private Sincronizacion sincronizacion;
    private int numeroProcesos;

    public DtoConfiguracion(int tamanoCola, ManejoColas manejoColas, Formato formato, Direccionamiento direccionamiento, Sincronizacion sincronizacion, int numeroProcesos) {
        this.tamanoCola = tamanoCola;
        this.manejoColas = manejoColas;
        this.formato = formato;
        this.direccionamiento = direccionamiento;
        this.sincronizacion = sincronizacion;
        this.numeroProcesos = numeroProcesos;
    }

    public int getTamanoCola() {
        return tamanoCola;
    }

    public void setTamanoCola(int tamanoCola) {
        this.tamanoCola = tamanoCola;
    }

    public ManejoColas getManejoColas() {
        return manejoColas;
    }

    public void setManejoColas(ManejoColas manejoColas) {
        this.manejoColas = manejoColas;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Direccionamiento getDireccionamiento() {
        return direccionamiento;
    }

    public void setDireccionamiento(Direccionamiento direccionamiento) {
        this.direccionamiento = direccionamiento;
    }

    public Sincronizacion getSincronizacion() {
        return sincronizacion;
    }

    public void setSincronizacion(Sincronizacion sincronizacion) {
        this.sincronizacion = sincronizacion;
    }

    public int getNumeroProcesos() {
        return numeroProcesos;
    }

    public void setNumeroProcesos(int numeroProcesos) {
        this.numeroProcesos = numeroProcesos;
    }
    
    
}
