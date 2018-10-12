package com.jyd.bms.tool;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Locale;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.reloading.ReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigurationUtils
{
    private static final Logger log;
    private static final MultiKeyMap CACHE;
    private static final Object TOKEN;
    
    static {
        log = LoggerFactory.getLogger((Class)ConfigurationUtils.class);
        CACHE = new MultiKeyMap();
        TOKEN = new Object();
    }
    
    public static Configuration getConfiguration(final String baseName, final Locale locale, final boolean delimiterParsingDisabled, final boolean cache) throws FileNotFoundException, ConfigurationException {
        return getConfiguration(baseName, locale, delimiterParsingDisabled, cache, false);
    }
    
    public static Configuration getConfiguration(final String baseName, final Locale locale, final boolean delimiterParsingDisabled, final boolean cache, final boolean dynamic) throws FileNotFoundException, ConfigurationException {
        PropertiesConfiguration config = null;
        if (cache) {
            config = (PropertiesConfiguration)ConfigurationUtils.CACHE.get((Object)baseName, (Object)locale);
        }
        if (config == null) {
            final URL url = PropertiesUtils.getPropertiesFileURL(baseName, locale);
            ConfigurationUtils.log.info("Load configuration (baseName:{}, locale:{}) from {}.", new Object[] { baseName, locale, url });
            config = getPropertiesConfiguration(url, delimiterParsingDisabled, dynamic);
            if (cache) {
                synchronized (ConfigurationUtils.TOKEN) {
                    if (!ConfigurationUtils.CACHE.containsKey((Object)baseName, (Object)locale)) {
                        ConfigurationUtils.CACHE.put((Object)baseName, (Object)locale, (Object)config);
                    }
                }
            }
        }
        return (config == null) ? null : ((Configuration)config.clone());
    }
    
    private static PropertiesConfiguration getPropertiesConfiguration(final URL url, final boolean delimiterParsingDisabled, final boolean dynamic) throws ConfigurationException {
        final PropertiesConfiguration config = new PropertiesConfiguration();
        config.setEncoding("UTF-8");
        config.setDelimiterParsingDisabled(delimiterParsingDisabled);
        config.load(url);
        if (dynamic) {
            final FlowQueryFileChangedReloadingStrategy fileChangedReloadingStrategy = new FlowQueryFileChangedReloadingStrategy();
            config.setReloadingStrategy((ReloadingStrategy)fileChangedReloadingStrategy);
            fileChangedReloadingStrategy.setFileName(url.getFile());
        }
        return config;
    }
    
    public static Configuration getConfiguration(final String baseName, final Locale locale) throws FileNotFoundException, ConfigurationException {
        return getConfiguration(baseName, locale, false, true);
    }
    
    public static Configuration getConfiguration(final String fileName, final boolean delimiterParsingDisabled, final boolean cache) throws FileNotFoundException, ConfigurationException {
        return getConfiguration(fileName, delimiterParsingDisabled, cache, false);
    }
    
    public static Configuration getConfiguration(final String fileName, final boolean delimiterParsingDisabled, final boolean cache, final boolean dynamic) throws FileNotFoundException, ConfigurationException {
        PropertiesConfiguration config = null;
        if (cache) {
            config = (PropertiesConfiguration)ConfigurationUtils.CACHE.get((Object)fileName, (Object)null);
        }
        if (config == null) {
            final URL url = PropertiesUtils.getResource(fileName);
            ConfigurationUtils.log.info("Load configuration (fileName:{}) from {}.", (Object)fileName, (Object)url);
            config = getPropertiesConfiguration(url, delimiterParsingDisabled, dynamic);
            if (cache) {
                synchronized (ConfigurationUtils.TOKEN) {
                    if (!ConfigurationUtils.CACHE.containsKey((Object)fileName, (Object)null)) {
                        ConfigurationUtils.CACHE.put((Object)fileName, (Object)null, (Object)config);
                    }
                }
                // monitorexit(ConfigurationUtils.TOKEN)
            }
        }
        return (config == null) ? null : ((Configuration)config.clone());
    }
    
    public static Configuration getConfiguration(final String fileName) throws FileNotFoundException, ConfigurationException {
        return getConfiguration(fileName, false, true);
    }
    
    public static Configuration getXMLConfiguration(final String fileName) throws FileNotFoundException, ConfigurationException {
        return getXMLConfiguration(fileName, false, true);
    }
    
    public static Configuration getXMLConfiguration(final String fileName, final boolean delimiterParsingDisabled, final boolean cache) throws FileNotFoundException, ConfigurationException {
        return getXMLConfiguration(fileName, delimiterParsingDisabled, cache, false);
    }
    
    public static Configuration getXMLConfiguration(final String fileName, final boolean delimiterParsingDisabled, final boolean cache, final boolean dynamic) throws FileNotFoundException, ConfigurationException {
        XMLConfiguration config = null;
        if (cache) {
            config = (XMLConfiguration)ConfigurationUtils.CACHE.get((Object)fileName, (Object)null);
        }
        if (config == null) {
            final URL url = PropertiesUtils.getResource(fileName);
            ConfigurationUtils.log.info("Load configuration (fileName:{}) from {}.", (Object)fileName, (Object)url);
            config = new XMLConfiguration();
            config.setEncoding("UTF-8");
            config.setDelimiterParsingDisabled(delimiterParsingDisabled);
            config.load(url);
            if (dynamic) {
                final FlowQueryFileChangedReloadingStrategy fileChangedReloadingStrategy = new FlowQueryFileChangedReloadingStrategy();
                config.setReloadingStrategy((ReloadingStrategy)fileChangedReloadingStrategy);
                fileChangedReloadingStrategy.setFileName(url.getFile());
            }
            if (cache) {
                synchronized (ConfigurationUtils.TOKEN) {
                    if (!ConfigurationUtils.CACHE.containsKey((Object)fileName, (Object)null)) {
                        ConfigurationUtils.CACHE.put((Object)fileName, (Object)null, (Object)config);
                    }
                }
                // monitorexit(ConfigurationUtils.TOKEN)
            }
        }
        return (config == null) ? null : ((Configuration)config.clone());
    }
    
    private static class FlowQueryFileChangedReloadingStrategy extends FileChangedReloadingStrategy
    {
        private static final Logger log;
        private String fileName;
        private static final Configuration CONFIG;
        
        static {
            log = LoggerFactory.getLogger((Class)FlowQueryFileChangedReloadingStrategy.class);
            try {
                CONFIG = ConfigurationUtils.getConfiguration("global.properties", false, false);
            }
            catch (Exception e) {
                final String errorMsg = "initial global.properties CONFIGURATION occurs error!";
                FlowQueryFileChangedReloadingStrategy.log.error(errorMsg, (Throwable)e);
                throw new RuntimeException(errorMsg, e);
            }
        }
        
        public FlowQueryFileChangedReloadingStrategy() {
            this.setRefreshDelay(FlowQueryFileChangedReloadingStrategy.CONFIG.getLong("dynamic.refresh.delay"));
        }
        
        public void reloadingPerformed() {
            super.reloadingPerformed();
            FlowQueryFileChangedReloadingStrategy.log.info("=========FileName {} is reload and Reload Time is {}====", (Object)this.getFileName(), (Object)new Timestamp(System.currentTimeMillis()));
        }
        
        public String getFileName() {
            return this.fileName;
        }
        
        public void setFileName(final String fileName) {
            this.fileName = fileName;
        }
    }
}
