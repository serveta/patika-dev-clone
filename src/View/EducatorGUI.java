package View;

import Helper.*;
import Model.Content;
import Model.Course;
import Model.Quiz;
import Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JComboBox cmb_courses;
    private JButton btn_content_delete;
    private JButton btn_quiz_delete;
    private JButton btn_content_add;
    private JButton btn_content_update;
    private JTable tbl_content_list;
    private JScrollPane scrl_content;
    private JPanel pnl_content;
    private JPanel pnl_content_top;
    private JPanel pnl_quiz_top;
    private JScrollPane scrl_quiz;
    private JTable tbl_quiz_list;
    private JButton btn_quiz_add;
    private JButton btn_quiz_update;
    private JPanel pnl_sh_content;
    private JTextField fld_sh_content;
    private JButton btn_sh_content;
    private JPanel pnl_sh_quiz;
    private JTextField fld_sh_quiz;
    private JButton btn_sh_quiz;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;

    private User educator;

    public EducatorGUI(User educator) {
        this.educator = educator;
        add(wrapper);
        setSize(1100, 1000);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome, " + educator.getName());
        comboBoxCoursesAddItem();

        // Model Content List
        mdl_content_list = new DefaultTableModel();
        Object[] col_content_list = {"ID", "Course", "Title"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];

        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_content_list.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    int content_id = Integer.parseInt(tbl_content_list.getValueAt(row, 0).toString());
                    if (content_id > 0) {
                        loadQuizModel(content_id);
                    } else {
                        clearQuizModel();
                    }
                }
            }
        });

        // ## Model Content List

        // Model Quiz List
        mdl_quiz_list = new DefaultTableModel();
        Object[] col_quiz_list = {"ID", "Content", "Question", "Answer"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];

        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false);
        tbl_quiz_list.getColumnModel().getColumn(0).setMaxWidth(75);
        // ## Model Quiz List

        cmb_courses.addActionListener(e -> {
            int course_id = comboBoxCourseGetSelectedIDOfItem();


            if (course_id > 0) {
                loadContentModel(course_id);
            } else {
                clearContentModel();
            }

            clearQuizModel();
        });
        btn_content_add.addActionListener(e -> {
            if (comboBoxCourseGetSelectedIDOfItem() != -1) {
                AddContentGUI addContentGUI = new AddContentGUI(comboBoxCourseGetSelectedIDOfItem());
                addContentGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadContentModel(comboBoxCourseGetSelectedIDOfItem());
                    }
                });
            } else {
                Helper.showMessage("You have to select a course!");
            }
        });
        btn_content_update.addActionListener(e -> {
            try {
                UpdateContentGUI updateContentGUI = new UpdateContentGUI(getSelectedContent());
                updateContentGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadContentModel(comboBoxCourseGetSelectedIDOfItem());
                        try {
                            loadQuizModel(getSelectedContent().getId());
                        } catch (Exception exception) {

                        }
                    }
                });
            } catch (Exception exception) {
                Helper.showMessage("You have to select a content!");
            }

        });
        btn_content_delete.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                try {
                    if (Content.delete(getSelectedContent().getId())) {
                        Helper.showMessage("done");
                        loadContentModel(comboBoxCourseGetSelectedIDOfItem());
                        clearQuizModel();
                    }
                } catch (Exception exception) {
                    Helper.showMessage("You have to select a content!");
                }
            }

        });
        btn_quiz_add.addActionListener(e -> {
            Content content = null;
            try {
                content = getSelectedContent();
            } catch (Exception exception) {
                Helper.showMessage("You have to select a content!");
            }
            if (content != null) {
                AddQuizGUI addQuizGUI = new AddQuizGUI(content.getId());
                addQuizGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadQuizModel(getSelectedContent().getId());
                    }
                });
            }
        });
        btn_quiz_update.addActionListener(e -> {
            try {
                UpdateQuizGUI updateQuizGUI = new UpdateQuizGUI(getSelectedQuiz());
                updateQuizGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadQuizModel(getSelectedContent().getId());
                    }
                });
            } catch (Exception exception) {
                Helper.showMessage("You have to select a quiz.");
            }
        });
        btn_quiz_delete.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                try {
                    if (Quiz.deleteByQuizId(getSelectedQuiz().getId())) {
                        Helper.showMessage("done");
                        loadQuizModel(getSelectedContent().getId());
                    }
                } catch (Exception exception) {
                    Helper.showMessage("You have to select a quiz!");
                }
            }
        });
        btn_sh_content.addActionListener(e -> {
            if (cmb_courses.getSelectedIndex() > 0) {
                loadContentModel(Content.getListByTitle(comboBoxCourseGetSelectedIDOfItem(), fld_sh_content.getText()));
            } else {
                Helper.showMessage("Please select a course.");
            }
        });
        btn_sh_quiz.addActionListener(e -> {
            try {
                if (getSelectedContent().getId() != -1) {
                    loadQuizModel(Quiz.getListByContentIdAndQuestion(getSelectedContent().getId(), fld_sh_quiz.getText()));
                }
            } catch (Exception exception) {
                Helper.showMessage("Please select a content.");
            }
        });
    }

    private void loadContentModel(int courseID) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_content_list.getModel();
        defaultTableModel.setRowCount(0);

        int i;
        for (Content content : Content.getListByCourseId(courseID)) {
            i = 0;
            row_content_list[i++] = content.getId();
            row_content_list[i++] = content.getCourse().getName();
            row_content_list[i++] = content.getTitle();
            mdl_content_list.addRow(row_content_list);
        }
    }

    private void loadContentModel(ArrayList<Content> contentSearch) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_content_list.getModel();
        defaultTableModel.setRowCount(0);

        int i;
        for (Content content : contentSearch) {
            i = 0;
            row_content_list[i++] = content.getId();
            row_content_list[i++] = content.getCourse().getName();
            row_content_list[i++] = content.getTitle();
            mdl_content_list.addRow(row_content_list);
        }
    }

    private void clearContentModel() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_content_list.getModel();
        defaultTableModel.setRowCount(0);
    }

    private void loadQuizModel(int contentID) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_quiz_list.getModel();
        defaultTableModel.setRowCount(0);

        int i;
        for (Quiz quiz : Quiz.getListByContentId(contentID)) {
            i = 0;
            row_quiz_list[i++] = quiz.getId();
            row_quiz_list[i++] = quiz.getContent().getTitle();
            row_quiz_list[i++] = quiz.getQuestion();
            row_quiz_list[i++] = quiz.getAnswer();
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }

    private void loadQuizModel(ArrayList<Quiz> quizSearch) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_quiz_list.getModel();
        defaultTableModel.setRowCount(0);

        int i;
        for (Quiz quiz : quizSearch) {
            i = 0;
            row_quiz_list[i++] = quiz.getId();
            row_quiz_list[i++] = quiz.getContent().getTitle();
            row_quiz_list[i++] = quiz.getQuestion();
            row_quiz_list[i++] = quiz.getAnswer();
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }

    private void clearQuizModel() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_quiz_list.getModel();
        defaultTableModel.setRowCount(0);
    }

    public void comboBoxCoursesAddItem() {
        cmb_courses.removeAllItems();
        cmb_courses.addItem("Select a course...");
        for (Course course : Course.getListByUser(educator.getId())) {
            cmb_courses.addItem(course.getName());
        }
    }

    public int comboBoxCourseGetSelectedIDOfItem() {
        for (Course course : Course.getListByUser(educator.getId())) {
            if (course.getName().equals(cmb_courses.getModel().getSelectedItem())) {
                return course.getId();
            }
        }
        return -1;
    }

    public Content getSelectedContent() {
        int contentID = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
        return Content.getFetch(contentID);
    }

    public Quiz getSelectedQuiz() {
        int quizID = Integer.parseInt(tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 0).toString());
        return Quiz.getFetch(quizID);
    }

    public static void main(String[] args) {
        Helper.setLayout();
        EducatorGUI educatorGUI = new EducatorGUI(new User(1, "Birce", "brce", "qqq", "educator"));
    }
}
