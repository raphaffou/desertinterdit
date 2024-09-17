package Controller;

import Vue.CarteFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CtrlCardPrev extends MouseAdapter {
    String filename;
    public CtrlCardPrev(String filename){
        this.filename = filename;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() != MouseEvent.BUTTON1){
            System.out.println("Click droit detecté");
            return;
        }
        System.out.println("Click Gauche detectée");
        new CarteFrame(filename); //set visible à true tout seul comme un grand
    }
}
