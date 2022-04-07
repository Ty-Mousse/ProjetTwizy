package roadSignClarification;


import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class roadSignClarification {

    public static Mat improvedDisplay(Mat image, List<String> label, List<MatOfPoint> contours) {
        MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
        for (int c = 0; c < contours.size(); c++) {
            MatOfPoint contour = contours.get(c);
            matOfPoint2f.fromList(contour.toList());
            Rect rect = Imgproc.boundingRect(contour);
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
            if (label.size() - 1 >= c) {
                Core.putText(image, label.get(c), new Point(rect.x + rect.width + 20, rect.y + rect.height + 20), Core.FONT_HERSHEY_COMPLEX, 0.7, new Scalar(0, 255, 0), 2);
            }
        }
        return image;
    }

}
