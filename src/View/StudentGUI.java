package View;

import Helper.*;
import Model.*;
import com.sun.source.tree.IfTree;

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
        setSize(600, 350);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome, " + student.getName());
        cmb_my_course.addItem("You didn't select a path yet.");
        cmb_my_content.addItem("You didn't select a course yet.");
        ComboBoxPathAddItem();

        if (StudentCourse.getMyPathName(this.student.getId()).size() > 0) {
            ComboBoxMyPathAddItem();
        } else {
            cmb_my_path.addItem("You don't have any path yet.");
        }


        cmb_my_path.addActionListener(e -> {
            if (cmb_my_path.getSelectedIndex() > 0) {
                ComboBoxMyCourseAddItem();
            } else {
                cmb_my_course.removeAllItems();
                cmb_my_course.addItem("You didn't select a path yet.");
            }
        });
        cmb_my_course.addActionListener(e -> {
            if (cmb_my_path.getSelectedIndex() > 0 && cmb_my_course.getSelectedIndex() > -1) {
                ComboBoxMyContentAddItem();
            } else {
                cmb_my_content.removeAllItems();
                cmb_my_content.addItem("You didn't select a course yet.");
            }
        });

        cmb_path.addActionListener(e -> {
            if (cmb_path.getSelectedIndex() > 0) {
                ComboBoxCourseAddItem();
            } else {
                cmb_course.removeAllItems();
                cmb_course.addItem("You didn't select a path yet.");
            }
        });

        btn_join.addActionListener(e -> {
            if (cmb_path.getSelectedIndex() > 0 && cmb_course.getSelectedIndex() > 0) {
                if (StudentCourse.add(student.getId(), String.valueOf(cmb_path.getSelectedItem()), String.valueOf(cmb_course.getSelectedItem()))) {
                    Helper.showMessage("done");
                } else {
                    Helper.showMessage("You have already join in this course.");
                }
            } else {
                Helper.showMessage("fill");
            }
        });
        btn_open.addActionListener(e -> {
            if (cmb_my_path.getSelectedIndex() > 0 && cmb_my_course.getSelectedIndex() > -1 && cmb_my_content.getSelectedIndex() > -1) {
                ContentGUI contentGUI = new ContentGUI(student, Content.getFetch(String.valueOf(cmb_my_content.getSelectedItem())));
            } else {
                Helper.showMessage("You have to select a content.");
            }
        });
        btn_logout.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });
    }

    private void ComboBoxMyPathAddItem() {
        cmb_my_path.removeAllItems();
        cmb_my_path.addItem("Choose a path...");
        for (String pathName : StudentCourse.getMyPathName(student.getId())) {
            cmb_my_path.addItem(pathName);
        }
    }

    private void ComboBoxMyCourseAddItem() {
        cmb_my_course.removeAllItems();
        for (String courseName : StudentCourse.getMyCourseName(student.getId(), String.valueOf(cmb_my_path.getSelectedItem()))) {
            cmb_my_course.addItem(courseName);
        }
    }

    private void ComboBoxMyContentAddItem() {
        cmb_my_content.removeAllItems();
        for (String contentName : StudentCourse.getMyContentTitle(student.getId(), String.valueOf(cmb_my_course.getSelectedItem()))) {
            cmb_my_content.addItem(contentName);
        }
    }

    private void ComboBoxPathAddItem() {
        cmb_path.removeAllItems();
        cmb_path.addItem("Choose a path...");
        for (Path pathName : Path.getList()) {
            cmb_path.addItem(pathName.getName());
        }
    }

    private void ComboBoxCourseAddItem() {
        cmb_course.removeAllItems();
        cmb_course.addItem("Choose a course...");
        for (Course courseName : Course.getList(String.valueOf(cmb_path.getSelectedItem()))) {
            cmb_course.addItem(courseName.getName());
        }
    }
}
