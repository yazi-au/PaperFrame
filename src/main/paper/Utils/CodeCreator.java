package paper.Utils;

import paper.ComponentData.ComponentData;
import paper.PaperComponent;
import paper.PaperFrame;
import paper.Type.FieldAdapter;

import javax.swing.*;
import java.util.ArrayList;

public class CodeCreator {
    public static void showCode(ArrayList<String> code){
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
        JTextArea text = new JTextArea();
        for (int i = 0; i < code.size(); i++){
            text.append(code.get(i));
            text.append("\n");
        }
        text.setSize(1000,700);
        text.setLocation(0,0);
        frame.add(text);
        frame.setVisible(true);
    }

    public static ArrayList<String> getCode(PaperFrame frame){
        ArrayList<String> code = new ArrayList<>();
        code.add("import javax.swing.*;");
        code.add("import java.awt.*;");
        code.add("public class MyFrame {");
        code.add("   public static void show(){");
        code.add("      JFrame frame = new JFrame(" + frame.title + ");");
        code.add("      frame.setBackground(new Color("+frame.background.getRed() + "," + frame.background.getGreen() + "," + frame.background.getBlue() + "));");
        code.add("      frame.setSize(" + frame.windowSize.x + "," + frame.windowSize.y + ");");
        code.add("      frame.setLocationRelativeTo(null);");
        code.add("      frame.setLayout(null);");
        getComponentCode(code,frame);
        code.add("      frame.setVisible(true);");
        code.add("   }");
        code.add("}");
        return code;
    }

    private static final String space = "      ";
    private static void getComponentCode(ArrayList<String> code, PaperFrame frame){
        int i = 0;
        PaperComponent visitor = frame.getFirst();
        while(visitor != null){
            String className = visitor.getComponent().getClass().getSimpleName();
            String s = className.substring(1);
            String componentName = Character.toLowerCase(s.charAt(0)) + s.substring(1) + (i == 0 ? "" : i);

            StringBuilder builder = new StringBuilder(space);
            builder.append(className);
            builder.append(' ');
            builder.append(componentName);
            builder.append(" = new ");
            builder.append(className);
            builder.append("();");
            code.add(builder.toString());

            ComponentData data = visitor.getData();
            for(FieldAdapter fieldAdapter : data.getFieldAdapters()){
                fieldAdapter.toCode(visitor.getComponent(),code,componentName);
            }

            code.add("");

            visitor = visitor.next;
            i++;
        }
        code.remove(code.size()-1);
    }
}
