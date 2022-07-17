package View;

import Helper.*;
import Model.Course;
import Model.Path;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JButton btn_logout;
    private JPanel pnl_join_path;
    private JPanel pnl_my_path;
    private JComboBox cmb_path;
    private JComboBox cmb_course;
    private JButton btn_join;
    private JComboBox cmb_my_path;
    private JComboBox cmb_my_course;
    private JComboBox cmb_my_content;
    private JButton btn_open;
    private JLabel lbl_welcome;

    private User student;

    public StudentGUI(User student) {
        this.student = student;
        add(wrapper);
        setSize(500, 350);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome, " + student.getName());

    }

    public static void main(String[] args) {
        Helper.setLayout();
        User student = new User(1,"servet","sea","123","student");
        StudentGUI studentGUI = new StudentGUI(student);
    }
}
