//Main.java
public class Main {
    public static void main(String[] args) {
        new EggOClockGUI();
    }
}

//EggOCLock.java
public class EggOClock {
    private String eggType;
    private int time;

    public void setEgg(String eggType, int time) {
        this.eggType = eggType;
        this.time = time;
    }

    public String getEggType() {
        return eggType;
    }

    public int getTime() {
        return time;
    }
}

//EggOClockGUI.java

import javax.swing.*;
import java.awt.*;

public class EggOClockGUI {
    JFrame frame;
    CardLayout cardLayout;
    JPanel mainPanel;

    EggOClock egg = new EggOClock();
    JLabel timerLabel;

    Color bgColor = new Color(255, 248, 200);
    Color buttonColor = new Color(255, 220, 130);

    public EggOClockGUI() {
        frame = new JFrame("🥚 Egg o'clock");
        frame.setSize(420, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(homePanel(), "home");
        mainPanel.add(optionPanel(), "options");
        mainPanel.add(timerPanel(), "timer");
        mainPanel.add(donePanel(), "done");

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    JPanel homePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel title = new JLabel("Set your egg timer now!");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton startBtn = new JButton("Start");
        startBtn.setBackground(buttonColor);
        startBtn.setFocusPainted(false);
        startBtn.setPreferredSize(new Dimension(150, 40));
        startBtn.addActionListener(e -> cardLayout.show(mainPanel, "options"));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(title, gbc);

        gbc.gridy = 1;
        panel.add(startBtn, gbc);

        return panel;
    }

    JPanel optionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel label = new JLabel("Select your egg type");
        label.setFont(new Font("Arial", Font.BOLD, 18));

        JButton soft = styledButton("Soft Boiled 🥚 (4 mins)");
        JButton medium = styledButton("Medium Boiled 🍳 (7 mins)");
        JButton hard = styledButton("Hard Boiled 🍘 (12 mins)");

        soft.addActionListener(e -> startTimer("Soft Boiled", 4));
        medium.addActionListener(e -> startTimer("Medium Boiled", 7));
        hard.addActionListener(e -> startTimer("Hard Boiled", 12));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(label, gbc);

        gbc.gridy = 1;
        panel.add(soft, gbc);

        gbc.gridy = 2;
        panel.add(medium, gbc);

        gbc.gridy = 3;
        panel.add(hard, gbc);

        return panel;
    }

    JPanel timerPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel text = new JLabel("Boiling!  your egg is ready in....");
        text.setFont(new Font("Arial", Font.BOLD, 16));

        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 42));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        panel.add(text, gbc);

        gbc.gridy = 1;
        panel.add(timerLabel, gbc);

        return panel;
    }

    JPanel donePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel doneText = new JLabel("Your egg is ready!");
        doneText.setFont(new Font("Arial", Font.BOLD, 18));

        JButton againBtn = styledButton("Boil another");
        JButton closeBtn = styledButton("Close");

        againBtn.addActionListener(e -> cardLayout.show(mainPanel, "options"));
        closeBtn.addActionListener(e -> System.exit(0));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(doneText, gbc);

        gbc.gridy = 1;
        panel.add(againBtn, gbc);

        gbc.gridy = 2;
        panel.add(closeBtn, gbc);

        return panel;
    }

    JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(buttonColor);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(220, 40));
        return btn;
    }

    void startTimer(String type, int minutes) {
        egg.setEgg(type, minutes);
        cardLayout.show(mainPanel, "timer");

        int totalSeconds = minutes * 60;

        new Thread(() -> {
            try {
                for (int seconds = totalSeconds; seconds >= 0; seconds--) {
                    int m = seconds / 60;
                    int s = seconds % 60;

                    timerLabel.setText(String.format("%02d:%02d", m, s));
                    Thread.sleep(1000);
                }

                cardLayout.show(mainPanel, "done");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
