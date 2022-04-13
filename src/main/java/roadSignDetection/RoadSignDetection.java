package roadSignDetection;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class RoadSignDetection {

    public static Mat detecterRouge(Mat hsv_image) {
        Mat threshold_img1 = new Mat();
        Mat threshold_img2 = new Mat();
        Mat threshold_img = new Mat();
        Core.inRange(hsv_image,  new Scalar(0,100,50),  new Scalar(10,255,255),  threshold_img1);
        Core.inRange(hsv_image,  new Scalar(160,100,50), new Scalar(179,255,255), threshold_img2);
        Core.bitwise_or(threshold_img1,  threshold_img2, threshold_img);
        Imgproc.GaussianBlur(threshold_img,  threshold_img, new Size(9,9), 2,2);
        return threshold_img;
    }

    public static List<MatOfPoint> detecterContours(Mat threshold_img) {
        int thresh = 100;
        Mat canny_output = new Mat();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        MatOfInt4 hierarchy = new MatOfInt4();
        Imgproc.Canny(threshold_img, canny_output, thresh, thresh*2);
        Imgproc.findContours(canny_output,  contours,  hierarchy,  Imgproc.RETR_EXTERNAL,  Imgproc.CHAIN_APPROX_SIMPLE);
        return contours;
    }

    // Etape 1 : Extraction des balles rouges
    public static ResultSignDetection extrairePanneau(Mat m) {
        Mat hsv_image = Mat.zeros(m.size(), m.type());
        Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);
        Mat img_seuillee = detecterRouge(hsv_image);
        List<MatOfPoint> contours = detecterContours(img_seuillee);

        List<Mat> ListeImage = new ArrayList();
        List<MatOfPoint> contoursCercles = new ArrayList();
        MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
        float[] radius = new float[1];
        Point center = new Point();

        for (int c = 0; c < contours.size(); c++) {
            MatOfPoint contour = contours.get(c);
            double contourArea = Imgproc.contourArea(contour);
            matOfPoint2f.fromList(contour.toList());
            Imgproc.minEnclosingCircle(matOfPoint2f,  center,  radius);;
            if ((contourArea/(Math.PI*radius[0]*radius[0])) >= 0.8){
                Core.circle(matOfPoint2f, center, (int)radius[0],  new Scalar(0,255,0), 2);
                Rect rect = Imgproc.boundingRect(contour);
                Core.rectangle(matOfPoint2f, new Point(rect.x, rect.y),  new Point(rect.x+rect.width, rect.y+rect.height), new Scalar (0,255,0), 2);
                Mat tmp = m.submat(rect.y, rect.y+rect.height,rect.x,rect.x+rect.width);
                Mat ball = Mat.zeros(tmp.size(), tmp.type());
                tmp.copyTo(ball);
                ListeImage.add(ball);
                contoursCercles.add(contour);
            }
        }
        ResultSignDetection results = new ResultSignDetection(contoursCercles, ListeImage);
        return results;
    }
}
