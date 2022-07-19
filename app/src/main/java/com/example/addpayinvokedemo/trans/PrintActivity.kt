package com.example.addpayinvokedemo.trans

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import bai.bai.bai.demoinvokeconsume.util.ViewUtil.checkTextIsEmpty
import com.example.addpayinvokedemo.R
import com.example.addpayinvokedemo.constant.InvokeConstant
import com.example.addpayinvokedemo.utils.DateUtil
import kotlinx.android.synthetic.main.activity_consume.*
import kotlinx.android.synthetic.main.activity_query.*
import kotlinx.android.synthetic.main.activity_query.et_order_no
import kotlinx.android.synthetic.main.activity_refund.*
import org.json.JSONException
import org.json.JSONObject

class PrintActivity : Activity(), View.OnClickListener {

    private val TAG = "invoke--QueryActivity"

    private lateinit var mContext: Context
    private val params: HashMap<String, String> = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        mContext = this
        btn_query.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_query -> {
                tv_result_query.text = ""
                invokeQuery(et_order_no.text.toString())
            }
        }
    }

    private fun invokeQuery(thirdTransNO: String) {
        if (checkTextIsEmpty(mContext, et_order_no)) return
        var intent = Intent()
        intent.setPackage("com.wiseasy.cashier")
        intent.action = "com.wiseasy.transaction.call"
        intent.putExtra("version", "A01")
        intent.putExtra("appId", "wz6012822ca2f1as78")
        intent.putExtra("transType", "PRINT")
        var jsonObject = JSONObject()
        try {
            jsonObject.put("businessOrderNo", thirdTransNO)
            intent.putExtra("transData", jsonObject.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        startActivityForResult(intent, InvokeConstant.REQUEST_PRINT)
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

                tv_result_query.text = "requestCode:$requestCode,resultCode:$resultCode ,result:${
                    data.getStringExtra("result")
                },transType:${data.getStringExtra("transType")},transData:${data.getStringExtra("transData")}"
            } else {
                Log.e(TAG, "-1")
                tv_result_query.text = "requestCode:$requestCode,resultCode:$resultCode ,result:${
                    data?.getStringExtra("result")
                },resultMsg:${data?.getStringExtra("resultMsg")},transType:${data?.getStringExtra("transType")}"
            }
        }
    }


}
