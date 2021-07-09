package GamePackage;
import javax.swing.*;
import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Timer;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Дима
 */
public class Game extends javax.swing.JFrame implements MouseListener {
    
    
//  Units
    //Pink panther TODO list
    OrcsFortress
        OrcBase1 = new OrcsFortress();
    /*GoldMine
            GoldMine1 = new GoldMine();*/
        
////////////////////////////////////////////////////////////////////////////////    
    
    Image
        GrassTextureMap = getToolkit().getImage("GrassTexture.png").getScaledInstance(29, 29, WIDTH),
        RockTextureMap = getToolkit().getImage("RockTextureMap.png").getScaledInstance(29, 29, WIDTH),
        WaveIcon = getToolkit().getImage("Wave.png").getScaledInstance(24, 24, WIDTH),
        GoldIcon = getToolkit().getImage("Gold.png").getScaledInstance(24, 24, WIDTH),
        WoodIcon = getToolkit().getImage("Wood.png").getScaledInstance(24, 24, WIDTH),
        FoodIcon = getToolkit().getImage("Food.png").getScaledInstance(24, 24, WIDTH);
        
    Map 
        GameMap = new Map();
    
    
    boolean
        FirstPressed = false,
        SearchX = true, 
        SearchY = true,
        Repaint = true;
    
    int
        EnemyWave = 0,
        MouseX = 0,
        MouseY = 0,
        MapX = 0, MapY = 0, CheckX = 0, CheckY = 0, TEST = 0;

    Player
        TheHuman = new Player(1000, 200, 0),
        players[]=new Player[2];
    
    // MOUSE X & Y COORDINATE
    
    
    //GUI
    GameEntity selected;
    
    Timer t=new Timer();
    
    private class GameProcess extends TimerTask
    {
        @Override
        public void run() {
            for(Player p:players)
                for(GameEntity e:p.PlayerEntity)
                {
                    e.action();
                }
            repaint();
        }
    }
    
//    Graphics graphics = getGraphics(); 
    
   // Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor")
    /**
     * Creates new form NewJFrame
     */
    @SuppressWarnings("unchecked")
    public Game() {
       initComponents();
       setLocationRelativeTo(null);
       this.addMouseListener(this);
       jTextField7.setVisible(false);
       
       // Image OrcFortressImage = getToolkit().getImage("OrcFortressLabel.jpg").getScaledInstance(88, 88, WIDTH);
       //Image OrcFortressImage1 = getToolkit().getImage("OrcFortress.png").getScaledInstance(50, 50, WIDTH);
  
       //    jButton1.setIcon(new ImageIcon(OrcFortressImage1));
//        jLabel7.setVisible(false);
        jLabel3.setIcon(new ImageIcon(WaveIcon));
        jLabel4.setIcon(new ImageIcon(GoldIcon));
        jLabel5.setIcon(new ImageIcon(WoodIcon));
        jLabel6.setIcon(new ImageIcon(FoodIcon));
        
        jTextField3.setText(String.valueOf(EnemyWave));
        jTextField4.setText(String.valueOf(TheHuman.Gold));
        jTextField5.setText(String.valueOf(TheHuman.Wood));
        jTextField6.setText(String.valueOf(TheHuman.UsedFood) + "/" + String.valueOf(TheHuman.FoodLimit + OrcBase1.InitialFoodLimit));



        int x = 4,y = 28;
        
        for (int i = 0; i<25; i++){
            GameMap.MapX[i] = x;
            System.out.print(GameMap.MapX[i] + "\t");
            x+=30;
        }

        players[0]=Player.Neutrals;
        players[1]=
                TheHuman;
        
        for (int i = 0; i<25; i++){ // 
            GameMap.MapY[i] = (GameMap.MapX[i] - 4) + 28;
            System.out.print(GameMap.MapY[i] + "\t");
        }
        
        new GameEntity(OrcBase1, GameMap, TheHuman, 3,7);
        GoldMine.createGoldMine(GameMap,8,12,25000);
        Tree.createTree(GameMap,5,12,20);
        Tree.createTree(GameMap,6,12,20);
        OrcGrunt.createOrcGrunt(GameMap, TheHuman, 14, 10);
        OrcPeon.createOrcPeon(GameMap, TheHuman, 5, 10);



        t.scheduleAtFixedRate(new GameProcess(), 0, 100);
    //   TEST SELECT ORC FORTRESS UNITS
    
//     final Timer time = new Timer();
//
//        time.schedule(new TimerTask() {
//            int i = 0;
//            @Override
//            public void run() { //ПЕРЕЗАГРУЖАЕМ МЕТОД RUN В КОТОРОМ ДЕЛАЕТЕ ТО ЧТО ВАМ НАДО
//                if(i>=2){
//                    System.out.println("Таймер завершил свою работу");
//                    time.cancel();
//                    return;
//                }
//                System.out.println("Прошло 4 секунды");
//                i = i + 1;
//            }
//        }, 4000, 4000);

        
        
    
    //  setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor")); // Set Custom Cursor
       setIconImage(Toolkit.getDefaultToolkit().getImage("icon.gif")); // Game Icon
    }
    
    
    public void Select(int SelectX,int SelectY){
       
    /*if (GameMap.EmploymentMap[SelectY][SelectX] == false){
        System.out.println("Empty");
    }
    else{
        CheckWho = GameMap.WhoIsTheFieldOccupied[SelectY][SelectX];
        if (CheckWho.equals("OrcBase1")) {
                System.out.println(OrcBase1.name);
                jButton7.setIcon(OrcBase1.IconPanel);
                jTextField1.setText(OrcBase1.name);
                jTextField2.setText("Hit Points: " + OrcBase1.HitPoints + "/" + OrcBase1.HitPointsConst);
                jLabel1.setIcon(OrcBase1.IconLabel);
            }
        }*/
    selected=GameMap.GroundLevel[SelectX][SelectY];
    paintUI();
    }

    
    
    public void paint(Graphics g) {
    Graphics2D gr2d = (Graphics2D) g;
    BufferedImage bufferedImage=(BufferedImage)createImage(755,778);
    Graphics2D bufGraphics=bufferedImage.createGraphics();

            ///////////////////////////

       /* public void update(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            if (firstTime) {
                Dimension dim = getSize();
                int w = dim.width;
                int h = dim.height;
                area = new Rectangle(dim);
                bi = (BufferedImage) createImage(w, h);
                big = bi.createGraphics();
                rect.setLocation(w / 2 - 50, h / 2 - 25);
                big.setStroke(new BasicStroke(8.0f));
                firstTime = false;
            }

            big.setColor(Color.white);
            big.clearRect(0, 0, area.width, area.height);

            big.setPaint(Color.red);
            big.draw(rect);
            big.setPaint(Color.blue);
            big.fill(rect);

            g2.drawImage(bi, 0, 0, this);
        }*/

        /////////////////


                
    // TEST MAP
    bufGraphics.setPaint(Color.green);
    bufGraphics.setStroke(new BasicStroke(1.0f));
    
    int x = 4,y = 28,size = 30,hight = 30;
        for (int i = 0; i<25; i++){
            for (int j=0; j<25; j++){
//              gr2d.drawRect(x,y,size,hight);
                bufGraphics.drawImage(GrassTextureMap, x+j*30, y+i*30, this);
//                    gr2d.drawImage(WaveIcon, x, y, this);
//                }
            //x+=30;

        }
//  System.out.println(y);
        x = 4;
        //y+=30;
        }

        for (int i = 0; i<25; i++){
            for (int j=0; j<25; j++){
                if(GameMap.GroundLevel[j][i]!=null) {
                    GameEntity e=GameMap.GroundLevel[j][i];
                    if(j==e.x && i==e.y) {
                        bufGraphics.drawImage(GameMap.GroundLevel[j][i].origin.ImageMap,
                                x + j * 30 + GameMap.GroundLevel[j][i].moveOffsetX(),
                                y + i * 30 + GameMap.GroundLevel[j][i].moveOffsetY(),
                                30*e.origin.width,30*e.origin.height, this);
                    }
                }
            }
            x = 4;
        }
        gr2d.drawImage(bufferedImage,0,0,this);
        paintUI();
    }
    private void paintUI()
    {
        if(selected!=null)
        {
        //System.out.println(selected);
        GamePrototype p=selected.origin;
        //CheckWho = GameMap.WhoIsTheFieldOccupied[SelectY][SelectX];
        //System.out.println(p.name);
        jButton7.setIcon(p.IconPanel);
        if(!jTextField1.getText().equals(p.name))
            jTextField1.setText(p.name);
        String stringHP="Hit Points: " + selected.HitPoints + "/" + p.HitPointsMax;
        if(!jTextField2.getText().equals(stringHP))
            jTextField2.setText(stringHP);
        jLabel1.setIcon(p.IconLabel);
        jButton5.setIcon(GamePrototype.icons.get("PeonBuildStructure.png"));
        }
        else
        {
            if(!jTextField1.getText().equals("nothing"))
                jTextField1.setText("nothing");
            jTextField2.setText("");
             jLabel1.setIcon(null);
             jButton5.setIcon(null);
             
        }
        jTextField4.setText(String.valueOf(TheHuman.Gold));
        jTextField5.setText(String.valueOf(TheHuman.Wood));
    }
    
    
    public void TEST() throws InterruptedException{
        
            
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new java.awt.Panel();
        jTextField7 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        panel2 = new java.awt.Panel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        panel3 = new java.awt.Panel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Orcs & Humans");
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor"));
        setPreferredSize(new java.awt.Dimension(760, 917));
        setResizable(false);

        panel1.setBackground(new java.awt.Color(102, 0, 0));
        panel1.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor"));

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(153, 0, 0));
        jTextField7.setFont(new java.awt.Font("Ravie", 0, 12)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(153, 255, 0));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setToolTipText("");
        jTextField7.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        jLabel1.setBackground(new java.awt.Color(51, 255, 51));
        jLabel1.setForeground(new java.awt.Color(51, 255, 51));
        jLabel1.setToolTipText("");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(153, 0, 0));
        jTextField1.setFont(new java.awt.Font("Ravie", 0, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(153, 255, 0));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setToolTipText("");
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(153, 0, 0));
        jTextField2.setFont(new java.awt.Font("Ravie", 0, 12)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(153, 255, 0));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setToolTipText("");
        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        panel2.setBackground(new java.awt.Color(51, 102, 0));
        panel2.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor"));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton5.setBackground(new java.awt.Color(0, 0, 0));
        jButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton7.setBackground(new java.awt.Color(0, 0, 0));
        jButton7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(0, 0, 0));
        jButton8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel3.setBackground(new java.awt.Color(102, 102, 102));
        panel3.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor"));
        panel3.setName(""); // NOI18N

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Ravie", 2, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 255, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Information");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Ravie", 2, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(247, 238, 47));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setToolTipText("");
        jLabel3.setMaximumSize(new java.awt.Dimension(37, 17));
        jLabel3.setMinimumSize(new java.awt.Dimension(37, 17));
        jLabel3.setPreferredSize(new java.awt.Dimension(37, 17));

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(51, 51, 51));
        jTextField3.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(247, 238, 47));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setBorder(new javax.swing.border.MatteBorder(null));
        jTextField3.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor"));
        jTextField3.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        jTextField3.setPreferredSize(new java.awt.Dimension(42, 24));
        jTextField3.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTextField3.setSelectionColor(new java.awt.Color(0, 255, 0));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(51, 51, 51));
        jTextField4.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(247, 238, 47));
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setBorder(new javax.swing.border.MatteBorder(null));
        jTextField4.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor"));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(51, 51, 51));
        jTextField5.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(247, 238, 47));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setBorder(new javax.swing.border.MatteBorder(null));
        jTextField5.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor"));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(51, 51, 51));
        jTextField6.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(247, 238, 47));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setBorder(new javax.swing.border.MatteBorder(null));
        jTextField6.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("OrcHandCursor.png").getImage(),new Point(0,0),"custom cursor"));

        jLabel4.setFont(new java.awt.Font("Ravie", 2, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(247, 238, 47));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setToolTipText("");
        jLabel4.setMaximumSize(new java.awt.Dimension(37, 17));
        jLabel4.setMinimumSize(new java.awt.Dimension(37, 17));
        jLabel4.setPreferredSize(new java.awt.Dimension(37, 17));

        jLabel5.setFont(new java.awt.Font("Ravie", 2, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setToolTipText("");
        jLabel5.setMaximumSize(new java.awt.Dimension(51, 21));
        jLabel5.setMinimumSize(new java.awt.Dimension(51, 21));
        jLabel5.setPreferredSize(new java.awt.Dimension(51, 21));

        jLabel6.setFont(new java.awt.Font("Ravie", 2, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(193, 154, 107));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setToolTipText("");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField6)))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, 0)
                                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField4.getAccessibleContext().setAccessibleDescription("");
        jTextField5.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField7))
                .addGap(2, 2, 2)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(784, Short.MAX_VALUE)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
        if(selected!=null) 
        {
            if(!selected.inflictDamage(100))
                selected=null;
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        repaint();
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private java.awt.Panel panel3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("Mouse " + e.getClickCount()  +  " times clicked at " + e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("Mouse " + e.getClickCount() + " times pressed at " + e.getPoint());
     
    SearchX = true; 
    SearchY = true;
    MouseX = 0;
    MouseY = 0;
    MapX = 0; 
    MapY = 0;
    CheckX = 0; 
    CheckY = 0;
    
    MouseX = e.getX();
    MouseY = e.getY();
    
    System.out.println();
    int SelectY=-1, SelectX=-1;
    
    if(e.getButton() == 1 || e.getButton() == 3)
    {
        SelectX=(MouseX-3)/30;
        SelectY=(MouseY-27)/30;
    
    }
    if(e.getButton() == 1)
        Select(SelectX,SelectY);
    if(e.getButton() == 3)
    {
        if(selected!=null)
        {
            if(selected.origin.ticksToMove>0)
            {
                GameMap.setPath(selected,SelectX, SelectY);
                selected.path=GameMap.createPath(SelectX, SelectY);
                selected.tx=SelectX;
                selected.ty=SelectY;
                /*for(int j=0;j<25;++j)
                {
                    for (int i = 0; i < 25; ++i)
                        System.out.print(pathLength[i][j] + "\t");
                    System.out.println();
                }*/
            }
        }
    }
    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("Mouse " + e.getClickCount() + " times released at " + e.getPoint());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println("Mouse entered at " + e.getPoint());
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("Mouse exited at " + e.getPoint());
    }

//    @SuppressWarnings("SleepWhileInLoop")
//    public void Redraw() throws InterruptedException{
//        
//        while(true){
//            Thread.sleep(2);
//            OrcBase1.HitPoints -= 100;
//            jTextField2.setText("Hit Points: " + OrcBase1.HitPoints + "/" + OrcBase1.HitPointsConst);
//            repaint();
//            if(OrcBase1.HitPoints <= 0){
//                repaint();
//                
//            }
//        }
//    }
}
