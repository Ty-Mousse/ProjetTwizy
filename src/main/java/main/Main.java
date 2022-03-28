package main;

import display.Window;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import roadSignIdentifier.RoadSignIdentifier;

import java.io.File;
import java.util.function.Function;


public class Main {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String[] labelList= {"Panneau 30","Panneau 50","Panneau 70","Panneau 90","Panneau 110","Panneau double"};
        File f30 = new File("Images/ref_30.jpg");
        Mat m_30 = Highgui.imread(f30.getAbsolutePath());
        File f50 = new File("Images/ref_50.JPG");
        Mat m_50 = Highgui.imread(f50.getAbsolutePath());
        File f70 = new File("Images/ref_70.jpg");
        Mat m_70 = Highgui.imread(f70.getAbsolutePath());
        File f90 = new File("Images/ref_90.jpg");
        Mat m_90 = Highgui.imread(f90.getAbsolutePath());
        File f30_2 = new File("Images/ref_interdiction_doubler.jpg");
        Mat m_30_2 = Highgui.imread(f30_2.getAbsolutePath());
        Mat[] refList={m_30, m_50, m_70, m_90, m_30_2};
        RoadSignIdentifier identifier = new RoadSignIdentifier(refList, labelList);

        Function<Mat, String> function = identifier::identifier;
        Window window = new Window("PanelFinder", 540, 240, function);
    }
}
