/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Rigo-PC
 */
public class Proceso {
    
    
    private int identificador;
    private String estado;
    private int prioridad;
    private int ProgramCounter;
    private ArrayList<String> logEventos;
    private boolean uso;
    private ArrayList<Mensaje> bufferMensajes;
    
    public Proceso(int identificador,String estado, int prioridad) {
        
        this.identificador = identificador;
        this.prioridad = prioridad;
        this.logEventos = new ArrayList<String>();
        this.uso=false;
        this.estado = estado;
        bufferMensajes =  new ArrayList<>();
    }
    
    public String getLogEventos(int cantidadEventos){
        
        if(cantidadEventos == -1){
            String evento;
            String logEventos = "";
            for(int i = 0 ; i <  this.logEventos.size(); i++){

                evento = this.logEventos.get(i);
                logEventos = logEventos + evento + "\n";           
            }
            return logEventos; 
        }
        
        else{
            
            String evento;
            String logEventos = "";
            for(int i = 0 ; i < cantidadEventos && i < this.logEventos.size(); i++){
                evento = this.logEventos.get(i);
                logEventos = logEventos + evento + "\n";           
        }
            return logEventos;
            
        }
        
    }
    
    public void AgregarMensaje(Mensaje mensaje){
        bufferMensajes.add(mensaje);
    }
    
    public void AgregarEvento(String evento){
       
        
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
                Date date = new Date();

        String timestamp = formatter.format(date);
        evento += " " + timestamp;

        this.logEventos.add(evento);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

    public boolean isUso() {
        return uso;
    }

    public void setUso(boolean uso) {
        this.uso = uso;
    } 
    
    public void ImprimirProceso(){
        System.out.println("Proceso # " + this.identificador +
        " Estado:  " + estado + " Prioridad: " + this.prioridad);
    }
    
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }


    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getProgramCounter() {
        return ProgramCounter;
    }

    public void setProgramCounter(int ProgramCounter) {
        this.ProgramCounter = ProgramCounter;
    }
    
    public String getStringColaMensajes(){
        
        String cola = "";
        
        int id;
        String tipo ; 
        int destino;
        int fuente;
        int largo;
        String contenido;
        String mensajeCompleto;
        int prioridad;
     
        for(Mensaje mensaje: this.bufferMensajes){
            
            id = mensaje.getId();
            contenido = (String)mensaje.getContenido();
            tipo = mensaje.getTipo();
            destino = mensaje.getDestino();
            fuente = mensaje.getFuente();
            largo = mensaje.getLargo();
            prioridad = mensaje.getPrioridad();
            
            mensajeCompleto = "ID: " + String.valueOf(id) + " Contenido: " 
                    + contenido + " Destino: " + 
                    String.valueOf(destino) + " Fuente: " + String.valueOf(fuente) 
                    + " Largo: " + largo  +" Prioridad: "+String.valueOf(prioridad)+ "\n"; 
            
            cola = cola + mensajeCompleto;
  
        }
        
        return cola;
        
        
        
    }
}
