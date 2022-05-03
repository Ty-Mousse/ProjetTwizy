package imageReading;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import static utils.Utils.Mat2bufferedImage;

public class ImageReading {

    public static Mat LectureImage(String fichier) {
        File f = new File(fichier);
        Mat m = Highgui.imread(f.getAbsolutePath());
        return m;
    }

    public static void ImShow(String title, Mat img) {
        try {
            BufferedImage bufImage = Mat2bufferedImage(img);
            JFrame frame = new JFrame();
            frame.setTitle(title);
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
