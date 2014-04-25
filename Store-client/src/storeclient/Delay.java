/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storeclient;

import java.util.logging.Level;
import java.util.logging.Logger;
import views.SecondView;

/**
 *
 * @author sylar
 */
public class Delay {
    private SecondView myView;
    
    public Delay(SecondView view){
        myView = view;
    }   
    public void run() {
        try {
            Thread.sleep(4000);
            this.myView.hideLabel();
        } catch (InterruptedException ex) {
            Logger.getLogger(Delay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
