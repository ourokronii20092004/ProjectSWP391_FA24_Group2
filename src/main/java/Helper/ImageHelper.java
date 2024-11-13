/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class ImageHelper {

    private static final String DEFAULT_IMAGE = "default.jpg";

    public static String saveImage(Part filePart, String folderName, String servletContextPath) {
        String originalFileName = getFileName(filePart);
        String newFileName;

        if (filePart != null && filePart.getSize() > 0) {
            String contentType = filePart.getContentType();
            if (!isValidImageType(contentType)) {
                System.out.println("Image of the wrong type.");
                return null;
            }

            String fileExtension = getFileExtension(originalFileName);
            newFileName = generateImageName() + fileExtension;

            String relativeUploadPath = "img" + File.separator + folderName + File.separator + newFileName;
            String fullUploadPath = servletContextPath + relativeUploadPath; //use servlet context path

            File targetFile = new File(fullUploadPath);
            File parentDir = targetFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try ( InputStream imageStream = filePart.getInputStream();  OutputStream out = new FileOutputStream(fullUploadPath)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = imageStream.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                System.out.println("Image saved at: " + fullUploadPath); //DEBUG

                return relativeUploadPath;
            } catch (IOException e) {
                System.err.println("Error saving image: " + e.getMessage());
                return null;
            }
        } else {
            newFileName = "img" + File.separator + folderName + File.separator + DEFAULT_IMAGE;
            System.out.println("Image not uploaded. Default: " + newFileName);
            return newFileName;
        }
    }

    private static String generateImageName() {
        return "image_" + System.currentTimeMillis();
    }

    private static String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
    }

    private static String getFileName(Part part) {
        if (part != null && part.getHeader("content-disposition") != null) {
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                }
            }
        }
        return null;
    }

    private static boolean isValidImageType(String contentType) {
        if (contentType == null) {
            return false;
        }
        switch (contentType) {
            case "image/jpeg":
            case "image/png":
            case "image/jpg":
                return true;
            default:
                return false;
        }
    }

}
