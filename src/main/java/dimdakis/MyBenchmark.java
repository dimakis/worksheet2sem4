/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package dimdakis;

//import javafx.scene.control.*;
//import javafx.scene.image.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.PixelReader;
//import javafx.scene.image.PixelWriter;
//import javafx.scene.image.WritableImage;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.openjdk.jmh.annotations.Benchmark;
//import Utils.Find;
//import Utils.Union;
//import javafx.application.Platform;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.*;
//import javafx.scene.image.*;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.awt.*;
import java.io.File;

//import javaimport Utils.Find;
//import Utils.Union;


public class MyBenchmark {

//    @Benchmark
//    public void setGrayScale() {
//        File newFile = new File("/home/dimdakis/Pictures/homerAndPeter.jpeg");
//        Image im = null;
//        try {
//            im = new Image(new FileInputStream(newFile));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        PixelReader pixelReader = im.getPixelReader();
//        WritableImage writableImage = new WritableImage(
//                (int) im.getWidth(), (int) im.getHeight());
//        PixelWriter pixelWriter = writableImage.getPixelWriter();
//
//        for (int y = 0; y < im.getHeight(); y++) {
//            for (int x = 0; x < im.getWidth(); x++) {
//                Color color = pixelReader.getColor(x, y);
//                double r = color.getRed();
//                double g = color.getGreen();
//                double b = color.getBlue();
//                int newColor = (int) ((r + g + b) / 3 * 255);
////                            int green = (int) ((r + g + b) / 3 * 255);
////                            int blue = (int) ((r + g + b) / 3 * 255);
//                color = Color.rgb(newColor, newColor, newColor); //green, blue);
//                pixelWriter.setColor(x, y, color);
//            }
//        }
//        imageViewEdited.setImage(writableImage);
//    }
//        );
//

    @Benchmark
    public void testMethod() {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.

        File newFile = new File("/home/dimdakis/Pictures/bloodCellsAssignment1/bloodCells2.jpeg");
//        Image im = new Image();
        try {
            Image im = new Image(new FileInputStream(newFile));
            //reading color from loaded image
            PixelReader pixelReader = im.getPixelReader();
            WritableImage writableImage = new WritableImage(
                    (int) im.getWidth(), (int) im.getHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            double aspectRatio = im.getWidth() / im.getHeight();
            int redAmount = 80;//(int) redSlider.getValue();
            int purpleAmount = 35;//(int) purpleSlider.getValue();
            int[] redCellArray = new int[(int) (im.getWidth() * (int) im.getHeight())];
            int[] whiteCellArray = new int[(int) (im.getWidth() * (int) im.getHeight())];
            int whitePixel = -1;

            for (int y = 0; y < im.getHeight(); y++) {
                for (int x = 0; x < im.getWidth(); x++) {
                    javafx.scene.paint.Color color = pixelReader.getColor(x, y);
                    double r = color.getRed() * 255;
                    double g = color.getGreen() * 255;
                    double b = color.getBlue() * 255;
                    //finding a red cell and making it bright red
//                if (r > 80 && (r - b) > 20 && r > g && r > b) {
                    if (r > redAmount && (r - b) > redAmount - 60 && r > g && r > b) {
                        r = 200;
                        g = 10;
                        b = 10;
                        redCellArray[(y * (int) im.getWidth()) + x] = (y * (int) im.getWidth()) + x;
                        whiteCellArray[(y * (int) im.getWidth()) + x] = whitePixel;
                    }
                    //setting purple 'white blood cells' nuclei
                    else if (Math.min(r, b - 12) - g > purpleAmount) {// && b > g && b > r) {
                        b = 160;
                        r = 140;
                        g = 75;
                        redCellArray[(y * (int) im.getWidth()) + x] = whitePixel;
                        whiteCellArray[(y * (int) im.getWidth()) + x] = (y * (int) im.getWidth()) + x;
                    }
                    //setting white background pixels
                    else {
                        b = r = g = 255;
                        redCellArray[(y * (int) im.getWidth()) + x] = whitePixel;
                        whiteCellArray[(y * (int) im.getWidth()) + x] = whitePixel;
                    }
                    javafx.scene.paint.Color c3 = Color.rgb((int) r, (int) g, (int) b);
                    pixelWriter.setColor(x, y, c3);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    public void testMethod2() {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
    }

}
