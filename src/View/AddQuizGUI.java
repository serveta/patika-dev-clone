package View;

import Helper.*;
import Model.Quiz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddQuizGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_add_quiz;
    private JTextField fld_question;
    private JTextField fld_answer;
    private JButton btn_add;

    private int contentID;

    public AddQuizGUI(int contentID) {
        this.contentID = contentID;
        add(wrapper);
        setSize(500, 175);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " - Add New Quiz");
        setVisible(true);
        btn_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_question) || Helper.isFieldEmpty(fld_answer)) {
                Helper.showMessage("fill");
            } else {
                if (Quiz.add(getContentID(), fld_question.getText(), fld_answer.getText())) {
                    Helper.showMessage("done");
                } else {
                    Helper.showMessage("error");
                }
            }
            dispose();
        });
    }

    public int getContentID() {
        return contentID;
    }

    public void setContentID(int contentID) {
        this.contentID = contentID;
    }

}
