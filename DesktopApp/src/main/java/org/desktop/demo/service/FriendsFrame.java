package org.desktop.demo.service;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Response;

class FriendsFrame extends JFrame {

    private List<String> friends;
    int[] friendsIDs = new int[50];

    private int userID = Common_var.getUserID();

    public FriendsFrame()  {
    try {	
        setTitle("Friends");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        friends = new ArrayList<>();
        loadFriends();

        JButton addFriendButton = new JButton("Add Friend");
        JButton addExpenseButton = new JButton("Add Expense");

        addFriendButton.addActionListener(e -> openAddFriendDialog());
        addExpenseButton.addActionListener(e -> openAddExpenseDialog());

        String friendsExpensesDisplay = FriendsExpensesDisplay();
        JLabel list = new JLabel();
        list.setText(friendsExpensesDisplay);

        JPanel panel = new JPanel();
        panel.add(addFriendButton);
        panel.add(addExpenseButton);
        panel.add(list);

        add(panel);
    }catch(Exception ex) {
    	   System.out.println("Error occured in instantiating the friend frames");
       }
    }

    public String FriendsExpensesDisplay() throws IOException
    {
        Response friendsExpenses = null;
        try {
            friendsExpenses = HttpInterface.GET(Common_var.URLBase+Common_var.getAllFriendsURL);
        }
        catch (Exception e)
        {

        }

        String rw_body = friendsExpenses.body().string();
        JSONArray jsonArray = new JSONArray(rw_body);
        String items = "<table>";
        for(int i=0; i < jsonArray.length() ; i++){
            JSONObject obj = jsonArray.getJSONObject(i);

            String item = "<tr>" +
                    "<td> Friend name:" + obj.getString("name") + "</td>" +
                    "<td> Friend username:" + obj.getString("username") + "</td>" +
                    "<td> Amount: " + obj.getBigDecimal("amountOwed") + "</td>" +
                    "<td> Email: " + obj.getString("email") + "</td>" +
                    "<td> Mobile Number: " + obj.getString("mobile") + "</td>" +
                    "</tr>";
            items += item + "<br>";

        }
        items += "</table>";
        String htmlDisplay = "<html> <body> " + items + "</body> </html>";
        return htmlDisplay;
    }

    private void openAddFriendDialog() {
        AddFriendDialog dialog = new AddFriendDialog(this);
        dialog.setVisible(true);
    }

    private void openAddExpenseDialog() {
        AddExpenseDialog dialog = new AddExpenseDialog(this, friends, friendsIDs);
        dialog.setVisible(true);
    }

    public List<String> getFriends() {
        return friends;
    }

    public void loadFriends() throws IOException {
        String url = Common_var.URLBase + Common_var.getAllFriendsURL;
        Response friendsExpenses = null;
        try{
            friendsExpenses = HttpInterface.GET(url);
        }
        catch (Exception e)
        {

        }
        String rw_body = friendsExpenses.body().string();
        JSONArray jsonArray = new JSONArray(rw_body);
        for(int i=0; i < jsonArray.length() ; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            friends.add(obj.getString("name"));
            friendsIDs[i] = obj.getInt("id");
        }
        int x = 5;
    }
}

class AddFriendDialog extends JDialog {

    private FriendsFrame parent;

    JTextField friendUsernameField;
    public AddFriendDialog(FriendsFrame parent) {
        super(parent, "Add Friend", true);
        this.parent = parent;
        setSize(300, 150);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        friendUsernameField = new JTextField(20);

        JButton submitButton = new JButton("Submit");
        JButton closeButton = new JButton("Close");

        submitButton.addActionListener(e -> {
            // Add logic to save friend
//            parent.getFriends().add(friendUsernameField.getText());
            addFriend(friendUsernameField.getText());
            dispose();
        });

        closeButton.addActionListener(e -> dispose());

        panel.setLayout(new GridLayout(2, 2));
        panel.add(usernameLabel);
        panel.add(friendUsernameField);
        panel.add(submitButton);
        panel.add(closeButton);

        add(panel);
    }

    public void addFriend(String friendUsername)
    {
        Response response;
        try {
            String url = Common_var.URLBase+ Common_var.addFriendURL +friendUsername;
            response = HttpInterface.POST(url, "");
        }
        catch (Exception e)
        {

        }
    }

}

class AddExpenseDialog extends JDialog {

    private List<String> friends;
    int[] friendsIDs;

    JTextField titleField;
    JTextField amountField;
    JTextField currencyField;
    JTextField categoryField;
    JComboBox<String> friendsDropdown;
    JCheckBox iPaidThisCheckBox;
    JCheckBox splitEquallyCheckBox;

    public AddExpenseDialog(JFrame parent, List<String> friends, int[] friendsIDs) {
        super(parent, "Add Expense", true);
        this.friends = friends;
        this.friendsIDs = friendsIDs;
        setSize(400, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel("Title:");
        JLabel amountLabel = new JLabel("Amount:");
        JLabel currencyLabel = new JLabel("Currency:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel friendsLabel = new JLabel("Friends:");

        titleField = new JTextField(20);
        amountField = new JTextField(20);
        currencyField = new JTextField(20);
        categoryField = new JTextField(20);

        friendsDropdown = new JComboBox<>(friends.toArray(new String[0]));
        iPaidThisCheckBox = new JCheckBox("I paid this.");
        splitEquallyCheckBox = new JCheckBox("Split Equally");

        // You can set the initial state of the checkbox
        iPaidThisCheckBox.setSelected(true);
        splitEquallyCheckBox.setSelected(false);



        JButton submitButton = new JButton("Submit");
        JButton closeButton = new JButton("Close");

        submitButton.addActionListener(e -> {
            // Add logic to save expense
            addFriendExpenditure();
            dispose();
        });

        closeButton.addActionListener(e -> dispose());

        panel.setLayout(new GridLayout(16, 1));
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(currencyLabel);
        panel.add(currencyField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(friendsLabel);
        panel.add(friendsDropdown);
        panel.add(iPaidThisCheckBox);
        panel.add(splitEquallyCheckBox);
        panel.add(submitButton);
        panel.add(closeButton);

        add(panel);
    }

    public void addFriendExpenditure()
    {
        String title = titleField.getText();
        String note= "";
        String category = categoryField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String currency = currencyField.getText();
        int friendUserID = friendsIDs[friendsDropdown.getSelectedIndex()];
        boolean iPaidThis = iPaidThisCheckBox.isSelected();
        boolean splitEqually = splitEquallyCheckBox.isSelected();
        List<Integer> owinguserId = new ArrayList<>();

        int owedUserId;

        if(iPaidThis){
            owedUserId = Common_var.getUserID();
            owinguserId.add(friendUserID);
        }
        else {
            owedUserId = friendUserID;
            owinguserId.add(Common_var.getUserID());
        }

        if(splitEqually)
            owinguserId.add(owedUserId);

        JSONObject obj = new JSONObject();
        obj.accumulate("title", title);
        obj.accumulate("note", note);
        obj.accumulate("category", category);
        obj.accumulate("amount", amount);
        obj.accumulate("currency", currency);
        obj.accumulate("owedUserId", owedUserId);
        obj.accumulate("owingUserId", owinguserId);

        Response response;
        try {
            String url = Common_var.URLBase + Common_var.postExpenseURL;
            response = HttpInterface.POST(url, obj.toString());
        }
        catch (Exception e)
        {

        }
    }
}
