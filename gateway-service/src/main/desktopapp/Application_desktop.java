package desktopapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Application_desktop {
    int userIDGlobal = -1;
    String passwordGlobal = "";
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}

class OptionsFrame extends JFrame {

    private String username;
    private List<String> friends;

    public OptionsFrame(String username) {
        this.username = username;
        this.friends = new ArrayList<>();

        setTitle("Options - " + username);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton personalButton = new JButton("Personal");
        JButton friendsButton = new JButton("Friends");
        JButton groupsButton = new JButton("Groups");

        personalButton.addActionListener(e -> openPersonalFrame());
        friendsButton.addActionListener(e -> openFriendsFrame());
        groupsButton.addActionListener(e -> openGroupsFrame());

        JPanel panel = new JPanel();
        panel.add(personalButton);
        panel.add(friendsButton);
        panel.add(groupsButton);

        add(panel);
    }

    private void openPersonalFrame() {
        PersonalFrame personalFrame = new PersonalFrame();
        personalFrame.setVisible(true);
    }

    private void openFriendsFrame() {
        FriendsFrame friendsFrame = new FriendsFrame();
        friendsFrame.setVisible(true);
    }

    private void openGroupsFrame() {
        GroupsFrame groupsFrame = new GroupsFrame(friends);
        groupsFrame.setVisible(true);
    }

    private void openFrame(String option) {
        JFrame frame = new JFrame(option);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}










