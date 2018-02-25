/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

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
import model.Registro;
import model.MensajeCifrado;
import model.MessageDecoder;
import model.MessageEncoder;
import util.AesUtil;
import service.RegistroService;
//import model.UserWS;

/**
 *
 * @author Rafa
 */
@ServerEndpoint( value = "/endpoint/{user}/{pass}",
        decoders = MessageDecoder.class,
  encoders = MessageEncoder.class,
  configurator = ServletAwareConfig.class)
public class PrimerEndpoint {
  
     @OnOpen
    public void onOpen(Session session,
            @PathParam("user")String user, @PathParam("pass")String pass) throws IOException {
        RegistroService rs = new RegistroService();
      session.getUserProperties().put("user", user);
      session.getUserProperties().put("pass", pass);

      if (!user.equals("google")) {
          if(rs.comprobarUser(user)){
            session.getUserProperties().put("login","OK");}
          else{
              Registro registro = new Registro();
              registro.setUser(user);
              registro.setPass(pass);
              rs.addUser(registro);
               session.getUserProperties().put("login", "MAL");
          }
            
        } else {
            session.getUserProperties().put("login",
              "NO");
        }
      //session.close();
    }
    
     @OnMessage
    public void echoText(MensajeCifrado mensaje, Session sessionQueManda) throws IOException {
        if (!sessionQueManda.getUserProperties().get("login").equals("OK")) {
            try {
                // comprobar login
                String idToken = mensaje.getContenido();
                GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
                String name = (String) payLoad.get("name");
                sessionQueManda.getUserProperties().put("user", name);
                System.out.println(payLoad.getJwtId());
                String email = payLoad.getEmail();
                sessionQueManda.getUserProperties().put("login", "OK");
            } catch (Exception ex) {
                try {
                    sessionQueManda.close();
                } catch (IOException ex1) {
                    Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            
            try {
//                ObjectMapper mapper = new ObjectMapper();
//                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                MensajeCifrado meta = mapper.readValue(mensaje,
//                  new TypeReference<MensajeCifrado>() {
//                  });
                AesUtil aes = new AesUtil(128, 1000);
                switch (mensaje.getTipo()) {
                    case "texto":
                        //descifrar contenido del mensaje.

                        mensaje.setContenido(aes.encrypt(mensaje.getSalt(), mensaje.getIv(), mensaje.getKey(), mensaje.getContenido()));

                        for (Session s : sessionQueManda.getOpenSessions()) {
                            try {
                                String user = (String) sessionQueManda.getUserProperties().get("user");
                                mensaje.setUser(user);
                                //if (!s.equals(sessionQueManda)) {
                                s.getBasicRemote().sendObject(mensaje);
                                //}
                            } catch (IOException ex) {
                                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case "canales":
                        //descifrar contenido del mensaje.
                        //aqui hay que hacer la select del array de todos los canales
                        ArrayList<String> canales = new ArrayList<>();
                        canales.add("canal2");
                        canales.add("to lo bueno");
                        ObjectMapper mapper = new ObjectMapper();
                        mensaje.setContenido(aes.encrypt(mensaje.getSalt(), mensaje.getIv(), mensaje.getKey(), mapper.writeValueAsString(canales)));
                        sessionQueManda.getBasicRemote().sendObject(mensaje);
                        break;
                }

            } catch (Exception ex) {
                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }

    
}
