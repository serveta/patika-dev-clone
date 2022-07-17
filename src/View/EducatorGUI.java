package View;

import Helper.*;
import Model.User;

import javax.swing.*;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;

    private User educator;
    public EducatorGUI(User educator){
        this.educator = educator;
        add(wrapper);
        setSize(1000,1000);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
    }
}
