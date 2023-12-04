package desktopapp;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.net.http.HttpResponse;
import java.util.Map;

class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel label1 = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel label2 = new JLabel("Password:");
        JTextField passwordField = new JTextField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            // Perform authentication here (e.g., check username and password)
            try {
                if (authenticate(username, password)) {
                    dispose(); // Close the login frame
                    showOptionsFrame(username);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username/password. Please try again.");
                }
            }
            catch (Exception except)
            {
                JOptionPane.showMessageDialog(this, "Network error.");
            }
        });

        registerButton.addActionListener(e -> {
            // Add logic for registering a new account here
            JOptionPane.showMessageDialog(this, "Registration feature not implemented yet.");
        });

        panel.add(label1);
        panel.add(usernameField);
        panel.add(label2);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
    }

    private boolean authenticate(String username, String password) throws Exception {
        // Add your authentication logic here
        // For simplicity, let's assume any non-empty username is valid in this example

        String url = Common_var.URLBase+"/loginauth?username="+username+"&password="+password;
        HttpResponse<String> loginResponse = HttpInterface.POST(url, "");

        Map<String,List<String>> headers = loginResponse.headers().map();
        List<String> cookies = headers.get("set-cookie");
        if(cookies == null)
            return false;
        int userID = Integer.parseInt(cookies.get(0).split(";")[0].split("=")[1]);
        Common_var.updateUserID(userID);
        Common_var.updatePassword(password);
        return true;
    }

    private void showOptionsFrame(String username) {
        OptionsFrame optionsFrame = new OptionsFrame(username);
        optionsFrame.setVisible(true);
    }
}