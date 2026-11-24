import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import controller.WeaponController;
import gui.MainWindow;
import gui.Theme;

public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            try
            {
                Theme.apply();

                WeaponController controller =
                    new WeaponController();

                MainWindow window =
                    new MainWindow(controller);

                window.setVisible(true);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Application Error",
                    JOptionPane.ERROR_MESSAGE
                );

                e.printStackTrace();
            }
        });
    }
}