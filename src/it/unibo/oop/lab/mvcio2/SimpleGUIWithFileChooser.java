package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import it.unibo.oop.lab.mvcio.Controller;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */
    private final JFrame frame = new JFrame();
    private static final int PROPORTION = 5;
    private final Controller ctr;

    /**
     * builds a new {@link SimpleGUI}.
     * @param ctr
     */
    public SimpleGUIWithFileChooser(final Controller ctr) {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.ctr = ctr;
        frame.setSize(sw / PROPORTION, sh / PROPORTION);
        frame.setLocationByPlatform(true);

    }

    private void display() throws FileNotFoundException, IOException {
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        final JButton b1 = new JButton("Serialize");
        final JTextArea t1 = new JTextArea();

        final JPanel canvas2 = new JPanel(new BorderLayout());
        final JButton buttonBrowse = new JButton("BROWSE");
        final JTextField label = new JTextField();
        final JFileChooser chooser  = new JFileChooser();

        chooser.setFileFilter(new FileNameExtensionFilter("testo", "txt"));
        canvas2.add(label, BorderLayout.CENTER);
        canvas2.add(buttonBrowse, BorderLayout.LINE_END);
        canvas.add(canvas2, BorderLayout.NORTH);
        label.setEnabled(false);
        label.setText(ctr.getPath());


        buttonBrowse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                if (chooser.showSaveDialog(canvas) == JFileChooser.APPROVE_OPTION) {
                    try {
                        ctr.setFile(chooser.getSelectedFile());
                        label.setText(chooser.getSelectedFile().getPath());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(frame, e1, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }

        });


        frame.setContentPane(canvas);
        canvas.add(t1, BorderLayout.CENTER);
        canvas.add(b1, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    ctr.doSerial(t1.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        });
        frame.setVisible(true);
    }

    public static void main(final String... args) throws FileNotFoundException, IOException {
        new SimpleGUIWithFileChooser(new Controller()).display();
    }
}
