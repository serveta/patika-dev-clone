package View;

import Helper.*;
import Model.Content;

import javax.swing.*;

public class CommentGUI extends JFrame {
    private JPanel wrapper;
    private JTextArea txt_comment;
    private JButton btn_comment_send;

    private Content content;

    public CommentGUI(Content content) {
        this.content = content;

        add(wrapper);
        setSize(270, 175);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " | For " + content.getTitle() + " Write a Comment");
        setVisible(true);


    }

    }
