import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnackFrame extends JFrame {
    JButton jButton = new JButton("start");

    SnackFrame() {
        add(jButton, BorderLayout.NORTH);
        setDefaultCloseOperation(3);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnackPanel contentPane = new SnackPanel();
                setContentPane(contentPane);
                pack();
                setLocationRelativeTo(null);
                contentPane.requestFocus();
            }
        });
    }

    public static void main(String[] args) {
        new SnackFrame();
    }
}
