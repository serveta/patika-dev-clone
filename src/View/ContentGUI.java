package View;

import Helper.*;
import Model.Content;
import Model.User;

import javax.swing.*;

public class ContentGUI extends JFrame {
    private JPanel wrapper;

    private User student;
    private Content content;

    public ContentGUI(User student, Content content) {
        this.student = student;
        this.content = content;
        add(wrapper);
        setSize(400, 300);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " | " + content.getTitle());
        setVisible(true);
    }
}
