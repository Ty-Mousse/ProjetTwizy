package roadSignIdentifier;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.features2d.DMatch;
import org.opencv.highgui.Highgui;
import roadSignDetection.ResultSignDetection;
import roadSignDetection.RoadSignDetection;

import java.awt.geom.RectangularShape;
import java.io.File;
import java.util.List;

public class IdentifierTest {
    public IdentifierTest(){}

    public void test(){
        /*
        Test la fonction d'identification avec les differentes images de panneaux de reference
         */
        String[] labelList= {"Panneau 30","Panneau 50","Panneau 70","Panneau 90","Panneau 110","Panneau double"};
        File f30 = new File("Images/ref_30.jpg");
        Mat m_30 = Highgui.imread(f30.getAbsolutePath());
        File f50 = new File("Images/ref_50.JPG");
        Mat m_50 = Highgui.imread(f50.getAbsolutePath());
        File f70 = new File("Images/ref_70.jpg");
        Mat m_70 = Highgui.imread(f70.getAbsolutePath());
        File f90 = new File("Images/ref_90.jpg");
        Mat m_90 = Highgui.imread(f90.getAbsolutePath());
        File f110 = new File("Images/ref_110.jpg");
        Mat m_110 = Highgui.imread(f110.getAbsolutePath());
        File fID = new File("Images/ref_interdiction_doubler.jpg");
        Mat m_ID = Highgui.imread(fID.getAbsolutePath());
        File f30_2 = new File("Images/limite_30_(2).jpg");
        Mat m_30_2 = Highgui.imread(f30_2.getAbsolutePath());

        Mat[] refList={m_30,m_50,m_70,m_90,m_110,m_ID};

        Mat mat=m_30_2;

        RoadSignDetection rdd = new RoadSignDetection();
        RoadSignIdentifier rdi = new RoadSignIdentifier(refList,labelList);

        ResultSignDetection res = rdd.extrairePanneau(mat);
        List<Mat> images = res.images;
        List<MatOfPoint> mop = res.coords;

        /*
        for (Mat extraction : images){
            System.out.println("\n***************\n");
            System.out.println(rdi.identifier(extraction));
        }

        System.out.print("mop : ");
        System.out.println(mop.size());
        System.out.println("images : "+images.size());
*/
        System.out.println("detection 110 : "+rdi.identifier(m_110));
    }
}
