/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author oscar
 */
public class UserCaja {
    
    private String userName;
    private String cajaNombre;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCajaNombre() {
        return cajaNombre;
    }

    public void setCajaNombre(String cajaNombre) {
        this.cajaNombre = cajaNombre;
    }

    public UserCaja() {
    }

    public UserCaja(String userName, String cajaNombre) {
        this.userName = userName;
        this.cajaNombre = cajaNombre;
    }
    
    
    
}
