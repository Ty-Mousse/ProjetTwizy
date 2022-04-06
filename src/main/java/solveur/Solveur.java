package solveur;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import roadSignIdentifier.RoadSignIdentifier;
import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

import static roadSignDetection.RoadSignDetection.extrairePanneau;

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

    public static Mat solve(Mat m) {
        List<Mat> results = extrairePanneau(m);
        List<String> labels = null;
        for (Mat current:results) {
            assert false;
            labels.add(signIdentifier.identifier(current));
        }
        return m;
    }
}
