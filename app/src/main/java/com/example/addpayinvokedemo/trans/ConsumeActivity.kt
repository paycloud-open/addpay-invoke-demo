package com.example.addpayinvokedemo.trans

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import bai.bai.bai.demoinvokeconsume.util.ViewUtil.checkTextIsEmpty
import com.example.addpayinvokedemo.R
import com.example.addpayinvokedemo.constant.InvokeConstant
import com.example.addpayinvokedemo.utils.DateUtil
import kotlinx.android.synthetic.main.activity_consume.*
import org.json.JSONException
import org.json.JSONObject


/**
 * A version later than 1.0.4.5-addpay-2122030988（1.0。4.5-tabbs-2122030966） is required
 */
class ConsumeActivity : Activity(), View.OnClickListener {

    private val TAG = "invoke--ConsumeActivity"

    private lateinit var mContext: Context
    private val params: HashMap<String, String> = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consume)


        mContext = this
        btn_consume.setOnClickListener(this)
        btn_consume_mobile.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_consume -> {
                tv_result_consume.text = ""
                params["third_trans_no"] = DateUtil.getCurDateStr("yyyyMMddHHmmss")
                params["order_amount"] = et_amount_consume.text.toString()
                params["pay_type"] = InvokeConstant.PAY_TYPE_BANK_CARD
                params["note"] = et_note.text.toString()
                invokeConsume()
            }
            R.id.btn_consume_mobile -> {
                tv_result_consume.text = ""
                params["third_trans_no"] = DateUtil.getCurDateStr("yyyyMMddHHmmss")
                params["order_amount"] = et_amount_consume.text.toString()
                params["pay_type"] = "1006"
                params["note"] = et_note.text.toString()
                invokeConsume()
            }
        }
    }

    private fun invokeConsume() {
        if (checkTextIsEmpty(mContext, et_amount_consume)) return

        var intent = Intent()
        intent.action = "com.wiseasy.transaction.call"
        intent.putExtra("version", "A01")
        intent.putExtra("appId", "yourappid")
        intent.putExtra("transType", "SALE")
        var jsonObject = JSONObject()
        try {
            jsonObject.put("businessOrderNo", DateUtil.getCurDateStr("yyyyMMddHHmmss"))
            jsonObject.put("paymentScenario", "CARD")
            jsonObject.put("amt", "000000010000")
            intent.putExtra("transData", jsonObject.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        startActivityForResult(intent, InvokeConstant.REQUEST_CONSUME)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(
            TAG, "requestCode:$requestCode,resultCode:$resultCode ,result:${
                data?.getStringExtra("result")
            },resultMsg:${data?.getStringExtra("resultMsg")},transType:${data?.getStringExtra("transType")}"
        )

        Log.e(TAG, "transData:${data?.getStringExtra("transData")}")
        if (resultCode == RESULT_OK) {

            if ("0" == data?.getStringExtra("result")) {
                Log.e(TAG, "0")

                tv_result_consume.text = "requestCode:$requestCode,resultCode:$resultCode ,result:${
                    data.getStringExtra("result")
                },transType:${data.getStringExtra("transType")},transData:${data.getStringExtra("transData")}"
            } else {
                Log.e(TAG, "-1")
                tv_result_consume.text = "requestCode:$requestCode,resultCode:$resultCode ,result:${
                    data?.getStringExtra("result")
                },resultMsg:${data?.getStringExtra("resultMsg")},transType:${data?.getStringExtra("transType")}"
            }
        }


    }
}
