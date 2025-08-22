package paper.ComponentData.Components;

import paper.ComponentData.ComponentData;
import paper.Type.FieldAdapter;
import paper.Type.Types.StringType;
import paper.Utils.SettingSlot;

import javax.swing.*;
import java.util.ArrayList;

public class ButtonData extends ComponentData {
    public ButtonData() {
        ArrayList<FieldAdapter> adapters = new ArrayList<>();
        int pointer = 0;
        initModel(1);

        adapters.add(new FieldAdapter("text", new StringType(), new SettingSlot(getModel(),pointer++)) {
            @Override
            public void set(JComponent target, Object value) {
                JButton button = (JButton) target;
                button.setText(value.toString());
            }
            @Override
            public Object get(JComponent target) {
                JButton button = (JButton) target;
                return button.getText();
            }

            @Override
            public void toCode(JComponent target,ArrayList<String> code,String componentName) {
                StringBuilder s = new StringBuilder();
                s.append(space);
                s.append(componentName);
                s.append(".setText(\"");
                s.append(get(target));
                s.append("\");");
                code.add(s.toString());
            }
        });

        setFieldAdapters(adapters);
    }
}
