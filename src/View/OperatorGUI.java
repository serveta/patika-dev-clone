package View;

import Helper.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Helper.*;
import Model.Operator;
import Model.User;

import java.net.http.HttpRequest;
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
    private DefaultTableModel mdl_user_list;
    Object[] row_user_list;

    private Operator operator;

    public OperatorGUI(Operator operator) {
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
                int user_id = Integer.parseInt(fld_user_id.getText());
                if (User.delete(user_id)) {
                    Helper.showMessage("done");
                    loadUserModel();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
    }

    public void loadUserModel() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_user_list.getModel();
        defaultTableModel.setRowCount(0);

        for (User user : User.getList()) {
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUsername();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator operator1 = new Operator();
        operator1.setId(1);
        operator1.setName("Servet");
        operator1.setPassword("12345");
        operator1.setType("operator");
        OperatorGUI operatorGUI = new OperatorGUI(operator1);
    }
}
