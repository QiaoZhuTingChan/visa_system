package com.jyd.bms.common;

import java.io.FileNotFoundException;
import java.sql.Timestamp;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jyd.bms.tool.ConfigurationUtils;
import com.jyd.bms.tool.zk.Messagebox;

public class Environment {
	private static final Logger log = LoggerFactory.getLogger(Environment.class);

	public static final Configuration CONFIGURATION;
	public static final String DEFAULT_LOGIN_NAME = "defaultLoginName";
	public static final String DateMillis = "dataMillis";
	public static final String REDIS_SERVER = "redis_Server";
	public static final String REDIS_PORT = "redis_Port";
	public static final String REDIS_EXPIRE = "redis_Expire";
	public static final String REDIS_MAXTOTAL = "redis_MaxTotal";
	public static final String REDIS_MAXIDLE = "redis_MaxIdle";
	public static final String REDIS_MAXWAITMILLIS = "redis_MaxWaitMillis";
	public static final String REDIS_PASSWORD = "redis_Password";
	public static final String REDIS_ONSERVICE = "redis_OnService";
	public static final String WEBSOCKET_URL = "websocket_url";
	public static final String WEBSOCKET_TYPE = "websocket_type";
	public static final String ENDPOINT = "endpoint";
	public static final String BUCKET_NAME = "bucketName";
	public static final String ACCESS_KEYID = "AccessKeyID";
	public static final String ACCESS_KEYSECRET = "AccessKeySecret";

	public static final String FILE_PROCESS_TYPE = "fileProcessType";
	public static final String FILE_PATH = "filePath";
	public static final String WEB_FILE_PATH = "webFilePath";

	// 白骑士
	public static final String HTML_PHYSICS_PATH = "htmlPhysicsPath";
	public static final String HTML_RELATIVE_PATH = "htmlRelativePath";
	public static final String BAIQISHI_PARTNERID = "baiQiShiPartnerId";
	public static final String BAIQISHI_VERIFYKEY = "baiQiShiVerifyKey";
	public static final String BAIQISHI_URL = "baiQiShiUrl";

	// 同盾
	public static final String TONGDUN_HTML_PHYSICS_PATH = "tongDunHtmlPhysicsPath";
	public static final String TONGDUN_HTML_RELATIVE_PATH = "tongDunHtmlRelativePath";
	public static final String TONGDUN_PARTNERCODE = "tongDunPartnerCode";
	public static final String TONGDUN_PARTNERKEY = "tongDunPartnerKey";
	public static final String TONGDUN_URL = "tongDunUrl";
	public static final String TONGDUN_APPNAME = "tongDunAppName";

	// 第三方gps
	public static final String GPS_IP = "gpsIp";
	public static final String GPS_KEY = "gpsKey";
	
	// 聚信立
	public static final String JUXINLI_CLIENT_SECRET = "clientSecret";
	// 蜜蜂
	public static final String HONEYBEE_HOST_URL = "honeybeeHostUrl";
	public static final String HONEYBEE_HTML_PHY_PATH = "honeybeeHtmlPhyPath";
	public static final String HONEYBEE_HTML_REL_PATH = "honeybeeHtmlRelPath";
	// 蜜罐
	public static final String HONEYPOT_HOST_URL = "honeypotHostUrl";
	public static final String HONEYPOT_HTML_PHY_PATH = "honeypotHtmlPhyPath";
	public static final String HONEYPOT_HTML_REL_PATH = "honeypotHtmlRelPath";

	public static final String GENERATE_CODE_PATH = "generateCodePath";

	public static final String BMS_TITLE = "bmsTitle";
	public static final String ENCRYPTION_KEY = "encryptionKey";
	public static final String NEED_IMAGE_CODE = "needImageCode";

	static {
		try {
			CONFIGURATION = ConfigurationUtils.getConfiguration("bms_config.properties");
		} catch (ConfigurationException ex) {
			String msg = "Initial environment configuration occurs error";
			log.error(msg, ex);
			throw new RuntimeException(msg, ex);
		} catch (FileNotFoundException ex) {
			String msg = "Initial environment configuration occurs error";
			log.error(msg, ex);
			throw new RuntimeException(msg, ex);
		}
	}

	/**
	 * No any Environment instance.
	 */
	private Environment() {
	}

	//JUXINLI_CLIENT_SECRET
	public static String getClientSecret() {
		String str = "";
		try {
			str = CONFIGURATION.getString(JUXINLI_CLIENT_SECRET);
		} catch (Exception e) {
			Messagebox.info(JUXINLI_CLIENT_SECRET);
		}
		return str;
	}
	
	public static String getPotHostUrl() {
		String str = "";
		try {
			str = CONFIGURATION.getString(HONEYPOT_HOST_URL);
		} catch (Exception e) {
			Messagebox.info(HONEYPOT_HOST_URL);
		}
		return str;
	}

	public static String getBeeHostUrl() {
		String str = "";
		try {
			str = CONFIGURATION.getString(HONEYBEE_HOST_URL);
		} catch (Exception e) {
			Messagebox.info(HONEYBEE_HOST_URL);
		}
		return str;
	}

	/*
	 * 获取蜜蜂html页面物理路径
	 */
	public static String getBeePhyPath() {
		String str = "";
		try {
			str = CONFIGURATION.getString(HONEYBEE_HTML_PHY_PATH);
		} catch (Exception e) {
			Messagebox.info(HONEYBEE_HTML_PHY_PATH);
		}
		return str;
	}

	/*
	 * 获取蜜蜂html页面相对路径
	 */
	public static String getBeeRelPath() {
		String str = "";
		try {
			str = CONFIGURATION.getString(HONEYBEE_HTML_REL_PATH);
		} catch (Exception e) {
			Messagebox.info(HONEYBEE_HTML_REL_PATH);
		}
		return str;
	}

	/*
	 * 获取蜜罐html页面相对路径
	 */
	public static String getPotRelPath() {
		String str = "";
		try {
			str = CONFIGURATION.getString(HONEYPOT_HTML_REL_PATH);
		} catch (Exception e) {
			Messagebox.info(HONEYPOT_HTML_REL_PATH);
		}
		return str;
	}

	/*
	 * 获取蜜罐html页面绝对路径
	 */
	public static String getPotPhyPath() {
		String str = "";
		try {
			str = CONFIGURATION.getString(HONEYPOT_HTML_PHY_PATH);
		} catch (Exception e) {
			Messagebox.info(HONEYPOT_HTML_PHY_PATH);
		}
		return str;
	}

	public static String getBmsTitle() {
		String title = "联融集团-业务系统入口";
		try {
			title = CONFIGURATION.getString(BMS_TITLE);
		} catch (Exception e) {
			Messagebox.info("找不到参数");
		}
		return title;
	}

	public static String getDefaultLoginName() {
		String loginName = "";
		try {
			loginName = CONFIGURATION.getString(DEFAULT_LOGIN_NAME);
		} catch (Exception e) {
			Messagebox.info("找不到参数:defaultLoginName");
		}
		return loginName;
	}

	/**
	 * 获取七天前的时间
	 * 
	 * @return
	 */
	public static String getDateMillis() {
		Timestamp date = new Timestamp(System.currentTimeMillis());
		String datemillis = "";
		try {
			datemillis = CONFIGURATION.getString(DateMillis);
		} catch (Exception e) {
			log.error("找不到参数:dateMillis", e);
		}
		return datemillis;
	}

	/**
	 * 获取Redis服务器地址
	 * 
	 * @return
	 */
	public static String getRedisServer() {
		String redisServer = "";
		try {
			redisServer = CONFIGURATION.getString(REDIS_SERVER);
		} catch (Exception e) {
			log.error("找不到参数:redisServer", e);
		}
		return redisServer;
	}

	/**
	 * 获取Redis端口
	 * 
	 * @return
	 */
	public static int getRedisPort() {
		int redisPort = 0;
		try {
			redisPort = CONFIGURATION.getInt(REDIS_PORT);
		} catch (Exception e) {
			log.error("找不到参数:redisPort", e);
		}
		return redisPort;
	}

	/**
	 * 获取Redis过期时数
	 * 
	 * @return
	 */
	public static int getRedisExpire() {
		int redisExpire = 0;
		try {
			redisExpire = CONFIGURATION.getInt(REDIS_EXPIRE);
		} catch (Exception e) {
			log.error("找不到参数:redisExpire", e);
		}
		return redisExpire;
	}

	/**
	 * 获取Redis最大连接数
	 * 
	 * @return
	 */
	public static int getRedisMaxTotal() {
		int redisMaxTotal = 0;
		try {
			redisMaxTotal = CONFIGURATION.getInt(REDIS_MAXTOTAL);
		} catch (Exception e) {
			log.error("找不到参数:redisMaxTotal", e);
		}
		return redisMaxTotal;
	}

	/**
	 * 最大空闲连接数
	 * 
	 * @return
	 */
	public static int getRedisMaxIdle() {
		int redisMaxIdle = 0;
		try {
			redisMaxIdle = CONFIGURATION.getInt(REDIS_MAXIDLE);
		} catch (Exception e) {
			log.error("找不到参数:redisMaxIdle", e);
		}
		return redisMaxIdle;
	}

	/**
	 * 获取Redis最大等待时间
	 * 
	 * @return
	 */
	public static int getRedisMaxWaitMillis() {
		int redisMaxWaitMillis = 0;
		try {
			redisMaxWaitMillis = CONFIGURATION.getInt(REDIS_MAXWAITMILLIS);
		} catch (Exception e) {
			log.error("找不到参数:redisMaxWaitMillis", e);
		}
		return redisMaxWaitMillis;
	}

	/**
	 * 获取Redis密码
	 * 
	 * @return
	 */
	public static String getRedisPassword() {
		String redisPassword = "";
		try {
			redisPassword = CONFIGURATION.getString(REDIS_PASSWORD);
		} catch (Exception e) {
			log.error("找不到参数:redisPassword", e);
		}
		return redisPassword;
	}

	/**
	 * 获取redis服务器是否开启
	 * 
	 * @return
	 */
	public static boolean getRedisOnService() {
		boolean redisOnService = false;
		try {
			redisOnService = CONFIGURATION.getBoolean(REDIS_ONSERVICE);
		} catch (Exception e) {
			log.error("找不到参数:redisOnService", e);
		}
		return redisOnService;
	}

	/**
	 * WebScoket路径
	 * 
	 * @return
	 */
	public static String getWebsocketUrl() {
		String websocketUrl = "";
		try {
			websocketUrl = CONFIGURATION.getString(WEBSOCKET_URL);
		} catch (Exception e) {
			log.error("找不到参数:websocketUrl", e);
		}
		return websocketUrl;
	}

	/**
	 * 获取WebSocket类型
	 * 
	 * @return
	 */
	public static String getWebsocketType() {
		String websocketType = "";
		try {
			websocketType = CONFIGURATION.getString(WEBSOCKET_TYPE);
		} catch (Exception e) {
			log.error("找不到参数:websocketUrl", e);
		}
		return websocketType;
	}

	/**
	 * 文件处理类型
	 * 
	 * @return
	 */
	public static String getFileProcessType() {
		String fileProcessType = "";
		try {
			fileProcessType = CONFIGURATION.getString(FILE_PROCESS_TYPE);
		} catch (Exception e) {
			log.error("找不到参数:fileProcessType", e);
		}
		return fileProcessType;
	}

	/**
	 * 文件存入绝对路径
	 * 
	 * @return
	 */
	public static String getFilePath() {
		String filePath = "";
		try {
			filePath = CONFIGURATION.getString(FILE_PATH);
		} catch (Exception e) {
			log.error("找不到参数:filePath", e);
		}
		return filePath;
	}

	/**
	 * 文件访问相对路径
	 * 
	 * @return
	 */
	public static String getWebFilePath() {
		String webFilePath = "";
		try {
			webFilePath = CONFIGURATION.getString(WEB_FILE_PATH);
		} catch (Exception e) {
			log.error("找不到参数:webFilePath", e);
		}
		return webFilePath;
	}

	
	/*
	 * 白骑士5个
	 */
	public static String getHtmlPhysicsPath() {
		String webFilePath = "";
		try {
			webFilePath = CONFIGURATION.getString(HTML_PHYSICS_PATH);
		} catch (Exception e) {
			log.error("找不到参数:htmlPhysicsPath", e);
		}
		return webFilePath;
	}
	public static String getHtmlRelativePath() {
		String webFilePath = "";
		try {
			webFilePath = CONFIGURATION.getString(HTML_RELATIVE_PATH);
		} catch (Exception e) {
			log.error("找不到参数:htmlRelativePath", e);
		}
		return webFilePath;
	}
	public static String getBaiQiShiPartnerId() {
		String webFilePath = "";
		try {
			webFilePath = CONFIGURATION.getString(BAIQISHI_PARTNERID);
		} catch (Exception e) {
			log.error("找不到参数:baiQiShiPartnerId", e);
		}
		return webFilePath;
	}
	public static String getBaiQiShiVerifyKey() {
		String webFilePath = "";
		try {
			webFilePath = CONFIGURATION.getString(BAIQISHI_VERIFYKEY);
		} catch (Exception e) {
			log.error("找不到参数:baiQiShiVerifyKey", e);
		}
		return webFilePath;
	}
	public static String getBaiQiShiUrl() {
		String webFilePath = "";
		try {
			webFilePath = CONFIGURATION.getString(BAIQISHI_URL);
		} catch (Exception e) {
			log.error("找不到参数:baiQiShiUrl", e);
		}
		return webFilePath;
	}

	/*
	 * 同盾6个
	 */
	public static String getTongDunHtmlPhysicsPath() {
		String str = "";
		try {
			str = CONFIGURATION.getString(TONGDUN_HTML_PHYSICS_PATH);
		} catch (Exception e) {
			log.error("找不到参数:tongDunHtmlPhysicsPath", e);
		}
		return str;
	}
	public static String getTongDunHtmlRelativePath() {
		String str = "";
		try {
			str = CONFIGURATION.getString(TONGDUN_HTML_RELATIVE_PATH);
		} catch (Exception e) {
			log.error("找不到参数:tongDunHtmlRelativePath", e);
		}
		return str;
	}
	public static String getTongDunPartnerCode() {
		String str = "";
		try {
			str = CONFIGURATION.getString(TONGDUN_PARTNERCODE);
		} catch (Exception e) {
			log.error("找不到参数:tongDunPartnerCode", e);
		}
		return str;
	}
	public static String getTongDunParnterKey() {
		String str = "";
		try {
			str = CONFIGURATION.getString(TONGDUN_PARTNERKEY);
		} catch (Exception e) {
			log.error("找不到参数:tongDunPartnerKey", e);
		}
		return str;
	}
	public static String getTongDunUrl() {
		String str = "";
		try {
			str = CONFIGURATION.getString(TONGDUN_URL);
		} catch (Exception e) {
			log.error("找不到参数:tongDunUrl", e);
		}
		return str;
	}
	public static String getTongDunAppName() {
		String str = "";
		try {
			str = CONFIGURATION.getString(TONGDUN_APPNAME);
		} catch (Exception e) {
			log.error("找不到参数:tongDunAppName", e);
		}
		return str;
	}
	
	/*
	 * 第三方gps 2个
	 */
	public static String getGpsIp() {
		String str = "";
		try {
			str = CONFIGURATION.getString(GPS_IP);
		} catch (Exception e) {
			log.error("找不到参数:gpsIp", e);
		}
		return str;
	}
	public static String getGpsKey() {
		String str = "";
		try {
			str = CONFIGURATION.getString(GPS_KEY);
		} catch (Exception e) {
			log.error("找不到参数:gpsKey", e);
		}
		return str;
	}
	

	public static String getEndpoint() {
		String endpoint = "";
		try {
			endpoint = CONFIGURATION.getString(ENDPOINT);
		} catch (Exception e) {
			log.error("找不到参数:endpoint", e);
		}
		return endpoint;
	}

	public static String getBucketName() {
		String bucketName = "";
		try {
			bucketName = CONFIGURATION.getString(BUCKET_NAME);
		} catch (Exception e) {
			log.error("找不到参数:bucketName", e);
		}
		return bucketName;
	}

	public static String getAccessKeyID() {
		String accessKeyID = "";
		try {
			accessKeyID = CONFIGURATION.getString(ACCESS_KEYID);
		} catch (Exception e) {
			log.error("找不到参数:accessKeyID", e);
		}
		return accessKeyID;
	}

	public static String getAccessKeySecret() {
		String accessKeySecret = "";
		try {
			accessKeySecret = CONFIGURATION.getString(ACCESS_KEYSECRET);
		} catch (Exception e) {
			log.error("找不到参数:accessKeySecret", e);
		}
		return accessKeySecret;
	}

	/**
	 * 自动生成代码存放路径
	 * 
	 * @return
	 */
	public static String getGenerateCodePath() {
		String generateCodePath = "";
		try {
			generateCodePath = CONFIGURATION.getString(GENERATE_CODE_PATH);
		} catch (Exception e) {
			log.error("找不到参数:generateCodePath", e);
		}
		return generateCodePath;
	}

	/**
	 * 获取加密密匙
	 * 
	 * @return
	 */
	public static String getEncryptionKey() {
		String encryptionKey = "";
		try {
			encryptionKey = CONFIGURATION.getString(ENCRYPTION_KEY);
		} catch (Exception e) {
			log.error("找不到参数:encryptionKey", e);
		}
		return encryptionKey;
	}

	public static boolean getNeedImageCode() {
		boolean needImageCode = false;
		try {
			needImageCode = CONFIGURATION.getBoolean(NEED_IMAGE_CODE);
		} catch (Exception e) {
			log.error("找不到参数:needImageCode", e);
		}
		return needImageCode;
	}
}
