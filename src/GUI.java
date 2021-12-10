import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI {

    private JFrame frame;
    private JPanel panel;

    private static String OUTPUT_PATH = "C:\\Users\\jason\\Desktop\\java things\\result.jpeg";
    private static String INPUT_PATH = "C:\\Users\\jason\\Desktop\\java things\\test.jpeg";
    private static Picture pic;
    private static SeamCarver sc;

    private String height = pic.height() + "";
    private String width = pic.width() + "";

    public static void main(String[] args) throws IOException {
        File f = new File(INPUT_PATH);
        pic = new Picture(f);
        sc = new SeamCarver(pic, new DualGradientEnergyFunction(), new DynamicProgrammingSeamFinder());
        new GUI();
    }

    public GUI() {
        frame = new JFrame();
        panel = new JPanel();

        JButton button = new JButton("Convert");
        button.setBounds(230, 250, 100, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    convert();
                } catch (IOException exception) {
                    System.out.println("it don't work");
                }

            }
        });


        JLabel widthLabel = new JLabel("Width");
        widthLabel.setBounds(130, 120, 100, 25);
        JTextField width = new JTextField();
        width.setText(pic.width() + "");
        width.setBounds(100, 150, 100, 25);
        width.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeWidth(width.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeWidth(width.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeWidth(width.getText());
            }
        });

        JLabel heightLabel = new JLabel("Height");
        heightLabel.setBounds(380, 120, 100, 25);
        JTextField height = new JTextField();
        height.setText(pic.height() + "");
        height.setBounds(350, 150, 100, 25);
        height.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeHeight(height.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeHeight(height.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeHeight(height.getText());
            }
        });


        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);
        panel.add(button);
        panel.add(width);
        panel.add(height);
        panel.add(widthLabel);
        panel.add(heightLabel);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setTitle("My GUI");
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

    }

    private void convert() throws IOException {
        if (testInput()) {
            int width = Integer.parseInt(this.width);
            int height = Integer.parseInt(this.height);

            Horizontal(width);
            Vertical(height);

            sc.picture().save(new File(OUTPUT_PATH));
        } else {
            System.out.println("invalid");
        }
    }

    private void changeWidth(String input) {
        this.width = input;
    }

    private void changeHeight(String input) {
        this.height = input;
    }

    private boolean testInput() {

        for (int i = 0; i < width.length(); i++) {
            if (width.charAt(i) < 48 || width.charAt(i) > 57) {
                return false;
            }
        }
        for (int i = 0; i < height.length(); i++) {
            if (height.charAt(i) < 48 || height.charAt(i) > 57) {
                return false;
            }
        }

        return true;
    }

    private static void Horizontal(int width) {
        if (pic.width() < width) {
            throw new IndexOutOfBoundsException("Given width must be less than image width");
        }
        int finalWidth = pic.width() - width;
        for (int i = 0; i < finalWidth; i++) {
            sc.removeVertical();
        }
    }

    private static void Vertical(int height) {
        if (pic.height() < height) {
            throw new IndexOutOfBoundsException("Given height must be less than image height");
        }
        int finalWidth = pic.height() - height;
        for (int i = 0; i < finalWidth; i++) {
            sc.removeHorizontal();
        }
    }


}
