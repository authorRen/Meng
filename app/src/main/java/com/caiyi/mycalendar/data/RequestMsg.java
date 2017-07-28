package com.caiyi.mycalendar.data;

import org.json.JSONObject;

/**
 * 接口请求返回的参数
 * @author dongqi
 * @since 2015/10/29
 */
public class RequestMsg {
    public static final String RESULT = "results";
    public static final String CODE = "code";
    public static final String DESC = "desc";
    public static final int RESULT_OK = 1;

    public static final int CODE_ERROR_DEFAULT = -1;

    public static final String MSG_ERROR_EMPTY = "empty error msg";
    public static final String MSG_RESULTS_EMPTY = "empty results msg";
    
    public static final int CODE_ERROR_TIMEOUT = 1000;
    public static final String MSG_ERROR_SOCKET_TIMEOUT = "网络连接超时，请检查您的网络状态，稍后重试";

    public static final int CODE_ERROR_UNCONNECTED = 1001;
    public static final String MSG_ERROR_CONNECT = "网络连接异常，请检查您的网络状态";

    public static final int CODE_ERROR_UNKNOWN_HOST = 1002;
    public static final String MSG_ERROR_UNKNOWN_HOST = "无法访问该主机";

    public static final int CODE_ERROR_SSL = 1003;
    public static final String MSG_ERROR_SSL = "证书错误";

    public static final int CODE_ERROR_MALFORMED_JSON = 1020;
    public static final String MSG_ERROR_MALFORMED_JSON = "数据解析错误";
    
    /** tokentoken已注销.*/
    public static final int LOGIN_TOKEN_LOGOUT = 9001;
    /** token登录过期.*/
    public static final int LOGIN_TOKEN_OUTOFDATE = 9002;
    /** token密码已修改.*/
    public static final int LOGIN_TOKEN_PWD_MODIFY = 9003;
    /** 账户禁用.*/
    public static final int LOGIN_TOKEN_ACCOUNT_FORBIDDEN = 9004;
    /** 未查到相关token记录.*/
    public static final int LOGIN_TOKEN_NOT_FOUND = 9005;
    /** 查询token信息出错.*/
    public static final int LOGIN_TOKEN_ERROR = 9006;
    /** token验证不通过.*/
    public static final int LOGIN_TOKEN_NOT_IDENTIFY = 9007;
    /** 未登录.*/
    public static final int LOGIN_UN_LOGIN = 9009;
    /** token需要重新获取.*/
    public static final int LOGIN_TOKEN_NEED_REGET = 10000;
    /** 登陆成功. */
    public static final int MSG_LOGIN_SUCCESS = 10000;
    /** 登陆失败. */
    public static final int MSG_LOGIN_ERROR = 10001;
    /** 登陆失败. */
    public static final int MSG_ACCIVE = 10002;
    /** 消息异常.*/
    public static final int MSG_ERROR = 10003;
    /** 请求成功*/
    public static final int MSG_SUCCESS = 10004;
    /** 登录需要验证码.*/
    public static final int MSG_LOGIN_WITHYZM = -2222;
    /** 需要本地登录.*/
    public static final int MSG_LOGIN_FROMLOCAL = -2223;
    /** 绑定信息被修改，需要重新绑定.*/
    public static final int MSG_LOGIG_WITHBINDING = -1111;
    /** 错误引导对话框，会跳转到绑定页，再跳转到目标页面.*/
    public static final int MSG_LOGIN_GUIDE = -1150;
    /** 错误引导对话框，直接跳转.*/
    public static final int MSG_LOGIN_GUIDE_2 = -1151;
    /** 绑定成功后有后续操作.*/
    public static final int MSG_LOGIN_STEP = -4;

    public static final int MSG_UNSUPPORT_CITY = -3000;
    /** 返回code.*/
    public int code;
    /** 返回描述.*/
    private String desc;
    /** 请求结果result-string .*/
    public JSONObject result;
    /** 更新时间.*/
    private String updateTime;
    /** result str.*/
    private String resultStr;
    /** empty constructor.*/
    public RequestMsg() { };
    /**
     * constructor.
     * @param obj jsonobj
     */
    public RequestMsg(JSONObject obj) {
        setCode(obj.optInt(CODE));
        setDesc(obj.optString(DESC));
        setResult(obj);
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    //CHECKSTYLE:OFF
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    //CHECKSTYLE:ON
}
