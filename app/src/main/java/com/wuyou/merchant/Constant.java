package com.wuyou.merchant;

import android.os.Environment;

/**
 * Created by Administrator on 2018\1\26 0026.
 */

public class Constant {
//    public static final String BASE_URL = "https://develop.api.iwantmei.com/merchants/v1/";
    public static final String BASE_URL = "https://stage.api.iwantmei.com/merchants/v1/";
//    public static final String BASE_URL = "https://api.iwantmei.com/merchants/v1/";
    public static final String INPUT_PHONE_FLAG = "input_phone_sign";
    public static final String ORDER_ID = "order_id";
    public static final String PHONE = "phone";
    public static final String CAPTCHA = "captcha";
    public static final String CONTRACT_ID = "contract_id";
    public static final String CONTRACT_ENTITY = "contract_entity";
    public static final String IMAGE1_URL = "image1_url";
    public static final String FUND_ID = "fund_id";
    public static final String WALLET_INFO_ENTITY = "wallet_info_entity";
    public static final String CREDIT_SCORE = "credit_score";
    public static final String CONTRACT_FROM = "contract_from";
    public static final String TRANSACTION_ENTITY = "transaction_entity";
    public static final String FUND = "fund";
    public static final String SHOP_ID = "shop_id";
    public static final String SERVE_BEAN = "serve_bean";
    public static final String PAY_TYPE = "pay_type";
    public static final String CONTRACT = "contract";
    public static final String SIGN_NUMBER = "sign_number";
    public static final String FUND_STATUS = "fund_status" ;
    public static final String FIRST_OPEN = "first_open";
    public static final String MAIN_ACTIVITY_FROM_WHERE = "main_activity_from_where";
    public static final String MAIN_ACTIVITY_FROM_CREATE_CONTRACT = "main_activity_from_create_contract";
    public static final String MAIN_ACTIVITY_FROM_APPLY_FUND = "main_activity_from_apply_fund";
    public static final String WX_ID ="wxb60eda0adace922f" ;
    public static final String WX_SECRET = "f69fe20fe89637098fe08474ce8b90bd";
    public static final String AUTH_IMG_PATH_1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/wuyou_tmp.jpg";
    public static final int REQUEST_NICK = 777;
    public static final int REQUEST_PHONE = 888;
    public static final String WEB_INTENT = "web_intent";
    public static final String WEB_TYPE = "web_type";
    public static final String WEB_URL = "web_url";
    public static final String AUTH_IMG_PATH_2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/wuyou_tmp_2.jpg";
    public static final String COMPANY_INFO = "company_info";
    public static String IMAGE1_URL_2 = "image1_url_2";
    public static String HELP_SERVE_AGENT_ID="181385";



    public static class IntentRequestCode{
        public static final int CHOOSE_SERVICE_CATEGORY = 200;
        public static final int CHOOSE_PAY_TYPE = 201;
        public static final int REQUEST_CODE_CHOOSE_IMAGE = 202;
        public static final int REQUEST_CODE_CHOOSE_IMAGE_2 = 203;
        public static final int REQUEST_UPDATE_COMPANY_INFO = 204;
    }
}
