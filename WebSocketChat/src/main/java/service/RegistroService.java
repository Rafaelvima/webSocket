/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.Login;

/**
 *
 * @author Rafa
 */
public class RegistroService {
    
    public List<Alumno> getAllAlumnos()
    {
        AlumnosDAO dao = new AlumnosDAO();
        
        return dao.getAllAlumnosJDBCTemplate();
    }
}
