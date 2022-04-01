package imageReading;

import org.opencv.core.Mat;

public class ReadingTest {
    public ReadingTest(){}

    public void test() {
        /*
        Test la fonction de lecture avec les differentes images de panneaux de reference
         */
    ImageReading IR = new ImageReading();
    Mat mat = IR.LectureImage("Images/limite_30.jpg");
    //IR.ImShow("caca", mat);
    System.out.println(mat);

    }



}
