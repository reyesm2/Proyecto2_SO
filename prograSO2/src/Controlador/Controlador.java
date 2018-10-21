/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.ArrayList;
import modelo.CasilleroMensajes;
import modelo.ColaMensajes;
import modelo.ColaProcesos;
import modelo.ConfiguracionSistema;
import modelo.Impresora;
import modelo.Proceso;

/**
 *
 * @author Rigo-PC
 */
public final class Controlador {
    
    private ConfiguracionSistema configuracionSistema;
    private ColaProcesos listaAplicaciones;
    private ArrayList<Impresora> listaImpresoras;

    public Controlador() {
        this.configuracionSistema=null;
        this.listaAplicaciones = new ColaProcesos(1000);
        this.listaImpresoras = new ArrayList<>();
    }

    public ConfiguracionSistema getConfiguracionSistema() {
        return configuracionSistema;
    }

    public void setConfiguracionSistema(ConfiguracionSistema configuracionSistema) {
        this.configuracionSistema = configuracionSistema;
    }

    public ColaProcesos getListaAplicaciones() {
        return listaAplicaciones;
    }

    public void setListaAplicaciones(ColaProcesos listaAplicaciones) {
        this.listaAplicaciones = listaAplicaciones;
    }

    public ArrayList<Impresora> getListaImpresoras() {
        return listaImpresoras;
    }

    public void setListaImpresoras(ArrayList<Impresora> listaImpresoras) {
        this.listaImpresoras = listaImpresoras;
    }

    //Lógica para Aplicaciones
    
    /**
     * Permite ver si existe otra aplicación con el mismo nombre
     * @param nombre
     * @return 
     */
    public boolean validarNombreAplicacion(String nombre){
        return this.listaAplicaciones.validarNombre(nombre);
    }
    
    /**
     * Metodo que permite agregar un proceso
     * @param proceso 
     */
    public void agregarAplicacion(Proceso proceso){
        this.listaAplicaciones.AgregarProceso(proceso);
    }
    
    //Lógica para Impresoras
    
    /**
     * Metodo que permite ver si existe el nombre de la impresora
     * @param nombre
     * @return 
     */
    public boolean validarNombreImpresora(String nombre){
        for (Impresora listaImpresora : listaImpresoras) {
            Proceso proceso = listaImpresora.getProceso();
            if(proceso.getNombre().equals(nombre)){
                return true;
            }
        }
        
        return false;
    }
    
    public void agregarImpresora(Impresora impresora){
        this.listaImpresoras.add(impresora);
    }
}
