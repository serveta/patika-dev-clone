package View;

import Helper.Helper;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import Helper.*;
import Model.Course;
import Model.Operator;
import Model.Path;
import Model.User;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JLabel lbl_welcome;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_name;
    private JTextField fld_username;
    private JTextField fld_password;
    private JComboBox cmb_user_type;
    private JButton btn_add;
    private JTextField fld_user_id;
    private JButton btn_delete;
    private JPanel pnl_sh;
    private JTextField fld_sh_name;
    private JTextField fld_sh_username;
    private JComboBox cmb_sh_type;
    private JButton btn_search;
    private JTextField fld_sh_id;
    private JPanel pnl_path_list;
    private JScrollPane scrl_path_list;
    private JTable tbl_path_list;
    private JTextField fld_path_name;
    private JButton btn_path_add;
    private JPanel onl_course_list;
    private JTable tbl_course_list;
    private JScrollPane scrl_course_list;
    private JTextField fld_course_name;
    private JTextField fld_programin_language;
    private JComboBox cmb_path;
    private JComboBox cmb_educator;
    private JButton btn_course_add;
    private JTextField fld_course_id;
    private JButton btn_course_delete;
    private JButton btn_course_update;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_path_list;
    private Object[] row_path_list;
    private JPopupMenu popup_path_list;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private User operator;

    public OperatorGUI(User operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000, 1000);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome, " + operator.getName());

        //Model User List
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Name", "Username", "Password", "User Type"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];
        loadUserModel();
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(selected_user_id);
            } catch (Exception exception) {

            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String username = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                if (User.update(user_id, user_name, username, password, user_type)) {
                    Helper.showMessage("done");
                }

                loadEducatorComboBox();
                loadUserModel();
                loadCourseModel();
            }
        });

        // ## Model User List

        // Model Path List
        popup_path_list = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        popup_path_list.add(updateMenu);
        popup_path_list.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selectedID = Integer.parseInt(tbl_path_list.getValueAt(tbl_path_list.getSelectedRow(), 0).toString());
            UpdatePathGUI updatePathGUI = new UpdatePathGUI(Path.getFetch(selectedID));
            updatePathGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPathModel();
                    loadPathComboBox();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedID = Integer.parseInt(tbl_path_list.getValueAt(tbl_path_list.getSelectedRow(), 0).toString());
                if (Path.delete(selectedID)) {
                    Helper.showMessage("done");
                    loadPathModel();
                    loadPathComboBox();
                    loadCourseModel();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        mdl_path_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_path_list = {"ID", "Path Name"};
        mdl_path_list.setColumnIdentifiers(col_path_list);
        row_path_list = new Object[col_path_list.length];
        loadPathModel();
        tbl_path_list.setModel((mdl_path_list));
        tbl_path_list.setComponentPopupMenu(popup_path_list);
        tbl_path_list.getTableHeader().setReorderingAllowed(false);
        tbl_path_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_path_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_path_list.rowAtPoint(point);
                tbl_path_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });

        // ## Model Path List

        // Model Course List
        mdl_course_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_course_list = {"ID", "Course Name", "Programing Language", "Path", "Educator"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();
        tbl_course_list.setModel((mdl_course_list));
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_course_id = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString();
                fld_course_id.setText(selected_course_id);
            } catch (Exception exception) {

            }
        });

        tbl_course_list.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int course_id = Integer.parseInt(tbl_course_list.getValueAt(row, 0).toString());
                    String course_name = tbl_course_list.getValueAt(row, 1).toString();
                    String programing_language = tbl_course_list.getValueAt(row, 2).toString();
                    String path = tbl_course_list.getValueAt(row, 3).toString();
                    String educator = tbl_course_list.getValueAt(row, 4).toString();

                    fld_course_name.setText(course_name);
                    fld_programin_language.setText(programing_language);
                    comboBoxSetSelectedItem(cmb_path, path);
                    comboBoxSetSelectedItem(cmb_educator, educator);
                    btn_course_update.setEnabled(true);
                }
            }
        });

        loadPathComboBox();
        loadEducatorComboBox();
        // ## Model Course List

        btn_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_name) || Helper.isFieldEmpty(fld_username) || Helper.isFieldEmpty(fld_password)) {
                Helper.showMessage("fill");
            } else {
                String name = fld_name.getText();
                String username = fld_username.getText();
                String password = fld_password.getText();
                String type = Objects.requireNonNull(cmb_user_type.getSelectedItem()).toString();

                if (User.add(name, username, password, type)) {
                    Helper.showMessage("done");
                    loadUserModel();
                    loadEducatorComboBox();
                    fld_name.setText(null);
                    fld_username.setText(null);
                    fld_password.setText(null);
                }

            }
        });
        btn_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)) {
                Helper.showMessage("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)) {
                        Helper.showMessage("done");
                        loadUserModel();
                        loadEducatorComboBox();
                        loadCourseModel();
                        fld_user_id.setText(null);
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

        btn_search.addActionListener(e -> {
            String user_id = fld_sh_id.getText();
            String user_name = fld_sh_name.getText();
            String user_username = fld_sh_username.getText();
            String user_type = cmb_sh_type.getSelectedItem().toString();

            loadUserModel(User.search(User.searchQuery(user_id, user_name, user_username, user_type)));
        });
        btn_path_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_path_name)) {
                Helper.showMessage("fill");
            } else {
                if (Path.add(fld_path_name.getText())) {
                    Helper.showMessage("done");
                    loadPathModel();
                    loadPathComboBox();
                    fld_path_name.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        btn_course_add.addActionListener(e -> {
            Item pathItem = (Item) cmb_path.getSelectedItem();
            Item userItem = (Item) cmb_educator.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_programin_language)) {
                Helper.showMessage("fill");
            } else {
                if (Course.add(userItem.getKey(), pathItem.getKey(), fld_course_name.getText(), fld_programin_language.getText())) {
                    Helper.showMessage("done");
                    loadCourseModel();
                    fld_course_name.setText(null);
                    fld_programin_language.setText(null);
                    btn_course_update.setEnabled(false);
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        btn_course_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_course_id)) {
                Helper.showMessage("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int userId = Integer.parseInt(fld_course_id.getText());
                    if (Course.delete(userId)) {
                        Helper.showMessage("done");
                        loadCourseModel();
                        fld_course_id.setText(null);
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });
        btn_course_update.addActionListener(e -> {
            Item pathItem = (Item) cmb_path.getSelectedItem();
            Item userItem = (Item) cmb_educator.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_programin_language)) {
                Helper.showMessage("fill");
            } else {
                if (Course.update(Integer.parseInt(fld_course_id.getText()), userItem.getKey(), pathItem.getKey(), fld_course_name.getText(), fld_programin_language.getText())) {
                    Helper.showMessage("done");
                    loadCourseModel();
                    fld_course_id.setText(null);
                    fld_course_name.setText(null);
                    fld_programin_language.setText(null);
                    btn_course_update.setEnabled(false);
                } else {
                    Helper.showMessage("error");
                }
            }

        });
        btn_logout.addActionListener(e -> {
            if (Helper.confirm("logout")) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
    }

    private void loadCourseModel() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_course_list.getModel();
        defaultTableModel.setRowCount(0);

        int i;
        for (Course course : Course.getList()) {
            i = 0;
            row_course_list[i++] = course.getId();
            row_course_list[i++] = course.getName();
            row_course_list[i++] = course.getPrograming_language();
            row_course_list[i++] = course.getPath().getName();
            row_course_list[i++] = course.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private void loadPathModel() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_path_list.getModel();
        defaultTableModel.setRowCount(0);

        int i;
        for (Path path : Path.getList()) {
            i = 0;
            row_path_list[i++] = path.getId();
            row_path_list[i++] = path.getName();
            mdl_path_list.addRow(row_path_list);
        }
    }

    public void loadUserModel() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_user_list.getModel();
        defaultTableModel.setRowCount(0);

        int i;
        for (User user : User.getList()) {
            i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUsername();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> userList) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_user_list.getModel();
        defaultTableModel.setRowCount(0);

        for (User user : userList) {
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUsername();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadPathComboBox() {
        cmb_path.removeAllItems();
        for (Path path : Path.getList()) {
            cmb_path.addItem(new Item(path.getId(), path.getName()));
        }
    }

    public void loadEducatorComboBox() {
        cmb_educator.removeAllItems();
        for (User user : User.getListOnlyEducator()) {
            cmb_educator.addItem(new Item(user.getId(), user.getName()));
        }
    }

    public void comboBoxSetSelectedItem(JComboBox jComboBox, String value) {
        Item item;
        for (int i = 0; i < jComboBox.getItemCount(); i++) {
            item = (Item) jComboBox.getItemAt(i);
            if (item.getValue().equalsIgnoreCase(value)) {
                jComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
}
