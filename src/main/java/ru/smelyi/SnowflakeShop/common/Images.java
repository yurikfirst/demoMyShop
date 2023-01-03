package ru.smelyi.SnowflakeShop.common;

import ru.smelyi.SnowflakeShop.models.database.entities.Snowflake;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

public class Images {
	private static final String WEB_IMAGES_PATH = "src/main/resources/static/images/snowflakes/snowflake?.png";

	/**
	 * Извлекает из БД изображения на диск для последующей подгрузки в представлении
	 */
	public void prepareImageFiles(List<Snowflake> snowflakes) {
		for (Snowflake snowflake : snowflakes) {
			try {
				File file = new File(WEB_IMAGES_PATH.replace("?", Integer.toString(snowflake.getId())));
				if (file.exists()) {
					file.delete();
				}
				ByteArrayInputStream imageStream = new ByteArrayInputStream(snowflake.getImage());
				BufferedImage bufImage = ImageIO.read(imageStream);
				ImageIO.write(bufImage, "png", file);
//                Thread.sleep(100);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
