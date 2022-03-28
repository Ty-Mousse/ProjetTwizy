package display;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window {

    private final JFrame window;
    private File currentFile;

    public Window (String title, int width, int height) {
        this.window = new JFrame();
        this.window.setTitle(title);
        this.window.setSize(width, height);
        this.window.setResizable(false);
        this.window.setVisible(true);
        this.initWindow();
    }

    private void initWindow() {
        // Ajout du bouton d'upload de fichier
        JButton button = new JButton("Upload a file");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentFile = openFileChooser();
                try {
                    updateRender();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.window.add(button, BorderLayout.PAGE_START);
    }

    private void updateRender() throws IOException {
        BufferedImage myPicture = ImageIO.read(currentFile);
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        this.window.add(picLabel);
        this.window.pack();
    }

    private File openFileChooser() {
        JFileChooser chooser = new JFileChooser("./");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, PNG or MP4 files", "jpg", "png", "mp4");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this.window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
        }
        return chooser.getSelectedFile();
    }

    public JFrame getWindow() {
        return window;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
}
