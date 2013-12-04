/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package concurrentfinalproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author urielhernandez
 */
public class ConcurrentFinalProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        File f = new File(args[0]);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String last_modified = sdf.format(f.lastModified());
        String size_of_file = Double.toString(f.length());
        String file_name = f.getName();
        
        Path path = Paths.get(args[0]);
        UserPrincipal owner = Files.getOwner(path);
        String user_owner = owner.getName();
        
        Conexion c = new Conexion("localhost", "root", "", "concurrente");
        
        if(c.insertar("INSERT INTO archivos(size,name,owner,updated_at) VALUES('"+size_of_file+"','"+file_name+"','"+user_owner+"','"+last_modified+"')")){
            MainWindow window = new MainWindow();
            ResultSet rs = c.buscar("SELECT * FROM archivos ORDER BY id DESC LIMIT 1");
                                
            window.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la sentencia SQL");
        }
    }
    
}
