package Game2048;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

class ScoreBoard extends JPanel{
    int score=0;
    int maxNumber=0;
    int moves=0;
    JLabel scoreLabel;
    JLabel maxNumberLabel;
    JLabel movesLabel;
    JLabel winLabel;
    JLabel overLabel;
    public ScoreBoard(int score,int maxNumber) {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setFocusable(false);

        this.init(score,maxNumber,0);
        this.updateScoreBoardContent();
    }
    public void updateScoreBoardContent() {
        scoreLabel.setText(String.format("Score: %d",score));
        maxNumberLabel.setText(String.format("MaxNumber: %d",maxNumber));
        movesLabel.setText(String.format("Moves: %d",moves));
        repaint();
    }
    public void init(int score,int maxNumber,int moves)
    {
        this.score=score;
        this.maxNumber=maxNumber;
        this.moves=moves;
        this.removeAll();
        scoreLabel=new JLabel(String.format("Score: %d",score),JLabel.CENTER);
        scoreLabel.setFont(new Font("Times New Roman",Font.PLAIN,24));
        maxNumberLabel=new JLabel(String.format("MaxNumber: %d",maxNumber),JLabel.CENTER);
        maxNumberLabel.setFont(new Font("Times New Roman",Font.PLAIN,24));
        movesLabel=new JLabel(String.format("Moves: %d",moves),JLabel.CENTER);
        movesLabel.setFont(new Font("Times New Roman",Font.PLAIN,24));

        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        maxNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        movesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        winLabel=new JLabel("Level Reached!",JLabel.CENTER);
        winLabel.setFont(new Font("Times New Roman",Font.ITALIC,24));
        winLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        overLabel=new JLabel("Game Over!",JLabel.CENTER);
        overLabel.setFont(new Font("Times New Roman",Font.BOLD,24));
        overLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(maxNumberLabel);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(movesLabel);
    }
    public void setScore(int score) {
        this.score=score;
        this.updateScoreBoardContent();
    }
    public void setMaxNumber(int maxNumber) {
        this.maxNumber=maxNumber;
        this.updateScoreBoardContent();
    }
    public void setMoves(int moves) {
        this.moves=moves;
        this.updateScoreBoardContent();
    }
    public void setWin() {
        add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(winLabel);
    }
    public void setGameOver() {
        add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(overLabel);
    }
}

public class GameUI extends JFrame {
    private int gcSize;
    private final GameCore gameCore;
    private final JPanel gamePad;
    private final ScoreBoard scoreBoard;
    private final JLabel currentSizeLabel;
    private final JLabel currentLevelLabel;
    private int sizeToSet=4;
    private int levelToSet=5;
    private static final Map<Integer,Color> colorMap=new HashMap<>();
    static {
        colorMap.put(0,Color.WHITE);
        colorMap.put(1, new Color(255, 255, 255)); // 2^0
        colorMap.put(2, new Color(238, 228, 218)); // 2^1
        colorMap.put(4, new Color(237, 224, 200)); // 2^2
        colorMap.put(8, new Color(242, 177, 121)); // 2^3
        colorMap.put(16, new Color(245, 149, 99)); // 2^4
        colorMap.put(32, new Color(246, 124, 95)); // 2^5
        colorMap.put(64, new Color(246, 94, 59)); // 2^6
        colorMap.put(128, new Color(237, 207, 114)); // 2^7
        colorMap.put(256, new Color(237, 204, 97)); // 2^8
        colorMap.put(512, new Color(237, 200, 80)); // 2^9
        colorMap.put(1024, new Color(237, 197, 63)); // 2^10
        colorMap.put(2048, new Color(237, 194, 46)); // 2^11
        colorMap.put(4096, new Color(237, 190, 30)); // 2^12
        colorMap.put(8192, new Color(237, 187, 14)); // 2^13
        colorMap.put(16384, new Color(237, 183, 7)); // 2^14
        colorMap.put(32768, new Color(220, 170, 7)); // 2^15
        colorMap.put(65536, new Color(204, 156, 7)); // 2^16
        colorMap.put(131072, new Color(189, 143, 7)); // 2^17
        colorMap.put(262144, new Color(173, 130, 7)); // 2^18
        colorMap.put(524288, new Color(158, 117, 7)); // 2^19
        colorMap.put(1048576, new Color(142, 104, 7)); // 2^20
        colorMap.put(2097152, new Color(127, 91, 7)); // 2^21
        colorMap.put(4194304, new Color(111, 78, 7)); // 2^22
        colorMap.put(8388608, new Color(96, 65, 7)); // 2^23
        colorMap.put(16777216, new Color(80, 52, 7)); // 2^24
        colorMap.put(33554432, new Color(65, 39, 7)); // 2^25
        colorMap.put(67108864, new Color(49, 26, 7)); // 2^26
        colorMap.put(134217728, new Color(34, 13, 7)); // 2^27
        colorMap.put(268435456, new Color(18, 16, 7)); // 2^28
        colorMap.put(536870912, new Color(9, 10, 7)); // 2^29
        colorMap.put(1073741824, new Color(4, 7, 1)); // 2^30
    }
    public GameUI(GameCore gameCore) {
        setTitle("2048Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp=getContentPane();
        cp.setLayout(new BoxLayout(cp,BoxLayout.X_AXIS));
        if(cp instanceof JPanel){
            ((JPanel)cp).setBorder(new EmptyBorder(5,5,5,5));
        }
        setSize(new Dimension(800,600));
        setResizable(false);

        this.gameCore=gameCore;
        this.gameCore.gameUI=this;
        gcSize= gameCore.getSize();

        //Game Pad
        gamePad=new JPanel(new GridLayout(gcSize,gcSize));
        gamePad.setSize(550,600);
        gamePad.setMinimumSize(new Dimension(550,600));
        gamePad.setMaximumSize(new Dimension(550,600));
        gamePad.setPreferredSize(new Dimension(550,600));
        JPanel[] numPanel = new JPanel[gcSize*gcSize];
        JLabel[] numLabel = new JLabel[gcSize*gcSize];
        for(int i=0;i<gcSize*gcSize;i++)
        {
            numPanel[i]=new JPanel();
            numPanel[i].setLayout(new BorderLayout());
            numPanel[i].setBackground(Color.WHITE);
            numPanel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

            numLabel[i]=new JLabel("",JLabel.CENTER);
            numLabel[i].setFont(new Font("Serif",Font.BOLD,24));
            numLabel[i].setSize(16,16);

            numPanel[i].add(numLabel[i],BorderLayout.CENTER);
            gamePad.add(numPanel[i]);
        }
        add(gamePad);

        JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel,BoxLayout.Y_AXIS));
        componentPanel.add(Box.createVerticalGlue());
        //Score Board
        scoreBoard = new ScoreBoard(0,0);
        componentPanel.add(scoreBoard);
        componentPanel.add(Box.createVerticalGlue());

        //Settings Panel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel,BoxLayout.Y_AXIS));

        JButton restartButton=new JButton("Restart");
        restartButton.setBackground(new Color(200,200,200));
        restartButton.setPreferredSize(new Dimension(250,20));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> {
            gameCore.start(sizeToSet,levelToSet,null);
            gcSize=gameCore.getSize();
            gamePadInit();
            updateGamePadContent();
            scoreBoardInit();
            updateScoreBoardContent();
            updateSettingsPanelContent();
            GameUI.this.requestFocus();
        });

        JPanel sizePanel=new JPanel();
        sizePanel.setLayout(new BoxLayout(sizePanel,BoxLayout.X_AXIS));
        sizePanel.setPreferredSize(new Dimension(250,20));
        sizePanel.setMaximumSize(new Dimension(250,20));
        sizePanel.setMinimumSize(new Dimension(250,20));

        JLabel sizeLabel=new JLabel("Size:");
        sizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizePanel.add(Box.createHorizontalGlue());
        sizePanel.add(sizeLabel);

        ComboBoxModel<Integer> comboModel=new DefaultComboBoxModel<>(new Integer[]{3,4,5,6,7,8});
        JComboBox<Integer> sizeComboBox=new JComboBox<>(comboModel);
        sizeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeComboBox.setSelectedIndex(1);
        sizeComboBox.addActionListener(e -> sizeToSet=sizeComboBox.getSelectedIndex()+3);
        sizePanel.add(sizeComboBox);
        sizePanel.add(Box.createHorizontalGlue());
        sizePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel levelPanel=new JPanel();
        levelPanel.setLayout(new BoxLayout(levelPanel,BoxLayout.X_AXIS));
        levelPanel.setPreferredSize(new Dimension(250,20));
        levelPanel.setMaximumSize(new Dimension(250,20));
        levelPanel.setMinimumSize(new Dimension(250,20));

        JLabel levelLabel=new JLabel("Level:");
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelPanel.add(Box.createHorizontalGlue());
        levelPanel.add(levelLabel);

        SpinnerNumberModel numberModel = new SpinnerNumberModel(6, 6, 30, 1);
        JSpinner levelSpinner = new JSpinner(numberModel);
        levelSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelSpinner.setBackground(new Color(200,200,200));
        levelSpinner.addChangeListener(e -> levelToSet=(int)levelSpinner.getValue());

        levelPanel.add(levelSpinner);
        levelPanel.add(Box.createHorizontalGlue());
        levelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        currentSizeLabel = new JLabel(String.format("Current Size: %d",gameCore.getSize()),JLabel.CENTER);
        currentSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentLevelLabel = new JLabel(String.format("Current Level: %d",gameCore.getLevel()));
        currentLevelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(restartButton);
        settingsPanel.add(currentSizeLabel);
        settingsPanel.add(Box.createHorizontalGlue());
        settingsPanel.add(sizePanel);
        settingsPanel.add(Box.createHorizontalGlue());
        settingsPanel.add(currentLevelLabel);
        settingsPanel.add(Box.createHorizontalGlue());
        settingsPanel.add(levelPanel);

        componentPanel.add(settingsPanel);
        componentPanel.add(Box.createVerticalGlue());

        add(componentPanel);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        gameCore.move(GameCore.DIRECTION.UP);
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        gameCore.move(GameCore.DIRECTION.DOWN);
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        gameCore.move(GameCore.DIRECTION.LEFT);
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        gameCore.move(GameCore.DIRECTION.RIGHT);
                        break;
                }
                updateGamePadContent();
                updateScoreBoardContent();
            }
        });
        updateGamePadContent();
        updateScoreBoardContent();

        setFocusTraversalPolicy(new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter(Container aContainer, Component aComponent) {
                if(aComponent==GameUI.this) return restartButton;
                if(aComponent==restartButton) return sizeComboBox;
                if(aComponent==sizeComboBox) return levelSpinner;
                return GameUI.this;
            }
            @Override
            public Component getComponentBefore(Container aContainer, Component aComponent) {
                if(aComponent==restartButton) return GameUI.this;
                if(aComponent==sizeComboBox) return restartButton;
                if(aComponent==levelSpinner) return sizeComboBox;
                if(aComponent==GameUI.this) return restartButton;
                return GameUI.this;
            }
            @Override
            public Component getDefaultComponent(Container aContainer) {
                return GameUI.this;
            }
            @Override
            public Component getLastComponent(Container aContainer) {
                return GameUI.this;
            }
            @Override
            public Component getFirstComponent(Container aContainer) {
                return GameUI.this;
            }
        });
    }
    private void gamePadInit()
    {
        gamePad.removeAll();
        gamePad.setLayout(new GridLayout(gcSize,gcSize));
        JPanel[] numPanel = new JPanel[gcSize*gcSize];
        JLabel[] numLabel = new JLabel[gcSize*gcSize];
        for(int i=0;i<gcSize*gcSize;i++)
        {
            numPanel[i]=new JPanel();
            numPanel[i].setLayout(new BorderLayout());
            numPanel[i].setBackground(Color.WHITE);
            numPanel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

            numLabel[i]=new JLabel("",JLabel.CENTER);
            numLabel[i].setFont(new Font("Serif",Font.BOLD,24));
            numLabel[i].setSize(16,16);

            numPanel[i].add(numLabel[i],BorderLayout.CENTER);
            gamePad.add(numPanel[i]);
        }
    }
    private void scoreBoardInit(){
        scoreBoard.init(0,0,0);
    }
    private void updateGamePadContent() {
        int cnt=0;
        for(Component comp: gamePad.getComponents()){
            if(comp instanceof JPanel){
                for(Component lb:((JPanel)comp).getComponents()){
                    if(lb instanceof JLabel){
                        int num = gameCore.getNumAt(cnt/gcSize,cnt%gcSize);
                        comp.setBackground(colorMap.get(num));
                        if(num!=0)
                            ((JLabel) lb).setText(String.valueOf(num));
                        else
                            ((JLabel) lb).setText("");
                        cnt++;
                    }
                }
            }
        }
        gamePad.repaint();
    }
    private void updateScoreBoardContent() {
        scoreBoard.setScore(gameCore.getScore());
        scoreBoard.setMaxNumber(gameCore.getMaxNumber());
        scoreBoard.setMoves(gameCore.getMoves());
    }
    private void updateSettingsPanelContent() {
        currentLevelLabel.setText(String.format("Current Level: %d",gameCore.getLevel()));
        currentSizeLabel.setText(String.format("Current Size: %d",gameCore.getSize()));
    }
    public void gameWin()
    {
        scoreBoard.setWin();
    }
    public void gameOver()
    {
        updateScoreBoardContent();
        updateGamePadContent();
        scoreBoard.setGameOver();
        Object[] options = {"Restart", "Quit"};
        int res = JOptionPane.showOptionDialog(this,String.format("Game Over!\nYou %s At Level %d!\nYou achieved Score %d and Max Number %d in %d Moves!"
                ,gameCore.getWin()?"Win":"Lose",gameCore.getLevel(),gameCore.getScore(),gameCore.getMaxNumber(),gameCore.getMoves()),"Game Over!",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
        if(res == JOptionPane.OK_OPTION)
        {
            gameCore.start(sizeToSet,levelToSet,null);
            gcSize=gameCore.getSize();
            gamePadInit();
            updateGamePadContent();
            scoreBoardInit();
            updateScoreBoardContent();
            updateSettingsPanelContent();
            GameUI.this.requestFocus();
        }
        else{
            System.exit(0);
        }
    }
}