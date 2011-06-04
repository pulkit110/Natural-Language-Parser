package parser;

import javax.swing.UIManager;

/**
 *
 * @author Pulkit, Sapan and Himanshu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
         * sets look and feel for the UI
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        
        UI.display();
    }

}