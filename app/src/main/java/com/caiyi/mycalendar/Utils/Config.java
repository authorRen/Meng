package com.caiyi.mycalendar.Utils;

import android.text.TextUtils;

import com.caiyi.mycalendar.BuildConfig;

/**
 * @author dongqi
 * @since 2015/12/21.
 */
public final class Config {
    public static final String RELEASE_DOMAIN = "https://andgjj.youyuwo.com";
    public static final String DEBUG_DOMAIN = "http://gjj_8095.gs.9188.com";
    /** DOMAIN.*/
    public static String DOMAIN;
    /** instance.*/
    private static Config sConfig;
    /** DOMAIN 供头像和内部web页面调用使用的域名.*/
    public static final String DOMAIN_EX = BuildConfig.DEBUG ? "http://192.168.1.155:9007" : RELEASE_DOMAIN;
    /** 静态文件地址.*/
    public static final String STATIC_FILE_DOMAIN = "http://gjjcms.youyuwo.com";
    /** 启动接口.*/
    public String getURL_START() {
        return DOMAIN + "/gjj/start.go";
    }

    /** 启动页 */
    public String getSplash() {
        return DOMAIN + "/gjj/configure.go";
    }

    /** 登陆获取验证码接口.*/
    public String getURL_GETYZM_CODE() {
            return DOMAIN + "/user/mobgetYzm.go";
    }
    /** 登陆验证验证码接口.*/
    public String getURL_LOGIN_CONFIRM() {
        return DOMAIN + "/user/mobRegisterOrLogin.go";
    }

    /** 公积金账户绑定前的操作，如获取验证码.*/
    public String getURL_GJJ_ACOUNT_PRELOGIN() {
        return DOMAIN + "/user/preLogin.go";
    }
    /** 公积金账户绑定后续操作，如获取短信验证码，提交后续绑定信息.*/
    public String getURL_GJJ_ACOUNT_SUFLOGIN() {
        return DOMAIN + "/user/sufLogin.go";
    }
    /** 公积金账户登陆.*/
    public String getURL_GJJ_ACOUNT_LOGIN() {
        return DOMAIN + "/user/gjjlogin.go";
    }
    /** 用户上传头像接口.*/
    public String getURL_UPLOAD_ICON() {
        return DOMAIN + "/user/uploadIcon.go";
    }
    /** 贷款测算.*/
    public String getURL_LOAN_CALC() {
        return DOMAIN + "/gjj/daikuancesuan.go";
    }
    /** 提款测算.*/
    public String getURL_EXTRACT_CALC() {
        return DOMAIN + "/gjj/tiqucesuan.go";
    }
    /** 资讯政策.*/
    public String getURL_POLICY_NEWS() {
        return DOMAIN + "/gjj/gonggao.go";
    }
    /** 用户首页信息接口-新版.*/
    public String getURL_HOME_NEW() {
        return DOMAIN + "/gjj/indexNew.go";
    }
    /** 强制刷新默认账户信息.*/
    public String getURL_ENFORCE_REFRESH_ACCOUNT() {
        return DOMAIN + "/gjj/forceFlushNew.go";
    }
    /** 用户账户信息明细接口 老版本.*/
    public String getURL_QUERY_LIST() {
        return DOMAIN + "/user/queryGjjSiPayRecord.go";
    }
    /** 用户账户信息明细接口 新版本.*/
    public String getURL_QUERY_NEW_LIST() {
        return DOMAIN + "/user/queryPayRecordByAccountInfo.go";
    }
    /** 账户详情页广告列表. */
    public String getURL_QUERY_ADVERTISING_LIST() {
        return DOMAIN + "/gjj/advertisementNew.go";
    }
    /** 公积金贷款利率和商业贷款利率.*/
    public String getURL_LOAN_RATE() {
        return DOMAIN + "/gjj/gjjRate.go";
    }
    /** 用户协议.*/
    public static final String URL_USER_PROTOCOL = "http://gjjcms.youyuwo.com/gongjijinwenzhang/2016/0117/673.html";
    /** 常见问题.*/
    public static final String URL_QUERESTIONS = "http://andgjj.youyuwo.com/app/material/changjianwenti.html";
    /** 分享地址.*/
    public static final String URL_SHARE = "http://andgjj.youyuwo.com/";
    /** 精品推荐.*/
    public static final String URL_RECOMMENDATION = "http://gjj.9188.com/query/down.html";
    /** 关注微信公众号.*/
    public static final String URL_WX_PUBLIC = "http://andgjj.youyuwo.com/app/wechat/index.html";
    /** 当前支持查询公积金城市列表 未分组.*/
    public String getURL_SUPPORT_CITIES_LIST() {
        return DOMAIN + "/gjj/getCityCodes.go";
    }
    /** 当前支持查询公积金城市列表.*/
    public String getURL_SUPPORT_CITIES() {
        return DOMAIN + "/gjj/getOrderedCitys.go";
    }

    /** 手机取回用户名和密码_提交 */
    public String getFUND_PWD() {
        return DOMAIN + "/gjjinterface/getPass.go";
    }
    /** 手机取回用户名和密码_获取验证码 */
    public String getYZM_PWD() {
        return DOMAIN + "/gjjinterface/getPass_getCheckCode.go";
    }
    /** 个人公积金账号查询 */
    public String getFUND_ACCOUNT() {
        return DOMAIN + "/gjjinterface/getAccount.go";
    }
    /** 个人公积金账号查询_获取验证码 */
    public String getFUND_ACCOUNT_YZM() {
        return DOMAIN + "/gjjinterface/getAccount_getCheckCode.go";
    }

    /** 绑定公积金时城市切换.*/
    public String URL_CITY_CHOSE = DOMAIN + "/gjj/getcitys.go";
    /** 账户列表.*/
    public String getURL_ACCOUNT_LIST() {
        return DOMAIN + "/user/queryqccountlist.go";
    }
    /** 设置默认账户.*/
    public String getURL_ACCOUNT_SETDEFAULT() {
        return DOMAIN + "/user/updatedefaultaccount.go";
    }
    /** 公积金登陆配置.*/
    public String getURL_LOGIN_CONFIG() {
        return DOMAIN + "/gjj/logInPageUiConfig.go";
    }
    /** 获取验证码.*/
    public  String getURL_YZM() {
        return DOMAIN + "/user/getLoginYzm.go?city=";
    }
    /** 获取验证码-绑定第二步.*/
    public  String getURL_YZM_2() {
        return DOMAIN + "/user/getYzm.go?city=";
    }

    /** 删除公积金.*/
    public String getURL_ACCOUT_DELETE() {
        return DOMAIN + "/user/unbindAccount.go";
    }
    /** 修改昵称.*/
    public String getURL_NICKNAME_CHANGE() {
        return DOMAIN + "/user/updateUgcNickName.go";
    }
    /** 公积金系统消息.*/
    public String getURL_PUSH_MESSAGE() {
        return DOMAIN + "/user/querySystemMessage.go";
    }
    /** 公积金(社保)登录页面推送服务提醒接口 */
    public String getURL_SET_NOTIFY() {
        return DOMAIN + "/gjj/cityNotificationService.go";
    }
    /** 个税税率查询 */
    public String getPersonaltax() {
        return DOMAIN + "/personaltax/start.go";
    }
    // ----------------------------------征信接口相关--------------------------------------------------//
    public String getURL_CREDIT_YZM_IMG() { return DOMAIN + "/user/creditGetImgYzm.go"; }
    /** 验证个人身份信息.*/
    public String getURL_CREDIT_CHECK_IDCARD() {
        return DOMAIN + "/user/creditCheckIdCard.go";
    }
    /** 发送手机验证码.*/
    public String getURL_CREDIT_SEND_YZM() {
        return DOMAIN + "/user/creditSendYzm.go";
    }
    /** 用户名密码注册.*/
    public String getURL_CREDIT_REGISTER() {
        return DOMAIN + "/user/creditRegister.go";
    }
    /** 用户登录.*/
    public String getURL_CREDIT_LOGIN() {
        return DOMAIN + "/user/creditLogin.go";
    }
    /** 登录或注册后获取征信问题. */
    public String getURL_CREDIT_QUESTIONS() {
        return DOMAIN + "/user/creditQuestion.go";
    }
    /** 提交回答的征信问题. */
    public String getURL_CREDIT_QUESTIONS_ANSWER() {
        return DOMAIN + "/user/creditApplyQuestion.go";
    }
    /** 提交问题页面的验证码. */
    public String getURL_CREDIT_QUESTIONS_YZM() {
        return DOMAIN + "/user/creditApplySms.go";
    }
    /** 忘记密码校检用户信息. */
    public String getURL_CREDIT_FORGET_PWD_1() {
        return DOMAIN + "/user/creditFindPwdCheck.go";
    }
    /** 忘记密码获取问题. */
    public String getURL_CREDIT_FORGET_PWD_QUESTIONS() {
        return DOMAIN + "/user/creditPassQuestion.go";
    }
    /** 忘记用户名. */
    public String getURL_CREDIT_FORGET_USERNAME() {
        return DOMAIN + "/user/creditFindLoginName.go";
    }
    /** 登陆提交央行验证码. */
    public String getURL_CREDIT_LOGIN_SUBMIT_YZM() {
        return DOMAIN + "/user/creditGetReport.go";
    }
    /** 获取征信报告. */
    public String getURL_CREDIT_REPORT_DETAIL() {
        return DOMAIN + "/user/creditQueryReport.go";
    }

    // ----------------------------------服务、社区接口相关--------------------------------------------------//
    /** 首页帖子列表. */
    public String getURL_FORUM_LIST_DATAS() {
        return DOMAIN + "/community/showArticleListByCityTag.go";//since 2.5.0
    }
    /** 帖子详情. */
    public String getURL_FORUM_DETAIL_DATAS() {
        return DOMAIN + "/community/showArticleDetail.go";
    }
    /** 发送帖子评论. */
    public String getURL_FORUM_DETAIL_COMMENT() {
        return DOMAIN + "/community/addComment.go";
    }

    /** 论坛发帖 */
    public String addArticle() {
        return DOMAIN + "/community/addArticle.go";
    }

    /** 获取标签 */
    public String getDefaultTags() {
        return DOMAIN + "/community/getDefaultTags.go";
    }

    /** 我的帖子 */
    public String myArticle() {
        return DOMAIN + "/community/myArticle.go";
    }

    /** 我的回帖 */
    public String myReply() {
        return DOMAIN + "/community/myReply.go";
    }

    /** 评论我的 */
    public String commentAboutMe() {
        return DOMAIN + "/community/commentAboutMe.go";
    }

    /** 评论我的未读消息 */
    public String commentAboutMeCnt() {
        return DOMAIN + "/community/commentAboutMeCnt.go";
    }

    // ----------------------------------缴存接口相关--------------------------------------------------//
    /** 缴存公积金时选择城市.*/
    public String getURL_CITY_LIST_PAYMENT() {
        return DOMAIN + "/gjj/getAllQQXBCitys.go";
    }
    /** 验证手机号是否支持缴存.*/
    public String getURL_CHECK_PHONE_SPPORT_PAYMENT() {
        return DOMAIN + "/gjj/checkUserMobileNo.go";
    }
    /** 获取社保和公积金的服务期限.*/
    public String getURL_PAYMENT_PERIOD() {
        return DOMAIN + "/gjj/getQQXBServiceFeeAndDuration.go";
    }
    /** 获取缴存需要的基本信息.*/
    public String getURL_GET_BASIC_INFO_PAYMENT() {
        return DOMAIN + "/gjj/getQQXBBasicServiceInfo.go";
    }
    /** 计算缴存所需费用.*/
    public String getURL_CALC_COST_PAYMENT() {
        return DOMAIN + "/gjj/calculateAllFee.go";
    }
    /** 提交缴存信息.*/
    public String getURL_SUBMIT_ORDER_PAYMENT() {
        return DOMAIN + "/gjj/submitOrder.go";
    }

    /** 缴存详情.*/
    public String getURL_ORDER_DETAIL_PAYMENT(){
        return DOMAIN + "/gjj/getOrderDetail.go";
    }

    /** 获取图片验证码.*/
    public String getURL_LOGIN_IMGYZM() {
        return DOMAIN + "/user/getImgYzmNew.go";
    }

    /** 验证码校验.*/
    public String getURL_YZM_CHECK() {
        return DOMAIN + "/user/checkYzm.go";
    }

    // ----------------------------------分割线 华丽嘛--------------------------------------------------//
    /** 参数签名key **/
    public static final String PARAMS_SIGN_KEY = "iwannapie?!";
    
    //公共参数
    public static final String PARAMS_APPID = "appId";
    public static final String PARAMS_TOKEN = "accessToken";
    public static final String VERSION_NAME = "releaseVersion";
    public static final String SOURCE = "source";
    public static final String ADDRESS_CODE = "addressCode";
    
    /** 用户选择的城市区号.*/
    public static final String PARAMS_USER_CITYCODE = "PARAMS_USER_CITYCODE";
    /** 用户选择的城市.*/
    public static final String PARAMS_USER_CITY = "PARAMS_USER_CITY";

    /*************************-----------------------------------------------------************************************************/
    /** 请求数据成功code.*/
    public static final String REQUEST_CODE_SUCCESS = "1";
    /** citycode-上海.*/
    public static final String CITYCODE_SH = "021";
    /** citycode-杭州.*/
    public static final String CITYCODE_HZ = "0571";
    /** citycode-北京.*/;
    public static final String CITYCODE_BJ = "010";
    /** citycode-深圳.*/
    public static final String CITYCODE_SZ = "0755";
    /** 手机号码参数.*/
    public static final String PARAM_PHONE = "PARAM_PHONE";
    /** 上级页面是否是图片验证码.*/
    public static final String PARAMS_FROMIMGYZM = "PARAM_FROMIMGYZM";
    /** sp domain.*/
    public static final String SP_DOMAIN = "SP_DOMAIN";

    public static Config getInstanceConfig() {
        if (null == sConfig) {
            synchronized (Config.class) {
                if (null == sConfig) {
                    sConfig = new Config();
                }
            }
        }
        return sConfig;
    }

    private Config() {
        if (BuildConfig.DEBUG) {
            //获取测试域名.
            Config.DOMAIN = SPUtils.getString("DOMAIN_FOR_DEBUG", Config.DEBUG_DOMAIN);
        } else {
            DOMAIN = RELEASE_DOMAIN;
        }
        //判断下发域名
        if (!TextUtils.isEmpty(SPUtils.getString(SP_DOMAIN))) {
            DOMAIN = SPUtils.getString(SP_DOMAIN);
        }
    }

    /**
     * 重新加载配置文件.
     */
    public void reloadConfig() {
        DOMAIN =  BuildConfig.DEBUG ? DEBUG_DOMAIN : RELEASE_DOMAIN;
        if (!TextUtils.isEmpty(SPUtils.getString(SP_DOMAIN))) {
            DOMAIN = SPUtils.getString(SP_DOMAIN);
        }
    }
}
