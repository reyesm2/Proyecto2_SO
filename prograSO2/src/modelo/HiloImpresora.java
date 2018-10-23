/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Controlador.Singleton;

/**
 *
 * @author Rigo-PC
 */
public class HiloImpresora extends Thread{
    private int impresora;
    private int sleep;

    public HiloImpresora(int impresora, int sleep) {
        this.impresora = impresora;
        this.sleep = sleep;
    }

    public int getImpresora() {
        return impresora;
    }

    public void setImpresora(int impresora) {
        this.impresora = impresora;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }
    
    @Override
    public void run(){
        while(true){
            this.esperarXsegundos(sleep);
            boolean estado = Singleton.getInstance().getControlador().receive(impresora);
            if(estado){
                System.out.println("IMPRESORA IMPRIME: "+impresora);
            }else{
                System.out.println("IMPRESORA NO IMPRIME: "+impresora);
            }
        }
    }

    private void esperarXsegundos(int segundos) {
            try {
                    Thread.sleep(segundos * 1000);
            } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
            }
    }

    @Override
    public String toString() {
        return "HiloImpresora{" + "impresora=" + impresora + ", sleep=" + sleep + '}';
    }
    
    
}
