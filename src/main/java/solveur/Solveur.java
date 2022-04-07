package solveur;

import display.Window;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.highgui.Highgui;
import roadSignDetection.ResultSignDetection;
import roadSignIdentifier.RoadSignIdentifier;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static roadSignDetection.RoadSignDetection.extrairePanneau;
import static roadSignClarification.roadSignClarification.improvedDisplay;

public class Solveur {

    public static Function<Mat, Mat> solve;
    private static String[] labelList = {"Panneau 30","Panneau 50","Panneau 70","Panneau 90","Panneau 110","Panneau double"};
    private static File f30 = new File("Images/ref_30.jpg");
    private static Mat m_30 = Highgui.imread(f30.getAbsolutePath());
    private static File f50 = new File("Images/ref_50.JPG");
    private static Mat m_50 = Highgui.imread(f50.getAbsolutePath());
    private static File f70 = new File("Images/ref_70.jpg");
    private static Mat m_70 = Highgui.imread(f70.getAbsolutePath());
    private static File f90 = new File("Images/ref_90.jpg");
    private static Mat m_90 = Highgui.imread(f90.getAbsolutePath());
    private static File f30_2 = new File("Images/ref_interdiction_doubler.jpg");
    private static Mat m_30_2 = Highgui.imread(f30_2.getAbsolutePath());
    private static Mat[] refList = {m_30, m_50, m_70, m_90, m_30_2};
    private static final RoadSignIdentifier signIdentifier = new RoadSignIdentifier(refList, labelList);
    private static final TextArea log = Window.getLog();

    public static Mat solve(Mat m) {
        ResultSignDetection extract = extrairePanneau(m);
        List<Mat> images = extract.images;
        List<MatOfPoint> coords = extract.coords;
        List<String> labels = new ArrayList<String>();
        for (Mat current:images) {
            assert false;
            String result = signIdentifier.identifier(current);
            log.append(result + "\n");
            labels.add(result);
        }
        Mat finalImage = improvedDisplay(m, labels, coords);
        return finalImage;
    }
}
