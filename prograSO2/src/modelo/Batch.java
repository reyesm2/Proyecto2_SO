/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Controlador.Singleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author josed
 */
public class Batch {
    
    
    
    public void leerArchivo(File file) throws FileNotFoundException, IOException{
        
        
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String linea = "";
        
        String nComandosBatch = "#ncomandos";
        String agregarAplicacionBatch = "#agregarAplicacion";
        String agregarImpresoraBatch = "#agregarImpresora";
        String sendBatch = "#send";
        String receiveBatch = "#receive";
        
        // Banderas
        
        boolean nComandos = false;
        boolean agregarImpresora = false;
        boolean agregarAplicacion  = false;
        boolean send = false;
        boolean receive = false;
        
        int cantidadNComandos = 1000;
        
        Singleton.getInstance().getControlador().DefinirConfiguraciónDefault();
        
        while( (linea = br.readLine() ) != null){
            

            if(linea.equals(nComandosBatch)){
                
                nComandos = true;
                agregarImpresora = false;
                agregarAplicacion = false;
                send = false;
                receive = false;
                
                
            }
            else if (linea.equals(agregarAplicacionBatch)){
                
                nComandos = false;
                agregarImpresora = false;
                agregarAplicacion = true;
                send = false;
                receive = false;
                
                /*
                String nombreAplicacion = "";
                String prioridadAplicacion = "";
                
                linea = br.readLine();
                nombreAplicacion = linea;
                
                linea = br.readLine();
                prioridadAplicacion = br.readLine();
*/
                
                               
            }
            else if(linea.equals(agregarImpresoraBatch)){
                
                nComandos = false;
                agregarImpresora = true;
                agregarAplicacion = false;
                send = false;
                receive = false;
                
                /*
                String nombreImpresora = "";
                String sizeImpresoraString = "";
                
                linea = br.readLine();
                nombreImpresora = br.readLine();
                
                linea = br.readLine();
                sizeImpresoraString = linea;  
*/
            }
            
            else if(linea.equals(sendBatch)){
                
                nComandos = false;
                agregarImpresora = false;
                agregarAplicacion = false;
                send = true;
                receive = false;
             
            }
            
            else if (linea.equals(receiveBatch)){
                
                nComandos = true;
                agregarImpresora = false;
                agregarAplicacion = false;
                send = false;
                receive = false;
                
                
                /*
                String numeroImpresoraString = "";
                linea = br.readLine();
                numeroImpresoraString = linea;
*/
            }
            else{
                
                if(nComandos){
                    
                    
                    String nComandosString = "";
                    nComandosString = linea;
                    cantidadNComandos = Integer.parseInt(nComandosString);
                    
                }
                else if(agregarImpresora && cantidadNComandos > 0){
                    
                
                    String nombreImpresora = "";
                    String sizeImpresoraString = "";
                
                    nombreImpresora = linea;
                    linea = br.readLine();
                    sizeImpresoraString = linea;
                    
                    
             
                    if(!nombreImpresora.equals("") && !nombreImpresora.equals("")){
                        
                        if(Singleton.getInstance().getControlador().agregarImpresora(nombreImpresora,sizeImpresoraString)){
                            
                    }
                        else{
                            this.mensajeDialog("Existe una impresora con el mismo nombre.", "Existencia impresora");
                        }
                        
                    }
                    
                    else{
                        this.mensajeDialog("Completo todos los datos.", "Datos Incompletos");
                    }
        
                    cantidadNComandos--;
                }
                
                else if(agregarAplicacion && cantidadNComandos > 0){
                    
                    String nombreAplicacion = "";
                    String prioridadAplicacion = "";
                
                    nombreAplicacion = linea;               
                    linea = br.readLine();
                    prioridadAplicacion = linea;
                    
                    
                    System.out.println(nombreAplicacion);
                    System.out.println(prioridadAplicacion);
                   
                    if(!nombreAplicacion.equals("") && !prioridadAplicacion.equals("")){
                        
                        if(Singleton.getInstance().getControlador().agregarAplicacion(nombreAplicacion,prioridadAplicacion)){
                        
                            this.mensajeDialog("Aplicación creada con éxito.","Creación éxitosa"); 
                        }
                        
                        else{                         
                            this.mensajeDialog("Existe una aplicación con el mismo nombre.", "Existencia aplicación");
                        }
                    }
                    
                    else{
                        this.mensajeDialog("Completo todos los datos.", "Datos Incompletos");
                    }
                    
                    
                    cantidadNComandos--;
                }
                
                
                
                else if(send && cantidadNComandos > 0){
                    
                    String sendFuenteString  = "";
                    String sendDestinoString  = "";
                    String mensajeAImprimir  = "";
                    
                    sendFuenteString = linea;
                    
                    linea = br.readLine();
                    sendDestinoString = linea;
                    
                    linea = br.readLine();
                    mensajeAImprimir = linea;
                    
                    int sendFuente = Integer.parseInt(sendFuenteString);
                    int sendDestino = Integer.parseInt(sendDestinoString);
                    
                  // fuente, destino, contenido
                  
                  
                    if(sendFuente !=-1 && sendDestino!=-1 && !mensajeAImprimir.equals("")){
                        
                        if(Singleton.getInstance().getControlador().validarSizeImpresora(sendDestino)){
                            
                            Singleton.getInstance().getControlador().send(sendFuente, sendDestino, mensajeAImprimir);
                            this.mensajeDialog("Se envio el mensaje.", "Mensaje enviado.");
                        //cargarLogApp(sendFuente);
                    }
                        else{
                            
                            this.mensajeDialog("Bandeja de entrada llena.", "Impresora llena."); 
                        }
                    }
                    
                    else{
                        this.mensajeDialog("Complete todos los datos.", "Datos Incompletos");
                    }
   
                }
                
                cantidadNComandos--;
                           
            }
 
        }
    


    
    
    
    }
    
    
    public void mensajeDialog(String mensaje, String tituloBarra){
        
        JOptionPane.showMessageDialog(null, mensaje, tituloBarra, JOptionPane.INFORMATION_MESSAGE);
    }
    
    
}
