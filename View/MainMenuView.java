package View;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {
    private JButton newGameButton;
    private JButton loadGameButton;

    public MainMenuView() {
        setTitle("Chess Game - Main Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Chess Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10,10,10,10);


        // New Game Button
        newGameButton = new JButton("New Game");
        buttonPanel.add(newGameButton, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10,10,10,10);

        // Load Game Button (disabled by default)
        loadGameButton = new JButton("Load Game");
        loadGameButton.setEnabled(false);
        buttonPanel.add(loadGameButton, c);

        add(buttonPanel, BorderLayout.CENTER);
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getLoadGameButton() {
        return loadGameButton;
    }
}