package Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static int screenCenterPoint(String axis, Dimension size) {
        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static boolean isFieldEmpty(JTextField jTextField) {
        return jTextField.getText().trim().length() == 0;
    }

    public static void showMessage(String msg) {
        String message;
        String title;

        switch (msg) {
            case "fill":
                message = "Please fill in all fields.";
                title = "WARNING";
                break;
            case "done":
                message = "The operation was successfully completed!";
                title = "SUCCESSFUL";
                break;
            case "error":
                message = "Something gets wrong!";
                title = "ERROR";
                break;
            case "usernameInUse":
                message = "The username already in use. Please change your username!";
                title = "ERROR";
                break;
            default:
                message = msg;
                title = "WARNING";
        }

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String msg) {
        String message;

        switch (msg) {
            case "sure":
                message = "Do you want to continue?";
                break;
            case "logout":
                message = "Are you sure you want to log out?";
                break;
            default:
                message = msg;
        }

        return JOptionPane.showConfirmDialog(null, message, "Are you sure?", JOptionPane.YES_NO_OPTION) == 0;
    }
}
