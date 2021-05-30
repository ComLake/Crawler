package com.company.file_config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageFile {

    public static byte[] imageToByteArray(File file) {
        ByteArrayOutputStream baos = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, imageType(file.getName()), baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static String imageType(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }
}
