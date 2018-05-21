/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpidersWeb;

import duralex.DuraLex;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.html.parser.Entity;
import static duralex.DuraLex.outL;



/**
 *
 * @author dizel
 */
public class runner {
    
    static TrzeciaPróba tp=new TrzeciaPróba();
    static SpidersWeb sw=new SpidersWeb();
    static {
        tp.focus="http://prawo.sejm.gov.pl/isap.nsf/DocDetails.xsp?id=WDU19970980602";
        
    }
    
    public static void main(String...args) {
        SW();}
    static void TP1(){
        new Thread(tp).start();
    }
    static void TP2(){
        try {System.out.print("\n\n\nTRZECIA PRÓBA\n\n\n");
            //tp.focus="http://prawo.sejm.gov.pl/isap.nsf/download.xsp/WDU20100520306/O/D20100306.pdf";
            String s= DuraLex.getText(tp.focus);
            StringBuilder sb= new StringBuilder();
            
            Matcher m=Pattern.compile(".").matcher(s);
            if(m.find())
            for(int f=m.start();f<s.length()&&m.find();f=m.start()){
                sb.append(m.group()).append("");
            }
            outL(sb);
        }catch (NullPointerException npe){
            System.out.print(npe.getCause().toString());
        }
    }
    static void SW(){
         
        try {
            System.out.println(
                    sw.buildDataBase(new URL(tp.focus)
                                ,"WDU\\d{11}.*\\.pdf"
                                ,null
                                ,"(uznany.+uchylony)|(ob.+cy)"
                                ,null)
                    +"\b"
            );
        } catch (IOException ex) {
            Logger.getLogger(SpidersWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}