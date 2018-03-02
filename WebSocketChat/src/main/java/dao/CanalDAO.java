/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Canal;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Rafa
 */
public class CanalDAO {

    public int addCanal(Canal c) {
         int filas = 0;
        
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.update("insert into canales(nombre,user_admin) values(?,?)", c.getNombre(), c.getUser_admin());
            
        } catch (Exception ex) {
            Logger.getLogger(CanalDAO.class.getName()).log(Level.SEVERE, null, ex);
           filas= 0;
        }
        return filas;
    }

    public List<Canal> getAllCanales() {
        JdbcTemplate jtm = new JdbcTemplate(
          DBConnection.getInstance().getDataSource());
        List<Canal> registros = jtm.query("Select * from canal",
          new BeanPropertyRowMapper(Canal.class));

        return registros;
    }
    
}
