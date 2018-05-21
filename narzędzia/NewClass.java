/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package narzędzia;

import java.awt.Component;
import java.awt.TextComponent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Joanna di Raków
 */
public class NewClass {
    public static void main(String...args) throws MalformedURLException, IOException{
        URL url=new URL("http://isap.sejm.gov.pl/isap.nsf/ByYear.xsp?type=WDU&year=2012");
        URLConnection con= url.openConnection();
        
        System.out.println("Allow:"+con.getAllowUserInteraction());
        
        con.getRequestProperties().forEach((k,v)->{
            System.out.println("_BEGIN_____\nKEY:"+k);
            v.forEach((l)->{System.out.print(l);});
            System.out.println("\n_END______");
            
        });
        
    }

    
    
}
