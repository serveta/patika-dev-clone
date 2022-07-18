package View;

import Helper.*;
import Model.Comment;
import Model.Content;
import Model.User;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private JList list_comment;
    private DefaultListModel commentModel;

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

        commentModel = new DefaultListModel<>();
        getCommentModel();

        list_comment.setModel(commentModel);

        btn_quiz.addActionListener(e -> {
            QuizGUI quizGUI = new QuizGUI(content);
        });
        writeACommentButton.addActionListener(e -> {
            CommentGUI commentGUI = new CommentGUI(content, student);
            commentGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    getCommentModel();
                }
            });
        });
    }

    private void getCommentModel() {
        commentModel.removeAllElements();
        for (String comment : Comment.getCommentsOfContent(content.getId())){
            commentModel.addElement(comment);
        }
    }
}
