/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Collection;
import java.util.List;
import modelo.Caja;
import modelo.Cosa;
import modelo.User;

/**
 *
 * @author oscar
 */
public class CajaDAO {
    
    
    public Collection<Caja> getAllCajas()
    {
        return StaticDB.getInstance().cajas.values();
    }
  
    public Caja getCaja(String name)
    {
        return StaticDB.getInstance().cajas.get(name);
    }
   
    
    public Collection<User> getAllUsersCaja(Caja temp)
    {
        return StaticDB.getInstance().cajasUsers.get(temp);
    }
    
    public boolean addCaja(Caja temp) {
        boolean userOK = false;
        Caja c = StaticDB.getInstance().cajas.get(temp.getNombre());
        if (c == null) {
            StaticDB.getInstance().cajas.put(temp.getNombre(), temp);
            userOK = true;
        }
        return userOK;
    }

    public boolean updateCaja(Caja temp) {
        boolean userOK = false;
        Caja caja = StaticDB.getInstance().cajas.get(temp.getNombre());
        if (caja != null) {
            caja.setNombre(temp.getNombre());
            
            userOK = true;
        }
        return userOK;
    }

    public boolean delCaja(Caja temp) throws DeleteForceException {
        boolean userOK = false;
        Caja u = StaticDB.getInstance().cajas.get(temp.getNombre());
        if (u != null) {
            if (StaticDB.getInstance().cajasUsers.get(u) != null) {
                throw new DeleteForceException();
            } else {
                StaticDB.getInstance().cajas.remove(temp.getNombre());
                userOK = true;
            }
        }
        return userOK;
    }

    public boolean delCajaForce(Caja temp)  {
        boolean userOK = false;
        Caja c = StaticDB.getInstance().cajas.get(temp.getNombre());
        if (c != null) {
            
            List<User> users = StaticDB.getInstance().cajasUsers.get(c);
            if (users != null) {
                for (User u : users)
                {
                   StaticDB.getInstance().usersCajas.get(u).remove(c);
                }
            } 
            
            StaticDB.getInstance().cajas.remove(temp.getNombre());
            userOK = true;
            
        }
        return userOK;
    }
    
    public boolean addCosaACaja(Cosa cosa, Caja caja)
    {
        boolean userOK = false;
        Caja u = StaticDB.getInstance().cajas.get(caja.getNombre());
        if (u != null) {
            u.addCosa(cosa);
            userOK = true;
        }
        return userOK;
    }

    public boolean addCantidadCosaACaja(Cosa cosa, Caja caja)
    {
        boolean userOK = false;
        Caja u = StaticDB.getInstance().cajas.get(caja.getNombre());
        if (u != null) {
            u.addCantidadCosa(cosa);
            userOK = true;
        }
        return userOK;
    }

}
