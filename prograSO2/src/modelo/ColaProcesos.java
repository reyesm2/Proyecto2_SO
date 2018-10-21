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
public class ColaProcesos {
    private int tamano;
    private ArrayList<Proceso> listaProcesos;

    public ColaProcesos(int tamano){

        this.tamano = tamano;
        listaProcesos = new ArrayList<Proceso>();
    }
    public ArrayList<Proceso> getListaProcesos(){
        return this.listaProcesos;
    }
    
    
    public String getLogEvento(int idProceso, int Ncomandos){
        
        int idTemporal;
        String logEvento;
        for(Proceso proceso: this.listaProcesos){
            idTemporal = proceso.getIdentificador();
            if(idTemporal == idProceso){
                logEvento = proceso.getLogEventos(Ncomandos);
                return logEvento;
            }
        }
        return "";
    }
  
    public String getProceso(int idProceso){
        int idTemporal;
        for(Proceso proceso: this.listaProcesos){
            idTemporal = proceso.getIdentificador();
            if(idTemporal == idProceso){
                String val = " ID:  "+proceso.getIdentificador()+"  Estado:  "+proceso.getEstado()
                        +"  Prioridad:  "+proceso.getPrioridad();
                
                
                
                return val;
            }
        }
        return "";
    }
    
    public String getProcesoMensaje(int idProceso){
        int idTemporal;
        for(Proceso proceso: this.listaProcesos){
            idTemporal = proceso.getIdentificador();
            if(idTemporal == idProceso){
                return proceso.getStringColaMensajes();
            }
        }
        return "";
    }
    
    public boolean isProcessBlocked(int idProceso){
        
        for(Proceso proceso : this.listaProcesos){
            
            int idProcesoTemporal = proceso.getIdentificador();
            String estadoTemporal  = proceso.getEstado();
            if(idProcesoTemporal == idProceso && estadoTemporal.equals("Block")){
                return true;
            }
        }
        return false;
        
    }
    
    
 
    
    public void ImprimirColaProcesos(){

        for (Proceso proceso : this.listaProcesos) {
            proceso.ImprimirProceso();
        }

    }

       public void AgregarProceso(Proceso proceso){
        this.listaProcesos.add(proceso);
    }


    public void agregarEventoProceso(int proceso,String evento){
        for(int i=0;i<this.listaProcesos.size();i++){
            Proceso proc = this.listaProcesos.get(i);
            if(proc.getIdentificador()==proceso){
                proc.AgregarEvento(evento);
                this.listaProcesos.set(i, proc);
            }
        }
    }
    
    public void agregarMensajeProceso(int proceso,Mensaje evento){
        for(int i=0;i<this.listaProcesos.size();i++){
            Proceso proc = this.listaProcesos.get(i);
            if(proc.getIdentificador()==proceso){
                proc.AgregarMensaje(evento);
                this.listaProcesos.set(i, proc);
            }
        }
    }
    
    public void cambiarPC(int proceso,boolean cambio){
        for(int i=0;i<this.listaProcesos.size();i++){
            Proceso proc = this.listaProcesos.get(i);
            if(proc.getIdentificador()==proceso){
                proc.setUso(cambio);
                this.listaProcesos.set(i, proc);
            }
        }
    }
    
    public void cambiarEstadoProceso(int proceso,String estado,boolean uso){
        for(int i=0;i<this.listaProcesos.size();i++){
            Proceso proc = this.listaProcesos.get(i);
            if(proc.getIdentificador()==proceso){
                
                
                String estadoViejo = proc.getEstado();
                if(!proc.getEstado().equals(estado)){
                    String evento = "El proceso: " + String.valueOf(proc.getIdentificador()) + " cambió de estado: " + estadoViejo + "  al nuevo estado: "  + estado;
                    proc.AgregarEvento(evento);
                }
                
                proc.setEstado(estado);
                proc.setUso(uso);
                this.listaProcesos.set(i, proc);
                
            }
        }
    }
}
