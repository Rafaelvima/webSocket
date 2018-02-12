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
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Rafa
 */
@ServerEndpoint("/endpoint/{user}")
public class PrimerEndpoint {
   
     @OnOpen
    public void onOpen(Session session,
            @PathParam("user")String user) throws IOException {

      session.getUserProperties().put("user", user);
      //session.close();
    }
    
    
    @OnMessage
    public void echoText(String mensaje, Session sessionQueManda) throws IOException {
        for (Session s : sessionQueManda.getOpenSessions()) {
            String user= (String)sessionQueManda.getUserProperties().get("user");
            if (!s.equals(sessionQueManda)) {
                s.getBasicRemote().sendText(user+mensaje);
            }            
        }
    }
    
}
