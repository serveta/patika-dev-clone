package View;

import Helper.*;
import Model.Content;
import Model.Course;
import Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;

    private User educator;

    public EducatorGUI(User educator) {
        this.educator = educator;
        add(wrapper);
        setSize(1000, 1000);
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

        // ## Model Content List

        cmb_courses.addActionListener(e -> {
            int course_id = 0;
            for (Course course : Course.getListByUser(educator.getId())) {
                if (course.getName().equals(cmb_courses.getModel().getSelectedItem())) {
                    course_id = course.getId();
                    break;
                }
            }

            if (course_id > 0){
                loadContentModel(course_id);
            } else {
                clearContentModel();
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

    private void clearContentModel() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_content_list.getModel();
        defaultTableModel.setRowCount(0);
    }

    public void comboBoxCoursesAddItem() {
        cmb_courses.removeAllItems();
        cmb_courses.addItem("Select a course...");
        for (Course course : Course.getListByUser(educator.getId())) {
            cmb_courses.addItem(course.getName());
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        EducatorGUI educatorGUI = new EducatorGUI(new User(1, "Birce", "brce", "qqq", "educator"));
    }
}
