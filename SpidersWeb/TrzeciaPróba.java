/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpidersWeb;

import Constants.ISAPs;
import duralex.DuraLex;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static duralex.DuraLex.outL;

/**
 *
 * @author dizel
 */
public class TrzeciaPróba implements Runnable  {
    public Stack<String> ustawa=new Stack<>();
    public Stack<String> treść=new Stack<>();
    public Stack<String> powiązane=new Stack<>();
    public Stack<String> stanPrawny=new Stack<>();
    public Stack<String> rodzajDokumentu=new Stack<>();
    public StringBuilder temporary=new StringBuilder();
    public ArrayList<Integer> kolejność=new ArrayList<>();
    String focus="";
    static private int bierzącyRok, bierzącyNumer,bierzącaPozycja;
    private boolean nowaBaza=true;
    ISAPs bierzącyTyp; 
    
    public static void main(String...args) {
    }
    public void budujBazę(String regexPodejrzany,String regexPoszlaka) throws IOException, NullPointerException{
        //BufferedInputStream bis=new BufferedInputStream(new URL(focus).openStream());
        String strony[] = DuraLex.getTexts(focus);
        System.out.print(Arrays.toString(strony));
        Stack<String> artykuł=new Stack<>(),wywołanie=new Stack<>();
        Pattern miarka=Pattern.compile("(?<Podejrzany>"+regexPodejrzany+")|(?<Poszlaka>"+regexPoszlaka+")");
        Matcher m;
        String temp=null;
        boolean pod=false;
        int t=0;//strony.length/3;
        
        if(strony==null){System.out.print("strony=null");}
        else{
            for(String s:strony){//szukam głodnych (od razu czy najpierw kompletuj
            m=miarka.matcher(s);
            
                while(m.find()&&!m.hitEnd()){
                    if(m.group("Podejrzany")!=null)temp=m.group("Podejrzany");
                    else if (m.group("Poszlaka")!=null) {System.out.print("\n"+temp+"<<<__-"+m.group("Poszlaka"));}
                    //System.out.print(t++ +", ");//:____>>>"+m.group("Podejrzany")+"  "+m.group("Poszlaka")+"_______");
                }
            }   
        }
    }
    public void wprowadźOstatniZbadanyURLiPrzesuń(String webUrl,boolean tył) throws MalformedURLException{
        String typ;
        if(webUrl.contains(".pdf")){
            
        };
    }
    public void wprowadźBazę(TrzeciaPróba tp){
        ustawa=tp.ustawa;
        treść=tp.treść;
        powiązane=tp.powiązane;
        stanPrawny=tp.stanPrawny;
        rodzajDokumentu=tp.rodzajDokumentu;
        nowaBaza=false;
    }
    public void metaDaneISAP(){
        
    
}
    void wprowadźArgumenty(int rok,int numer,int pozycja,ISAPs typ){
            bierzącyRok=rok;
            bierzącyNumer=numer;
            bierzącaPozycja=pozycja;
            bierzącyTyp=typ;
    }
    /**
     * Czyta plik z ustawieniami.
     * Trść pliku w formie znaczników <znacznik: treść opcji/>.
     * @param f ścieżka pliku konfifguracji
     * @throws FileNotFoundException 
     */
    public void wprowadźPlikKonfiguracji(File f) throws FileNotFoundException{
        StringBuilder sb=new StringBuilder();
        BufferedReader fr=new BufferedReader(new FileReader(f));
        
    }
    @Override
    public void run(){
        try {
            wprowadźOstatniZbadanyURLiPrzesuń(focus, true);
            budujBazę("Art.{30}", "Ustawa.+");
        } catch (MalformedURLException ex) {
            Logger.getLogger(TrzeciaPróba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NullPointerException ex) {
            Logger.getLogger(TrzeciaPróba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//INNE
    static String[] zbiórOdnośników(String[] pages, String początek, String koniec){

        String ok[]=new String[pages.length];
        
        for (int p=0;p<pages.length;++p){
            ok[p]="";
            String page=pages[p],temp="";

                    Pattern patent=Pattern.compile("Dz\\.");
                    Matcher mmm = patent.matcher(page.replace('\n', ' '));
                        while(mmm.find()){
                                temp+=mmm.group()+"||";
                                outL(mmm.group());
                ok[p]+=p+": "+temp+"\n";
            }
                        
        }
        return ok;
    }

}
