
package Pizza;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScaleImage {
	//method created by refactor
	public Image getScaledImage(File inputImage) throws IOException {
		BufferedImage originalImage = ImageIO.read(inputImage);
		int width = originalImage.getWidth(); 
		int height = originalImage.getHeight(); 
		width = width / 3;
		height = height / 3;
		Image image = originalImage.getScaledInstance(width, height,
				Image.SCALE_DEFAULT);
		return image;
	}
}
