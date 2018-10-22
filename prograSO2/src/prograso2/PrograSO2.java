/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prograso2;

import Controlador.Singleton;
import Frames.MenuImpresion;


/**
 *
 * @author Rigo-PC
 */
public class PrograSO2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MenuImpresion menuImpresion = new MenuImpresion();
        Singleton.getInstance().getControlador().DefinirConfiguraciónDefault();
        menuImpresion.show();
    }
    
}
