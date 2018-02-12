/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Rafa
 */
@ServerEndpoint("/endpoint")
public class PrimerEndpoint {
    
    @OnMessage
    public void echoText(String mensaje, Session sessionQueManda) throws IOException {
        for (Session s : sessionQueManda.getOpenSessions()) {
            if (!s.equals(sessionQueManda)) {
                s.getBasicRemote().sendText(mensaje);
            }            
        }
    }
    
}
