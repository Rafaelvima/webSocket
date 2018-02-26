/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DBConnection;
import java.util.List;
import model.Registro;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafa
 */
public class RegistroDAO {
     public List<Registro> getAllRegistrosJDBCTemplate() {
     
        JdbcTemplate jtm = new JdbcTemplate(
          DBConnection.getInstance().getDataSource());
        List<Registro> registros = jtm.query("Select * from REGISTROS",
          new BeanPropertyRowMapper(Registro.class));
        
        
        return registros;
    }

    public String comprobarRegistro(String user) {
        
        
        
          String existe = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
//            String nombres = (String)jtm.queryForObject("SELECT nombre FROM registro where nombre=",new Object[] { user }, String.class);
            String pass = (String)jtm.queryForObject("SELECT pass FROM registro where nombre=", String.class,user);
            if (pass != null) {
                existe = pass;
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
            existe=null;
        }
        return existe;
        
       
    }

    public Registro addUser(Registro r) {
        int filas = 0;
        
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.update("insert into registro(nombre,pass) values(?,?)", r.getUser(), r.getPass());
            if (filas == 0) {
                r = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
            r = null;
        }
        return r;
    }
}
