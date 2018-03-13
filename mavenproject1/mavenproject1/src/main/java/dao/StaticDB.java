/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import modelo.Caja;
import modelo.Cosa;
import modelo.User;

/**
 *
 * @author oscar
 */
public class StaticDB {
    
    public Map<String,User> users;
    public Map<String,Caja> cajas;
    public Map<User,List<Caja>> usersCajas;
    public Map<Caja,List<User>> cajasUsers;
    
    private static StaticDB instance;
    
    private StaticDB(){
        users = new LinkedHashMap<>();
        cajas = new LinkedHashMap<>();
        usersCajas = new LinkedHashMap<>();
        cajasUsers = new LinkedHashMap<>();
        
        users.put("juan", new User("juan","juan"));
        users.put("eduardo", new User("eduardo","eduardo"));
        users.put("guti", new User("guti","guti"));
        users.put("miguel", new User("miguel","miguel"));
        users.put("erasto", new User("erasto","erasto"));
        users.put("carlos", new User("carlos","carlos"));
        
        
        Caja c = new Caja("aseo");
        c.addCosa(new Cosa("peine",1));
        c.addCosa(new Cosa("jabon",5));
        c.addCosa(new Cosa("esponja",3));
        cajas.put(c.getNombre(), c);
        
        c = new Caja("comida");
        c.addCosa(new Cosa("gelatina",2));
        c.addCosa(new Cosa("flan",5));
        c.addCosa(new Cosa("helado",3));
        cajas.put(c.getNombre(), c);
        
        c = new Caja("juegos");
        c.addCosa(new Cosa("bang",2));
        c.addCosa(new Cosa("catan",5));
        c.addCosa(new Cosa("cartas",3));
        cajas.put(c.getNombre(), c);

        List<Caja> cajasUser1 = new LinkedList<>();
        cajasUser1.add(cajas.get("aseo"));
        cajasUser1.add(cajas.get("comida"));
        usersCajas.put(users.get("juan"),cajasUser1);
        cajasUser1 = new LinkedList<>();
        cajasUser1.add(cajas.get("aseo"));
        usersCajas.put(users.get("miguel"),cajasUser1);
        cajasUser1 = new LinkedList<>();
        cajasUser1.add(cajas.get("aseo"));
        cajasUser1.add(cajas.get("comida"));
        cajasUser1.add(cajas.get("juegos"));
        usersCajas.put(users.get("erasto"),cajasUser1);
        
        List<User> userCajas1 = new LinkedList<>();
        userCajas1.add(users.get("juan"));
        userCajas1.add(users.get("miguel"));
        userCajas1.add(users.get("erasto"));
        cajasUsers.put(cajas.get("aseo"),userCajas1);
        
        userCajas1 = new LinkedList<>();
        userCajas1.add(users.get("miguel"));
        userCajas1.add(users.get("erasto"));
        cajasUsers.put(cajas.get("comida"),userCajas1);
        
        userCajas1 = new LinkedList<>();
        userCajas1.add(users.get("erasto"));
        cajasUsers.put(cajas.get("cartas"),userCajas1);


    }
    
    public static StaticDB getInstance()
    {
        if (instance==null)
        {
            instance = new StaticDB();
        }
        
        return instance;
    }
    

    
}
