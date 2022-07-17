package View;

import Helper.*;
import Model.Content;

import javax.swing.*;

public class AddContentGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_content_title;
    private JTextField fld_content_link;
    private JTextArea txt_content_description;
    private JButton btn_content_add;

    private int courseID;

    public AddContentGUI(int courseID) {
        this.courseID = courseID;
        add(wrapper);
        setSize(500, 275);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " - Add New Content");
        setVisible(true);

        btn_content_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_title) || Helper.isFieldEmpty(fld_content_link)) {
                Helper.showMessage("fill");
            } else {
                if (getCourseID() > 0) {
                    if (Content.add(getCourseID(), fld_content_title.getText(), fld_content_link.getText(), txt_content_description.getText())) {
                        Helper.showMessage("done");
                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }
                } else {
                    Helper.showMessage("Please select a course first.");
                }
            }
        });
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
