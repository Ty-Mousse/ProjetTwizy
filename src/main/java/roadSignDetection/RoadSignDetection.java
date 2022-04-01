package roadSignDetection;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


import static imageReading.ImageReading.ImShow;
import static imageReading.ImageReading.LectureImage;


public class RoadSignDetection {


    /*public static void rgb_hsv() {
        // De BGR à HSV

        Mat m_bis = LectureImage("hsv.png");
        Mat output = Mat.zeros(m_bis.size(), m_bis.type());
        Imgproc.cvtColor(m_bis,  output, Imgproc.COLOR_BGR2HSV);			
        ImShow("HSV", output);
        Vector<Mat> channels_bis = new Vector<Mat>();
        Core.split(output, channels_bis);
        double hsv_values[][] = {{1, 255, 255}, {179, 1, 255}, {179, 0, 1}};
        for(int i=0; i<3; i++) {
            ImShow(Integer.toString(i)+"-HSV", channels_bis.get(i));
            Mat chans_bis[] = new Mat[3];
            for (int j=0; j<3; j++) {
                Mat empty_bis = Mat.ones(m_bis.size(), CvType.CV_8UC1);
                Mat comp = Mat.ones(m_bis.size(), CvType.CV_8UC1);
                Scalar v = new Scalar(hsv_values[i][j]);					Core.multiply(empty_bis,v,comp);
                chans_bis[j] = comp;
            }
            chans_bis[i] = channels_bis.get(i);
            Mat dst_bis = Mat.zeros(output.size(), output.type());
            Mat res = Mat.ones(dst_bis.size(), dst_bis.type());
            Core.merge(Arrays.asList(chans_bis), dst_bis);
            Imgproc.cvtColor(dst_bis, res, Imgproc.COLOR_HSV2BGR);
            ImShow(Integer.toString(i), res);
        }
    }
*/
   /* public static void BGR_niveaux_gris() {
        // BGR order et mode niveaux de gris
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String fichier = "bgr.png";
        Mat m = LectureImage(fichier);
        Vector<Mat> channels = new Vector<Mat>();
        Core.split(m,  channels);
        Mat dst = Mat.zeros(m.size(),m.type());
        Vector<Mat> chans = new Vector<Mat>();
        Mat empty = Mat.zeros(m.size(), CvType.CV_8UC1);
        for (int i =0; i<channels.size(); i++) {
            ImShow(Integer.toString(i), channels.get(i));
            chans.removeAllElements();
            for (int j=0; j<channels.size();j++) {
                if (j != i) {
                    chans.add(empty);
                }else {
                    chans.add(channels.get(i));
                }
            }
            Core.merge(chans, dst);
            ImShow(Integer.toString(i), dst);
        }
    }
*/

    public static void seuillage() {
        // Extraire les cerlces rouges de l'image
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m = LectureImage("circles.jpg");
        Mat hsv_image = Mat.zeros(m.size(), m.type());
        Imgproc.cvtColor(m,  hsv_image,  Imgproc.COLOR_BGR2HSV);
        Mat threshold_img1 = new Mat();
        Mat threshold_img2 = new Mat();
        Mat threshold_img = new Mat();
        Core.inRange(hsv_image,  new Scalar(0,100,100),  new Scalar(10,255,255),  threshold_img1);
        Core.inRange(hsv_image,  new Scalar(160,100,100), new Scalar(179,255,255), threshold_img2);
        Core.bitwise_or(threshold_img1,  threshold_img2, threshold_img);
        Imgproc.GaussianBlur(threshold_img,  threshold_img, new Size(9,9), 2,2);
        ImShow("Cercles rouges", threshold_img);
    }


    public static Mat DetecterCercles(Mat hsv_image) {
        Mat threshold_img1 = new Mat();
        Mat threshold_img2 = new Mat();
        Mat threshold_img = new Mat();
        Core.inRange(hsv_image,  new Scalar(0,100,100),  new Scalar(10,255,255),  threshold_img1);
        Core.inRange(hsv_image,  new Scalar(160,100,100), new Scalar(179,255,255), threshold_img2);
        Core.bitwise_or(threshold_img1,  threshold_img2, threshold_img);
        Imgproc.GaussianBlur(threshold_img,  threshold_img, new Size(9,9), 2,2);
        return threshold_img;
    }


    public static void contours() {
        //extraire le contours des cercles
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m = LectureImage("circles.jpg");
        Mat hsv_image = Mat.zeros(m.size(), m.type());
        Imgproc.cvtColor(m,  hsv_image,  Imgproc.COLOR_BGR2HSV);
        Mat threshold_img1 = new Mat();
        Mat threshold_img2 = new Mat();
        Mat threshold_img = new Mat();
        Core.inRange(hsv_image,  new Scalar(0,100,100),  new Scalar(10,255,255),  threshold_img1);
        Core.inRange(hsv_image,  new Scalar(160,100,100), new Scalar(179,255,255), threshold_img2);
        Core.bitwise_or(threshold_img1,  threshold_img2, threshold_img);
        Imgproc.GaussianBlur(threshold_img,  threshold_img, new Size(9,9), 2,2);
        ImShow("Cercles rouges", threshold_img);

        ImShow("Cercles", m);
        ImShow("HSV", hsv_image);
        ImShow("Seuillage", threshold_img);
        int thresh = 100;
        Mat canny_output = new Mat();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        MatOfInt4 hierarchy = new MatOfInt4();
        Imgproc.Canny(threshold_img, canny_output, thresh, thresh*2);
        Imgproc.findContours(canny_output,  contours,  hierarchy,  Imgproc.RETR_EXTERNAL,  Imgproc.CHAIN_APPROX_SIMPLE);
        Mat drawing = Mat.zeros(canny_output.size(), CvType.CV_8UC3);
        Random rand = new Random();
        for(int i =0; i<contours.size(); i++) {
            Scalar color = new Scalar(rand.nextInt(255 - 0 + 1), rand.nextInt(255 - 0 + 1), rand.nextInt(255 - 0 + 1));
            Imgproc.drawContours(drawing,  contours,  i,  color, 1,8,hierarchy, 0, new Point());
            ImShow("Contours", drawing);
        }
    }

    public static List<MatOfPoint> DetecterContours(Mat threshold_img) {
        int thresh = 100;
        Mat canny_output = new Mat();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        MatOfInt4 hierarchy = new MatOfInt4();
        Imgproc.Canny(threshold_img, canny_output, thresh, thresh*2);
        Imgproc.findContours(canny_output,  contours,  hierarchy,  Imgproc.RETR_EXTERNAL,  Imgproc.CHAIN_APPROX_SIMPLE);
        return contours;
    }


    public static void detecter_formes() {
        // detecter les cercles rouges 
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m = LectureImage("circles_rectangles.jpg");
        ImShow("Cerles", m);
        Mat hsv_image = Mat.zeros(m.size(), m.type());
        Imgproc.cvtColor(m, hsv_image,  Imgproc.COLOR_BGR2HSV);
        ImShow("Seuillage", hsv_image);
        Mat threshold_img = DetecterCercles(hsv_image);
        ImShow("Seuillage", threshold_img);
        List<MatOfPoint> contours = DetecterContours(threshold_img);

        MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
        float[] radius = new float[1];
        Point center = new Point();
        for (int c=0; c<contours.size(); c++) {
            MatOfPoint contour = contours.get(c);
            double contourArea = Imgproc.contourArea(contour);
            matOfPoint2f.fromList(contour.toList());
            Imgproc.minEnclosingCircle(matOfPoint2f, center, radius);
            if ((contourArea/(Math.PI*radius[0]*radius[0])) >= 0.8) {
                Core.circle(m,  center,  (int)radius[0],  new Scalar(0,255,0),2);
            }
        }
        ImShow("Detection des cercles rouges", m);
    }


    // Etape 1 : Extraction des balles rouges
    public static List<Mat> Extr_panneau(Mat m) {
        Mat hsv_image = Mat.zeros(m.size(), m.type());
        Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);
        List<MatOfPoint> contours = DetecterContours(hsv_image);
        List<Mat> ListeImage = new ArrayList();
        MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
        float[] radius = new float[1];
        Point center = new Point();
        for (int c=0; c<contours.size();c++) {
            MatOfPoint contour = contours.get(c);
            double contourArea = Imgproc.contourArea(contour);
            matOfPoint2f.fromList(contour.toList());
            Imgproc.minEnclosingCircle(matOfPoint2f,  center,  radius);;
            if ((contourArea/(Math.PI*radius[0]*radius[0])) >= 0.8){
                Core.circle(matOfPoint2f,  center,  (int)radius[0],  new Scalar(0,255,0), 2);
                Rect rect = Imgproc.boundingRect(contour);
                Core.rectangle(matOfPoint2f, new Point(rect.x, rect.y),  new Point(rect.x+rect.width, rect.y+rect.height), new Scalar (0,255,0), 2);
                Mat tmp = m.submat(rect.y, rect.y+rect.height,rect.x,rect.x+rect.width);
                Mat ball = Mat.zeros(tmp.size(), tmp.type());
                tmp.copyTo(ball);
                ListeImage.add(ball);
            }
        }
        return ListeImage;
    }

}
