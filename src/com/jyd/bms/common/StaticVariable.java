package com.jyd.bms.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StaticVariable {
	// 系统名称
	public final static String SYSTEM_NAME = "业务系统";
	// 系统
	public final static int MENU_TYPE_SYSTEM = 1;
	// 模块
	public final static int MENU_TYPE_MODULE = 2;
	// 页面
	public final static int MENU_TYPE_PAGE = 3;
	// 按钮
	public final static int MENU_TYPE_BUTTON = 4;
	// 特殊
	public final static int MENU_TYPE_SPECIAL = 5;

	// 工作流审批状态
	// 进行中
	public final static int WORKFLOW_STATUS_PROCESSING = 0;
	// 通过结束
	public final static int WORKFLOW_STATUS_OVER = 1;
	// 退回结束
	public final static int WORKFLOW_STATUS_REJECT = 2;

	// 消息状态
	// 已读
	public final static int MESSAGE_STATUS_READ = 1;
	// 未读
	public final static int MESSAGE_STATUS_UNREAD = 0;

	// 合同相关下拉框查询
	// 合同编号
	public final static int SEARCH_STATE_CONTRACTID = 0;
	// 姓名
	public final static int SEARCH_STATE_NAME = 1;
	// 身份证
	public final static int SEARCH_STATE_IDCARD = 2;
	// 车牌号
	public final static int SEARCH_STATE_CARNUMBER = 3;

	/************************************************
	 * 合同表的state字段 状态,0正常，1结束，-1审批中，2转逾期，3转账外资产
	 ************************************************/
	public static final int GREEN = 0;
	public static final String GREEN_COLOR = "#8FBC8F";
	public static final int YELLOW = -1;
	public static final String YELLOW_COLOR = "#BDB76B";
	public static final int BLUE = 1;
	public static final String BLUE_COLOR = "#2F4F4F";
	public static final int RED = 2;
	public static final String RED_COLOR = "#FA8072";
	public static final int WHTER = 3;
	public static final String WHTER_COLOR = "#FAF0E6";

	// 表单默认值
	public static final int CUS_DATA_FORM = 4;

	// 1请假，2加班，3出差，4旷工，5迟到，6早退，7基本薪资项目，8手工录入项目
	public static Map<Integer, String> getType() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "系统计算");
		map.put(1, "请假");
		map.put(2, "加班");
		map.put(3, "出差");
		map.put(4, "旷工");
		map.put(5, "迟到");
		map.put(6, "早退");
		map.put(7, "基本薪资项目");
		map.put(8, "手工录入项目");
		map.put(9, "保险");
		map.put(10, "上班天数");
		map.put(11, "病假");
		map.put(12, "事假");
		map.put(13, "婚假");
		map.put(14, "丧假");
		map.put(15, "产假/流产假");
		map.put(16, "工伤假");
		map.put(17, "年假");
		map.put(18, "公派培训");
		map.put(19, "调休");
		map.put(20, "公假");
		map.put(21, "忘记打卡");
		map.put(22, "外出");
		return map;
	}

	public static Map<Integer, String> getOperation() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "取数据");
		map.put(1, "系统计算");
		return map;
	}

	public static String getValueMap(int type, Map map) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = (Map.Entry) it.next();
			int key = entry.getKey();
			String value = entry.getValue();
			if (type == key) {
				return value;
			}
		}
		return null;
	}
}