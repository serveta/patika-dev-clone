package View;

import Helper.*;
import Model.Path;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePathGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_path_name;
    private JButton btn_update;
    Path path;

    public UpdatePathGUI(Path path) {
        this.path = path;
        add(wrapper);
        setSize(300, 150);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_path_name.setText(path.getName());

        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_path_name)) {
                Helper.showMessage("fill");
            } else {
                if (Path.update(path.getId(), fld_path_name.getText())) {
                    Helper.showMessage("done");
                }
                dispose();
            }
        });
    }
/*
    public static void main(String[] args) {
        Path path1 = new Path(4,"asdsad");
        UpdatePathGUI updatePathGUI = new UpdatePathGUI(path1);
    }
*/
}
