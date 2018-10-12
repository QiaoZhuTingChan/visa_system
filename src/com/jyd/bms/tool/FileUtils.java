package com.jyd.bms.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private static final Logger log;
    public static final MimetypesFileTypeMap MIME_TYPES;
    private static final String FILE_CHARSET = "UTF-8";
    
    static {
        log = LoggerFactory.getLogger((Class)FileUtils.class);
        MIME_TYPES = new MimetypesFileTypeMap();
    }
    
    public static void deleteFileOnExit(final File file, final boolean deleteParentFile) {
        boolean isDelete = file.delete();
        FileUtils.log.debug("delete file {} ... Result: {}.", (Object)file.getAbsolutePath(), (Object)isDelete);
        if (!isDelete) {
            FileUtils.log.warn("file {} delete fail! RETRY delete it on exit!", (Object)file.getAbsolutePath());
            file.deleteOnExit();
        }
        if (deleteParentFile) {
            final File parentFolder = file.getParentFile();
            isDelete = parentFolder.delete();
            FileUtils.log.debug("remove the parant folder {} ... Result: {}. ", (Object)parentFolder, (Object)isDelete);
        }
    }
    
    public static void makeFolderExists(final String folder) {
        final File file = new File(folder);
        makeFolderExists(file);
    }
    
    public static void makeFolderExists(final File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    public static File createTempFile(final InputStream is, final String prefix, final String suffix, final String fileFolder) throws IOException {
        final File targetFile = createTempFile(prefix, suffix, fileFolder);
        inputStreamToFile(is, targetFile);
        return targetFile;
    }
    
    public static File createTempFile(final String prefix, final String suffix, final String fileFolder) throws IOException {
        makeFolderExists(fileFolder);
        return File.createTempFile(prefix, suffix, new File(fileFolder));
    }
    
    public static File createFile(final InputStream is, final String fileFolder, final String fileName) throws IOException {
        makeFolderExists(fileFolder);
        final File targetFile = new File(String.valueOf(fileFolder) + fileName);
        inputStreamToFile(is, targetFile);
        return targetFile;
    }
    
    private static File inputStreamToFile(final InputStream is, final File file) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            IOUtils.copy(is, os);
        }
        finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        if (os != null) {
            os.flush();
            os.close();
        }
        return file;
    }
    
    public static String getContentType(final String fileName) {
        final String contentType = FileUtils.MIME_TYPES.getContentType(fileName);
        if ("application/octet-stream".equals(contentType)) {
            FileUtils.log.error("file {} mimetype mapping not found. Please add new ContentType record in /META-INF/mime.types", (Object)fileName);
        }
        return contentType;
    }
    
    public static byte[] base64StringToByteArray(final String base64String) {
        try {
            return Base64.decodeBase64(base64String.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException ex) {
            final String errorMsg = "The encoding: UTF-8 isn't support!!";
            FileUtils.log.error(errorMsg, (Throwable)ex);
            throw new RuntimeException(errorMsg);
        }
    }
    
    public static String byteArrayToBase64String(final byte[] fileByteArray) {
        try {
            return new String(Base64.encodeBase64(fileByteArray), "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            final String errorMsg = "The encoding: UTF-8 isn't support!!";
            FileUtils.log.error(errorMsg, (Throwable)ex);
            throw new RuntimeException(errorMsg);
        }
    }
}
