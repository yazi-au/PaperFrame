package paper.Type.Types;

import paper.Type.TypeAdjuster;
import paper.Utils.SettingSlot;

public class StringType implements TypeAdjuster {
    @Override
    public void update(Object object, SettingSlot settingSlot) {
        settingSlot.setValue(object);
    }

    @Override
    public boolean correct(Object object) {
        return object instanceof String;
    }
}
