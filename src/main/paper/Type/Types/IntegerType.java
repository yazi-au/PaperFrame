package paper.Type.Types;

import paper.Type.TypeAdjuster;
import paper.Utils.SettingSlot;

public final class IntegerType implements TypeAdjuster {
    @Override
    public void update(Object object, SettingSlot settingSlot) {
        if(object instanceof Integer || object instanceof Long) {
            settingSlot.setValue(object);
        }
        settingSlot.setValue(Integer.valueOf(object.toString()));
    }

    @Override
    public boolean correct(Object object) {
        return object instanceof Integer || object instanceof Long || object instanceof String;
    }
}
