package desktopapp;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;


class PersonalFrame extends JFrame {

    private int userID = Common_var.getUserID();
    public PersonalFrame() {
        setTitle("Personal");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton addExpenditureButton = new JButton("Add Expenditure");
        addExpenditureButton.addActionListener(e -> openAddExpenditureDialog());
        String personalExpensesDisplay = PersonalExpensesDisplay();
        JLabel list = new JLabel();
        list.setText(personalExpensesDisplay);

        JPanel panel = new JPanel();
        panel.add(addExpenditureButton);
        panel.add(list);

        add(panel);
    }

    public String PersonalExpensesDisplay()
    {
        HttpResponse<String> personalExpenses = null;
        try {
            personalExpenses = HttpInterface.GET(Common_var.URLBase + Common_var.getExpenseAll);
        }
        catch (Exception e)
        {

        }

        String rw_body = personalExpenses.body();
        JSONArray jsonArray = new JSONArray(rw_body);
        String items = "<table>";
        for(int i=0; i < jsonArray.length() ; i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            JSONArray owingUserIdArray = obj.getJSONArray("owingUserId");
            List owingUsersList = owingUserIdArray.toList();
            if(obj.getInt("owedUserId") == userID && obj.getInt("groupId") == 0 && owingUsersList.get(0).equals(0) ) {
                String item = "<tr>" +
                        "<td> Title:" + obj.getString("title") + "</td>" +
                        "<td> Cost: " + obj.getBigDecimal("amount") + "</td>" +
                        "<td> Date: " + obj.getString("trans_dttm") + "</td>" +
                        "<td> Category: " + obj.getString("category") + "</td>" +
                        "</tr>";
                items += item + "<br>";
            }
        }
        items += "</table>";
        String htmlDisplay = "<html> <body> " + items + "</body> </html>";
        return htmlDisplay;
    }

    private void openAddExpenditureDialog() {
        AddExpenditureDialog dialog = new AddExpenditureDialog(this);
        dialog.setVisible(true);
    }
}

class AddExpenditureDialog extends JDialog {

    JTextField titleField;
    JTextField amountField;
    JTextField currencyField;
    JTextField categoryField;
    public AddExpenditureDialog(JFrame parent) {
        super(parent, "Add Expenditure", true);
        setSize(300, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel("Title:");
        JLabel amountLabel = new JLabel("Amount:");
        JLabel currencyLabel = new JLabel("Currency:");
        JLabel categoryLabel = new JLabel("Category:");

        titleField = new JTextField(20);
        amountField = new JTextField(20);
        currencyField = new JTextField(20);
        categoryField = new JTextField(20);

        JButton submitButton = new JButton("Submit");
        JButton closeButton = new JButton("Close");

        submitButton.addActionListener(e -> {
            // Add logic to save expenditure
            addPersonalExpenditure();
            dispose();
        });

        closeButton.addActionListener(e -> dispose());

        panel.setLayout(new GridLayout(5, 2));
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(currencyLabel);
        panel.add(currencyField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(submitButton);
        panel.add(closeButton);

        add(panel);
    }

    public void addPersonalExpenditure()
    {
        String title = titleField.getText();
        String note= "";
        String category = categoryField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String currency = currencyField.getText();

        JSONObject obj = new JSONObject();
        obj.accumulate("title", title);
        obj.accumulate("note", note);
        obj.accumulate("category", category);
        obj.accumulate("amount", amount);
        obj.accumulate("currency", currency);

        HttpResponse<String> response;
        try {
            String url = Common_var.URLBase + Common_var.postExpenseURL;
            response = HttpInterface.POST(url, obj.toString());
        }
        catch (Exception e)
        {

        }
    }
}
