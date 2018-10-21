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
public class ListaSolicitudes {
        private ArrayList<Solicitudes> listaSolicitudes;

    public ListaSolicitudes() {
        listaSolicitudes = new ArrayList<>();
    }

        
    public ListaSolicitudes(ArrayList<Solicitudes> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }

    public ArrayList<Solicitudes> getListaSolicitudes() {
        return listaSolicitudes;
    }

    public void setListaSolicitudes(ArrayList<Solicitudes> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }
        
    
    public boolean cambiarEstadoImplicito(String mensaje,int idProcesoFuente){
        for (int i=0;i<listaSolicitudes.size();i++) {
            Solicitudes Solicitud = listaSolicitudes.get(i);
            if(mensaje.equals(Solicitud.getMensaje()) && !Solicitud.isEstado()){
                Solicitud.setDetalle("Implicito Confirmado");
                Solicitud.setProceso(idProcesoFuente);
                Solicitud.setEstado(true);
                listaSolicitudes.set(i, Solicitud);
                return true;
            }
        }
        return false;
    }
}
