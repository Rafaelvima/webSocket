/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import model.Mensaje;
import model.Registro;
import model.MensajeCifrado;
import model.MessageDecoder;
import model.MessageEncoder;
import service.MensajeService;
import util.PasswordHash;
import service.RegistroService;
//import model.UserWS;

/**
 *
 * @author Rafa
 */
@ServerEndpoint(value = "/endpoint/{user}/{pass}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class,
        configurator = ServletAwareConfig.class)
public class PrimerEndpoint {

    @OnOpen
    public void onOpen(Session session,
            @PathParam("user") String user, @PathParam("pass") String pass) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        RegistroService rs = new RegistroService();
        session.getUserProperties().put("user", user);
        session.getUserProperties().put("pass", pass);
        if (!user.equals("google")) {
            if (rs.comprobarUser(user) != null && PasswordHash.getInstance().validatePassword(pass, rs.comprobarUser(user))) {
                session.getUserProperties().put("login", "OK");
            } else {
                Registro registro = new Registro();
                registro.setUser(user);
                registro.setPass(PasswordHash.getInstance().createHash(pass));
                rs.addUser(registro);
                session.getUserProperties().put("login", "OK");

            }

        } else {

            session.getUserProperties().put("login", "NO");

        }
        //session.close();
    }

    @OnMessage
    public void echoText(MensajeCifrado mensaje, Session sessionQueManda) throws IOException {
        RegistroService rs = new RegistroService();
        if (!sessionQueManda.getUserProperties().get("login").equals("OK")) {
            try {
                // comprobar login
                String idToken = mensaje.getContenido();
                GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
                String name = (String) payLoad.get("name");
                sessionQueManda.getUserProperties().put("user", name);
                System.out.println(payLoad.getJwtId());
                String email = payLoad.getEmail();
                
                if (rs.comprobarUser(email) != null && rs.comprobarUser(email).equals("google")) {
                    sessionQueManda.getUserProperties().put("login", "OK");
                } else {
                    Registro registro = new Registro();
                    registro.setUser(email);
                    registro.setPass("google");
                    rs.addUser(registro);
                    sessionQueManda.getUserProperties().put("login", "OK");
                }
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

                switch (mensaje.getTipo()) {
                    case "texto":
                        if(mensaje.getGuardar()){
                            MensajeService ms = new MensajeService();
                            Mensaje nuevoMensaje = new Mensaje();
                            nuevoMensaje.setContenido(mensaje.getContenido());
                            nuevoMensaje.setFecha(mensaje.getFecha());
                            nuevoMensaje.setId_canal(mensaje.getDestino());
                            nuevoMensaje.setNombre_user((String) sessionQueManda.getUserProperties().get("user"));
                            ms.addMensaje(nuevoMensaje);
                                }
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
                        
                        ArrayList<String> canales = new ArrayList<>();
                        canales.add("canal2");
                        canales.add("to lo bueno");
                        ObjectMapper mapper = new ObjectMapper();
                        mensaje.setContenido(mapper.writeValueAsString(canales));
                        sessionQueManda.getBasicRemote().sendObject(mensaje);
                        break;
                    case "addcanal":
                        CanalService cs = new CanalService();
                        cs.addCanal(nombreCanal);
                            
                }

            } catch (Exception ex) {
                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
