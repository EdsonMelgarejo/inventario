package inventario;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Inventario {
    
    public static void main(String[] args) {
         try {
            Process pro = Runtime.getRuntime().exec("C:\\xampp/mysql_start.bat");
        } catch (IOException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventanaMenu vent = new ventanaMenu();
    }
}
