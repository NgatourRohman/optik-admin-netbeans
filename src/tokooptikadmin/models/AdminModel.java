/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.models;

/**
 *
 * @author ngato
 */
import java.io.Serializable;

public class AdminModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String username;

    public AdminModel(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
