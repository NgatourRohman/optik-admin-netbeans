/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.utils;

/**
 *
 * @author ngato
 */
import java.io.*;
import tokooptikadmin.models.AdminModel;

public class SessionStorage {

    private static final String FILE_NAME = "session.cache";

    public static void simpanSession(AdminModel admin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AdminModel loadSession() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (AdminModel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static void hapusSession() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }
}
