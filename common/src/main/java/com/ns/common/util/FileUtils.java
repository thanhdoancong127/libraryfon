package com.ns.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(index + 1);
        }
        return null;
    }

    public static String getFileName(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(0, index);
        }
        return null;
    }

    public static String storeImageToResource(String folderPathnFileName, MultipartFile image) {
        String fileName = CommonUtils.generateFile(16);
        String extension = FileUtils.getExtension(image.getOriginalFilename());
        String path = folderPathnFileName + fileName + "." + extension;
        try (FileOutputStream fos = new FileOutputStream(new File(path))) {
            fos.write(image.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName + "." + extension;
    }

    public static void deleteFile(String folderPathnFileName) {
        File file = new File(folderPathnFileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
