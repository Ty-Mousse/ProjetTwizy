package main;

import display.Window;
import org.opencv.core.Core;

public class Main {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Window window = new Window("PanelFinder", 540, 240);
    }
}
