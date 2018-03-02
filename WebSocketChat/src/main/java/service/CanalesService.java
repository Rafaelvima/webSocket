/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.CanalDAO;
import java.util.ArrayList;
import java.util.List;
import model.Canal;

/**
 *
 * @author Rafa
 */
public class CanalesService {
    public int addCanal(Canal canal) {
       CanalDAO canaldao = new CanalDAO();
       return canaldao.addCanal(canal);
    }

    public List<Canal> getAllCanales() {
        CanalDAO canaldao = new CanalDAO();
        return canaldao.getAllCanales();
    }
}
