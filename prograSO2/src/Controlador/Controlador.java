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
import modelo.Direccionamiento;
import modelo.Formato;
import modelo.HiloImpresora;
import modelo.Impresora;
import modelo.ListaSolicitudes;
import modelo.ManejoColas;
import modelo.Mensaje;
import modelo.Proceso;
import modelo.Sincronizacion;

/**
 *
 * @author Rigo-PC
 */
public final class Controlador {
    
    private ConfiguracionSistema configuracionSistema;
    private ColaProcesos listaAplicaciones;
    private ArrayList<Impresora> listaImpresoras;
    private final int velocidadImpresion = 30;
    
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
    
    public void DefinirConfiguraciónDefault(){
        Sincronizacion sincronizacion = new Sincronizacion("Nonblocking","Prueba de llegada");
        Direccionamiento direccionamiento = new Direccionamiento("IndirectoEstático",true,true,false,false,false);
        Formato formato = new Formato("Archivo","Largo Variable",-1);
        ManejoColas manejoColas = new ManejoColas("Priodad");
        int tamanoColaProcesos = 10;
        int tamanoColaMensajes = 10;
        this.configuracionSistema = new ConfiguracionSistema(tamanoColaProcesos, tamanoColaMensajes, sincronizacion, direccionamiento, formato, manejoColas);

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
     * @param nombre 
     * @param prioridad 
     * @return  
     */
    public boolean agregarAplicacion(String nombre,String prioridad){
        if(!Singleton.getInstance().getControlador().validarNombreAplicacion(nombre)){
                int contador = Singleton.getInstance().getCantidadAplicaciones();
                Proceso proceso = new Proceso(contador, "Running", Integer.parseInt(prioridad), nombre);
                Singleton.getInstance().setCantidadAplicaciones(contador++);
                proceso.AgregarEvento("La aplicación : "+nombre+" se ha creado.");
                this.listaAplicaciones.AgregarProceso(proceso);
                return true;
        }else{
            return false;
        }  
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
     * Metodo que se encarga de enviar un mensaje
     * @param fuente
     * @param destino
     * @param contenido 
     * @return  
     */
    public boolean send(int fuente, int destino, String contenido){
        int cont = obtenerContadorImpresora(destino);
        int prioridad = obtenerPrioridadAplicacion(fuente);
        Mensaje mensaje = new Mensaje(cont,"Archivo", destino, fuente, -1, contenido, prioridad);
        
        this.listaImpresoras.get(destino).getCasilleroMensajes().AgregarMensajeCasillero(mensaje);
        String impre = Singleton.getInstance().getControlador().nombreImpresora(destino);
        String evento = "La aplicación envio el mensaje: "+contenido+" a la impresora: "+impre;
        agregarEventoAplicacion(fuente, evento);
        //System.out.println(this.listaImpresoras.get(0).getCasilleroMensajes().getCasilleroString());;
        return true;
    }
    
    public String nombreAplicacion(int aplicacion){
        return this.listaAplicaciones.getListaProcesos().get(aplicacion).getNombre();
    }
    
    /**
     * Metodo que permite agregarle un evento a la aplicación
     * @param app
     * @param evento 
     */
    public void agregarEventoAplicacion(int app,String evento){
        this.listaAplicaciones.getListaProcesos().get(app).AgregarEvento(evento);
    }
    
    /**
     * Metodo que obtiene el log del proceso del app
     * @param app
     * @return 
     */
    public String obtenerLogAplicacion(int app){
        return this.listaAplicaciones.getListaProcesos().get(app).getLogEventos(100);
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
     * @param nombre
     * @param size 
     * @return  
     */
    public boolean agregarImpresora(String nombre,String size){
        if(!Singleton.getInstance().getControlador().validarNombreImpresora(nombre)){
            int contador = Singleton.getInstance().getCantidadImpresoras();
            Proceso proceso = new Proceso(contador, "Running",contador, nombre);
            proceso.AgregarEvento("El proceso de la impresora: "+nombre+" se ha creado.");
            CasilleroMensajes casilleroMensajes = new CasilleroMensajes(Integer.parseInt(size),"Prioridad", "");
            HiloImpresora hilo = new HiloImpresora(contador, velocidadImpresion);
            Impresora impresora = new Impresora(proceso, casilleroMensajes,hilo);
            Singleton.getInstance().setCantidadImpresoras(contador+1);
            impresora.iniciarHilo();
            this.listaImpresoras.add(impresora);

            try {
                String current = new java.io.File( "." ).getCanonicalPath();
                current+="\\Impresoras\\"+nombre;
                //System.out.println(current);
                File directorio = new File(current);
                directorio.mkdir(); 
                return true;
            } catch (IOException ex) {
                return false;
                //Logger.getLogger(MenuImpresion.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }else{
            return false;
        }
        
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
     * @return  
     */
    public boolean receive(int impresora){
        CasilleroMensajes casilleroMensajes = this.listaImpresoras.get(impresora).getCasilleroMensajes();
        Mensaje mensaje = casilleroMensajes.SacarMensaje();
        if(mensaje!=null){
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
                String nombreArchivo = appName+"_"+formatoFecha+"_"+origen.getName();
                current+=impreName+"\\"+nombreArchivo;

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
                String evento = "El proceso de la impresora: "+impreName+ " realizo la impresión del archivo: "+nombreArchivo
                        +" de la aplicación: "+appName;
                agregarEventoImpresora(impresora, evento);
                String eventoApp = "El mensaje: "+pathArchivo+" fue impreso por la impresora: "+impreName;
                agregarEventoAplicacion(mensaje.getFuente(), eventoApp);
                return true;
            } catch (IOException ioe){
                return false;
            }
        }else{
            return false;
        }

    }
    
    /**
     * Metodo que permite agregar un evento a una impresora
     * @param impresora
     * @param evento 
     */
    public void agregarEventoImpresora(int impresora,String evento){
        this.listaImpresoras.get(impresora).getProceso().AgregarEvento(evento);
    }

}
