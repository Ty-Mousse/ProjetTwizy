package roadSignIdentifier;

import display.Window;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.*;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static imageReading.ImageReading.ImShow;

public class RoadSignIdentifier {

    Mat[] refList;
    String[] labelList;

    public RoadSignIdentifier(Mat[] refList, String[] labelList){
        /*
        Constructeur: Prend en paramatre un tableau d'image de reference et un tableau de label
         */
        this.refList = refList;
        this.labelList = labelList;
    }

    public String identifier(Mat imgTest) {
        /*
        Prend en parametre une image a identifier
        Compare cette image avec toutes celles de la liste des images de reference
        Etablit un critere de matching en l'image test et chaque images de reference
        Critere: min de la moyenne des distances pour chaque Dmatch
        Renvoie le label qui correspond a l'image de ref dont le critere est le meilleyr
         */
        List<Double> qualityCriteria = new ArrayList<>();

        //resize and normalize
        Mat grayImgTest = new Mat(imgTest.rows(), imgTest.cols(), imgTest.type());
        Imgproc.resize(imgTest, imgTest, refList[0].size());
        Imgproc.cvtColor(imgTest, grayImgTest, Imgproc.COLOR_BGRA2GRAY);
        Core.normalize(grayImgTest, grayImgTest, 0, 255, Core.NORM_MINMAX);

        // Creation du detecteur et extracteur d'orbe
        FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

        // creation du descriptor img test
        MatOfKeyPoint imgKeyPointTest = new MatOfKeyPoint();
        Mat imgDescriptorTest = new Mat(imgTest.rows(), imgTest.cols(), imgTest.type());

        orbDetector.detect(grayImgTest, imgKeyPointTest);
        orbExtractor.compute(imgTest, imgKeyPointTest, imgDescriptorTest);

        for (Mat imgRef : refList) {
            /*
            grayImgTest: panneau en nuance de gris et mis à l'echelle
            imgRef: image de reference
            */

            // Resize and normalize
            Mat grayImgRef = new Mat(imgRef.rows(), imgRef.cols(), imgRef.type());
            Imgproc.cvtColor(imgRef, grayImgRef, Imgproc.COLOR_BGRA2GRAY);
            Core.normalize(grayImgRef, grayImgRef, 0, 255, Core.NORM_MINMAX);
            // Imgproc.resize(grayImgRef, grayImgRef, grayImgTest.size());

            // Crétion du descriptor img ref
            MatOfKeyPoint imgKeyPointRef = new MatOfKeyPoint();
            Mat imgDescriptorRef = new Mat(imgRef.rows(), imgRef.cols(), imgRef.type());

            orbDetector.detect(grayImgRef, imgKeyPointRef);
            orbExtractor.compute(imgRef, imgKeyPointRef, imgDescriptorRef);

            //Match entre imgDescriptorRef et imgDescriptorTest
            MatOfDMatch matchs = new MatOfDMatch();
            DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
            matcher.match(imgDescriptorTest, imgDescriptorRef, matchs);

            /*

            Mat matchedImage = new Mat(grayImgRef.rows(), grayImgRef.cols()*2, grayImgRef.type());
            Features2d.drawMatches(grayImgTest, imgKeyPointTest, grayImgRef, imgKeyPointRef, matchs, matchedImage);
            ImShow("testo",matchedImage);

             */

            //critère sur la distance minimal entre les KeyPoints des images
            List<DMatch> DmatchList = matchs.toList();
            int cpt = 0;
            double total = 0;
            for (DMatch dmatch : DmatchList) {
                cpt++;
                total += dmatch.distance;
            }
            qualityCriteria.add(total / cpt);// A TESTER
        }
        int index = qualityCriteria.indexOf(Collections.min(qualityCriteria));
        return labelList[index];
    }
}
