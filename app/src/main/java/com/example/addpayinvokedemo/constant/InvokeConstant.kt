package com.example.addpayinvokedemo.constant

object InvokeConstant {
    val ACTION_NONE = 0;
    val ACTION_CONSUME = 2;
    val ACTION_REFUND = 9;
    val ACTION_QUERY = 5;
    val ACTION_SETTLE = 4;
    val ACTION_PRINT = 16;
    val RESULT_SUCCESS = 0;
    val RESULT_FAIL_COMMON = -1;
    val RESULT_FAIL_NO_NEED_BREAK = 1001;
    val PAY_TYPE_BANK_CARD = "1001";
    val PAY_TYPE_WX_SCAN = "1002";
    val PAY_TYPE_WX_CODE = "1003";
    val PAY_TYPE_ALI_SCAN = "1004";
    val PAY_TYPE_ALI_CODE = "1005";


    //RequestCode
    val REQUEST_CONSUME = 100;
    val REQUEST_REFUND = 101;
    val REQUEST_PREAUTH = 102;
    val REQUEST_PREAUTH_COMPLETE = 103;
    val REQUEST_QUERY = 104;
    val REQUEST_PRINT = 105;
    val REQUEST_SETTLEMENT = 106;


}