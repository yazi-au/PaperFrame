package paper.Utils;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicUtils {
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static Field getField(Field[] fields, String name){
        for(Field field : fields){
            if (name.equals(field.getName())){
                return field;
            }
        }
        System.out.println("Null: " + name);
        return null;
    }

    public static void errorPopup(String content) {
        JOptionPane.showMessageDialog(
                null,
                content,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
