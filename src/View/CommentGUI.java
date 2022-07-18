package View;

import Helper.*;
import Model.Comment;
import Model.Content;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentGUI extends JFrame {
    private JPanel wrapper;
    private JTextArea txt_comment;
    private JButton btn_comment_send;

    private Content content;
    private User user;

    public CommentGUI(Content content, User user) {
        this.content = content;
        this.user = user;

        add(wrapper);
        setSize(270, 175);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " | For " + content.getTitle() + " Write a Comment");
        setVisible(true);


        btn_comment_send.addActionListener(e -> {
            if (txt_comment.getText().length() > 0){
                if(Comment.add(content.getId(),user.getId(),txt_comment.getText())){
                    Helper.showMessage("done");
                    dispose();
                }
            } else {
                Helper.showMessage("fill");
            }
        });
    }

    }
