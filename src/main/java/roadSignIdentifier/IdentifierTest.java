package roadSignIdentifier;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.File;

public class IdentifierTest {
    public IdentifierTest(){}

    public void test(){
        /*
        Test la fonction d'identification avec les differentes images de panneaux de reference
         */
        String[] labelList= {"panneau 30","panneau 50","panneau 70","panneau 90","panneau 110","panneau double"};
        File f30 = new File("Images/limite_30.jpg");
        Mat m_30 = Highgui.imread(f30.getAbsolutePath());
        File f50 = new File("Images/limite_50.JPG");
        Mat m_50 = Highgui.imread(f50.getAbsolutePath());
        File f70 = new File("Images/limite_70.jpg");
        Mat m_70 = Highgui.imread(f70.getAbsolutePath());
        File f90 = new File("Images/limite_90.jpg");
        Mat m_90 = Highgui.imread(f90.getAbsolutePath());
        File f30_2 = new File("Images/limite_30_(2).jpg");
        Mat m_30_2 = Highgui.imread(f30_2.getAbsolutePath());

        Mat[] refList={m_30,m_50,m_70,m_90};

        RoadSignIdentifier rdi = new RoadSignIdentifier(refList,labelList);
        System.out.println("resultat 30: "+rdi.identifier(m_30));
        System.out.println("resultat 30 2: "+rdi.identifier(m_30_2));
    }
}
