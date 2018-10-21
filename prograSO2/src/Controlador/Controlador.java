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
import modelo.Direccionamiento;
import modelo.Formato;
import modelo.ListaSolicitudes;
import modelo.ManejoColas;
import modelo.Mensaje;
import modelo.Proceso;
import modelo.Sincronizacion;
import modelo.Solicitudes;

/**
 *
 * @author Rigo-PC
 */
public final class Controlador {
    
    private ColaMensajes mensajes;
    private ColaMensajes colaMensajes;
    private ColaMensajes colaMensajesProcesados;
    private ConfiguracionSistema configuracionSistema;
    private ColaProcesos colaProcesos;
    private CasilleroMensajes casilleroMensaje;
    private ListaSolicitudes listaSolicitudes;

    public CasilleroMensajes getCasilleroMensaje() {
        return casilleroMensaje;
    }

    public void setCasilleroMensaje(CasilleroMensajes casilleroMensaje) {
        this.casilleroMensaje = casilleroMensaje;
    }

    @Override
    public String toString() {
        return "Controlador{" + "colaMensajes=" + colaMensajes + ", colaMensajesProcesados=" + colaMensajesProcesados + ", configuracionSistema=" + configuracionSistema + ", colaProcesos=" + colaProcesos + ", casilleroMensaje=" + casilleroMensaje + ", listaSolicitudes=" + listaSolicitudes + '}';
    }
    
    public void DefinirConfiguraciónDefault(){
        Sincronizacion sincronizacion = new Sincronizacion("Nonblocking","NonBlocking");
        Direccionamiento direccionamiento = new Direccionamiento("DirectoReceive Explícito",true,true,false,false,false);
        Formato formato = new Formato("Texto","Largo Variable",-1);
        ManejoColas manejoColas = new ManejoColas("FIFO");
        int tamanoColaProcesos = 10;
        int tamanoColaMensajes = 10;
        this.configuracionSistema = new ConfiguracionSistema(tamanoColaProcesos, tamanoColaMensajes, sincronizacion, direccionamiento, formato, manejoColas);
        // Cola de Procesos
        ColaProcesos colaProcesos = new ColaProcesos(tamanoColaProcesos);
        this.colaProcesos = colaProcesos;
        
        // Cola de Mensajes
        ColaMensajes colaMensajes = new ColaMensajes(tamanoColaMensajes);
        this.colaMensajes = colaMensajes;
        
        // Creacion de Procesos
        Singleton.getInstance().getControlador().crearProcesos();
        Singleton.getInstance().getControlador().getColaProcesos().ImprimirColaProcesos();
       
            
        //lista Solicitudes
        ListaSolicitudes listaSolicitudes = new ListaSolicitudes();
        Singleton.getInstance().getControlador().setListaSolicitudes(listaSolicitudes);
        
        //Cola mensajes procesados
        ColaMensajes colaMensajesProcesados = new ColaMensajes(-1);
        Singleton.getInstance().getControlador().setColaMensajesProcesados(colaMensajesProcesados);
    }

    public void CambiarEstadoProceso(int idProceso, String estado){
        
        int idTemporal;
        Proceso procesoTemporal ;
        for(int i = 0; i < this.colaProcesos.getListaProcesos().size();i++){
            
            procesoTemporal =  this.colaProcesos.getListaProcesos().get(i);
            idTemporal = procesoTemporal.getIdentificador();
            if(idTemporal == idProceso){
                
                
                String estadoViejo = procesoTemporal.getEstado();
                procesoTemporal.setEstado(estado);
                this.colaProcesos.getListaProcesos().set(i, procesoTemporal);
                
                String evento = "El proceso " + String.valueOf(idProceso) + " cambió de estado: " + estadoViejo + "al nuevo estado: "  + estado;
                procesoTemporal.AgregarEvento(evento);
                return;
                
            }
        }
    }
    
    public boolean isBlockingSend(){
        
        String configSyncSend = this.configuracionSistema.getSincronizacion().getSend();
        if(configSyncSend.equals("Blocking")){
            return true;
        }
        else{
            return false;
        }
        
    }
    
    public boolean isReceiveExplicit(){
        
        return this.configuracionSistema.getDireccionamiento().isReceiveExplicit();
        
    }
    public boolean isReceiveIndirectStatic(){
       return this.configuracionSistema.getDireccionamiento().isIndirectStatic();
    }
    
    
    public void cambiarEstadoProceso(int proceso,String estado,boolean uso){
        this.colaProcesos.cambiarEstadoProceso(proceso, estado, uso);
    }
    
    public boolean Create(String contenido,int prioridad){
        
        
        String tipoContenido = this.configuracionSistema.getFormato().getContenido();
        
        
        int tamanoCola = Singleton.getInstance().getControlador().getColaMensajes().getListaMensajes().size();
        int maximo = Singleton.getInstance().getControlador().getColaMensajes().getTamano();
        
        if(tamanoCola<maximo){
            int idMensaje = Singleton.getInstance().getCantidadMensajesCreados();
            int largo = this.configuracionSistema.getFormato().getTamano();

            Mensaje mensaje = new Mensaje(idMensaje, tipoContenido, -1, -1, largo, contenido,prioridad);

            // Agrega mensaje a la cola de mensajes
            Singleton.getInstance().getControlador().AgregarMensaje(mensaje);
            int cantidadMensajes = Singleton.getInstance().getCantidadMensajesCreados();
            cantidadMensajes++;

            Singleton.getInstance().setCantidadMensajesCreados(cantidadMensajes);
            //Singleton.getInstance().getControlador().colaMensajes.ImprimirColaMensaje();
            return true;
        }
        
        return false;
    }
    
    public boolean Send(int proceso,int destino, String contenidoMensaje){
        
        boolean sendDirect = this.IsDirectSend();
        boolean sendBlocking = this.isBlockingSend();
        
        if(sendDirect){ // Direccionamiento Directo    (FiFo/Prioridad)

            agregarIdDestinoAMensaje(contenidoMensaje, destino);
            agregarIdFuenteAMensaje(contenidoMensaje, proceso);
            String var = "El proceso: "+String.valueOf(proceso)+" pudo enviar el mensaje: "+contenidoMensaje;
            Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(proceso,var);
            if(sendBlocking){
                this.cambiarEstadoProceso(proceso,"Block", true);
            }else{
                this.cambiarEstadoProceso(proceso,"Running", false);
            }
            boolean estadoDestino = Singleton.getInstance().getControlador().getColaProcesos().isProcessBlocked(destino);
            if(estadoDestino){
                this.cambiarEstadoProceso(destino, "Running", false);
            }
            
        } // Direccionamiento Indirecto
        else{ 
            //boolean isIndirectStatic = this.isReceiveIndirectStatic();

            Mensaje mensaje = this.colaMensajes.encontrarMensaje(contenidoMensaje);
            mensaje.setFuente(proceso);
            boolean agregar = this.casilleroMensaje.AgregarMensajeCasillero(mensaje);
            agregarIdFuenteAMensaje(contenidoMensaje, proceso);
            if(agregar){
                String var = "El proceso: "+String.valueOf(proceso)+" pudo enviar el mensaje: "+contenidoMensaje;
                Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(proceso,var);
            }
            if(sendBlocking){
                this.cambiarEstadoProceso(proceso,"Block", true);
            }else{
                this.cambiarEstadoProceso(proceso,"Running", false);
            }
            
            return agregar;

             
        }
        
        return true;
        
    }
    // por aqui 2 
    public boolean Receive(int idProceso,int idProcesoFuente){
        
        
        boolean isDirectAdressing = this.IsDirectSend();
        String estadoProceso = Singleton.getInstance().getControlador().getConfiguracionSistema().getSincronizacion().getReceive();
        if(isDirectAdressing){ // Direccionamiento Directo (FiFo/Prioridad)

           
            boolean envio;
            Mensaje msg = efectuarManejoCola();
            if(msg!=null){
               envio = efectuarReceive(idProceso, idProcesoFuente, msg); 
            }else{
                envio=false;
            }
            switch (estadoProceso) {
                case "Blocking":
                    if(!envio){
                        this.cambiarEstadoProceso(idProceso,"Block", true);
                        System.out.println("Proceso bloqueado");
                    }else{
                        this.colaMensajes.removerMensaje(msg);
                        msg.setDestino(idProceso);
                        this.colaMensajesProcesados.agregarMensaje(msg);
                        String var = "El proceso: "+String.valueOf(idProcesoFuente)+" recibio la confirmación del proceso: "+idProceso;
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProcesoFuente,var);
                        String var1 = "El proceso: "+String.valueOf(idProceso)+" envio la confirmación al proceso: "+idProcesoFuente;
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProceso,var1);
                        this.cambiarEstadoProceso(idProceso,"Running", false);
                        this.cambiarEstadoProceso(idProcesoFuente,"Running", false);
                        this.agregarMensajeProceso(idProceso, msg);
                        System.out.println("Mensaje procesado");
                    }
                    break;
                case "NonBlocking":
                    if(!envio){
                        this.cambiarEstadoProceso(idProceso,"NonBlock", false);
                        System.out.println("Proceso bloqueado-Non");
                    }else{
                        this.colaMensajes.removerMensaje(msg);
                        msg.setDestino(idProceso);
                        this.colaMensajesProcesados.agregarMensaje(msg);
                        String var = "El proceso: "+String.valueOf(idProcesoFuente)+" recibio la confirmación del proceso: "+idProceso;
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProcesoFuente,var);
                        String var1 = "El proceso: "+String.valueOf(idProceso)+" envio la confirmación al proceso: "+idProcesoFuente;
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProceso,var1);
                        this.cambiarEstadoProceso(idProceso,"Running", false);
                        this.cambiarEstadoProceso(idProcesoFuente,"Running", false);
                        this.agregarMensajeProceso(idProceso, msg);
                        System.out.println("Mensaje procesado-Non");
                    }
                    break;
                default: //test of arrive esta igual al nonblocking
                    if(!envio){
                        this.cambiarEstadoProceso(idProceso,"NonBlock", false);
                        System.out.println("Proceso bloqueado-Non");
                    }else{
                        this.colaMensajes.removerMensaje(msg);
                        msg.setDestino(idProceso);
                        this.colaMensajesProcesados.agregarMensaje(msg);
                        String var = "El proceso: "+String.valueOf(idProcesoFuente)+" recibio la confirmación del proceso: "+idProceso;
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProcesoFuente,var);
                        String var1 = "El proceso: "+String.valueOf(idProceso)+" envio la confirmación al proceso: "+idProcesoFuente;
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProceso,var1);
                        this.cambiarEstadoProceso(idProceso,"Running", false);
                        this.cambiarEstadoProceso(idProcesoFuente,"Running", false);
                        this.agregarMensajeProceso(idProceso, msg);
                        System.out.println("Mensaje procesado-Non");
                    }
                   break;
            }
            if(envio){
                String var = "El proceso: "+String.valueOf(idProceso)+" pudo recibir el mensaje: "+(String)msg.getContenido();
                Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProceso,var);
            }
            return envio;
        }
        else{ // Direccionamiento Indirecto
            String valor = Singleton.getInstance().getControlador().getConfiguracionSistema().getDireccionamiento().getTipoDireccionamiento();
            if(valor.equals("IndirectoEstático")){
                if(idProceso==Singleton.getInstance().getControlador().getCasilleroMensaje().getProcesoAsociado()){
                    Mensaje mensaje = this.casilleroMensaje.SacarMensaje();
                    if(mensaje!=null){
                        switch(estadoProceso){
                            case "Blocking":
                                break;
                            case "NonBlocking":
                                break;
                            default:
                                break;
                        }        
                        mensaje.setDestino(idProceso);
                       
                        boolean eliminado = this.colaMensajes.getListaMensajes().remove(mensaje);
                        if(eliminado){
                            String var1 = "El proceso: "+String.valueOf(idProcesoFuente)+" recibio la confirmación del proceso: "+idProceso;
                            Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProcesoFuente,var1);
                            String var2 = "El proceso: "+String.valueOf(idProceso)+" envio la confirmación al proceso: "+idProcesoFuente;
                            Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProceso,var2);
                            String var = "El proceso: "+String.valueOf(idProceso)+" pudo recibir el mensaje: "+(String)mensaje.getContenido();
                            Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProceso,var);
                            
                            this.colaMensajesProcesados.agregarMensaje(mensaje);
                            this.agregarMensajeProceso(idProceso, mensaje);
                            this.cambiarEstadoProceso(mensaje.getDestino(), "Running", false);
                            this.cambiarEstadoProceso(mensaje.getFuente(), "Running", false);
                            return eliminado;
                        }

                    }
                }
            }else{
                Mensaje mensaje = this.casilleroMensaje.SacarMensaje();
                if(mensaje!=null){
                    switch(estadoProceso){
                        case "Blocking":
                            break;
                        case "NonBlocking":
                            break;
                        default:
                            break;
                    }        
                    mensaje.setDestino(idProceso);

                    boolean eliminado = this.colaMensajes.getListaMensajes().remove(mensaje);
                    if(eliminado){
                        String var1 = "El proceso: "+String.valueOf(idProcesoFuente)+" recibio la confirmación del proceso: "+idProceso;
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProcesoFuente,var1);
                        String var2 = "El proceso: "+String.valueOf(idProceso)+" envio la confirmación al proceso: "+idProcesoFuente;
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProceso,var2);
                        String var = "El proceso: "+String.valueOf(idProceso)+" pudo recibir el mensaje: "+(String)mensaje.getContenido();
                        Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProceso,var);

                        this.colaMensajesProcesados.agregarMensaje(mensaje);
                        this.agregarMensajeProceso(idProceso, mensaje);
                        this.cambiarEstadoProceso(mensaje.getDestino(), "Running", false);
                        this.cambiarEstadoProceso(mensaje.getFuente(), "Running", false);
                        return eliminado;
                    }

                }
            }
            return false;
        }
    }
    
    public boolean efectuarReceive(int idProceso,int idFuente,Mensaje msg){
        if(determinarSendEjecutado(msg)){
            boolean receiveDirectExplicit = this.isReceiveExplicit();
            if(receiveDirectExplicit){ // Receive Directo Explicito
                if(msg.getFuente()==idFuente){
                    return true;
                }else{
                    return false;
                }
            }          
            else{ // Receive Directo Implicito
                return true;
            }
        }
        return false;
            
    }
    
    public Mensaje efectuarManejoCola(){
        if(this.colaMensajes.getListaMensajes().size()>0){
            if(this.getConfiguracionSistema().getManejoColas().getTipo().equals("FIFO")){ //FIFO
                Mensaje msg = this.primeroColaMensajes();
                return msg;

            }else{ //prioridad
                 return ejecutarPrioridades();
            }
        }
        return null;
    }
    
    public Mensaje ejecutarPrioridades(){
        
        ArrayList<Integer> listaPrioridades;
        int pos;
        
        listaPrioridades = determinarPrioridadMensajes();
        pos = determinarPrioridadMayor(listaPrioridades);
        //System.out.println("Posicion menor prioridad: "+pos);
        Mensaje msg = (Mensaje) this.colaMensajes.getListaMensajes().get(pos);
        
        return msg;

    }
    
    public ArrayList<Integer> determinarPrioridadMensajes(){
        ArrayList<Integer> listaPrioridad = new ArrayList<>();
        for(int i=0;i<this.colaMensajes.getListaMensajes().size();i++){
           Mensaje msg = (Mensaje) this.colaMensajes.getListaMensajes().get(i);
           int prioridad = msg.getPrioridad();
           listaPrioridad.add(prioridad);
        
        }
        
        return listaPrioridad;
    }
    
    public int determinarPrioridadMayor(ArrayList<Integer> prioridades){
        int result=0;
        int valor=prioridades.get(0);
        int valor1=0;
        for(int i=1;i<prioridades.size();i++){
            valor1 = prioridades.get(i);
            //if(valor1>=0){
                if(valor1>valor){
                    result=i;
                }
            //}
        }
        return result;
    }
    
    public void agregarMensajeProceso(int proceso,Mensaje mensaje){
        this.colaProcesos.agregarMensajeProceso(proceso, mensaje);
    }
    
    /**
     * determina si un mensaje ya se le ejecuto un send
     * @param msg 
     * @return  
     */
    public boolean determinarSendEjecutado(Mensaje msg){
        return msg.getDestino()!=-1;
    }
    
    public void setSolicitud(Solicitudes solicitudes){
        this.listaSolicitudes.getListaSolicitudes().add(solicitudes);
    }
    
    public boolean agregarFuenteImplicito(String idMensaje,int idProcesoFuente){
        Solicitudes solicitud = new Solicitudes(idMensaje, idProcesoFuente, "ReceiveImplicito", true);
        this.listaSolicitudes.getListaSolicitudes().add(solicitud);
        return true;
    }
    public boolean agregarFuenteExplicito(String idMensaje,int idProcesoFuente){
        Solicitudes solicitud = new Solicitudes(idMensaje, idProcesoFuente, "ReceiveExplicito", true);
        this.listaSolicitudes.getListaSolicitudes().add(solicitud);
        return true;
    }
    
    public boolean isProcessBlocked(int idProceso){
        
        return this.colaProcesos.isProcessBlocked(idProceso);
        
    }
    
  
    
    public boolean agregarIdDestinoAMensaje(String contenido, int idDestino){
        
        return this.colaMensajes.agregarIdDestino(contenido, idDestino);

    }
    
    public boolean agregarIdFuenteAMensaje(String contenido, int idFuente){
        
        return this.colaMensajes.agregarIdFuente(contenido, idFuente);

        
    }
    
    public boolean completitudMensaje(String contenidoMensaje){
        String contenidoActual;
        Mensaje mensaje;
        for(int i=0;i<this.colaMensajes.getListaMensajes().size();i++){
            mensaje = (Mensaje) this.colaMensajes.getListaMensajes().get(i);
            contenidoActual = (String) mensaje.getContenido();
            if (contenidoActual.equals(contenidoMensaje)){
                if(mensaje.getDestino()!=-1 && mensaje.getFuente()!=-1){
                    this.colaMensajesProcesados.getListaMensajes().add(mensaje);
                    this.getColaProcesos().cambiarPC(mensaje.getDestino(), false);
                    this.getColaProcesos().cambiarPC(mensaje.getFuente(), false);
                    return colaMensajes.removerMensaje(mensaje);
                }else{
                    return false;
                }
            }
        }
        return false;
    }
    
    public boolean evaluarFiFo(String operacion){
        Mensaje mensaje = (Mensaje) this.colaMensajes.getListaMensajes().get(0);
        if(operacion.equals("Send")){
            if(mensaje.getFuente()!=-1){
                return true;
            }
        }else{
            if(mensaje.getDestino()!=-1){
                return true;
            }
        }
        return false;
    }
    
    public Mensaje primeroColaMensajes(){
        if(this.colaMensajes.getListaMensajes().size()>0){
            return (Mensaje) this.colaMensajes.getListaMensajes().get(0);
        }
        return null;
    }
    
    public Mensaje encontrarMensaje(String contenidoMensaje){
        Mensaje mensaje = this.colaMensajes.encontrarMensaje(contenidoMensaje);
        return mensaje;
    }
    // Pregunta si la configuracion tiene Send de tipo Directo
    public boolean IsDirectSend(){
        
        
        boolean isDirect = this.configuracionSistema.getDireccionamiento().isDirect();
        //String direcionamiento = this.getConfiguracionSistema().getDireccionamiento();
        return isDirect;
        
    }
    
    public ColaMensajes getColaMensajes() {
        return colaMensajes;
    }
    
    public void AgregarMensaje(Mensaje mensaje){
        
        this.colaMensajes.agregarMensaje(mensaje);
        
    }
            

    public void setColaMensajes(ColaMensajes colaMensajes) {
        this.colaMensajes = colaMensajes;
    }

    public ConfiguracionSistema getConfiguracionSistema() {
        return configuracionSistema;
    }

    public void setConfiguracionSistema(ConfiguracionSistema configuracionSistema) {
        this.configuracionSistema = configuracionSistema;
    }

    public ColaProcesos getColaProcesos() {
        return colaProcesos;
    }

    public void setColaProcesos(ColaProcesos colaProcesos) {
        this.colaProcesos = colaProcesos;
    }
    
    
    public boolean crearProcesos(){
        
        int identificador ;
        int cantidadProcesos = this.getConfiguracionSistema().getNumeroProcesos();
        
        
        if (cantidadProcesos == 0){
            return false;
        }
        
        String estadoSend ;
        String estadoReceive;
        String evento;
        for(int i = 0; i < cantidadProcesos; i++){
            
            identificador = Singleton.getInstance().getCantidadProcesosCreados();
            
            //estado = this.configuracionSistema.
            estadoSend = this.configuracionSistema.getSincronizacion().getSend();
            estadoReceive = this.configuracionSistema.getSincronizacion().getReceive();
            
            Proceso proceso = new Proceso(identificador,"Running",identificador);
            int idProceso = proceso.getIdentificador();
            
            // Evento de creacion
            evento = "El proceso: " + String.valueOf(idProceso) + " fue creado";
            proceso.AgregarEvento(evento);
            
            identificador++;
            Singleton.getInstance().setCantidadProcesosCreados(identificador);
            
            //Singleton.getInstance().getControlador().getColaProcesos();
            Singleton.getInstance().getControlador().getColaProcesos().AgregarProceso(proceso);
            
        }
        return true;
    }

    public void cambiarAsociacionMailbox(int proceso){
        this.getCasilleroMensaje().setProcesoAsociado(proceso);
    }
    
    public ListaSolicitudes getListaSolicitudes() {
        return listaSolicitudes;
    }

    public void setListaSolicitudes(ListaSolicitudes listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }

    public ColaMensajes getColaMensajesProcesados() {
        return colaMensajesProcesados;
    }

    public void setColaMensajesProcesados(ColaMensajes colaMensajesProcesados) {
        this.colaMensajesProcesados = colaMensajesProcesados;
    }

    public ColaMensajes getMensajes() {
        return mensajes;
    }

    public void setMensajes(ColaMensajes mensajes) {
        this.mensajes = mensajes;
    }
    
}
