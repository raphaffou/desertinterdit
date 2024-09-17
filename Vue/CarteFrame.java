package Vue;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class CarteFrame extends JFrame{

    Image img;
    JPanel content;


    public CarteFrame(String filename){
        super();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(320, 450);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Carte preview");
        CarteFrame self = this;
        this.addWindowListener(new WindowAdapter() {
            public void windowDeactivated(WindowEvent evt) {
                self.dispose();
            }
        });

        
        this.setVisible(true);
        this.add(new JLabel(new ImageIcon("src/images/cartes/"+filename)));
    }

}
