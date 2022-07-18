package View;

import Helper.*;
import Model.Content;
import Model.User;

import javax.swing.*;

public class ContentGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JTextField fld_title;
    private JTextField fld_link;
    private JTextArea txt_description;
    private JScrollPane scrl_comments;
    private JPanel pnl_buttons;
    private JButton btn_quiz;
    private JButton writeACommentButton;

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

        fld_title.setText(content.getTitle());
        fld_link.setText(content.getLink());
        txt_description.setText(content.getDescription());

    }
}
