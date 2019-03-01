package com.rong.base.utils;

/**
 * 系统初始化常量
 * @author qsr
 *
 */
public interface IConstants {
	
	/** 编码格式UTF8 */
	String UTF8 = "UTF-8";

	/** 统计（日） */
	public String[] DAY = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
			"22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	/** 统计（月） */
	public String[] MONTH = { "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" };
	
	/**日期格式*/
	String DATETIME_FORMAT="yyyy-MM-dd HH:mm";
	String DATETIME_MIN_FORMAT="yyyy-MM-dd HH:mm";
	String DATETIME_MS_FORMAT="yyyy-MM-dd HH:mm:sss";
	String DATE_FORMAT="yyyy-MM-dd";
	String TIME_FORMAT="HH:mm:ss";
	String[] FORMAT_PATTERNS= {DATETIME_FORMAT,DATETIME_MIN_FORMAT,DATE_FORMAT,TIME_FORMAT};
	
	/** 文件位置 */
	public String FILESITE = "resources/attachments";
	
	//-------------------资源与附件路径--------------------------
	/**资源路径*/
	public String RESPATH ="resource";
	/**附件路径*/
	public String ENCPATH ="enclosure";
	/**图片路径*/
	public String IMAGE ="image";
	/**资源文件保存标记*/
	public String RES="R";
	/**附件保存标记*/
	public String ENC="E";
	/**图片保存标记*/
	public String IMG="I";
	/**资源文件保存标记*/
	public Integer RESTYPE=0;
	/**附件保存标记*/
	public Integer ENCTYPE=1;
	//---------------------------------------------
	
	/** 英文字母，流水号时使用 */
	String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/** 用户默认密码 */
	String DEFAULT_PASSWORD = "888888";
	
	// -------------------分页参数封装 START-----------------------
		// -------------------分页参数封装 START-----------------------
		// -------------------分页参数封装 START-----------------------

		/** 页面View相关输入输出常量名 */
		String PAGE_NUM = "number";
		/** 页面View相关输入输出常量名 */
		String PAGE_SIZE = "size";
		/** 页面View相关输入输出常量名 */
		String PAGELIST = "list";
		/** 页面View相关输入输出常量名 */
		String SORTTYPES = "sortTypes";
		String SORT_TYPE_VAL="id";
		/** 页面View相关输入输出常量名 */
		String SORTTYPE = "sortType";
		/** 页面View相关输入输出常量名 */
		String SEARCHPARAMS = "searchParams";

		/** 页面尺寸 */
		String PAGE_SIZE_VAL = "12";
		/** 默认首页 */
		String PAGE_NUM_VAL = "1";
		/** 搜索排序默认类型 */
		String AUTO = "auto";
		/** 搜索排序默认类型 */
		String AUTO_STR = "自动";
		/** 搜索默认前缀 */
		String SEARCH_PREFIX = "search_";
		/** 搜索默认前缀 */
		String SEARCH_PREFIX1 = "";

		// -------------------分页参数封装  END-----------------------
		// -------------------分页参数封装  END-----------------------
		// -------------------分页参数封装  END-----------------------
	
		
		String MSG_ERROR_USER = "用户已失效，请重新进入！";
		String MSG_ERROR_PERMISSION = "权限不足！";
		String MSG_SUCCESS = "操作成功！";
		String MSG_ERROR = "操作失败！";
		
		public static final String CURRENT_USER = "user";
	    public static final String SESSION_KICK_LOGOUT_KEY = "session.kick.logout";
	    
	    //-----------------------------消息模板动态内容组装-------------------------
	    /**应用名 */
	    String APPNAME="[appName]";
	    /**团队名 */
	    String TEAMNAME="[teamName]";
	    /**操作用户名 */
	    String USERNAME="[userName]";
	    /**操作类型 */
	    String INFORMATION="[information]";
	    /**操作备注内容 */
	    String REMARKS="[remarks]";
	    /**操作URL */
	    String URL="[url]";

	    //----------------------------用于定向跳转----------------------
	    /**应用ID */
	    String APPID="[appId]";
	    /**修改提交id */
	    String MERGEID="[mergeId]";
	    /**应用动态UUID */
	    String APPUUID="[appUUID]";
	    /**团队ID */
	    String TEAMID="[teamId]";
	    /**团队记录ID */
	    String TEAMRECORDID="[teamRecordId]";
	    /**消息ID*/
	    String MSGID="[msgId]";
	    
	    /** 应用-修改提交-提交*/
	    String APP_MERGE_URL="/app/[appId]/merge/[mergeId]";
	    String APP_MODIFYMERGE_URL="/app/show/[appUUID]?key=modifyMerge";
	    String APP_UPDATE_URL="/app/[appUUID]/merge";
	    String APP_ISSUE_URL="/app/show/[appUUID]?key=issue";
	    String APP_RECOMMEND_URL="/app/recommend/[appId]";
	    String APP_ACTIVATION_URL="/app/show/[appUUID]";
	    String TEAM_INVITATION_URL="/app-sharing-api/team/[teamId]/userInvitation/[teamRecordId]";
	    String TEAM_SHOW_URL="/team/show/[teamId]";
	    String TEAM_URL="/team";
	    String MSG_URL="/allMessage";
	    String SHOW_DETAILS="/loading/msg/[msgId]";
	    
		/**操作成功标记*/
		int  SUCCESS =200;
		/**操作失败标记*/
		int  ERROR =1;
		/**操作成功标记*/
		String  SUCCESSMSG ="操作成功";
		/**操作成功标记*/
		String  ERRORMSG ="操作失败";
	    
	    
	    

	    
}
