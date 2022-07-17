package View;

import Helper.*;
import Model.Quiz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateQuizGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_update_quiz;
    private JTextField fld_question;
    private JTextField fld_answer;
    private JButton btn_update;
    private Quiz quiz;

    public UpdateQuizGUI(Quiz quiz) {
        this.quiz = quiz;
        add(wrapper);
        setSize(500, 175);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " - Update Quiz");
        setVisible(true);

        fld_question.setText(quiz.getQuestion());
        fld_answer.setText(quiz.getAnswer());

        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_question) || Helper.isFieldEmpty(fld_answer)) {
                Helper.showMessage("fill");
            } else {
                if (Quiz.update(getQuiz().getId(), fld_question.getText(), fld_answer.getText())) {
                    Helper.showMessage("done");
                } else {
                    Helper.showMessage("error");
                }
            }
            dispose();
        });
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
