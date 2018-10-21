/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Rigo-PC
 */
public class ColaMensajes {
    
    private int tamano;
    private ArrayList<Mensaje> listaMensajes;
    
    public ColaMensajes(int tamano) {
        this.tamano = tamano;
        this.listaMensajes = new ArrayList<Mensaje>();
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
     
        for(Mensaje mensaje: this.listaMensajes){
            
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

    
    
    public boolean agregarIdDestino(String contenidoMensaje, int idDestino){
        
        
        Mensaje mensajeTemporal;
        String contenidoTemporal;
        for(int i = 0; i < this.listaMensajes.size(); i++){
            
            mensajeTemporal = listaMensajes.get(i);
            contenidoTemporal = (String)mensajeTemporal.getContenido();
            if(contenidoTemporal.equals(contenidoMensaje)){
                mensajeTemporal.setDestino(idDestino);
                listaMensajes.set(i, mensajeTemporal);
                return true;
            }
        }
        return false;
    }
    public boolean agregarIdFuente(String contenidoMensaje, int idFuente){
        
        Mensaje mensajeTemporal;
        String contenidoTemporal;
        for(int i = 0; i < this.listaMensajes.size(); i++){
            
            mensajeTemporal = listaMensajes.get(i);
            contenidoTemporal = (String)mensajeTemporal.getContenido();
            if(contenidoTemporal.equals(contenidoMensaje)){
                mensajeTemporal.setFuente(idFuente);
                listaMensajes.set(i, mensajeTemporal);
                return true;
            }
        }
        return false;
    }
    
    public Mensaje encontrarMensaje(String contenidoMensaje){
        
        String contenidoActual;
        for(Mensaje mensaje : listaMensajes){
            
            contenidoActual = (String)mensaje.getContenido();
            if (contenidoActual.equals(contenidoMensaje)){
                return mensaje;
            }
        }
        return null;
    }
    
    public boolean removerMensaje(Mensaje mensaje){
        return listaMensajes.remove(mensaje);
    }
    
    public void agregarMensaje(Mensaje mensaje){
        this.listaMensajes.add(mensaje);
        
    }
    
    public void ImprimirColaMensaje(){
        
        for(Mensaje mensaje: this.listaMensajes){
            mensaje.ImprimirMensaje();
        }
    }
    
    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public ArrayList getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(ArrayList listaMensajes) {
        this.listaMensajes = listaMensajes;
    }
    
    
}
