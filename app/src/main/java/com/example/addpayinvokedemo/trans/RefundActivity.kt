package com.example.addpayinvokedemo.trans

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import org.json.JSONObject
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.text.TextUtils
import bai.bai.bai.demoinvokeconsume.util.ViewUtil.checkTextIsEmpty
import com.example.addpayinvokedemo.R
import com.example.addpayinvokedemo.constant.InvokeConstant
import com.example.addpayinvokedemo.utils.DateUtil
import kotlinx.android.synthetic.main.activity_consume.*
import kotlinx.android.synthetic.main.activity_refund.*
import org.json.JSONException
import java.text.SimpleDateFormat
import kotlin.collections.HashMap


class RefundActivity : Activity(), View.OnClickListener {

    private val TAG = "invoke--RefundActivity"

    private lateinit var mContext: Context
    private val params: HashMap<String, String> = HashMap<String, String>()

    private val format = SimpleDateFormat("yyyyMMdd")
    private var selectDateAndTime = DateUtil.StringToDate(DateUtil.getCurDateStr(), "yyyyMMdd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refund)
        Log.e(TAG, "onCreate")
        mContext = this
        btn_refund.setOnClickListener(this)
        btn_refund_mobile.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.btn_refund -> {
                tv_result_refund.text = ""
                params["pay_type"] = "1001"
                invokeRefund()
            }
            R.id.btn_refund_mobile -> {
                tv_result_refund.text = ""
                params["pay_type"] = "1006"
                invokeRefund()
            }
            R.id.tv_time -> {
                getData()
            }
        }
    }

    private fun putData() {

    }


    private fun getData() {
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                selectDateAndTime = DateUtil.setDate(selectDateAndTime, year, month, day)
                tv_time.text = format.format(selectDateAndTime)
//            getTime()
            },
            selectDateAndTime.year + 1900,
            selectDateAndTime.month,
            selectDateAndTime.date
        ).show()//year是从1900后开始的，所以要加1

    }

    private fun getTime() {
        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            selectDateAndTime = DateUtil.setTime(selectDateAndTime, hourOfDay, minute, 0)
            tv_time.text = format.format(selectDateAndTime)
            Log.d(TAG, "选择的日期时间：${tv_time.text}")
        }, selectDateAndTime.hours, selectDateAndTime.minutes, true).show()
    }


    private fun invokeRefund() {

        var intent = Intent()
        intent.setPackage("com.wiseasy.cashier")
        intent.setAction("com.wiseasy.transaction.call")
        intent.putExtra("version", "A01")
        intent.putExtra("appId", "wz6012822ca2f1as78")
        intent.putExtra("transType", "REFUND")
        var jsonObject = JSONObject()
        try {
            jsonObject.put("businessOrderNo", "1234567"+DateUtil.getCurDateStr("yyyyMMddHHmmss"))
            jsonObject.put("originBusinessOrderNo", et_order_no.text.toString())
            jsonObject.put("paymentScenario", "CARD")
            jsonObject.put("amt", et_amount_refund.text.toString())
            jsonObject.put("refNo", et_refer_no.text.toString())
            jsonObject.put("originTransDate", tv_time.text.toString())
            intent.putExtra("transData", jsonObject.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.e(TAG, "data:${jsonObject}")
        startActivityForResult(intent, InvokeConstant.REQUEST_REFUND)
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

                tv_result_refund.text = "requestCode:$requestCode,resultCode:$resultCode ,result:${
                    data?.getStringExtra("result")
                },transType:${data?.getStringExtra("transType")},transData:${data?.getStringExtra("transData")}"
            } else {
                Log.e(TAG, "-1")
                tv_result_refund.text = "requestCode:$requestCode,resultCode:$resultCode ,result:${
                    data?.getStringExtra("result")
                },resultMsg:${data?.getStringExtra("resultMsg")},transType:${data?.getStringExtra("transType")}"
            }
        }


    }

}
