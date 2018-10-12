package com.jyd.bms.tool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils
{
    private static Logger log;
    private static final ClassLoader LOADER;
    
    static {
        PropertiesUtils.log = LoggerFactory.getLogger((Class)PropertiesUtils.class);
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
            PropertiesUtils.log.info("classloader is using context class loader");
            LOADER = loader;
        }
        else {
            PropertiesUtils.log.info("current classloader is using normal class loader");
            LOADER = PropertiesUtils.class.getClassLoader();
        }
    }
    
    public static Properties getPropertiesFromClassPath(final String fileLocation) {
        final Properties properties = new Properties();
        URL url = getResource(fileLocation);
        if (url == null) {
            url = getResource(fileLocation);
            if (url == null) {
                final String msg = "load properties file from " + fileLocation + ", but not found";
                PropertiesUtils.log.error(msg);
                throw new RuntimeException(msg);
            }
        }
        else {
            PropertiesUtils.log.info("loaded properties from resource {}: {} ", (Object)fileLocation, (Object)properties);
            try {
                final InputStream stream = url.openStream();
                properties.load(stream);
                stream.close();
            }
            catch (IOException ex) {
                throw new RuntimeException("problem loading properties from " + fileLocation + "!", ex);
            }
        }
        PropertiesUtils.log.info("load properties complete...");
        return properties;
    }
    
    public static Properties getProperties(final String fileLocation, final String baseName, final Locale locale) throws FileNotFoundException, MalformedURLException {
        PropertiesUtils.log.info("Get the specific resource bundle file...(folder:{}, baseName:{}, locale:{})", new Object[] { fileLocation, baseName, locale });
        final String baseFile = String.valueOf(fileLocation) + "/" + baseName + "?.properties";
        PropertiesUtils.log.debug(" + base file:{}", (Object)baseFile);
        String filePath = baseFile.replaceAll("\\?", "_" + locale.toString());
        PropertiesUtils.log.debug(" + try get file!({})", (Object)filePath);
        URL url = new File(filePath).toURL();
        if (url == null) {
            filePath = baseFile.replaceAll("\\?", "_" + locale.getLanguage());
            PropertiesUtils.log.debug(" + try get file!({})", (Object)filePath);
            url = new File(filePath).toURL();
            if (url == null) {
                filePath = baseFile.replaceAll("\\?", "");
                PropertiesUtils.log.debug(" + try get file!({})", (Object)filePath);
                url = new File(filePath).toURL();
                if (url == null) {
                    throw new FileNotFoundException("The specific resource bundle file not found!(folder:" + fileLocation + ", baseName:" + baseName + ", locale:" + locale + ")");
                }
            }
        }
        final Properties properties = new Properties();
        try {
            final InputStream stream = url.openStream();
            properties.load(stream);
            stream.close();
        }
        catch (IOException ex) {
            throw new RuntimeException("problem loading properties from " + fileLocation + "!", ex);
        }
        PropertiesUtils.log.info("load properties complete...");
        return properties;
    }
    
    public static URL getPropertiesFileURL(final String baseName, final Locale locale) throws FileNotFoundException {
        if (baseName == null) {
            throw new NullPointerException("The specific baseName can't be null!");
        }
        PropertiesUtils.log.info("Get the specific resource bundle file...(baseName: {}, locale:{})", (Object)baseName, (Object)locale);
        final String baseFile = String.valueOf(baseName) + "?.properties";
        PropertiesUtils.log.debug(" + base file:{}", (Object)baseFile);
        String filePath = null;
        URL url = null;
        if (locale == null) {
            PropertiesUtils.log.warn("The specific locale is null!");
            filePath = baseFile.replaceAll("\\?", "");
            url = getResource(filePath);
        }
        else {
            filePath = baseFile.replaceAll("\\?", "_" + locale.toString());
            url = getResource(filePath);
            if (url == null) {
                filePath = baseFile.replaceAll("\\?", "_" + locale.getLanguage());
                url = getResource(filePath);
                if (url == null) {
                    filePath = baseFile.replaceAll("\\?", "");
                    url = getResource(filePath);
                }
            }
        }
        if (url == null) {
            throw new FileNotFoundException("The specific resource bundle file not found!(baseName:" + baseName + ", locale:" + locale + ")");
        }
        PropertiesUtils.log.info("get file:{}", (Object)url);
        return url;
    }
    
    public static URL getResource(final String filePath) {
        PropertiesUtils.log.info("Get the specific resource bundle file....(filePath:{})", (Object)filePath);
        URL url = PropertiesUtils.LOADER.getResource(filePath);
        if (url == null) {
            PropertiesUtils.log.warn("No resource({}) get by classloader. Try by class.getResource.", (Object)filePath);
            url = Class.class.getResource(filePath);
        }
        return url;
    }
    
    public static InputStream getResourceAsStream(final String filePath) {
        PropertiesUtils.log.info("Get the specific resource bundle file....(filePath:{})", (Object)filePath);
        return PropertiesUtils.LOADER.getResourceAsStream(filePath);
    }
}
