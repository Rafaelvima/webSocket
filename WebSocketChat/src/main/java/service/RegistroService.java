/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.RegistroDAO;
import java.util.List;
import model.Registro;

/**
 *
 * @author Rafa
 */
public class RegistroService {
    
    public List<Registro> getAllRegistros()
    {
        RegistroDAO dao = new RegistroDAO();
        
        return dao.getAllRegistrosJDBCTemplate();
    }
     public boolean comprobarUser()
    {
        RegistroDAO dao = new RegistroDAO();
        
        return dao.comprobarUser();
    }
}
