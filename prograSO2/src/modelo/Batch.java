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

/**
 *
 * @author josed
 */
public class Batch {
    
    
    
    public void leerArchivo(File file) throws FileNotFoundException, IOException{
        
        
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String linea = "";
        
        String nComandosBatch = "ncomandos";
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
                    
                }
                else if(agregarImpresora){
                    
                
                    String nombreImpresora = "";
                    String sizeImpresoraString = "";
                
                    nombreImpresora = linea;
                    linea = br.readLine();
                    sizeImpresoraString = linea; 
                    
                }
                
                else if(agregarAplicacion){
                    
                    String nombreAplicacion = "";
                    String prioridadAplicacion = "";
                
                    nombreAplicacion = linea;               
                    linea = br.readLine();
                    prioridadAplicacion = br.readLine();
                
                    
                }
                else if(send){
                    
                    String sendFuenteString  = "";
                    String sendDestinoString  = "";
                
                    sendFuenteString = linea;              
                    linea = br.readLine();
                    sendDestinoString = linea;
                
                    
                }
                else if(receive){
                    
                    
                }
                
                
            }
            
            
            
        }
    }
    
    
}
