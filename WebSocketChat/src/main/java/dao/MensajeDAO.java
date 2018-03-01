/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mensaje;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author rafa
 */
public class MensajeDAO {

    public int addMensaje(Mensaje m) {
       int filas = 0;
        
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.update("insert into mensajes(mensaje,fecha,id_canal,nombre_user) values(?,?,?,?)", m.getContenido(), m.getFecha(),m.getId_canal(),m.getNombre_user());
            
        } catch (Exception ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
           filas= 0;
        }
        return filas;
    }
    
    
}
