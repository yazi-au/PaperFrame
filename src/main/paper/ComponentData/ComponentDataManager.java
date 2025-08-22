package paper.ComponentData;

import paper.ComponentData.Components.ButtonData;

import javax.swing.*;
import java.util.HashMap;

public class ComponentDataManager {
    private final static HashMap<Class<? extends JComponent>, ComponentData> dataMap = new HashMap<>();

    public static void init(){
        //init adapters...
        dataMap.put(JButton.class, new ButtonData());
    }

    public static ComponentData searchData(JComponent component){
        ComponentData data = dataMap.get(component.getClass());
        if(data == null){
            return null;
        }
        data.setComponent(component);
        return data;
    }
}
