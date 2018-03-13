/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Collection;
import java.util.List;
import modelo.Caja;
import modelo.User;

/**
 *
 * @author oscar
 */
public class UserDAO {

    public User getUser(String name) {
        return StaticDB.getInstance().users.get(name);
    }

    public Collection<User> getAllUser() {

        return StaticDB.getInstance().users.values();
    }

    public Collection<Caja> getAllCajasUser(User temp) {

        return StaticDB.getInstance().usersCajas.get(temp);
    }

    public boolean addCajaAUser(User u, Caja c) {
        boolean userOK = false;
        User user = StaticDB.getInstance().users.get(u.getName());
        if (u != null) {
            List<Caja> cajas = StaticDB.getInstance().usersCajas.get(u);
            cajas.add(c);
            userOK = true;
        }
        return userOK;
    }

    public boolean addUser(User temp) {
        boolean userOK = false;
        User u = StaticDB.getInstance().users.get(temp.getName());
        if (u == null) {
            StaticDB.getInstance().users.put(temp.getName(), temp);
            userOK = true;
        }
        return userOK;
    }

    public boolean updateUser(User temp) {
        boolean userOK = false;
        User u = StaticDB.getInstance().users.get(temp.getName());
        if (u != null) {
            StaticDB.getInstance().users.put(temp.getName(), temp);
            userOK = true;
        }
        return userOK;
    }

    public boolean delUser(User temp) throws DeleteForceException {
        boolean userOK = false;
        User u = StaticDB.getInstance().users.get(temp.getName());
        if (u != null) {
            if (StaticDB.getInstance().usersCajas.get(u) != null) {
                throw new DeleteForceException();
            } else {
                StaticDB.getInstance().users.remove(temp.getName());
                userOK = true;
            }
        }
        return userOK;
    }

    public boolean delUserForce(User temp) {
        boolean userOK = false;
        User u = StaticDB.getInstance().users.get(temp.getName());
        if (u != null) {

            List<Caja> cajas = StaticDB.getInstance().usersCajas.get(u);
            if (cajas != null) {
                for (Caja c : cajas) {
                    StaticDB.getInstance().cajasUsers.get(c).remove(u);
                }
            }

            StaticDB.getInstance().users.remove(temp.getName());
            userOK = true;

        }
        return userOK;
    }

}
