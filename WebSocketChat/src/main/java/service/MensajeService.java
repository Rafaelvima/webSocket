/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.MensajeDAO;
import java.util.Date;
import java.util.List;
import model.Mensaje;

/**
 *
 * @author rafa
 */
public class MensajeService {

    public int addMensaje(Mensaje mensaje) {
       MensajeDAO mensajedao = new MensajeDAO();
       return mensajedao.addMensaje(mensaje);
    }
    public List<Mensaje> getMensajesIntervalo(Date ini,Date fin){
          MensajeDAO mensajedao = new MensajeDAO();
       return mensajedao.getMensajesIntervalo(ini, fin);
    }
    
}
