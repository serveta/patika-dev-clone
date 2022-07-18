package View;

import Helper.*;
import Model.Content;
import Model.Quiz;
import Model.StudentCourse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuizGUI extends JFrame {
    private JComboBox cmb_question;
    private JPanel wrapper;
    private JTextField fld_answer;
    private JLabel lbl_answer;
    private JButton btn_answer;

    private Content content;

    public QuizGUI(Content content) {
        this.content = content;

        add(wrapper);
        setSize(270, 175);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " | " + content.getTitle() + " Questions");
        setVisible(true);

        ComboBoxQuestionAddItem();


        btn_answer.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_answer)){
                Helper.showMessage("fill");
            } else {
                String answer = Quiz.getAnswer(content.getId(), String.valueOf(cmb_question.getSelectedItem()));
                if (answer.equals(fld_answer.getText())){
                    lbl_answer.setText("Your answer is correct.");
                } else {
                    lbl_answer.setText("Answer is : " + answer);
                }
            }
        });
    }

    private void ComboBoxQuestionAddItem() {
        cmb_question.removeAllItems();
        cmb_question.addItem("Choose a question...");
        for (Quiz quiz : Quiz.getListByContentId(content.getId())) {
            cmb_question.addItem(quiz.getQuestion());
        }
    }
}
