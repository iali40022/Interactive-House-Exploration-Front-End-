package OOP.ec22473.MP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;


public class MP_ec22473 implements Visitor {


    ImageIcon roomImage = new ImageIcon("1.jpg");
    JLabel roomLabel = new JLabel();

    private JTextArea outputArea;
    private JTextField inputField;
    private JButton submitButton;

    private JFrame frame;

    private JLabel Gold;
    private List<Integer> gold;
    public MP_ec22473() {
        gold = new ArrayList<>();
        gold.add(0);

        roomLabel.setIcon(roomImage);


        Gold = new JLabel("Gold: 0");
        frame = new JFrame("Room Visitor");
        frame.setSize(400, 300);

        Font customFont = new Font("Arial", Font.BOLD, 20);
        roomLabel.setFont(customFont);

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        inputField = new JTextField(20);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (submitButton) {
                    submitButton.notifyAll();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(outputArea));
        panel.add(inputField);
        panel.add(submitButton);
        panel.add(Gold);
        panel.add(roomLabel);

        ImageIcon itemIcon = new ImageIcon("1.jpg");
        JLabel itemLabel = new JLabel();
        itemLabel.setIcon(itemIcon);
        panel.add(itemLabel);

        JButton button = new JButton();
        button.setIcon(itemIcon);
        panel.add(button);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void tell(String message) {
        outputArea.append(message + "\n");
    }

    public char getChoice(String prompt, char[] choices) {
        tell(prompt);
        inputField.setText("");
        submitButton.setEnabled(true);

        while (true) {
            synchronized (submitButton) {
                try {
                    submitButton.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String input = inputField.getText();
            if (input != null && input.length() > 0) {
                char selected = input.charAt(0);
                for (char c : choices) {
                    if (selected == c) {
                        return selected;
                    }
                }
            }
        }
    }

    public boolean giveItem(Item item) {
        tell("You are being offered: " + item.name);
        char choice = getChoice("Do you accept (y/n)?", new char[]{'y', 'n'});
        return choice == 'y';
    }

    public boolean hasIdenticalItem(Item item) {
        return false;
    }

    public boolean hasEqualItem(Item item) {
        return false;
    }

    public void giveGold(int amount) {
        tell("You are given " + amount + " gold pieces.");
        int gold_remaining = gold.get(gold.size() - 1) + amount;
        gold.add(gold_remaining);
        Gold.setText("Gold: "+ String.valueOf(gold_remaining));
    }

    public int takeGold(int amount) {
        int gold_remaining = gold.get(gold.size() - 1) - amount;
        gold.add(gold_remaining);
        tell(amount + " pieces of gold are taken from you.");
        Gold.setText("Gold: "+  String.valueOf(gold_remaining));
        return gold_remaining;
    }

    public static void main(String[] args) {
        Room room = new Room_ec22473();
        MP_ec22473 visitor = new MP_ec22473();

        Direction location = Direction.FROM_NORTH;

        while (true) {
            location = room.visit(visitor, location);
            visitor.tell("You are in the " + location + " direction");

            char choice = visitor.getChoice("Do you want to keep exploring (y/n)?", new char[]{'y', 'n'});
            if (choice == 'n') {
                visitor.tell("Thanks for playing!");
                break;
            }
        }
    }
}