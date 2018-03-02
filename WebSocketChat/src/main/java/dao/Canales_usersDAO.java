/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Canales_users;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Rafa
 */
public class Canales_usersDAO {

    public List<Integer> addCanales_users(String user) {
         JdbcTemplate jtm = new JdbcTemplate(
          DBConnection.getInstance().getDataSource());
        List<Integer> registros = jtm.query("Select id_canal from canales_users where user=?",
          new BeanPropertyRowMapper(Integer.class),user);
        
        
        return registros;
    }
    public List<Canales_users> getAllCanales_users(){
         JdbcTemplate jtm = new JdbcTemplate(
          DBConnection.getInstance().getDataSource());
        List<Canales_users> registros = jtm.query("Select * from canales_users",
          new BeanPropertyRowMapper(Canales_users.class));

        return registros;
    }
    public int getPermisoUser(int c, String u) {
        int filas = 0;
        
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.update("insert into canales_users(id_canal,user) values(?,?)", c,u);
            
        } catch (Exception ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
           filas= 0;
        }
        return filas;
    }
    
}
