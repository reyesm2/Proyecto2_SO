/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Controlador.Singleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author josed
 */
public class Batch {
    
    
    
   public Batch(){
       
   }
   
   public void leerArchivo(File file) throws IOException{
       
       FileReader fileReader = new FileReader(file);
       String linea = "";
       BufferedReader br = new BufferedReader(fileReader);
       
       boolean configuracion = false;
       boolean create = false;
       boolean send = false;
       boolean receive = false;
       
       String tipoContenido = "";
       String tipoLargo = "";
       String largoMensajeString = "";
       String manejoColasString = "";
       String syncSend = "";
       String syncReceive = "";
       String addresingType = "";
       String addresingSubType = "";
       String numeroProcesos = "";
       String tamanoColaMensajesString = "";
       String tamanoMailBoxString = "";
       
       
       
       
       
       int cantidadInstruccionConfiguracion = -1;
       int cantidadNComandos = 1000 ;
       
       
       while( (linea = br.readLine() ) != null){
           
           if(linea.equals("#configuracion")){
               
               configuracion = true;
               create = false;
               send = false;
               receive = false;
               cantidadInstruccionConfiguracion = 0;
           }
           
           else if(linea.equals("#create")){
               
               create = true;   
               configuracion = false;
               send = false;
               receive = false;
               cantidadInstruccionConfiguracion = 0;
           }
           
           else if(linea.equals("#send")){
               send = true;
               configuracion = false;
               create = false;
               receive = false;
               cantidadInstruccionConfiguracion = 0;
               
           }
           else if(linea.equals("#receive")){
               System.out.println("RECEIVE");
               receive = true;
               configuracion = false;
               create = false;
               send = false;
               cantidadInstruccionConfiguracion = 0;
           }
           
           
           else{
               
               //System.out.println("Cantidad de Comandos: " + cantidadNComandos);
               
               if(configuracion){
                   
                   switch(cantidadInstruccionConfiguracion){
 
                       
                       
                       case (0):{
                           
                           String cantidadComandos = linea;
                           cantidadNComandos = Integer.parseInt(cantidadComandos);
                           //cantidadNComandos = 10;
                           break;                          
                       }
                          
                       case(1): { // Tipo de Mensaje
                           
                           tipoContenido = linea;
                           break;
                       }
                       case(2):{
                           
                           tipoLargo = linea;
                           break;
                       }
                       case(3):{
                           
                           largoMensajeString = linea;
                           break;    
                       }
                       case(4): {
                           
                           manejoColasString = linea;
                           break;    
                       }
                       case(5):{
                           
                           syncSend = linea;
                           break;
                           
                       }
                       case(6):{
                           
                           syncReceive = linea;
                           break;
                       }
                       case(7): {
                           addresingType = linea;
                           break;
                       }
                       case(8): {
                           addresingSubType = linea;
                           break;
                           
                       }
                       case(9):{
                           
                           numeroProcesos = linea;
                           break;
                           
                       }
                       case(10):{
                           tamanoColaMensajesString = linea;
                           break;
                       }
                       case(11): {
                           tamanoMailBoxString = linea;
                           break;
                           
                       }
                   }
                   
                   cantidadInstruccionConfiguracion++;
  
               }
               else if(create){
                   
                   
                   if(cantidadNComandos > 0 ){
                       
                       
                       if(Singleton.getInstance().getControlador().getConfiguracionSistema() == null){

                             // Datos de interfaz
                            //int cantidadProcesos = Integer.parseInt(cantidadProcesosString);

                            // Instancia de objetos
                            ManejoColas manejoColas = new ManejoColas(manejoColasString);                          
                            System.out.println("Largo Mensaje String: " + largoMensajeString);
                            
                            
                            Formato formato = new Formato(tipoContenido,tipoLargo,Integer.parseInt(largoMensajeString));
                            String addresing = addresingType + addresingSubType;
                            
                            //Direccionamiento
                            Direccionamiento direccionamiento = new Direccionamiento(addresing);

                            if(addresingType.equals("Directo")){
                                direccionamiento.setDirect(true);
                                System.out.println(addresingSubType);
                                if(addresingSubType.equals("Receive Explicito")){
                                    direccionamiento.setReceiveExplicit(true);
                                    direccionamiento.setReceiveImplicit(false);
                                }
                                else{
                                    direccionamiento.setReceiveExplicit(false);
                                    direccionamiento.setReceiveImplicit(true);
                                }  
                            }
                            else{
                                direccionamiento.setDirect(false);
                                if(addresingSubType.equals("Estatico")){
                                    direccionamiento.setIndirectStatic(true);
                                    direccionamiento.setIndirectDynamic(false);
                                }
                                else{           
                                    direccionamiento.setIndirectStatic(false);
                                    direccionamiento.setIndirectDynamic(true);

                                }

                            }

                            //---------------------------------------------------------------------
                            Sincronizacion sincronizacion = new Sincronizacion(syncSend,syncReceive);

                            // Instancia Confuguracion
                            ConfiguracionSistema configuracionS = new ConfiguracionSistema(Integer.parseInt(numeroProcesos), Integer.parseInt(tamanoColaMensajesString), sincronizacion, direccionamiento, formato, manejoColas);
                            System.out.println(configuracionS.toString());
                            // Configuracion
                            Singleton.getInstance().getControlador().setConfiguracionSistema(configuracionS);
                            int tamanoColaProcesos = Singleton.getInstance().getControlador().getConfiguracionSistema().getNumeroProcesos();

                            // Cola de Procesos
                            ColaProcesos colaProcesos = new ColaProcesos(tamanoColaProcesos);
                            Singleton.getInstance().getControlador().setColaProcesos(colaProcesos);

                            // Cola de Mensajes
                            ColaMensajes colaMensajes = new ColaMensajes(Integer.parseInt(tamanoColaMensajesString));
                            Singleton.getInstance().getControlador().setColaMensajes(colaMensajes);

                            System.out.println("Configuracion Lista");


                            // Casillero de Mensajes int largoMaximo, String manejoCola, String tipoLargo){
                            /*
                            int tamanoMailBox = 0;
                            String sts = this.jTextField_mailboxSize.getText();
                            if(!sts.equals("")){
                                tamanoMailBox = Integer.parseInt(sts);
                            }
                            
*/

                            CasilleroMensajes casilleroMensajes = new CasilleroMensajes(Integer.parseInt(tamanoMailBoxString),manejoColasString,tipoLargo);
                            Singleton.getInstance().getControlador().setCasilleroMensaje(casilleroMensajes);

                            // Creacion de Procesos
                            Singleton.getInstance().getControlador().crearProcesos();
                            Singleton.getInstance().getControlador().getColaProcesos().ImprimirColaProcesos();


                            //lista Solicitudes
                            ListaSolicitudes listaSolicitudes = new ListaSolicitudes();
                            Singleton.getInstance().getControlador().setListaSolicitudes(listaSolicitudes);

                            //Cola mensajes procesados
                            ColaMensajes colaMensajesProcesados = new ColaMensajes(-1);
                            Singleton.getInstance().getControlador().setColaMensajesProcesados(colaMensajesProcesados);
                            Singleton.getInstance().getControlador().setMensajes(colaMensajesProcesados);
                            
                            System.out.println("Config desde batch list");
                        
                   }
                   
 
                   String contenido = linea;
                   int largoMensaje = contenido.length();
                   int largoMaximo = Singleton.getInstance().getControlador().getConfiguracionSistema().getFormato().getTamano();
                   if(Singleton.getInstance().getControlador().getConfiguracionSistema().getFormato().getLargo().equals("Largo Fijo")){
                       
                       if(largoMensaje > largoMaximo){
                           String mensajeDialog = "El tamaño del mensaje es mayor que el permitido";
                           String tituloBarra = "Tamano no permitido";
                           this.mensajeDialog(mensajeDialog, tituloBarra);
                           return;
                       }
                   }
                   
                   
                   // Lectura segundo parametro de create 
                   linea = br.readLine();
                   int prioridadMensaje = Integer.parseInt(linea);
                   
                   boolean estado = Singleton.getInstance().getControlador().Create(contenido,prioridadMensaje);
        
                   if(estado){
                       String mensajeDialogoContenido = "Se ha creado el mensaje";
                       String tituloDialogoContenido = "Mensaje creado";
                       this.mensajeDialog(mensajeDialogoContenido ,tituloDialogoContenido);
                   }
                   else{
                       String mensajeDialogoContenido = "No se ha creado el mensaje";
                       String tituloDialogoContenido = "Mensaje no creado";
                       this.mensajeDialog(mensajeDialogoContenido ,tituloDialogoContenido);
                   }
                   
                   
                   cantidadNComandos--;
                       
                   }
                   else{
                       
                       System.out.println(" Se acabaron los comandos");
                       // Ya se ejecutaron la cantidad de comandos establecidos
                   }
                   
                   
               }
               else if(send){
                   
                   if(cantidadNComandos > 0){
                       
                       
                        String idProcesoFuenteString= linea;
                        
                        linea = br.readLine();                       
                        String idProcesoDestinoString = linea;
                        
                        String contenidoMensaje = br.readLine();
                        
                        int idProcesoDestino;
                        int idProcesoFuente;
                        
                        try{
                            idProcesoFuente = Integer.parseInt(idProcesoFuenteString);
                            idProcesoDestino = Integer.parseInt(idProcesoDestinoString);
                        }
                        catch(Exception e){
                            idProcesoDestino = 0;
                            idProcesoFuente =  0;
                        }
                        
                        
                        if(idProcesoDestino != idProcesoFuente){

                            boolean sendS = Singleton.getInstance().getControlador().Send(idProcesoFuente,idProcesoDestino, contenidoMensaje);

                            String contenidoMensajeDialog = "Se ha enviado el mensaje";
                            String tituloBarra = "Send";

                            if(sendS){
                                //String var = "El proceso: "+String.valueOf(idProcesoFuente)+" pudo enviar el mensaje: "+contenidoMensaje;
                                //Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProcesoFuente,var);
                                contenidoMensajeDialog = "Se ha enviado el mensaje";
                                this.mensajeDialog(contenidoMensajeDialog, tituloBarra);

                            }
                            else{
                                //String var = "El proceso: "+String.valueOf(idProcesoDestino)+" no pudo recibir el mensaje: "+contenidoMensaje;
                                //Singleton.getInstance().getControlador().getColaProcesos().agregarEventoProceso(idProcesoDestino,var);
                                contenidoMensajeDialog = "El mensaje no se pudo enviar";
                                this.mensajeDialog(contenidoMensajeDialog, tituloBarra);
                            }
                         
                        }else{
                            this.mensajeDialog("Se esta realizando un envio al mismo proceso", "Error:");
                        }
                        
                        
                        cantidadNComandos--;   


                   
                    
               }
               
           }
           else if(receive){

               if(cantidadNComandos > 0){


                        String idProcesoDestinoString = linea;
                        linea = br.readLine();
                        String idProcesoFuenteString = linea;
                        System.out.println(idProcesoFuenteString+" "+idProcesoDestinoString);
                        int idProcesoFuente;
                        int idProcesoDestino;

                        try{

                            idProcesoFuente = Integer.parseInt(idProcesoFuenteString);
                            idProcesoDestino = Integer.parseInt(idProcesoDestinoString);
                        }

                        catch(Exception e){
                            idProcesoFuente = 0;
                            idProcesoDestino = 0;
                        }


                        System.out.println("RECEIVE");

                        boolean receiveDirectExplicit = Singleton.getInstance().getControlador().isReceiveExplicit();
                        if(receiveDirectExplicit){

                            receive = Singleton.getInstance().getControlador().Receive(idProcesoDestino,idProcesoFuente);
                        }else{

                            receive = Singleton.getInstance().getControlador().Receive(idProcesoDestino,0);
                        }

                        String contenidoMensajeDialog = "";
                        String tituloBarra = "Receive";

                        if(receive){
                            contenidoMensajeDialog = "Receive procesado";
                            this.mensajeDialog(contenidoMensajeDialog, tituloBarra);

                        }
                        else{

                            contenidoMensajeDialog = "Receive no procesado";
                            this.mensajeDialog(contenidoMensajeDialog, tituloBarra);
                        }

               }
               else{
                   // se ejecutaron la cantidad de comandos establecidos
               }


           }
       }
           
       }
   }
       
       
        
        
       
       
       
   
   public void mensajeDialog(String mensaje, String tituloBarra){
        
        JOptionPane.showMessageDialog(null, mensaje, tituloBarra, JOptionPane.INFORMATION_MESSAGE);
    }
    
}
