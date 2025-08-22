package paper.Type;

import paper.Utils.SettingSlot;

import javax.swing.*;
import java.util.ArrayList;

public abstract class FieldAdapter {
    private final String name;
    private final TypeAdjuster typeAdjuster;
    private final SettingSlot settingSlot;
    public FieldAdapter(String name, TypeAdjuster typeAdjuster, SettingSlot settingSlot) {
        this.typeAdjuster = typeAdjuster;
        this.name = name;
        this.settingSlot = settingSlot;
    }

    public void update(JComponent target) {
        typeAdjuster.update(get(target),settingSlot);
        settingSlot.setName(name);
    }

    public String getName(){
        return name;
    }

    public boolean correct(Object object){
        return typeAdjuster.correct(object);
    }

    public abstract void set(JComponent target, Object value);
    public abstract Object get(JComponent target);

    public static final String space = "      ";
    public abstract void toCode(JComponent target,ArrayList<String> code,String componentName);
}
