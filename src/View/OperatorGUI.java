package View;

import Helper.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Helper.*;
import Model.Operator;
import Model.User;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JLabel lbl_welcome;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private DefaultTableModel mdl_user_list;

    private Operator operator;
    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000,1000);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome, " + operator.getName());

        //Model User List
        mdl_user_list = new DefaultTableModel();
        Object[] col_user_list = {"ID", "Name", "Username", "Password", "User Type"};
        mdl_user_list.setColumnIdentifiers(col_user_list);

        for (User user : User.getList()) {
            Object[] row_user_list = new Object[col_user_list.length];
            row_user_list[0] = user.getId();
            row_user_list[1] = user.getName();
            row_user_list[2] = user.getUsername();
            row_user_list[3] = user.getPassword();
            row_user_list[4] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

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
