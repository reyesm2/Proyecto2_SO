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
public class Formato {
    private String contenido;
    private String largo;
    private int tamano;

    public Formato(String contenido, String largo, int tamano) {
        this.contenido = contenido;
        this.largo = largo;
        this.tamano = tamano;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getLargo() {
        return largo;
    }

    public void setLargo(String largo) {
        this.largo = largo;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    @Override
    public String toString() {
        return "Formato{" + "contenido=" + contenido + ", largo=" + largo + ", tamano=" + tamano + '}';
    }
    
    
}
