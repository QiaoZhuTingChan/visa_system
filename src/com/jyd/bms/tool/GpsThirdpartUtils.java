package com.jyd.bms.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jyd.bms.common.Environment;

public class GpsThirdpartUtils {
	
	private static final String IP = Environment.getGpsIp();
	private static final String KEY = Environment.getGpsKey();
	private static final int TYPE = 1;//已json的形式返回数据
	
    public static String getUrlByMap(String url, Map<String,Object> map){
        if(StringUtils.isEmpty(url) || map == null){
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder(url);
        if(!map.isEmpty()){
            stringBuilder.append("?");
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue());
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * 1、查询车辆基本资料
     */
    public static String queryVehicleBaseData() {
        //调用的地址
        //String ip = "http://120.76.69.92:7515";
        //接口名称
        String urlName = "/VehicleData/GetVehicleBaseData.json";
        //调用地址
        String apiAddress = IP + urlName;
        //参数
        Map<String, Object> map = new HashMap<>();
        //调用申请api密匙接口
        map.put("key", KEY);
        map.put("type", TYPE);
        String url = getUrlByMap(apiAddress, map);
        String result = "";
        try {
        	//获得结果
            result = httpGet(url);
            
        } catch (Exception e) {
            System.err.println("报错" + e.getLocalizedMessage());
        }
        return result;
    }

   /**
    * 2、查询车辆动态数据
    * @param id 车辆id
    */
    public static String queryVehicleDynamicData(String id) {
        //调用的地址
       // String ip = "http://120.76.69.92:7515";
        //接口名称
        String urlName = "/VehicleData/GetPosData.json";
        //调用地址
        String apiAddress = IP + urlName;
        //参数
        Map<String, Object> map = new HashMap<>();
        //调用申请api密匙接口
        map.put("key", KEY);//秘钥
        map.put("id", id);//车辆id
        map.put("type", TYPE);//以json的格式返回数据
        String url = getUrlByMap(apiAddress, map);
        String result = "";
        try {
            result = httpGet(url);
            //获得结果
            
        } catch (Exception e) {
            System.err.println("报错" + e.getLocalizedMessage());
        }
        return result;
    }
    
    /**
     * 3.查询用户下所有车辆轨迹基础数据(访问间隔不得超过10秒)
     * @param id
     */
//    @SuppressWarnings("unused")
//	private static String queryTrajectoryBaseData() {
//        //调用的地址
//        //String ip = "http://120.76.69.92:7515";
//        //接口名称
//        String urlName = "/trajectoryData/GetTrajectoryBaseData.json";
//        //调用地址
//        String apiAddress = ip + urlName;
//        //参数
//        Map<String, Object> map = new HashMap<>();
//        //调用申请api密匙接口
//        map.put("key", key);//秘钥
//        map.put("type", type);//以json的格式返回数据
//        String url = getUrlByMap(apiAddress, map);
//        JSONObject parseObject = null;
//        try {
//            String result = httpGet(url);
//            //获得结果
//            
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("/home/aa/Desktop", "3-queryTrajectoryBaseData.txt")));
//            bos.write(result.getBytes());
//            
//            bos.flush();
//            bos.close();
//            
//            //System.err.println(result);
//            parseObject  = JSON.parseObject(result);
//        } catch (Exception e) {
//            System.err.println("报错" + e.getLocalizedMessage());
//        }
//        return parseObject.getIntValue("flag")+"--->"+parseObject.getString("msg");
//    }
    
    /**
     * 4.查询单个车辆里程数据
     * @param id 车辆ID
     * @param startTime 查询开始时间--格式：yyyy-MM-ddHH:mm:ss
     * @param endTime 查询结束时间--格式：yyyy-MM-ddHH:mm:ss
     */
//    @SuppressWarnings("unused")
//	private static String queryMileageData(String id, String startTime, String endTime) {
//        //调用的地址
//        //String ip = "http://120.76.69.92:7515";
//        //接口名称
//        String urlName = "/trajectoryData/GetMileageData.json";
//        //调用地址
//        String apiAddress = ip + urlName;
//        //参数
//        Map<String, Object> map = new HashMap<>();
//        //调用申请api密匙接口
//        map.put("key", key);//秘钥
//        map.put("id", id);//车辆id
//        map.put("type", type);//以json的格式返回数据
//        map.put("startTime", startTime);//以json的格式返回数据
//        map.put("endTime", endTime);//以json的格式返回数据
//        String url = getUrlByMap(apiAddress, map);
//        JSONObject parseObject = null;
//        try {
//            String result = httpGet(url);
//            //获得结果
//            
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("/home/aa/Desktop", "4-queryMileageData.txt")));
//            bos.write(result.getBytes());
//            
//            bos.flush();
//            bos.close();
//            
//            System.err.println(result);
//            parseObject  = JSON.parseObject(result);
//        } catch (Exception e) {
//            System.err.println("报错" + e.getLocalizedMessage());
//        }
//        return parseObject.getIntValue("flag")+"--->"+parseObject.getString("msg");
//    }
    
    /**
     * 5.根据时间查询单个车辆轨迹数据
     * @param id 车辆ID
     * @param startTime 查询开始时间--格式：yyyy-MM-ddHH:mm:ss
     * @param endTime 查询结束时间--格式：yyyy-MM-ddHH:mm:ss
     */
//    @SuppressWarnings("unused")
//	private static String queryVehTrackMongoData(String id, String startTime, String endTime) {
//        //调用的地址
//        //String ip = "http://120.76.69.92:7515";
//        //接口名称
//        String urlName = "/trajectoryData/GetVehTrackMongoData.json";
//        //调用地址
//        String apiAddress = ip + urlName;
//        //参数
//        Map<String, Object> map = new HashMap<>();
//        //调用申请api密匙接口
//        map.put("key", key);//秘钥
//        map.put("id", id);//车辆id
//        map.put("type", type);//以json的格式返回数据
//        map.put("startTime", startTime);//以json的格式返回数据
//        map.put("endTime", endTime);//以json的格式返回数据
//        String url = getUrlByMap(apiAddress, map);
//        JSONObject parseObject = null;
//        try {
//            String result = httpGet(url);
//            //获得结果
//            
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("/home/aa/Desktop", "5-queryVehTrackMongoData.txt")));
//            bos.write(result.getBytes());
//            
//            bos.flush();
//            bos.close();
//            
//            System.err.println(result);
//            parseObject  = JSON.parseObject(result);
//        } catch (Exception e) {
//            System.err.println("报错" + e.getLocalizedMessage());
//        }
//        return parseObject.getIntValue("flag")+"--->"+parseObject.getString("msg");
//    }
    
    
    
    public  static String httpGet(String url) throws Exception {
        // 调用接口，打开链接
        String inputLine = "";
        URL fixposition = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) fixposition.openConnection();// 打开连接
        //必须设置时间了，不然卡死
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(1000);
        connection.connect();// 连接会话
        BufferedReader in = new BufferedReader(new InputStreamReader(fixposition.openStream(), "UTF-8"));
        try {
            String readLine;
            while ((readLine = in.readLine()) != null) {
                inputLine = inputLine + readLine;
            }
        } finally {
            in.close();
        }
        return inputLine;

    }

	
	
	
}
