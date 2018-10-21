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
public class Direccionamiento {
    
    private String tipoDireccionamiento;
    
    
    private boolean direct ; 
    private boolean receiveExplicit;
    private boolean receiveImplicit;
    private boolean indirectStatic;
    private boolean indirectDynamic;

    public Direccionamiento(String tipoDireccionamiento, boolean direct, boolean receiveExplicit, boolean receiveImplicit, boolean indirectStatic, boolean indirectDynamic) {
        this.tipoDireccionamiento = tipoDireccionamiento;
        this.direct = direct;
        this.receiveExplicit = receiveExplicit;
        this.receiveImplicit = receiveImplicit;
        this.indirectStatic = indirectStatic;
        this.indirectDynamic = indirectDynamic;
    }
    
    
    public Direccionamiento(String tipoDireccionamiento) {
        this.tipoDireccionamiento = tipoDireccionamiento;

    }

    public String getTipoDireccionamiento() {
        return tipoDireccionamiento;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public boolean isReceiveExplicit() {
        return receiveExplicit;
    }

    public void setReceiveExplicit(boolean receiveExplicit) {
        this.receiveExplicit = receiveExplicit;
    }

    public boolean isReceiveImplicit() {
        return receiveImplicit;
    }

    public void setReceiveImplicit(boolean receiveImplicit) {
        this.receiveImplicit = receiveImplicit;
    }

    public boolean isIndirectStatic() {
        return indirectStatic;
    }

    public void setIndirectStatic(boolean indirectStatic) {
        this.indirectStatic = indirectStatic;
    }

    public boolean isIndirectDynamic() {
        return indirectDynamic;
    }

    public void setIndirectDynamic(boolean indirectDynamic) {
        this.indirectDynamic = indirectDynamic;
    }

    public void setTipoDireccionamiento(String tipoDireccionamiento) {
        this.tipoDireccionamiento = tipoDireccionamiento;
    }

    @Override
    public String toString() {
        return "Direccionamiento{" + "tipoDireccionamiento=" + tipoDireccionamiento + ", direct=" + direct + ", receiveExplicit=" + receiveExplicit + ", receiveImplicit=" + receiveImplicit + ", indirectStatic=" + indirectStatic + ", indirectDynamic=" + indirectDynamic + '}';
    }

    
    
    
}
