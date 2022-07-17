package View;

import Helper.*;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_username;
    private JTextField fld_password;
    private JButton btn_login;

    public LoginGUI(){
        add(wrapper);
        setSize(420,420);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_username) || Helper.isFieldEmpty(fld_password)){
                Helper.showMessage("fill");
            } else {
                User user = User.getFetch(fld_username.getText(), fld_password.getText());
                if (user == null){
                    Helper.showMessage("The username or password is wrong!");
                } else {
                    switch (user.getType()){
                        case "operator" :
                            OperatorGUI operatorGUI = new OperatorGUI(user);
                            break;
                        case "educator":
                            EducatorGUI educatorGUI = new EducatorGUI(user);
                            break;
                        case "student":
                            StudentGUI studentGUI = new StudentGUI(user);
                            break;
                    }
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
