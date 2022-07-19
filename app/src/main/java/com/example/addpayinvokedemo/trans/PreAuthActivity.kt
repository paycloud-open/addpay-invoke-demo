package com.example.addpayinvokedemo.trans

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import bai.bai.bai.demoinvokeconsume.util.ViewUtil
import com.example.addpayinvokedemo.R
import com.example.addpayinvokedemo.constant.InvokeConstant
import com.example.addpayinvokedemo.utils.DateUtil
import kotlinx.android.synthetic.main.activity_consume.*
import kotlinx.android.synthetic.main.activity_preauth.*
import org.json.JSONException
import org.json.JSONObject

/**
 *
 * @ProjectName: AddPayInvokeDemo
 * @Package: com.example.addpayinvokedemo.trans
 * @ClassName: PreAuthActivity
 * @Description: java类作用描述
 * @Author: dongwei
 * @CreateDate: 2022/3/15 上午9:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/3/15 上午9:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class PreAuthActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = PreAuthActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preauth)

        findViewById<Button>(R.id.btn_preauth).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_preauth -> {
                tv_result_preauth.text = ""
                invokePreAuth()
            }
        }
    }

    private fun invokePreAuth() {
        if (ViewUtil.checkTextIsEmpty(this, et_amount_preauth)) return

        var intent = Intent()
        intent.setPackage("com.wiseasy.cashier")
        intent.action = "com.wiseasy.transaction.call"
        intent.putExtra("version", "A01")
        intent.putExtra("appId", "yourappid")
        intent.putExtra("transType", "PREAUTH")
        var jsonObject = JSONObject()
        try {
            jsonObject.put("businessOrderNo", DateUtil.getCurDateStr("yyyyMMddHHmmss"))
            jsonObject.put("paymentScenario", "CARD")
            jsonObject.put("amt", "000000010000")
            intent.putExtra("transData", jsonObject.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        startActivityForResult(intent, InvokeConstant.REQUEST_PREAUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(
            TAG, "requestCode:$requestCode,resultCode:$resultCode ,result:${
                data?.getStringExtra("result")
            },resultMsg:${data?.getStringExtra("resultMsg")},transType:${data?.getStringExtra("transType")}"
        )

        Log.e(TAG, "transData:${data?.getStringExtra("transData")}")
        if (resultCode == Activity.RESULT_OK) {

            if ("0" == data?.getStringExtra("result")) {
                Log.e(TAG, "0")

                tv_result_preauth.text = "requestCode:$requestCode,resultCode:$resultCode ,result:${
                    data.getStringExtra("result")
                },transType:${data.getStringExtra("transType")},transData:${data.getStringExtra("transData")}"
            } else {
                Log.e(TAG, "-1")
                tv_result_preauth.text = "requestCode:$requestCode,resultCode:$resultCode ,result:${
                    data?.getStringExtra("result")
                },resultMsg:${data?.getStringExtra("resultMsg")},transType:${data?.getStringExtra("transType")}"
            }
        }


    }


}