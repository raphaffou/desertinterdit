package Vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.Border;

class RoundedBorder implements Border {
        
    private int radius;
    private Color color;
    private int borderWidth;
    
    RoundedBorder(int radius, Color color, int borderWidth) {
        this.radius = radius;
        this.color = color;
        this.borderWidth = borderWidth;
    }
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius, this.radius,
                this.radius, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRoundRect(x,y,width,height,radius,radius);
        g2d.dispose();
    }
}
