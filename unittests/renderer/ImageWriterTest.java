package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing renderer.ImageWriter
 *
 * @author Shilo and Aviad
 */
class ImageWriterTest {

    /**
     * Test method for {@link .renderer.ImageWriter#WriteToImage(.renderer.ImageWriter)}.
     * In this test we will create a grid of 10x16 red squares
     * on a yellow background measuring 800x500 pixels
     */
    @Test
    void testWriteToImage() {
        // ============ Equivalence Partitions Tests ==============
        ImageWriter imageWriter = new ImageWriter("test", 800, 500);//create a file for the image
        for (int i = 0; i < 500; ) {//Goes over the image from top to bottom. rows
            for (int j = 0; j < 800; j++) {//Draws a red line
                imageWriter.writePixel(j, i, new Color(255, 0, 0));
            }
            i++;
            ///////Paint a line of yellow squares
            for (int h = 0; h < 49; h++, i++) {
                for (int j = 0; j < 800; j += 50) {//column of red
                    imageWriter.writePixel(j, i, new Color(255, 0, 0));
                    for (int k = 1; k < 50; k++) {//column of yellow
                        imageWriter.writePixel(k + j, i, new Color(255, 255, 0));
                    }
                }

            }
            ///////
        }
        imageWriter.writeToImage();

    }
    // =============== Boundary Values Tests ==================

}