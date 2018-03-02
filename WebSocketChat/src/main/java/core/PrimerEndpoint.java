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
import java.util.Date;
import java.util.List;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import model.Canal;
import model.Mensaje;
import model.Registro;
import model.MensajeCifrado;
import model.MessageDecoder;
import model.MessageEncoder;
import service.CanalesService;
import service.Canales_usersService;
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
                CanalesService cs = new CanalesService();
                Canales_usersService cus = new Canales_usersService();
                MensajeService ms = new MensajeService();
                Mensaje nuevoMensaje = new Mensaje();
                Canal nuevoCanal = new Canal();
                List<Integer> canalesUser = new ArrayList<>();
                canalesUser = cus.getAllCanalesByUser((String) sessionQueManda.getUserProperties().get("user"));
                String tipo = mensaje.getTipo();
                switch (tipo) {
                    case "texto":
                        String origiUser = (String) sessionQueManda.getUserProperties().get("user");
                        if (mensaje.getGuardar()) {
                            
                            nuevoMensaje.setContenido(mensaje.getContenido());
                            nuevoMensaje.setFecha(mensaje.getFecha());
                            nuevoMensaje.setId_canal(mensaje.getDestino());
                            nuevoMensaje.setNombre_user(origiUser);
                            ms.addMensaje(nuevoMensaje);
                        }
                        for (Session s : sessionQueManda.getOpenSessions()) {
                            try {
                                String user = (String) sessionQueManda.getUserProperties().get("user");
                                mensaje.setUser(user);
                                if (canalesUser.contains(nuevoMensaje.getId_canal())) {
                                    if (!s.equals(sessionQueManda)) {
                                        mensaje.setContenido(nuevoMensaje.getContenido());
                                        mensaje.setDestino(nuevoMensaje.getId_canal());
                                        mensaje.setTipo(tipo);
                                        s.getBasicRemote().sendObject(mensaje);
                                    }
                                } else {
                                    mensaje.setTipo(tipo);
                                    mensaje.setContenido("EL USUSARIO " + origiUser + "NO TIENE ACCESO AL CANAL" + nuevoMensaje.getId_canal() + "SOLICITA ACCESO");
                                    mensaje.setDestino("general");
                                    s.getBasicRemote().sendObject(mensaje);
                                    mensaje.setUser(user);
                                    mensaje.setDestino(nuevoMensaje.getId_canal());
                                    mensaje.setTipo("pedirPCanal");
                                     mensaje.setContenido("EL USUSARIO " + origiUser + "NO TIENE ACCESO AL CANAL" +nuevoMensaje.getId_canal() + "LE DEJAS QUE PUEDA ENVIAR MENSAJES?");
                                    s.getBasicRemote().sendObject(mensaje);

                                }

                            } catch (IOException ex) {
                                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //el ususario no tiene permiso para escribir por ese canal
                        break;
                    case "canales":

                        List<Canal> canales = new ArrayList<>();
                        canales = cs.getAllCanales();
                        ObjectMapper mapper = new ObjectMapper();
                        mensaje.setContenido(mapper.writeValueAsString(canales));
                        sessionQueManda.getBasicRemote().sendObject(mensaje);
                        break;
                    case "addcanal":
                        String userorigi = (String) sessionQueManda.getUserProperties().get("user");
                        cs.addCanal(nuevoCanal);
                        for (Session s : sessionQueManda.getOpenSessions()) {
                            try {
                                String user = (String) sessionQueManda.getUserProperties().get("user");
                                mensaje.setUser(user);
                                mensaje.setDestino("general");
                                mensaje.setContenido("NUEVO CANAL AÑADIDO POR" + userorigi);

                                if (!s.equals(sessionQueManda)) {
                                    s.getBasicRemote().sendObject(mensaje);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    
                    case "darPCanal":
                        cus.getPermisoUser(Integer.parseInt(mensaje.getDestino()), mensaje.getUser());
                        mensaje.setTipo("darPCanal");
                        mensaje.setContenido("USUARIO"+mensaje.getUser()+" AÑADIDO CORRECTAMENTE al canal"+mensaje.getDestino());
                        sessionQueManda.getBasicRemote().sendObject(mensaje);
                        
                        break;
                    case "cargarMensajes":
                        List<Mensaje> AllMensajesfechas = new ArrayList<>();
                        AllMensajesfechas = ms.getMensajesIntervalo(mensaje.getFechaInicial(), mensaje.getFechaFinal());
                            try {
                         ObjectMapper mappera = new ObjectMapper();
                         mensaje.setTipo("darMensajes");
                        mensaje.setContenido(mappera.writeValueAsString(AllMensajesfechas));
                        sessionQueManda.getBasicRemote().sendObject(mensaje);
                                

                            } catch (IOException ex) {
                                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        break;
                }

            } catch (Exception ex) {
                Logger.getLogger(PrimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
