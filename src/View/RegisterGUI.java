package View;

import Helper.*;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_name;
    private JTextField fld_username;
    private JTextField fld_password;
    private JTextField fld_type;
    private JButton btn_register;

    public RegisterGUI() {
        add(wrapper);
        setSize(380, 250);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " | Register");
        setResizable(false);
        setVisible(true);


        btn_register.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_name) || Helper.isFieldEmpty(fld_password) || Helper.isFieldEmpty(fld_username) || Helper.isFieldEmpty(fld_type)) {
                Helper.showMessage("fill");
            } else {
                if (User.add(fld_name.getText(), fld_username.getText(), fld_password.getText(), fld_type.getText())) {
                    Helper.showMessage("done");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
    }
}
