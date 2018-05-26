
import com.rahkar.database.ConfigHandler;
import java.io.IOException;
import javax.xml.bind.JAXBException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alirzea
 */
public class NewClass {
    public static void main(String[] args) {
        try{
            ConfigHandler config = new ConfigHandler();
            System.out.println(config);
        }catch(IOException | JAXBException e){
            
        }
    }
}
