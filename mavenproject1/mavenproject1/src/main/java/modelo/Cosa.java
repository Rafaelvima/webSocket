/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author oscar
 */
public class Cosa {
    
    private String nombre;
    private int cantidad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Cosa() {
        this.cantidad = 0;
    }

    public Cosa(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
    
    public void addCantidad(int cantidad)
    {
        this.cantidad += cantidad;
    }
    
    
    
}
