/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author oscar
 */
public class Caja {
    
    private String nombre;
    
    private List<Cosa> cosas;

    public Caja(String nombre) {
        this.nombre = nombre;
        this.cosas = new LinkedList<>();
    }

    public Caja() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cosa> getCosas() {
        return cosas;
    }

    public void setCosas(List<Cosa> cosas) {
        this.cosas = cosas;
    }
    
    public void addCosa(Cosa c){
        this.cosas.add(c);
    }
    
    public void addCantidadCosa(Cosa temp)
    {
        for (Cosa c : cosas)
        {
            if (c.getNombre().equalsIgnoreCase(temp.getNombre()))
            {
                c.addCantidad(temp.getCantidad());
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Caja other = (Caja) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
    
}
