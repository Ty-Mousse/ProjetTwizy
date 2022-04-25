package videoReading;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import display.Window;
import static utils.Utils.Mat2bufferedImage;

public class VideoReading {

    public static void readVideo(File file, Boolean solving) throws IOException {
        JFrame jframe = new JFrame(file.getName());
        JLabel vidPanel = new JLabel();
        jframe.setContentPane(vidPanel);
        jframe.setSize(720, 480);
        jframe.setVisible(true);

        Mat frame = new Mat();
        VideoCapture camera = new VideoCapture("Videos/video1.avi");

        if (camera.isOpened()) {
            while (camera.read(frame)) {
                if (!frame.empty()) {
                /*ImageIcon image = null;
                if (solving) { // Si appelé depuis bouton 'Solve'
                    Mat result = solve(frame);
                    image = new ImageIcon(Mat2bufferedImage(result));
                } else { // Sinon simple ouverture du fichier
                    image = new ImageIcon(Mat2bufferedImage(frame));
                }*/
                    ImageIcon image = new ImageIcon(Mat2bufferedImage(frame));
                    vidPanel.setIcon(image);
                    vidPanel.repaint();
                } else {
                    Window.getLog().append("Video corrupted or ended.\n");
                    break;
                }
            }
        } else {
            System.out.println("Couldn't open the video.");
            Window.getLog().append("Couldn't open the video.");
        }
    }
}