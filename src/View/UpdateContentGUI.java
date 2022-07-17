package View;

import Helper.*;
import Model.Content;

import javax.swing.*;

public class UpdateContentGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_content_title;
    private JTextField fld_content_link;
    private JTextArea txt_content_description;
    private JButton btn_content_update;

    private Content content;

    public UpdateContentGUI(Content content) {
        this.content = content;
        add(wrapper);
        setSize(500, 275);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " - Update Content");
        setVisible(true);

        fld_content_title.setText(getContent().getTitle());
        fld_content_link.setText(getContent().getLink());
        txt_content_description.setText(getContent().getDescription());

        btn_content_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_title) || Helper.isFieldEmpty(fld_content_link)) {
                Helper.showMessage("fill");
            } else {
                if (getContent().getId() > 0) {
                    if (Content.update(getContent().getId(), fld_content_title.getText(), fld_content_link.getText(), txt_content_description.getText())) {
                        Helper.showMessage("done");
                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }
                } else {
                    Helper.showMessage("Please select a course first.");
                }
            }
        });
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
