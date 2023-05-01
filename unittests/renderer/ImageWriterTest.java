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
     */
    @Test
    void testWriteToImage() {
        // ============ Equivalence Partitions Tests ==============
        ImageWriter imageWriter = new ImageWriter("test", 800, 500);
        for (int i = 0; i < 500; ) {
            for (int j = 0; j < 800; j++) {
                imageWriter.writePixel(j, i, new Color(255, 0, 0));
            }//line of rad
            i++;
            for (int h = 0; h < 49; h++, i++) {
                for (int j = 0; j < 800; j += 50) {//column of red
                    imageWriter.writePixel(j, i, new Color(255, 0, 0));
                    for (int k = 1; k < 50; k++) {//column of yellow
                        imageWriter.writePixel(k+j, i, new Color(255, 255, 0));
                    }
                }

            }

        }
        imageWriter.writeToImage();

    }
    // =============== Boundary Values Tests ==================

}
