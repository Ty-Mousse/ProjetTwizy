package imageReading;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import org.opencv.highgui.Highgui;


public class ImageReading {

    public static Mat LectureImage(String fichier) {
        File f = new File(fichier);
        Mat m = Highgui.imread(f.getAbsolutePath());
        System.out.println(f.getAbsolutePath());
        return m;
    }

    public static void ImShow(String title, Mat img) {
        MatOfByte matOfByte = new MatOfByte();
        Highgui.imencode(".png",  img,  matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            JFrame frame = new JFrame();
            frame.setTitle(title);
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }





}
