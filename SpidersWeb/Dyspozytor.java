/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpidersWeb;

import duralex.DuraLex;
import static duralex.DuraLex.asPDF;
import java.awt.CardLayout;
import java.awt.Component;
import java.net.URL;
import java.util.Stack;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author columbo5
 */
public class Dyspozytor {//implements Runnable  {
    public int pageBufferSize=256;
    Stack<URL> stosK=new Stack<>();
    Stack<int[]> stosV=new Stack<>();
    
    public static void updateView(JPanel container,URL url,int[] newPages,JLabel label){
        
        for(int n:newPages){
            Component c= asPDF(url,n,label).getPanel();
            c.setBounds(container.getComponent(n).getBounds());
            container.remove(n);
            container.add(c, n);
        }
    }
    public void setPageBuffer(int size){pageBufferSize=size;}
    /**
     * Dodaje plik na szczyt stosu wczytywania plików.
     * @param url
     * @param pages 
     */
    public void addToStacks(URL url,int[]pages){
        int s;
        if ((s=stosK.indexOf(url))>-1){
            stosK.add(stosK.remove(s));
            stosV.remove(s);
            stosV.add(pages);
        }
    }
    /**
     * 
     * @param buffer
     * @return 
     */
    public Dyspozytor getInstance(int buffer){
        Dyspozytor d=new Dyspozytor();
        d.pageBufferSize=buffer;
        return d;
    }

    
    
    
    
    /**
     * Wstępnie sztywna metoda - do sprawdzenia działania.
     * Zadaniem jej będzie wrzucanie stron we właściwe miejsce. 
     * Ma być wywoływana przez swoją superklasę oraz z możliwością dostępu z zewnątrz.
     * @param scr Scroll, w którym jest kontener ze stronami
     * @param path ścieżka dokumentu, którym ma się posłużyć
     * @param pageNumber numer strony do wrzucenia
     * @param l JLabel, w którym będzie można śledzić przebieg otwierania dokumentu
     * @return 
     */
    public JScrollPane podmieńStronę(JScrollPane scr,URL path,int pageNumber, JLabel l){
        
         Component c =DuraLex.asPDF(path, pageNumber, l).getPanel();
         c.setBounds(((JPanel) scr.getViewport().getComponent(0)).getComponent(pageNumber).getBounds());
        ((JPanel) scr.getViewport().getComponent(0)).remove(pageNumber);
        //scr.invalidate();
        ((JPanel) scr.getViewport().getComponent(0))
            .add(
                   new JPanel().add(c)
                    ,new CardLayout()//((JPanel) scr.getViewport().getComponent(0)).getLayout()
                    ,pageNumber
                );
               // scr=new JScrollPane(scr.getViewport().getComponent(0));
                  //outr(((JPanel) scr.getViewport().getComponent(0)),new Component() {}.getClass()).repaint();
        return scr;
        
    }

  //  @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
