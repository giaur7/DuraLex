/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package narzędzia;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Stack;

/**
 * Obserwator monitoruje system za zmianami w trybach ciągłym/pobudki-snu.
 * Zmiany mogą być badane pobieżnie (czas ostatniego dostępu/modyfikacji i t.p. ) albo konkretnie (porównywanie (meta)danych).
 * 
 * 
 * Wprowadzony śledzony element jest kwalifikowany do jednej z dwóch rodzajowych - w zależności od głębokości badania.
 * Obsługuje odpowiednie zdarzenie w przypadku napotkanej zmiany.
 * @author DuraLex systems
 */
public class SystemObserver implements Runnable {
    private Stack<? extends Object> s;
    private static final HashMap<? super Object,Long> CHECK_LIST=new HashMap<>();
    public static String waitingForRun;
    public void addProperty(Path p){
        CHECK_LIST.put(p,p.toFile().lastModified());
        
    }
    private SystemObserver(){
    }
    @Override
    public void run() {
        
    }
}
