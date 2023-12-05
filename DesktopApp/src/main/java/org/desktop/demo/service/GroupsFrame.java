package org.desktop.demo.service;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GroupsFrame extends JFrame {

    private List<String> friends;

    public GroupsFrame(List<String> friends) {
    	try {
        setTitle("Groups");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.friends = friends;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3));

        JButton newGroupButton = new JButton("New Group");
        newGroupButton.addActionListener(e -> openNewGroupDialog());
        panel.add(newGroupButton);

        JSONArray groupOverallView = GroupExpensesList();
        String items = "<table>";
        for (int i = 0; i < groupOverallView.length(); i++) {
            JSONObject obj = groupOverallView.getJSONObject(i);
            GroupRowPanel groupRowPanel = new GroupRowPanel(this, obj);
            panel.add(groupRowPanel);
        }

        add(panel);
    }catch(Exception ex) {
 	   System.out.println("Error occured in instantiating the friend frames");
    }
    }

    private void openNewGroupDialog() {
        NewGroupDialog dialog = new NewGroupDialog(this, friends);
        dialog.setVisible(true);
    }

    public List<String> getFriends() {
        return friends;
    }

    public JSONArray GroupExpensesList() throws IOException
    {
        Response groupsExpenses = null;
        try {
            groupsExpenses = HttpInterface.GET(Common_var.URLBase + Common_var.getAllGroupsURL);
        }
        catch (Exception e)
        {

        }

        String rw_body = groupsExpenses.body().string();
        JSONArray jsonArray = new JSONArray(rw_body);
        return jsonArray;
    }

}

class NewGroupDialog extends JDialog {

    private GroupsFrame parent;
    Map<String , Integer> getFriendsListAndID = Common_var.getFriendsListAndID();
    JTextField groupNameField;
    JPanel friendsPanel;
    JPanel panel;
    public NewGroupDialog(GroupsFrame parent, List<String> friends) {
        super(parent, "New Group", true);
        this.parent = parent;
        setSize(500, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        JLabel groupNameLabel = new JLabel("Enter group name:");
        groupNameField = new JTextField(20);
        friendsPanel = new JPanel();
//        Dimension preferredSize = new Dimension(400, 600);
//        friendsPanel.setPreferredSize(preferredSize);

        JButton submitButton = new JButton("Submit");
        JButton closeButton = new JButton("Close");

        submitButton.addActionListener(e -> {
            // Add logic to create a new group
            addNewGroup();
            dispose();
        });

        closeButton.addActionListener(e -> dispose());

        panel.setLayout(new GridLayout(11, 2));
        panel.add(groupNameLabel);
        panel.add(groupNameField);
        for (String friend : getFriendsListAndID.keySet()) {
            JCheckBox checkBox = new JCheckBox(friend);
            panel.add(checkBox);
        }
//        panel.add(friendsPanel);
        panel.add(submitButton);
        panel.add(closeButton);

        add(panel);
    }

    public void addNewGroup()
    {
        List<Integer> userIds = new ArrayList<>();
        userIds.add(Common_var.getUserID());
        for (Component component : panel.getComponents()) {
            if(component instanceof JCheckBox){
                JCheckBox c = (JCheckBox) component;
                if(c.isSelected()){
                    userIds.add(getFriendsListAndID.get(c.getText()));
                }
            }
        }

        String groupName = groupNameField.getText();
        JSONObject obj = new JSONObject();

        obj.accumulate("groupName", groupName);
        obj.accumulate("userIdList", userIds);

        Response response;
        try {
            String url = Common_var.URLBase + Common_var.createGroupURL;
            response = HttpInterface.POST(url, obj.toString());
        }
        catch (Exception e)
        {

        }

    }

}

class GroupRowPanel extends JPanel {

    private GroupsFrame parent;
    private int groupID;
    private JSONArray userIDList;
    private String groupName;

    public GroupRowPanel(GroupsFrame parent, JSONObject groupDetail) {
        this.parent = parent;

        String groupContent = GroupExpensesDisplay(groupDetail);
        JLabel content = new JLabel();
        content.setText(groupContent);

        this.add(content);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openGroupDetailsDialog(groupID, userIDList, groupName);
            }
        });

    }

    public String GroupExpensesDisplay(JSONObject groupDetail)
    {
        groupID = groupDetail.getInt("groupId");
        userIDList = groupDetail.getJSONArray("userIdList");
        groupName = groupDetail.getString("groupName");
            String item = "<table> <tr>" +
                    "<td> Group name:" + groupDetail.getString("groupName") + "</td>" + "<br>" +
                    "<td> Group ID:" + groupDetail.getInt("groupId") + "</td>" + "<br>" +
                    "<td> Amount: " + groupDetail.getBigDecimal("amount") + "</td>" + "<br>" +
                    "<td> Group Total Expenditure: " + groupDetail.getBigDecimal("totalGroupSpend") + "</td>" + "<br>" +
                    "</tr> </table>";

            return "<html> <body> " + item + "</body> </html>";

    }

    private void openGroupDetailsDialog(int groupID, JSONArray userIDList, String groupName) {
        GroupDetailsDialog dialog = new GroupDetailsDialog(parent, groupID, userIDList, groupName);
        dialog.setVisible(true);
    }
}

class GroupDetailsDialog extends JDialog {

    private GroupsFrame parent;

    public GroupDetailsDialog(GroupsFrame parent, int groupID, JSONArray userIDList, String groupName) {
        super(parent, "Group Details", true);
        this.parent = parent;
        setSize(300, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton updateDetailsButton = new JButton("Add more people");
        JButton updateExpensesButton = new JButton("Update Group Expenses");
        JButton closeButton = new JButton("Close");

        updateDetailsButton.addActionListener(e -> openUpdateDetailsDialog(groupID, userIDList, groupName));
        updateExpensesButton.addActionListener(e -> openUpdateExpensesDialog(groupID, userIDList, groupName));
        closeButton.addActionListener(e -> dispose());

        panel.add(updateDetailsButton);
        panel.add(updateExpensesButton);
        panel.add(closeButton);

        add(panel);
    }

    private void openUpdateDetailsDialog(int groupID, JSONArray userIDList, String groupName) {
        UpdateDetailsDialog dialog = new UpdateDetailsDialog(parent, groupID, userIDList, groupName);
        dialog.setVisible(true);
    }

    private void openUpdateExpensesDialog(int groupID, JSONArray userIDList, String groupName) {
        UpdateExpensesDialog dialog = new UpdateExpensesDialog(parent, groupID, userIDList, groupName);
        dialog.setVisible(true);
    }
}

class UpdateDetailsDialog extends JDialog {

    private GroupsFrame parent;
    Map<String , Integer> getFriendsListAndID = Common_var.getFriendsListAndID();
    int groupID;
    JSONArray userIDList;
    String groupName;
    JPanel updateGrpPanel;

    public UpdateDetailsDialog(GroupsFrame parent, int groupID, JSONArray userIDList, String groupName) {
        super(parent, "Add more people", true);
        this.groupID = groupID;
        this.userIDList = userIDList;
        this.parent = parent;
        this.groupName = groupName;
        setSize(300, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        updateGrpPanel = new JPanel();
        updateGrpPanel.setLayout(new GridLayout(15, 1));
        List<String> newUsersList = Common_var.get_MutuallyExclusive_Or_Overlapping_Friends(getFriendsListAndID, userIDList, false);

        for (String friend : newUsersList) {
            JCheckBox checkBox = new JCheckBox(friend);
            updateGrpPanel.add(checkBox);
        }

        JButton submitButton = new JButton("Submit");
        JButton closeButton = new JButton("Close");

        submitButton.addActionListener(e -> {
            // Add logic to update group details
            updateGroupDetails();
            dispose();
        });

        closeButton.addActionListener(e -> dispose());

        updateGrpPanel.add(submitButton);
        updateGrpPanel.add(closeButton);

        add(updateGrpPanel);
    }

    public void updateGroupDetails()
    {
        List<Integer> userIds = new ArrayList<>();
    //    userIds.add(Common_var.getUserID());
        for (Component component : updateGrpPanel.getComponents()) {
            if(component instanceof JCheckBox){
                JCheckBox c = (JCheckBox) component;
                if(c.isSelected()){
                    userIds.add(getFriendsListAndID.get(c.getText()));
                }
            }
        }

        String groupName = this.groupName;
        int grpId = this.groupID;
        JSONObject obj = new JSONObject();
        obj.accumulate("groupId", grpId);
        obj.accumulate("groupName", groupName);
//        obj.accumulate("groupId", grpId);
        obj.accumulate("userIdList", userIds);

        Response response;
        try {
            String url = Common_var.URLBase + Common_var.createGroupURL;
            response = HttpInterface.POST(url, obj.toString());
        }
        catch (Exception e)
        {

        }
    }
}

class UpdateExpensesDialog extends JDialog {

    private GroupsFrame parent;
    Map<String , Integer> getFriendsListAndID = Common_var.getFriendsListAndID();
    int groupID;
    JSONArray userIDList;
    String groupName;
    JTextField titleField;
    JTextField amountField;
    JTextField currencyField;
    JTextField categoryField;
    JPanel friendsPanel;
    JPanel panel;
    JComboBox<String> payerDropdown;

    public UpdateExpensesDialog(GroupsFrame parent, int groupID, JSONArray userIDList, String groupName) {
        super(parent, "Update Group Expenses", true);
        this.groupID = groupID;
        this.userIDList = userIDList;
        this.groupName = groupName;
        this.parent = parent;

        List<String> friendsList = Common_var.get_MutuallyExclusive_Or_Overlapping_Friends(getFriendsListAndID, this.userIDList, true);
        friendsList.add("Me");
        getFriendsListAndID.put("Me", Common_var.getUserID());
        setSize(600, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        JLabel titleLabel = new JLabel("Title:");
        JLabel amountLabel = new JLabel("Amount:");
        JLabel currencyLabel = new JLabel("Currency:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel friendsLabel = new JLabel("Friends:");
        JLabel payerLabel = new JLabel("Payer:");

        titleField = new JTextField(20);
        amountField = new JTextField(20);
        currencyField = new JTextField(20);
        categoryField = new JTextField(20);

        payerDropdown = new JComboBox<>(friendsList.toArray(new String[0]));
        friendsPanel = new JPanel();
        friendsPanel.setSize(50, 50);

        for (String friend : friendsList) {
            JCheckBox checkBox = new JCheckBox(friend);
            friendsPanel.add(checkBox);
        }

        JButton submitButton = new JButton("Submit");
        JButton closeButton = new JButton("Close");

        submitButton.addActionListener(e -> {
            // Add logic to update group expenses
            addGroupExpenditure();
            dispose();
        });

        closeButton.addActionListener(e -> dispose());

        panel.setLayout(new GridLayout(15, 2));
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(currencyLabel);
        panel.add(currencyField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(friendsLabel);
        panel.add(friendsPanel);
        panel.add(payerLabel);
        panel.add(payerDropdown);
        panel.add(submitButton);
        panel.add(closeButton);

        add(panel);
    }

    public void addGroupExpenditure()
    {
        String title = titleField.getText();
        String note= "";
        String category = categoryField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String currency = currencyField.getText();
        List<Integer> owinguserId = new ArrayList<>();

    //    owinguserId.add(Common_var.getUserID());
        for (Component component : friendsPanel.getComponents()) {
            if(component instanceof JCheckBox){
                JCheckBox c = (JCheckBox) component;
                if(c.isSelected()){
                    owinguserId.add(getFriendsListAndID.get(c.getText()));
                }
            }
        }

        int owedUserId = getFriendsListAndID.get(this.payerDropdown.getSelectedItem().toString());

        JSONObject obj = new JSONObject();
        obj.accumulate("title", title);
        obj.accumulate("note", note);
        obj.accumulate("category", category);
        obj.accumulate("amount", amount);
        obj.accumulate("currency", currency);
        obj.accumulate("owedUserId", owedUserId);
        obj.accumulate("owingUserId", owinguserId);
        obj.accumulate("groupId", this.groupID);

        Response response;
        try {
            String url = Common_var.URLBase + Common_var.updateGroupExpenseURL;
            response = HttpInterface.POST(url, obj.toString());
        }
        catch (Exception e)
        {

        }
    }
}