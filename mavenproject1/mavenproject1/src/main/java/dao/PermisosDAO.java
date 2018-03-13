/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import modelo.Caja;
import modelo.User;

/**
 *
 * @author oscar
 */
public class PermisosDAO {
    
    
     public boolean checkUser(User temp) {
        boolean userOK = false;
        User u = StaticDB.getInstance().users.get(temp.getName());
        if (u != null) {
            if (temp.getPassword().equalsIgnoreCase(u.getPassword())) {
                userOK = true;
            }
        }
        return userOK;
    }
     
     
    public boolean checkPermisoCajaUser(User u,Caja c)
    {
        boolean userOK = false;
        List<Caja> cajas = StaticDB.getInstance().usersCajas.get(u);
        if (cajas != null && cajas.contains(c)) {
            userOK = true;
        }
        return userOK;
    }
    



}
