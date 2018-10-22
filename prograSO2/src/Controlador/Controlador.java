/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import modelo.CasilleroMensajes;
import modelo.ColaMensajes;
import modelo.ColaProcesos;
import modelo.ConfiguracionSistema;
import modelo.Impresora;
import modelo.Mensaje;
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
    /**
     * Metodo que obtiene el ultimo mensaje de la impresora
     * @param impresora
     * @return 
     */
    public int obtenerContadorImpresora(int impresora){
        return this.listaImpresoras.get(impresora).getContador();
    }
    
    public int obtenerPrioridadAplicacion(int aplicacion){
        return this.listaAplicaciones.getListaProcesos().get(aplicacion).getPrioridad();
    }
    
    /**
     * 
     * @param fuente
     * @param destino
     * @param contenido 
     */
    public void send(int fuente, int destino, String contenido){
        int cont = obtenerContadorImpresora(destino);
        int prioridad = obtenerPrioridadAplicacion(fuente);
        Mensaje mensaje = new Mensaje(cont,"Archivo", destino, fuente, -1, contenido, prioridad);
        
        this.listaImpresoras.get(destino).getCasilleroMensajes().AgregarMensajeCasillero(mensaje);
        //System.out.println(this.listaImpresoras.get(0).getCasilleroMensajes().getCasilleroString());;
    }
    
    public String nombreAplicacion(int aplicacion){
        return this.listaAplicaciones.getListaProcesos().get(aplicacion).getNombre();
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
    /**
     * Metodo que permite agregar una nueva impresora
     * @param impresora 
     */
    public void agregarImpresora(Impresora impresora){
        this.listaImpresoras.add(impresora);
    }
    /**
     *  metodo que retorna el nombre de una impresora
     * @param impresora
     * @return 
     */
    public String nombreImpresora(int impresora){
        return this.listaImpresoras.get(impresora).getProceso().getNombre();
    }
    
    /**
     * Valida si pueden agregar mas mensajes
     * @param impresora
     * @return 
     */
    public boolean validarSizeImpresora(int impresora){
        return this.listaImpresoras.get(impresora).getCasilleroMensajes().validarSize();
    }
    
    /**
     * Metodo encargo de realizar la impresión de una impresora
     * @param impresora 
     */
    public void receive(int impresora){
        CasilleroMensajes casilleroMensajes = this.listaImpresoras.get(impresora).getCasilleroMensajes();
        Mensaje mensaje = casilleroMensajes.SacarMensaje();
        this.listaImpresoras.get(impresora).getProceso().AgregarMensaje(mensaje);
    
        String pathArchivo = (String) mensaje.getContenido();
        
        try {
            String current = new java.io.File( "." ).getCanonicalPath();
                    current+="\\Impresoras\\";

            File origen = new File(pathArchivo);
            
            String appName = Singleton.getInstance().getControlador().nombreAplicacion(mensaje.getFuente());
            String impreName = Singleton.getInstance().getControlador().nombreImpresora(mensaje.getDestino());
            Date fecha = new Date();
            String formatoFecha =String.valueOf(fecha.getDate())+"_"+
                    String.valueOf(fecha.getHours())+"-"+
                    String.valueOf(fecha.getMinutes())+"-"+
                    String.valueOf(fecha.getSeconds());
            System.out.println(formatoFecha);
            current+=impreName+"\\"+appName+"_"+formatoFecha+"_"+origen.getName();
            
            File destino = new File(current);

            InputStream in = new FileInputStream(origen);
            OutputStream out = new FileOutputStream(destino);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
            }

            in.close();
            out.close();
        } catch (IOException ioe){
        }
        
    }

}
