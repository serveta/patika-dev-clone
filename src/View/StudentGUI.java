package View;

import Helper.*;
import Model.User;

import javax.swing.*;

public class StudentGUI extends JFrame {
    private JPanel wrapper;

    private User student;
    public StudentGUI(User student){
        this.student = student;
        add(wrapper);
        setSize(1000,1000);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
    }
}
