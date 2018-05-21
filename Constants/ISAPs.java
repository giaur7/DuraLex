/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constants;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author dizel
 */
public enum ISAPs {

    TEKST_AKTU,OGŁOSZONY,UJEDNOLICONY;
    private ISAPs(){};
    static StringBuffer sb;
        public static void main (String...ar){
        try {
            switchPage(new URL("http://isap.sejm.gov.pl/isap.nsf/ByYear.xsp?type=WDU&year=2012"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(ISAPs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ISAPs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//http://isap.sejm.gov.pl/isap.nsf/home.xsp
    public final static String 
        ROOT="http://prawo.sejm.gov.pl/isap.nsf/"
        ,AS_DOWNLOAD="download.xsp/WDU"//(y)ear (v)olume (p)osition
        ,AS_HOME="home.xsp"
        ,MIDDLE_CHRONO0_YEARS="ByYear.xsp"
        ,MIDDLE_CHRONO1_YEAR= "ByYear.xsp?type=WDU&year="//+ year number
        ,MIDDLE_CHRONO2_VOL=  "&vol="//+ volume number
        ,MIDDLE_CHRONO_POS=  "/DocDetails.xsp?id=WDU"//+yearvolposition number
        ,MIDDLE_TYPY[][]={{"/T/D","/O/D","/U/D"},{"L.pdf",".pdf","Lj.pdf"}}
        ,TRACE_YEARS="year=(?<capt>\\d{4})"
        ,TRACE_IN_YEAR="vol=(?<capt>\\d{1,3})"
        ,TRACE_IN_VOL="poz\\. (?<capt>\\d{1,4})"
        ,TRACE_IN_POS="/isap.nsf/download.xsp/WDU\\d{11}/[TOU]/D\\d{8}(Lj?)?.pdf"
        ,SEARCH_REGION="<body.+<footer>";
    
    
    public static synchronized String getAdressAsString(String rok,String numer,String pozycja,ISAPs isap){
        sb=new StringBuffer();
        
        return sb.append(ROOT)
                .append(AS_DOWNLOAD)
                .append(rok).append(numer).append(pozycja)
                .append(MIDDLE_TYPY[0][isap.ordinal()])
                .append(rok).append(pozycja)
                .append(MIDDLE_TYPY[1][isap.ordinal()])
               .toString();
    }
    public static synchronized String getAdressAsString(String rok, String numer, String pozycja) {
    sb=new StringBuffer();
        
        return sb.append(ROOT)
                .append(MIDDLE_CHRONO_POS)
                .append(rok).append(numer).append(pozycja)
               .toString();
                
                
    }
    public static synchronized URL getAdressAsURL(int rok,int numer, int pozycja,ISAPs isap) throws MalformedURLException{
        sb=new StringBuffer();
        return new URL(
        sb.append(ROOT)
                .append(AS_DOWNLOAD)
                .append(rok).append(numer).append(pozycja)
                .append(MIDDLE_TYPY[0][isap.ordinal()])
                .append(rok).append(pozycja)
                .append(MIDDLE_TYPY[1][isap.ordinal()]).toString()
        );
    }
    public static void switchPage(URL url) throws IOException{
        //url.openConnection().connect();
        url.openConnection().getRequestProperties().forEach((k,v)->{
            System.out.print("\n"+(k!=null?k.toUpperCase():"null")+":\n");
            v.forEach((l)->{System.out.println(l);});
        });
        System.out.print("____________");
        url.openConnection().getHeaderFields().forEach((k,v)->{
            System.out.print("\n"+(k!=null?k.toUpperCase():"null")+":\n");
            v.forEach((l)->{System.out.println(l);});
        });
    }
}