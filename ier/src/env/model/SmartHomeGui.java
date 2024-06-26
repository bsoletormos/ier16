package src.env.model;

import jason.asSyntax.Literal;
import src.env.SmartHomeEnvironment;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class SmartHomeGui implements ActionListener, ChangeListener, Observer {
    private static src.env.SmartHomeEnvironment environment;
    private int timeCounter = 0;
    private Timer timer;
    private JTextField time;
    private JFrame frame;
    private JPanel topLeftPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;
    private JSlider[] slidersForRight;
    private JPanel counterPanel;
    private JLabel counterLabel;
    private JButton incrementButton;
    private JButton decrementButton;
    private int counter;
    private JTextField[] textsForDown;

    private JTextField[] textsForRight;
    JRadioButton sunnyButton = new JRadioButton("Sunny");
    JRadioButton rainyButton = new JRadioButton("Rainy");
    JRadioButton extraWindyButton = new JRadioButton("Extra Windy");
    JRadioButton clouyButton = new JRadioButton("Cloudy");
    JRadioButton foggyButton = new JRadioButton("Foggy");
    JRadioButton trueButton = new JRadioButton("true");
    JRadioButton falseButton = new JRadioButton("false");
    ImageIcon door_closed = new ImageIcon("pics/door_closed.png");
    ImageIcon curtains_closed = new ImageIcon("pics/curtains_closed.png");
    ImageIcon curtains_open = new ImageIcon("pics/curtains_open.png");
    ImageIcon curtains_halfopen = new ImageIcon("pics/curtains_halfopen.png");
    ImageIcon window_open = new ImageIcon("pics/window_open.png");
    ImageIcon light_off = new ImageIcon("pics/light_off.png");
    ImageIcon light_on = new ImageIcon("pics/light_on.png");
    ImageIcon wall = new ImageIcon("pics/wall.png");
    ImageIcon wallrotated = new ImageIcon("pics/wallrotated.png");
    ImageIcon corner_ul = new ImageIcon("pics/corner_ul.png");
    ImageIcon corner_dr = new ImageIcon("pics/corner_dr.png");
    ImageIcon corner_dl = new ImageIcon("pics/corner_dl.png");
    ImageIcon corner_ur = new ImageIcon("pics/corner_ur.png");
    ImageIcon window_closed = new ImageIcon("pics/window_closed.png");
    ImageIcon door_open = new ImageIcon("pics/door_open.png");
    ImageIcon ac_off = new ImageIcon("pics/ac_off.png");
    ImageIcon ac_notworking = new ImageIcon("pics/ac_notworking.png");
    ImageIcon ac_on = new ImageIcon("pics/ac_on.png");

    private JLabel[] leftLabels;

    public SmartHomeGui(src.env.SmartHomeEnvironment environment) {
        // JFrame létrehozása
        frame = new JFrame("SmartHomeGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new GridBagLayout());
        this.environment = environment;


        // Bal felső panel létrehozása 8x8-as GridLayout-tal


        initWest();
        initEast();
        initSouth();


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Bal felső panel hozzáadása a JFrame-hez (WEST pozícióba)
        frame.add(topLeftPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        // Jobb oldali panel hozzáadása a JFrame-hez (EAST pozícióba)
        frame.add(rightPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        // Alsó panel hozzáadása a JFrame-hez (SOUTH pozícióba)
        frame.add(bottomPanel, gbc);

        // JFrame megjelenítése
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // GUI alkalmazás indítása
        SmartHomeEnvironment environment = new SmartHomeEnvironment();
        environment.init(new String[]{});  // Inicializáljuk a környezetet
        new SmartHomeGui(environment);
    }

    public void initWest() {
        topLeftPanel = new JPanel(new GridLayout(7, 7));
        leftLabels = new JLabel[49];
        for (int i = 0; i < 49; i++)
            leftLabels[i] = new JLabel();
        leftLabels[0].setIcon(corner_ul);
        for (int i = 1; i < 6; i++) {
            leftLabels[i].setIcon(wall);
            int k = 48 - i;
            leftLabels[k].setIcon(wall);

        }
        for (int i = 7; i < 49; i += 7) {
            leftLabels[i].setIcon(wallrotated);
            int k = i - 1;
            leftLabels[k].setIcon(wallrotated);
        }

        leftLabels[6].setIcon(corner_ur);
        leftLabels[42].setIcon(corner_dl);
        leftLabels[48].setIcon(corner_dr);
        leftLabels[3].setIcon(window_open);
        leftLabels[10].setIcon(curtains_open);
        leftLabels[27].setIcon(ac_on);
        leftLabels[45].setIcon(door_open);
        leftLabels[24].setIcon(light_off);
        for (int i = 0; i < 49; i++) {
            topLeftPanel.add(leftLabels[i]);
        }
    }

    //Külső és belső hőmérséklet mutató
    //Napszak irányító
//Milyen idő van kint
//Hány ember van házban
//Elromlott légkondi
    public void initSouth() {
        textsForDown = new JTextField[5];
        textsForDown[0] = new JTextField("Temperature inside");
        textsForDown[1] = new JTextField();
        textsForDown[1].addActionListener(this);
        textsForDown[2] = new JTextField("AC on");
        textsForDown[3] = new JTextField("on");
        textsForDown[3].addActionListener(this);

        bottomPanel = new JPanel(new GridLayout(2, 4));
        for (int i = 0; i < 4; i++)
            bottomPanel.add(textsForDown[i]);

    }

    public void initEast() {
        slidersForRight = new JSlider[2];
        textsForRight = new JTextField[5];

        time = new JTextField(10);
        time.setEditable(false);
        time.setText(String.valueOf(timeCounter));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeCounter = (timeCounter + 1) % 25; // Növeljük a számlálót és visszaállítjuk 0-ra, ha eléri a 25-öt
                time.setText(String.valueOf(timeCounter)); // Frissítjük a JTextField-t
                environment.addPercept(Literal.parseLiteral("time("+ timeCounter+ ")"));
            }
        });
        timer.start();
        rightPanel = new JPanel(new GridLayout(5, 2));
        for (int i = 0; i < 5; i++) {
            textsForRight[i] = new JTextField();
        }
        textsForRight[0].setText("Time(0-24)");
        textsForRight[1].setText("Outside temperature");
        textsForRight[2].setText("Weather");
        textsForRight[3].setText("Number of people");
        textsForRight[4].setText("AC working");
        for (int i = 0; i < 2; i++) {
            rightPanel.add(textsForRight[i]);
            rightPanel.add(slidersForRight[i]);
        }
        rightPanel.add(textsForRight[0]);
        rightPanel.add(time);
        rightPanel.add(textsForRight[1]);
        rightPanel.add(slidersForRight[1]);

        JPanel multiChoicePanel = new JPanel(new BorderLayout());

        JPanel radioPanel = new JPanel(new GridLayout(3, 2));
        ButtonGroup group = new ButtonGroup();

        sunnyButton.addActionListener(this);
        rainyButton.addActionListener(this);
        extraWindyButton.addActionListener(this);
        clouyButton.addActionListener(this);
        foggyButton.addActionListener(this);

        group.add(sunnyButton);
        group.add(rainyButton);
        group.add(extraWindyButton);
        group.add(clouyButton);
        group.add(foggyButton);
        radioPanel.add(sunnyButton);
        radioPanel.add(rainyButton);
        radioPanel.add(extraWindyButton);
        radioPanel.add(clouyButton);
        radioPanel.add(foggyButton);

        multiChoicePanel.add(radioPanel, BorderLayout.CENTER);
        rightPanel.add(textsForRight[2]);
        rightPanel.add(multiChoicePanel);
        rightPanel.add((textsForRight[3]));
        counterPanel = new JPanel();
        counterPanel.setLayout(new GridLayout(1, 3));

        // Counter label létrehozása
        counter = 0;
        counterLabel = new JLabel("0", SwingConstants.CENTER);

        // Increment és decrement gombok létrehozása
        incrementButton = new JButton("+");
        decrementButton = new JButton("-");

        // ActionListener-ek hozzáadása a gombokhoz
        incrementButton.addActionListener(this);
        decrementButton.addActionListener(this);

        // Gombok és label hozzáadása a panelhez
        counterPanel.add(decrementButton);
        counterPanel.add(counterLabel);
        counterPanel.add(incrementButton);
        rightPanel.add(counterPanel);

        rightPanel.add(textsForRight[4]);

        JPanel multiChoicePanel2 = new JPanel(new BorderLayout());

        JPanel radioPanel2 = new JPanel(new GridLayout(2, 1));
        ButtonGroup group2 = new ButtonGroup();
        group2.add(trueButton);
        group2.add(falseButton);

        falseButton.addActionListener(this);
        trueButton.addActionListener(this);

        radioPanel2.add(trueButton);
        radioPanel2.add(falseButton);
        multiChoicePanel2.add(radioPanel2, BorderLayout.CENTER);
        rightPanel.add(multiChoicePanel2);

    }



  /*  leftLabels[3].setIcon(window_closed);
    leftLabels[10].setIcon(curtains_closed);
    leftLabels[27].setIcon(ac_on);
    leftLabels[45].setIcon(door_closed);
    leftLabels[24].setIcon(light_on);*/
    @Override
    public void actionPerformed(ActionEvent e) {
        counterListener(e);
        radioPanel1Listener(e);
        radioPanel2Listener(e);
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof src.env.model.SmartHomeModel) {
            updateStatus();
        }

    }  /*  leftLabels[3].setIcon(window_closed);
    leftLabels[10].setIcon(curtains_closed);
    leftLabels[27].setIcon(ac_on);
    leftLabels[45].setIcon(door_closed);
    leftLabels[24].setIcon(light_on);*/
    private void updateStatus() {
        leftLabels[24].setIcon(environment.getModel().getDeviceState("lights_on") ? light_on : light_off);
       /* temperatureStatus.setText(String.valueOf(model.getTemperature()));
        windowStatus.setText(model.getDeviceState("window") ? "Open" : "Closed");
        doorStatus.setText(model.getDeviceState("door") ? "Open" : "Closed");
    */}
    public void counterListener(ActionEvent e){
        // Ellenőrzés, hogy melyik gomb lett megnyomva
        if (e.getSource() == incrementButton && counter != 1) {
            environment.addPercept(Literal.parseLiteral("Human(1)"));
            counter++;
        } else if (e.getSource() == decrementButton&& counter != 0) {
            environment.addPercept(Literal.parseLiteral("Human(0)"));

            counter--;
        }
        // Counter label frissítése
        counterLabel.setText(String.valueOf(counter));
    }
public void radioPanel1Listener(ActionEvent e){
        if(e.getSource() == sunnyButton){
            environment.setRainyWeather(true);


        }
        if(e.getSource() == rainyButton){
            environment.setRainyWeather(false);
        }
        if(e.getSource() == extraWindyButton){

        }
        if(e.getSource()== clouyButton){

        }
        if(e.getSource() == foggyButton){
        }
}
public void radioPanel2Listener(ActionEvent e){
        if (e.getSource()== trueButton){
            leftLabels[27].setIcon(ac_off);
        }
        if(e.getSource() == falseButton){
            leftLabels[27].setIcon(ac_notworking);
        }
}
    @Override
    public void stateChanged(ChangeEvent e) {

        int value = slidersForRight[1].getValue()/2;
        environment.addPercept(Literal.parseLiteral("outsideTemperature("+value+")"));
    }

}

