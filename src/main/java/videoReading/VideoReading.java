package videoReading;

import java.io.File;

import javax.swing.*;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import static solveur.Solveur.solve;
import static utils.Utils.Mat2bufferedImage;

public class VideoReading {

    public static void readVideo(File file, Boolean solving) {

        System.load("C:\\opencv\\build\\x64\\vc12\\bin\\opencv_ffmpeg2413_64.dll");

        JFrame jframe = new JFrame(file.getName());
        JLabel vidPanel = new JLabel();
        jframe.setContentPane(vidPanel);
        jframe.setSize(1080, 720);
        jframe.setVisible(true);

        Mat frame = new Mat();
        VideoCapture camera = new VideoCapture(file.getAbsolutePath());

        while (camera.read(frame)) {
            ImageIcon image;
            if (solving) { // Si appelé depuis bouton 'Solve'
                Mat result = solve(frame);
                image = new ImageIcon(Mat2bufferedImage(result));
            } else { // Sinon simple ouverture du fichier
                image = new ImageIcon(Mat2bufferedImage(frame));
            }
            vidPanel.setIcon(image);
            vidPanel.repaint();
        }

    }
}