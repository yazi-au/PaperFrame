package paper.Type;

import paper.Utils.SettingSlot;

public interface TypeAdjuster {
    void update(Object object, SettingSlot settingSlot);
    boolean correct(Object object);
}
