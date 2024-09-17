package Vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class JButtonCustom extends JButton {
    
    private static final int ARC_WIDTH = 15;
    private static final int ARC_HEIGHT = 15;
    private  Color BACKGROUND_COLOR = new Color(0, 100, 235);
    private  Color HOVER_COLOR = new Color(20, 120, 245);
    private  Color PRESSED_COLOR = new Color(40, 140, 255);
    
    private boolean hovering = false;
    private boolean pressed = false;

    // private int width, height;

    public JButtonCustom(String text) {
        this(text, 0, 0);

    }

    public JButtonCustom(String text, Color color){
        this(text);
        this.BACKGROUND_COLOR = color;
        this.HOVER_COLOR = color.brighter();
        this.PRESSED_COLOR = color.darker();
    }

    public JButtonCustom(String text, int width, int height) {
        super(text);
        // this.width = width;
        // this.height = height;
        
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.BLACK);
        setOpaque(false);
        setFocusPainted(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                repaint();
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (pressed) {
            g.setColor(PRESSED_COLOR);
        } else if (hovering) {
            g.setColor(HOVER_COLOR);
        } else {
            g.setColor(BACKGROUND_COLOR);
        }
        
        g.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
        
        super.paintComponent(g);
        g.dispose();
    }
}
