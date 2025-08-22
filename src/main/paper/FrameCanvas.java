package paper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FrameCanvas extends JComponent {
    private final BufferedImage image;
    private PaperFrame frame;

    public FrameCanvas(int width, int height) {
        setSize(width, height);
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public void setFrame(PaperFrame frame) {
        this.frame = frame;
    }

    @Override
    public void paint(Graphics draw){
        if(frame == null) return;
        Graphics2D g = image.createGraphics();
        frame.drawAll(g,image.getWidth(),image.getHeight());
        draw.drawImage(image, 0, 0, null);
        g.dispose();
    }
}