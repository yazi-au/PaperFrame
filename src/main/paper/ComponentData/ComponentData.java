package paper.ComponentData;

import paper.Main;
import paper.Type.FieldAdapter;
import paper.Utils.BasicUtils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ComponentData {
    private boolean isActive;
    private ArrayList<FieldAdapter> fieldAdapters;
    private DefaultTableModel model;
    private JComponent component;
    private boolean loading;

    public void setFieldAdapters(ArrayList<FieldAdapter> fieldAdapters) {
        this.fieldAdapters = fieldAdapters;
    }

    public void initModel(int length){
        String[] columnNames = {"Key", "Value"};
        Object[][] data = new Object[length][2];
        model = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                if(loading) return;
                int row = e.getFirstRow();
                int column = e.getColumn();
                Object updatedValue = model.getValueAt(row, column);
                setValue(model.getValueAt(row,0).toString(), updatedValue);
                Main.repaint();
            }
        });
    }

    public void updateAll(){
        loading = true;
        for(FieldAdapter fieldAdapter:fieldAdapters){
            fieldAdapter.update(component);
        }
        Main.setSettingModel(model);
        loading = false;
    }

    public void setValue(String name, Object value){
        for(FieldAdapter field : fieldAdapters){
            if(field.getName().equals(name)){
                if(!field.correct(value)) {
                    BasicUtils.errorPopup("Wrong Value Type!");
                    return;
                }
                field.set(component,value);
                if(isActive) field.update(component);
                return;
            }
        }
    }

    public Object getValue(String name){
        for(FieldAdapter field : fieldAdapters){
            if(field.getName().equals(name)){
                return field.get(component);
            }
        }
        return null;
    }

    public ArrayList<FieldAdapter> getFieldAdapters() {
        return fieldAdapters;
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }

    public boolean isActive(){
        return isActive;
    }

    public JComponent getComponent(){
        return component;
    }

    public void setComponent(JComponent component){
        this.component = component;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
