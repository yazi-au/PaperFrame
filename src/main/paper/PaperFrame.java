package paper;

import paper.Utils.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PaperFrame {
    private PaperComponent first,last;

    public String title;
    public Color background = Color.LIGHT_GRAY;
    public Vec2i camera = new Vec2i();
    public Vec2i windowSize;
    public Insets insets = new Insets(30,7,7,7);
    public BufferedImage icon;

    public PaperFrame(String title,int width,int height){
        this.title = title;
        this.windowSize = new Vec2i(width,height);
    }

    public void add(PaperComponent component) {
        if(first == null){
            first = component;
            last = component;
            return;
        }
        this.last.next = component;
        this.last = component;
    }

    public void spawnWindow(){
        JFrame frame = new JFrame(title);
        frame.setSize(windowSize.x,windowSize.y);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        PaperComponent visitor = first;
        while (visitor != null) {
            frame.add(visitor.getComponent());
            visitor = visitor.next;
        }
        frame.setVisible(true);
    }

    private void drawAllComponent(Graphics2D g) {
        PaperComponent visitor = first;
        while (visitor != null) {
            g.translate(visitor.getComponent().getX()-camera.x+insets.left,visitor.getComponent().getY()-camera.y+insets.top);
            visitor.getComponent().paint(g);
            g.translate(-visitor.getComponent().getX()+camera.x-insets.left,-visitor.getComponent().getY()+camera.y-insets.top);
            visitor = visitor.next;
        }
    }

    private void drawWindowPre(Graphics g,int x,int y){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, x,y);
        g.setColor(background);
        g.fillRect(-camera.x, -camera.y, windowSize.x, windowSize.y);
        g.setColor(Color.white);
        g.fillRect(-camera.x, -camera.y, windowSize.x, insets.top);
        g.fillRect(-camera.x, -camera.y + insets.top, insets.left, windowSize.y - insets.top - insets.bottom);
        g.fillRect(-camera.x + windowSize.x - insets.right, -camera.y + insets.top, insets.right, windowSize.y - insets.top - insets.bottom);
        g.fillRect(-camera.x, -camera.y + windowSize.y - insets.bottom, windowSize.x, insets.bottom);
        if(icon != null) g.drawImage(icon, -camera.x + 5, -camera.y + 5, null);
        g.setColor(Color.BLACK);
        g.drawString(title, -camera.x + 30, -camera.y + 20);
    }

    public void drawAll(Graphics2D g,int x,int y){
        drawWindowPre(g,x,y);
        drawAllComponent(g);
    }

    public PaperComponent getClickedComponent(Point point){
        Point clickPoint = new Point();
        clickPoint.x = point.x + camera.x - insets.left;
        clickPoint.y = point.y + camera.y - insets.top;

        PaperComponent visitor = first;
        while (visitor != null) {
            Rectangle bounds = visitor.getComponent().getBounds();
            if (bounds.contains(clickPoint)) {
                return visitor;
            }
            visitor = visitor.next;
        }
        return null;
    }

    public PaperComponent getFirst() {
        return first;
    }
    public void setFirst(PaperComponent first) {
        this.first = first;
    }
    public PaperComponent getLast() {
        return last;
    }
    public void setLast(PaperComponent last) {
        this.last = last;
    }
}
