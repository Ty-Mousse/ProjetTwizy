package main;

import display.Window;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import solveur.Solveur;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Function<Mat, Mat> function = Solveur.solve;
        Window window = new Window("PanelFinder", 540, 240, function);
    }
}
