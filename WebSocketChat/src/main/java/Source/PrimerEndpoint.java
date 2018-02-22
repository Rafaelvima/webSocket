/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import model.MensajeCifrado;
//import model.MessageDecoder;
//import model.MessageEncoder;
//import model.UserWS;
//import util.AesUtil;

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
      if (!user.equals("google")) {
            session.getUserProperties().put("login",
              "OK");
            
        } else {
            session.getUserProperties().put("login",
              "NO");
        }
      //session.close();
    }
    
    
//    @OnMessage
//    public void echoText(String mensaje, Session sessionQueManda) throws IOException {
//        for (Session s : sessionQueManda.getOpenSessions()) {
//            String user= (String)sessionQueManda.getUserProperties().get("user");
//            if (!s.equals(sessionQueManda)) {
//                s.getBasicRemote().sendText(user+" dijo: "+mensaje);
//            }            
//        }
//    }
     @OnMessage
    public void echoText(String mensaje, Session sessionQueManda) throws IOException {
        if (!sessionQueManda.getUserProperties().get("login").equals("OK")) {
//            try {
//                // comprobar login
//                String idToken = mensaje.getContenido();
//                GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
//                String name = (String) payLoad.get("name");
//                sessionQueManda.getUserProperties().put("user", name);
//                System.out.println(payLoad.getJwtId());
//                String email = payLoad.getEmail();
//                sessionQueManda.getUserProperties().put("login", "OK");
//            } catch (Exception ex) {
//                try {
//                    sessionQueManda.close();
//                } catch (IOException ex1) {
//                    Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex1);
//                }
//                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
//            }

        } else {
            for (Session s : sessionQueManda.getOpenSessions()) {
                String user= (String)sessionQueManda.getUserProperties().get("user");
                    if (!s.equals(sessionQueManda)) {
                    s.getBasicRemote().sendText(user+" dijo: "+mensaje);
            }            
        }
//            try {
////                ObjectMapper mapper = new ObjectMapper();
////                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////                MensajeCifrado meta = mapper.readValue(mensaje,
////                  new TypeReference<MensajeCifrado>() {
////                  });
//               // AesUtil aes = new AesUtil(128, 1000);
//                switch (mensaje.getTipo()) {
//                    case "texto":
//                        //descifrar contenido del mensaje.
//
//                        mensaje.setContenido(aes.encrypt(mensaje.getSalt(), mensaje.getIv(), mensaje.getKey(), "mensaje del servidor"));
//
//                        for (Session s : sessionQueManda.getOpenSessions()) {
//                            try {
//                                String user = (String) sessionQueManda.getUserProperties().get("user");
//                                mensaje.setUser(user);
//                                //if (!s.equals(sessionQueManda)) {
//                                s.getBasicRemote().sendObject(mensaje);
//                                //}
//                            } catch (IOException ex) {
//                                Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                        break;
//                    case "canales":
//                        //descifrar contenido del mensaje.
//                        ArrayList<String> canales = new ArrayList<>();
//                        canales.add("canal2");
//                        canales.add("to lo bueno");
//                        ObjectMapper mapper = new ObjectMapper();
//                        mensaje.setContenido(aes.encrypt(mensaje.getSalt(), mensaje.getIv(), mensaje.getKey(), mapper.writeValueAsString(canales)));
//                        sessionQueManda.getBasicRemote().sendObject(mensaje);
//                        break;
//                }
//
//            } catch (Exception ex) {
//                Logger.getLogger(ChatEndPoint.class.getName()).log(Level.SEVERE, null, ex);
//            }
    }
    }

    
}
