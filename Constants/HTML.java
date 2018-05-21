/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constants;

import java.util.Locale;

/**
 *
 * @author Joanna di Raków
 */
public final class HTML {
    public final static int LENGTH_PL=18;
    public final static char[]ZNAK_PL={'ą','Ą','ć','Ć','ę','Ę','ł','Ł','ń','Ń','ó','Ó','ś','Ś','ż','Ż','ź','Ź','ó','Ó'};
    public final static String[]ENCJA_PL={"&#261;","&#260;","&#263;","&#262;","&#281;","&#280;","&#322;","&#321;","&#324;","&#323;","&#243;","&#211;","&#347;","&#346;","&#380;","&#379;","&#378;","&#377;","&oacute;","&Oacute;"};
    
    
    public static char fromEntityElement(String entity){
        int i=0;
        while(i<ENCJA_PL.length&&!entity.equals(ENCJA_PL[i]))i++;
        return ENCJA_PL[i].equals(entity)?ZNAK_PL[i]:null;
    }
    public static String toEntityElement(char c){
        int i=0;
        while(i<ZNAK_PL.length&&c!=ZNAK_PL[i])i++;
        return ZNAK_PL[i]==c?ENCJA_PL[i]:null;
    }
    public static String fromEntity(String input, Locale loc){
        if(loc==null||loc.getCountry().equals("pl_PL"))System.out.print("Wykryto polski język");
        for(int e=0;e<ENCJA_PL.length;e++)
            input=input.replaceAll(ENCJA_PL[e], ""+ZNAK_PL[e]);
        return input;
    }
    public static String toEntity(String input,Locale loc){
        if(loc==null||loc.getCountry().equals("pl_PL"))System.out.print("Wykryto polski język");
        for(int e=0;e<ZNAK_PL.length;e++)
            input=input.replaceAll(""+ZNAK_PL[e],ENCJA_PL[e]);
        return input;
    }
}
