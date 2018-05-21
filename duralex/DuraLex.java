/**
Program pomaga w analizowaniu tekstów ustaw
* oraz wyszukiwaniu relacji między przepisami prawa.
 */
package duralex;

import Constants.ISAPs;
import java.awt.Color;
import java.awt.Component;
import narzędzia.regexer;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.filechooser.FileFilter;
import org.apache.pdfbox.debugger.pagepane.PagePane;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.action.PDPageAdditionalActions;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author dizel
 */
public class DuraLex implements Runnable{
    
    static String[] argumenty={"https://static.rainfocus.com/oracle/oraclecode17/sess/1485992822413001Yd6N/PF/Cool%20in%20Java%208,%20and%20new%20in%20Java%209.pdf",ISAPs.ROOT+ISAPs.AS_DOWNLOAD+"19920210086/O/D19920086.pdf"};
    
    /**
     * @param args the command line arguments
     */
    public static void main(String...args)  {
        
     if(args.length>0)argumenty=args;
     outL(Arrays.toString(argumenty));
    //    wybierzDziałanie();
    new Thread(new DuraLex()).start();
   
    
    
    }

    
//oferta
    /**
     * Menu z metodami, używanymi bez kontekstu.
     */
    @Override
    public void run(){
        
        if(argumenty[0].matches("(http)|(www)|([a-zA-Z]).+pdf")){
            outL(argumenty[0]);//.matches("(http\\://)|(www\\.)|([a-zA-Z]\\:\\\\).+\\.pdf"));
            Okno o=new Okno(argumenty);
            o.setVisible(true);
            o.setState(Frame.NORMAL);
            
            return;
        }
        
        JFrame fr=new javax.swing.JFrame("",null);
        JPanel p=new javax.swing.JPanel(null,true);
        
        JComboBox<String> c= new javax.swing.JComboBox<>(new String[]{
            "wybierz akcję",
            "DuraLex",
            "Developer_helper",
            "Pulpit\\przykład.pdf(windows)",
            "getText(\"URL\")",
            "analizujDlaISAP()",
            "System.getenv()...",
            "nowa instancja"
        });
        
        
        
        JButton b=new javax.swing.JButton("uruchom");
        JEditorPane t=new javax.swing.JEditorPane("plain/text", "");
        JScrollPane s=new javax.swing.JScrollPane(t);
        fr.setBounds(0,0,330,540);
        p.setBounds(fr.getBounds());
        c.setBounds(0,0,200,40);
        b.setBounds(200,0,130,40);
        t.setBounds(0,40,330,500);
        
        s.setBounds(t.getBounds());
        s.setMinimumSize(new Dimension(150,150));
        s.setMaximumSize(new Dimension(1050,1500));
        t.setMinimumSize(s.getMinimumSize());
        t.setMaximumSize(s.getMaximumSize());
        fr.setLayout(p.getLayout());
        fr.add(c);fr.add(b);fr.add(s); fr.add(new PopupMenu());
        StringBuilder sb=new StringBuilder();
        for(String e:argumenty)sb.append(e).append('\n');

        t.setText(sb.toString());

        b.addMouseListener(new MouseListener() {
            @Override

            public void mouseClicked(MouseEvent e){
            //    if(b.getBounds().contains(e.getLocationOnScreen())){
                    JFrame o;
                   
                    switch(c.getSelectedIndex()){
                        case 1: {(o=new Okno(argumenty)).setVisible(true);o.setState(0);o.toFront();}    break;
                        case 2: {(o=new regexer()).setVisible(true);o.setState(0);o.toFront();}    break;
                        case 3: try {outL(Arrays.toString(getTexts("file:/home/dizel/Pulpit/przykład.pdf")));}catch(IOException ioe){}    break;
                        case 4: for(String s:t.getText().split("\n"))outL(getText(s));    break;
                        case 5: analizujDlaISAP();    break;
                        case 6: outL(System.getenv().toString().replace(", ", "\n"));    break;
                        case 7: new Thread(new DuraLex()).start();  break;
                        default: b.setText("brak wyboru");    break;
                    }
            //    }
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
 //               throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
  //              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
 //               throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
      
        
        fr.setVisible(true);
        fr.setDefaultCloseOperation(3);
    }
  /** *  @param ścieżka PDF url
     * @return Parsed PDF
     */
    public static String   getText (String ścieżka) {
        outL("DuraLex.getText(String) started");
        try (PDDocument pdf=PDDocument.load(( new URL(ścieżka) ).openStream())) {
            return new PDFTextStripper().getText(pdf);
        } catch (IOException ioe) {
            return ioe.getLocalizedMessage();
            
        }
    }
   /** * @param ścieżka PDF url
    * @return parsed PDF's array
     * @throws java.io.IOException
    */
    public static String []getTexts(String ścieżka) throws IOException{
        //outl("DuraLex getTexts(String) started");
        try( PDDocument pdf=PDDocument.load( (new URL(ścieżka)).openStream());PDDocument page=new PDDocument()){
            int i=0;
            String[] pages= new String[pdf.getNumberOfPages()];
            
            for(PDPage p:pdf.getPages()){
                page.addPage(p);
                pages[i++]=new PDFTextStripper().getText(page);
                page.removePage(p);
            }
            page.close();
            return pages;}
    }
    
   /**
    * @param plik pdfFile
    * @return equal to PDFStripper.parse();
    */
    public static String   getText (File plik) {
        outL("DuraLex.getText(File) started");
        try (PDDocument pdf=PDDocument.load(plik)) {
            PDFTextStripper strip=new PDFTextStripper();
            return strip.getText(pdf);
        } catch (IOException ex) {
            Logger.getLogger(DuraLex.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        }
    /**
     * @param PDF selected PDF files 
     * @return String array of set PDF array
     */
    static String   getTexts (File[] PDF) {
        try {
            outL("DuraLex.getText(File[]) started");
            String wynik="";
            PDFTextStripper strip = new PDFTextStripper();
            
            for (File plik: PDF) {
                try (PDDocument pdf=PDDocument.load(plik)) {
                    wynik+=strip.getText(pdf);
                    pdf.close();
                } catch (Exception ioe) {}

                return wynik;
            }
            return null;
        } catch (IOException ex) {
            Logger.getLogger(DuraLex.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
 
    /** Use of "new Scanner(File);"
     * @param file
     * @return file content
     */
    static String   openTXT (File file) {
        outL("DuraLex.openTXT(File) started");
        try {
            String txt;
            try (Scanner s=new Scanner(file)) {
                txt="";
                while (s.hasNext())
                    txt+=s.nextLine()+"\n";
            }
            return txt;
        } catch (FileNotFoundException ex) {
            return "nie udało się odczytać pliku";
        }
    }
    /**
     * Liczy każde słowo w Biblii bez rozpoznawania koniugacji.
     * @param multi
     * @return 
     */
 
//cegiełki ofert
    @SuppressWarnings("empty-statement")
    public static File[] getFiles(boolean multi){
        JFileChooser c=new JFileChooser();
        c.setMultiSelectionEnabled(multi);
        c.showOpenDialog(null);
        
        return multi?c.getSelectedFiles():new File[]{c.getSelectedFile()};
   }
    void pokażDokument(URL plik) throws IOException {
        outL("DuraLex.pokażDokument(URL) started");
       // PDDocument pdf=PDDocument.load(plik.openStream());
        PDPage page;
        PDFParser pars;
        PDFTextStripper par;
        PDFParser g;
    }

    static void analizujWgSłów(String string) {
        outL("DuraLex.analizuj(String) started");
        try (PDDocument pdf=PDDocument.load(new java.io.File(string))) {
            String tekst=new PDFTextStripper().getText(pdf);
            String[] wynik=tekst.split(" ");
            String[] WYNIK=new String[wynik.length];
            int[] ilość=new int[wynik.length];
            int i=0;
            int j=0;
            for (String słowo: wynik) {
                while (( wynik[i]==null?WYNIK[j]!=null:!wynik[i].equals(WYNIK[j]) )&&WYNIK[j]!=null)
                    j++;
                WYNIK[j]=wynik[i];
                j=0;
                ilość[i]+=1;

            }
            System.out.println(wynik[244]+":"+ilość[244]);
            System.out.println(wynik[103]+":"+ilość[103]);
            System.out.println(wynik[22]+":"+ilość[22]);
        } catch (IOException ex) {
            Logger.getLogger(DuraLex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void setTxt(File plik, JTabbedPane TABlp, int tab) {
        outL("DuraLex.setTxt(File,JTabbedPane,int) started");
        ( (JTextPane) ( (JViewport) ( (JScrollPane) TABlp.getSelectedComponent() )
                .getComponent(tab) ).getComponent(0) )
                .setText(DuraLex.getText(plik));
    }
    
    boolean contains(String query, String...m){
        for(String s:m)if(query.contains(s)) return true;
        return false;
    }
    static int LP=0;
//próbki
    
//RC
//eksperymenty
    static synchronized JPanel getNewSize(JTabbedPane tab,JPanel pan,int size){
        for(int i=0;i<pan.getComponents().length;i++){
            Rectangle c=pan.getComponents()[i].getBounds();
            pan.getComponents()[i].repaint(
                    c.x, c.y,c.width*(1+size/10), c.height*(1+size/10)
            );
            
        }
        tab.add(pan);
        return pan;
    }
    static void ładowarkaPDF(JPanel cel,PDPage[]pgs,int p){
        int t=p;
        
    }
    static void analizujDlaISAP(){
            
        File[] f=getFiles(true);
        
        outL("start");
        for(int i=0,l=f.length;i<l;i++) try (PDDocument pdf=PDDocument.load(f[i])){
            PDFParser parse;
            PDFTextStripper strip =  new PDFTextStripper();
            String[] in =strip.getText(pdf).split("\n");
            int j=0;
            for(String s:in)System.out.print(s+"\n"+s.length()+"\n");
        
    }       catch (IOException ex) {
                Logger.getLogger(DuraLex.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    static String szukajLinku(String[] wzór, JEditorPane txt) {
        int caret=txt.getCaretPosition();
        String S=txt.getText().substring(caret, caret+25);
        for (int s=0; s<50; s++) {
        }
        return null;
    }
 
    /**
     *
     */
    
    /**
     * Podobne do TrzeciaPróba.BudujBazę
     * @param url - of site to parse
     * @param target - scheme after whitch it takes String to the element
     * @param range the value of ange
     * @param offset offset of poszlaka's possible location
     * @param length the value of length
     * @return the java.lang.String[] 
    */
    static  String[] menu(
            String url, String targetString, String range, int offset, int length) throws IOException, MalformedURLException
    {
        //URLConnection uc=new URL(url).openConnection();
        BufferedReader br=new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()));
        StringBuilder temp=new StringBuilder();
        Stack<String> out=new Stack<>();
        
        while(br.ready())temp.append(br.readLine());//WCZYTYWANIE ŹRÓDŁA
        
        Matcher pool=Pattern.compile(range).matcher(temp.toString());
        Matcher target;
        Pattern tgt=Pattern.compile(targetString);
        
        //wyszukiwanie w uwzględnianych zakresach
        while(pool.find()){
            target=tgt.matcher(pool.group());
            while(target.find())
                if(length>0)out.add(target.group().substring(offset,offset+length));
                else out.add((target.group().substring(offset).replace("\\D+","")));
        }
        return out.toArray(new String[out.size()]);
    }


//testy
    
    static int tab, mouX=0, mouY=0,pHmax=0,pH=-1;
    static JTabbedPane TAB;
    static JComponent dragDrop;
    static LocalTime start, stop;
    static Object skupienie;
    static String link="";
    static int retry=0;
    static boolean jump=false;
    
    static String[]pHistoria=new String[1];    
    
        //interfejsy
    static FileFilter FF_TXT=new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.toString().toLowerCase(Locale.getDefault()).endsWith("txt")
                    ||f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "folder and \"*.txt\"";
        }
    };
    static FileFilter FF_PDF=new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.toString().toLowerCase(Locale.getDefault()).endsWith("pdf")
                    ||f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "folder and \"*.pdf\"";
        }
    };
   
//monitoring
       /** skrót System.out.println(Object o)
     * @param o 
     */
    public static void outL(Object o) {System.out.println(o);}
    public static String outR(String o){ System.out.print(o); return o;}
    public static <T extends Object> T outr(Object o, Class<T> type){
        System.out.println(o);
        return type.cast(o);
    }
    public static <T extends Object> T outCR(String s, Object returnValue, Class<T> type){
        System.out.println(s);
        return type.cast(returnValue);
    }
    static String generujLink(String rok,String numer,String pozycja,String typ,String część){
        return ISAPs.ROOT+pozycja==null?
                ISAPs.MIDDLE_CHRONO1_YEAR+rok+numer==null?"":("&vol="+numer) //to display year's or volume's resources
               :typ==null?
                 ISAPs.getAdressAsString(rok, numer, pozycja) //to dosplay position's resources
                :ISAPs.getAdressAsString(rok, numer, pozycja, ISAPs.valueOf(typ));//to get a file's adress
    }
    static JEditorPane asText(URL url) {
        return new JEditorPane("text", getText(url.toString()));
    }
    static JEditorPane asText(File file) {
        return new JEditorPane("text", getText(file));
    }
    static JPanel asPDF(URL url, JTabbedPane jTab, JLabel pasekStanu) {
        JPanel jpan;
        jpan = new JPanel(null);//.addMouseListener(TAB.getMouseListeners()[0]);
        
        try {
            PDDocument pdf=PDDocument.load(url.openStream());
            int numOfPgs=pdf.getNumberOfPages();
            int i=numOfPgs;
            int w=(int) pdf.getPage(0).getArtBox().getWidth()+1;
            int h=(int) pdf.getPage(0).getArtBox().getHeight()+1;
            
            Component jc;
            while (i-->0){//numOfPgs) {
                (jc=new org.apache.pdfbox.debugger.pagepane.PagePane(pdf, pdf.getPage(i).getCOSObject(), pasekStanu).getPanel()).addMouseListener(jTab.getMouseListeners()[0]);
                jc.setBounds(0, i*h-30,w,h);// w, h);
                jpan.add(jc);
            }
            
            jpan.setPreferredSize(new Dimension(w, h*numOfPgs));
            
            return jpan;

        } catch (IOException ex) {
            outL("asPDF(URL) nie powiodło się");
            return null;
        }
    }
    static JPanel asTXT(URL url) throws IOException {
        PDDocument PDF=PDDocument.load(url.openStream());
        PDDocument pdf=new PDDocument();
        JPanel jp=new JPanel(null);
        jp.setDoubleBuffered(true);
        JEditorPane e;

        PDFTextStripper strip=new PDFTextStripper();
        int numOfPgs=PDF.getNumberOfPages();
        int i=0, h=0, w=0;

        JSeparator sep=new javax.swing.JSeparator(0);
        sep.setPreferredSize(new Dimension(w, 20));
        for (PDPage p: PDF.getPages()) {
            if (pdf.getNumberOfPages()>0)
                pdf.removePage(0);
            pdf.addPage(p);
            h=(int) p.getArtBox().getHeight()+10;
            w=(int) p.getArtBox().getWidth()+1;
            e=new javax.swing.JEditorPane("text/plain", strip.getText(pdf));
            e.setBounds(0, i*20+i++*h, w, h);
            e.setDoubleBuffered(true);
            jp.add(e);
            sep.setBorder(new javax.swing.border.MatteBorder(new Insets(5, 5, 5, 5), Color.GREEN));
            sep.setBounds(0, i*h, w, 20);

            jp.add(sep);
        }
        jp.setPreferredSize(new Dimension(w, i*h));
        return jp;
    }
    public static PagePane asPDF(URL url, int pageNumber, JLabel pasekStanu) {
        try {
            PDDocument PDF=PDDocument.load(url.openStream());
            return new PagePane(PDF, PDF.getPage(pageNumber).getCOSObject(), pasekStanu);
        } catch (IOException ex) {
            outL("asPDF nie powiodło się");
            return null;
        }
    }

//test
    static Clipboard clip = new java.awt.datatransfer.Clipboard("Clipboard - imię");
    static InputVerifier iv=new InputVerifier() {
        @Override
        public boolean verify(JComponent c) {
            try{
            char[] in=( (JTextField) c ).getText().toCharArray();
            for (char i: in)
                if (i>9||i<0)
                    return false;
            return true;}catch (ClassCastException cce){
                System.out.print(cce.toString()+"____DuraLex.java_line_558_ClassCastExeption____");
                return false;
            }
        }
    };
    static JPanel asPDFmarks(URL url, JTabbedPane jTab, JLabel pasekStanu,JTextField...test) {
        JPanel jpan;
        jpan = new JPanel(null);//.addMouseListener(TAB.getMouseListeners()[0]);
        PDActionURI pau;//=new PDActionURI;
        try {
            PDDocument pdf=PDDocument.load(url.openStream());
            int numOfPgs=pdf.getNumberOfPages();
            int i=numOfPgs;
            int w=(int) pdf.getPage(0).getArtBox().getWidth()+1;
            int h=(int) pdf.getPage(0).getArtBox().getHeight()+1;
            
            Component jc;
            while (i-->0){
                PDPage p=pdf.getPage(i);
                pau=new PDActionURI(p.getCOSObject());
                PagePane pp=new org.apache.pdfbox.debugger.pagepane.PagePane(
                                pdf
                                ,pau.getCOSObject()// pdf.getPage(i).getCOSObject()
                                , pasekStanu);
                PDPageAdditionalActions paa;
                PDAction pa;
                
                
                
                test[0].setToolTipText("SubType");
                test[1].setToolTipText("TrackMousePosition");
                test[2].setToolTipText("Type");
                test[3].setToolTipText("URI");
                pau.setSubType(test[0].getText());
                pau.setTrackMousePosition(test[1].getText().equals("track"));
                pau.setType(test[2].getText());
                pau.setURI(test[3].getText());
                
                
                
                jc=pp.getPanel();
                jc.addMouseListener(jTab.getMouseListeners()[0]);
                outL(
                        jc.getName()
                
                
                );
                        
                jc.setBounds(0, i*h-30,w,h);// w, h);
                
                jpan.add(jc);
            }
            
            jpan.setPreferredSize(new Dimension(w, h*numOfPgs));
            
            return jpan;

        } catch (IOException ex) {
            outL("asPDF(URL) nie powiodło się");
            return null;
        }
    }
    static void start(String s) {
        System.out.println(( start=LocalTime.now() ).toString()+"\n"+s); 
    }
    static void stop(String s) {
        System.out.println(s+"\n"+( stop=LocalTime.now() )+"\n"+( stop.compareTo(start) ));
    }
    

//ŚMIETNIK
}