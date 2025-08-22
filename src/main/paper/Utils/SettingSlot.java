package paper.Utils;

import javax.swing.table.DefaultTableModel;

public class SettingSlot {
    DefaultTableModel model;
    int index;
    public SettingSlot(DefaultTableModel model,int index) {
        this.model = model;
        this.index = index;
    }
    public void setName(String name){
        model.setValueAt(name,index,0);
    }
    public void setValue(Object value){
        model.setValueAt(value,index,1);
    }
    public Object getValue(){
        return model.getValueAt(index,1);
    }
    public void setModel(DefaultTableModel model) {
        this.model = model;
    }
}
