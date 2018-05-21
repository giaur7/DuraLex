/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpidersWeb;

import Constants.HTML;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


/**
 *
 * @author columbo5
 */
public class SpidersWeb { //implements Collection<feeder,hungry> {
File cel;
ArrayList<String> przepisy=new ArrayList<>();
ArrayList<Integer>właściwości=new ArrayList<>();///
ArrayList<String> martwiŻywiciele=new ArrayList<>();
ArrayList<String> martweUstawy=new ArrayList<>();
    public static void main (String...args) throws IOException, MalformedURLException{
       
       
    }
    public void gromadźDane(long numer){
        
        if(numer<-20120000000l){
            
            
        }
    }
/**
     * @param pdf
     * @param regexHungry
     * @param regexFeeder
     * @param regexAsterisk
 * @deprecated
 * @throws IOException 
 */
    public void getContent(URL pdf,String regexHungry,String regexFeeder,String regexAsterisk) throws IOException{
        String source;
    try (PDDocument pdd = PDDocument.load(pdf.openStream())) {  source = new PDFTextStripper().getText(pdd);}
    catch(Exception e) {return;}
        
        Matcher feeders=Pattern.compile(regexFeeder).matcher(source);
        Matcher hungries=Pattern.compile(regexHungry).matcher(source);
        Matcher refs=Pattern.compile(regexAsterisk).matcher(source);
        
        while(hungries.find()){
            
//            feeders.region(hungries.start(), hungries.)
        }
       
        
    }
    /**
     * 
     * @param searchField site to analyze
     * @param savedData a data to be enlarged (null means new one)
     * @param regexStraightContent regex-type content to add to the data
     * @param regexTrace puts (to determinate weather certain site should be searched)
     * @param regexAditionalInfo puts (additional info )
     * @return
     * @throws IOException 
     */
    public ArrayList<String> buildDataBase(URL searchField, String regexStraightContent, String regexTrace, String regexAditionalInfo, ArrayList<String> savedData) throws IOException{
        URLConnection uc=searchField.openConnection();
        ArrayList<String> out=savedData==null?new ArrayList<>():savedData;
        StringBuilder 
                sb=new StringBuilder()
                , aditional=new StringBuilder()
                ,trace=new StringBuilder();
        
        BufferedReader br=new BufferedReader(
                new InputStreamReader(
                        searchField.openStream()
                ));
            
                
        String s;
        Pattern rsc=Pattern.compile(regexStraightContent),rt=Pattern.compile(regexTrace==null?"^[.]":regexTrace),rai=Pattern.compile(regexAditionalInfo==null?"^[.]":regexAditionalInfo);
        Matcher matcher;
        while (br.ready()){
        //System.out.print("_____<ŹRÓDŁO___"+(
                s=HTML.fromEntity(br.readLine(),Locale.getDefault());
                        //)      +"_____/>\n");    
            (matcher=rsc.matcher(s)).usePattern(rsc);
        

            if(matcher.find()) out.add(matcher.group());
            else if(matcher.usePattern(rt ).find()){
                trace.append("\n").append(matcher.group());
                //out.add("<TRACE>\n"+matcher.group()+"\n</TRACE>\n");
            }
            else if(matcher.usePattern(rai).find()){
                aditional.append("\n").append(matcher.group());
                //out.add("<ADDITIONAL INFO>\n"+matcher.group()+"\n</ADDITIONAL INFO>\n");
            }
            }
        
        out.add(0, "\b"+(trace.length()>0?"<TRACE>\n"+trace.toString()+"\n</TRACE>\n":"")+(aditional.length()>0?("<ADDITIONAL INFO>"+aditional.toString()+"\n</ADDITIONAL INFO>\n"):""));
        out.trimToSize();
        return out;
    }
}