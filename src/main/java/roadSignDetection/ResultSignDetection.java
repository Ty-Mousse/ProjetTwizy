package roadSignDetection;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.List;

public class ResultSignDetection {

    public List<MatOfPoint> coords;
    public List<Mat> images;

    public ResultSignDetection(List<MatOfPoint> coords, List<Mat> images) {
        this.coords = coords;
        this.images = images;
    }
}
