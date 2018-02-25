/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import model.Registro;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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
}
