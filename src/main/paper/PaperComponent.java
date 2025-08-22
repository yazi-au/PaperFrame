package paper;

import paper.ComponentData.ComponentData;
import paper.ComponentData.ComponentDataManager;

import javax.swing.*;

public class PaperComponent {
    private final JComponent component;
    public PaperComponent prev,next;

    public PaperComponent(JComponent component) {
        this.component = component;
    }

    public ComponentData getData() {
        return ComponentDataManager.searchData(component);
    }

    public JComponent getComponent(){
        return component;
    }

    public void delete(){
        if(prev != null){
            prev.next = next;
        }
        if(next != null){
            next.prev = prev;
        }
    }
}
