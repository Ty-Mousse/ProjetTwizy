package display;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class Window {

    private final ImageIcon icon = new ImageIcon("./src/main/resources/logo.png");
    private final JFrame window;
    private File currentFile;
    private final JLabel labelText = new JLabel("No file selected...");
    private final TextArea log = new TextArea();

    public Window (String title, int width, int height) {
        this.window = new JFrame();
        this.window.setTitle(title);
        this.window.setSize(width, height);
        this.window.setResizable(false);
        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.window.setIconImage(icon.getImage());
        this.window.setContentPane(this.initWindow());
        this.window.setVisible(true);
    }

    private JPanel initWindow() {
        // Ajout d'un panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        // Ajout du bouton d'upload de fichier
        JButton upload = new JButton("Upload");
        upload.setPreferredSize(new Dimension(100, 20));
        upload.addActionListener(e -> {
            currentFile = openFileChooser();
            if (currentFile != null) {
                showImage(currentFile.getName(), currentFile);
                updateRender();
            }
        });
        panel.add(upload, BorderLayout.PAGE_START);
        // Ajout du label
        panel.add(labelText);
        // Ajout du bouton pour reconnaitre les panneaux
        JButton solve = new JButton("Solve");
        solve.setPreferredSize(new Dimension(100, 20));
        panel.add(solve);
        // Ajout du textarea de logs
        log.setEditable(false);
        panel.add(log);
        return panel;
    }

    private void updateRender() {
        labelText.setText(currentFile.getName());
        log.append(currentFile.getName() + " as been uploaded.\n");
    }

    private File openFileChooser() {
        JFileChooser chooser = new JFileChooser("./");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG or MP4 files", "jpg", "png", "mp4");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this.window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
        }
        return chooser.getSelectedFile();
    }

    private void showImage(String title, File img) {
        try {
            JFrame frame = new JFrame();
            frame.setTitle(title);
            frame.getContentPane().add(new JLabel(new ImageIcon(String.valueOf(img))));
            frame.setIconImage(icon.getImage());
            frame.pack();
            frame.setVisible(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public JFrame getWindow() {
        return window;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public TextArea getLog() {
        return log;
    }
}
