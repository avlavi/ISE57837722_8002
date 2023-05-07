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
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(j, i, new Color(255, 0, 0));
                else
                    imageWriter.writePixel(j, i, new Color(255, 255, 0));

            }
        }
        imageWriter.writeToImage();

    }
}