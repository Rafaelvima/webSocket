/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.Canales_usersDAO;
import java.util.ArrayList;
import java.util.List;
import model.Canales_users;

/**
 *
 * @author Rafa
 */
public class Canales_usersService {


    public List<Integer> getAllCanalesByUser(String user) {
Canales_usersDAO canales_usersdao = new Canales_usersDAO();
       return canales_usersdao.addCanales_users(user);
    }
   public List<Canales_users> getAllCanales_users() {
Canales_usersDAO canales_usersdao = new Canales_usersDAO();
       return canales_usersdao.getAllCanales_users();
    }
   public int getPermisoUser(int canal, String user){
       Canales_usersDAO canales_usersdao = new Canales_usersDAO();
       return canales_usersdao.getPermisoUser(canal,user);
   }
    
}
