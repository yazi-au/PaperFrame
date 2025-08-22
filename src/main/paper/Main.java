package paper;

import paper.ComponentData.ComponentDataManager;
import paper.ComponentData.Components.ButtonData;
import paper.Utils.CodeCreator;
import paper.Utils.Vec2i;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class Main {
    private static Vec2i lastLeft = new Vec2i();
    private static Vec2i lastRc = new Vec2i();
    private static Vec2i offset = new Vec2i();
    private static JFrame window;
    private static PaperComponent selected,lastComponent;
    private static FrameCanvas canvas;
    private static PaperFrame currentFrame = new PaperFrame("Default",500,500);
    public static void main(String[] args) {
        ComponentDataManager.init();
        initWindow();
        addConfigPanel();
        addPopupMenu();
        bindWASD();
        initDeleteKey();
        initMenu();
        addDragAction();
        canvas.setFrame(currentFrame);
        window.setVisible(true);

        JButton button = new JButton();
        currentFrame.add(new PaperComponent(button));
        button.setSize(100,50);
        button.setLocation(100,50);
        ButtonData data = (ButtonData) ComponentDataManager.searchData(button);
        data.setValue("text","hello world");

        canvas.repaint();
    }

    public static void initWindow(){
        window = new JFrame("Paper Frame Designer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1112,700);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);

        canvas = new FrameCanvas(800,700);
        canvas.setLocation(0,0);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvas.requestFocusInWindow();
            }
        });

        window.add(canvas);
    }

    private static JTable table;
    public static void setSettingModel(DefaultTableModel model){
        table.setModel(model);
    }

    public static void addConfigPanel(){
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(800, 0, 300, 700);
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                scrollPane.requestFocusInWindow();
            }
        });
        window.add(scrollPane);
    }

    public static void addDragAction(){
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                PaperComponent clicked = currentFrame.getClickedComponent(e.getPoint());
                if(clicked != null){
                    offset.x = e.getX()-currentFrame.insets.left - clicked.getComponent().getX() + currentFrame.camera.x;
                    offset.y = e.getY()-currentFrame.insets.top - clicked.getComponent().getY() + currentFrame.camera.y;
                    selected = clicked;
                    lastComponent = clicked;
                    clicked.getData().updateAll();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selected = null;
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(selected == null) return;
                lastLeft.x = e.getPoint().x;
                lastLeft.y = e.getPoint().y;
                selected.getComponent().setLocation(e.getPoint().x -currentFrame.insets.left-offset.x + currentFrame.camera.x,e.getPoint().y-currentFrame.insets.top-offset.y + currentFrame.camera.y);
                canvas.repaint();
            }
        });
    }

    private static void initDeleteKey(){
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if(currentFrame.getFirst() == lastComponent){
                        currentFrame.setFirst(lastComponent.next);
                    }
                    if(currentFrame.getLast() == lastComponent){
                        currentFrame.setLast(lastComponent.prev);
                    }
                    lastComponent.delete();
                    lastComponent = null;
                    canvas.repaint();
                }
            }
        });
    }

    public static void addPopupMenu(){
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem item1 = new JMenuItem("View");
        JMenuItem item2 = new JMenuItem("GetCode");
        JMenu subMenu = new JMenu("Create");

        String[] items = new String[]{
                "JButton",
                "JCheckBox",
                "JComboBox",
                "JTextArea",
                "JPasswordField",
                "JTextField",
                "JList",
                "JRadioButton",
                "JLabel",
                "JFileChooser",
                "JColorChooser",
                "JOptionPane"
        };
        ActionListener createListener = e -> {
            String name = e.getActionCommand();
            JComponent component = null;
            switch (name){
                case "JButton":{
                    component = new JButton();
                    break;
                }
                case "JCheckBox":{
                    component = new JCheckBox();
                    break;
                }
                case "JComboBox":{
                    component = new JComboBox<>();
                    break;
                }
                case "JFileChooser":{
                    component = new JFileChooser();
                    break;
                }
                case "JColorChooser":{
                    component = new JColorChooser();
                    break;
                }case "JLabel":{
                    component = new JLabel();
                    break;
                }
                case "JRadioButton":{
                    component = new JRadioButton();
                    break;
                }
                case "JList":{
                    component = new JList();
                    break;
                }
                case "JTextField":{
                    component = new JTextField();
                    break;
                }
                case "JPasswordField":{
                    component = new JPasswordField();
                    break;
                }
                case "JTextArea":{
                    component = new JTextArea();
                    break;
                }
                case "JOptionPane":{
                    component = new JOptionPane();
                    break;
                }
            }
            component.setLocation(lastRc.x,lastRc.y);
            component.setSize(200,50);
            currentFrame.add(new PaperComponent(component));
            canvas.repaint();
        };
        ActionListener viewListener = e -> {
            currentFrame.spawnWindow();
        };
        item1.addActionListener(viewListener);
        ActionListener codeListener = e -> {
            CodeCreator.showCode(CodeCreator.getCode(currentFrame));
        };
        item2.addActionListener(codeListener);
        for (int i = 0; i < items.length; i++){
            JMenuItem createItem = new JMenuItem(items[i]);
            createItem.addActionListener(createListener);
            subMenu.add(createItem);
        }

        popupMenu.add(subMenu);
        popupMenu.add(item1);
        popupMenu.add(item2);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    lastRc.x = e.getX() + currentFrame.camera.x;
                    lastRc.y = e.getY() + currentFrame.camera.y;
                }
            }
        });
    }

    private static void initMenu(){
        JMenuBar menu = new JMenuBar();

        JMenu fileMenu = new JMenu("文件");
        JMenu editMenu = new JMenu("编辑");

        JMenuItem newItem = new JMenuItem("新建");
        JMenuItem openItem = new JMenuItem("打开");
        JMenuItem setWindow = new JMenuItem("设置Frame");
        JMenuItem exitItem = new JMenuItem("退出");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        exitItem.addActionListener(e -> System.exit(0));

        editMenu.add(setWindow);
        setWindow.addActionListener(e -> {
            JFrame frame = new JFrame();

        });

        menu.add(fileMenu);
        menu.add(editMenu);

        window.setJMenuBar(menu);
    }

    private static int moveSpeed = 12;
    public static void bindWASD() {
        boolean[] keys = new boolean[4];
        Timer timer = new Timer(16, e -> {
            if(!canvas.hasFocus()) return;
            if (keys[0]) {
                currentFrame.camera.y -= moveSpeed;
                if(selected != null) selected.getComponent().setLocation(lastLeft.x -currentFrame.insets.left-offset.x + currentFrame.camera.x,lastLeft.y-currentFrame.insets.top-offset.y + currentFrame.camera.y);
                canvas.repaint();
            }
            if (keys[1]) {
                currentFrame.camera.x -= moveSpeed;
                if(selected != null) selected.getComponent().setLocation(lastLeft.x -currentFrame.insets.left-offset.x + currentFrame.camera.x,lastLeft.y-currentFrame.insets.top-offset.y + currentFrame.camera.y);
                canvas.repaint();
            }
            if (keys[2]) {
                currentFrame.camera.y += moveSpeed;
                if(selected != null) selected.getComponent().setLocation(lastLeft.x -currentFrame.insets.left-offset.x + currentFrame.camera.x,lastLeft.y-currentFrame.insets.top-offset.y + currentFrame.camera.y);
                canvas.repaint();
            }
            if (keys[3]) {
                currentFrame.camera.x += moveSpeed;
                if(selected != null) selected.getComponent().setLocation(lastLeft.x -currentFrame.insets.left-offset.x + currentFrame.camera.x,lastLeft.y-currentFrame.insets.top-offset.y + currentFrame.camera.y);
                canvas.repaint();
            }
        });
        timer.start();
        String[] keyNames = {"W","A","S","D"};
        for (int i = 0; i < 4; i++) {
            final int idx = i;
            canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(keyNames[i]), "pressed" + keyNames[i]);
            canvas.getActionMap().put("pressed" + keyNames[i], new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keys[idx] = true;
                }
            });

            canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke("released " + keyNames[i]), "released" + keyNames[i]);
            canvas.getActionMap().put("released" + keyNames[i], new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keys[idx] = false;
                }
            });
        }
    }

    public static void repaint(){
        canvas.repaint();
    }
}
