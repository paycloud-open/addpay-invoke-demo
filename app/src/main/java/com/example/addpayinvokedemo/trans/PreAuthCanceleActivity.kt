package com.example.addpayinvokedemo.trans

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.addpayinvokedemo.R
import com.example.addpayinvokedemo.constant.InvokeConstant
import com.example.addpayinvokedemo.utils.DateUtil
import kotlinx.android.synthetic.main.activity_preauth_complete.*
import kotlinx.android.synthetic.main.activity_refund.*
import kotlinx.android.synthetic.main.activity_refund.tv_time
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat

/**
 *
 * @ProjectName: AddPayInvokeDemo
 * @Package: com.example.addpayinvokedemo.trans
 * @ClassName: PreauthCompleteActivity
 * @Description: java类作用描述
 * @Author: dongwei
 * @CreateDate: 2022/3/15 下午5:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/3/15 下午5:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class PreAuthCanceleActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = PreAuthCanceleActivity::class.java.simpleName

    private val format = SimpleDateFormat("yyyyMMdd")
    private var selectDateAndTime = DateUtil.StringToDate(DateUtil.getCurDateStr(), "yyyyMMdd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preauth_complete)

        findViewById<Button>(R.id.btn_auth_complete).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_time).setOnClickListener(this)


    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_auth_complete -> {
                invokeComplete()
            }
            R.id.tv_time -> {
                getData()
            }
        }
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


    private fun invokeComplete() {

        var intent = Intent()
        intent.setAction("com.wiseasy.transaction.call")
        intent.putExtra("version", "A01")
        intent.putExtra("appId", "yourappid")
        intent.putExtra("transType", "PREAUTHCANCEL")
        var jsonObject = JSONObject()
        try {
            jsonObject.put("businessOrderNo", "1234567" + DateUtil.getCurDateStr("yyyyMMddHHmmss"))
            jsonObject.put("paymentScenario", "CARD")
            jsonObject.put("originBusinessOrderNo", et_origin_preauth_no.text.toString())
            jsonObject.put("amt", et_amount_preauth_complete.text.toString())
            jsonObject.put("authCode", et_auth_no.text.toString())
            intent.putExtra("transData", jsonObject.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.e(TAG, "data:${jsonObject}")
        startActivityForResult(intent, InvokeConstant.REQUEST_PREAUTH_COMPLETE)
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

                tv_result_preauth_complete.text =
                    "requestCode:$requestCode,resultCode:$resultCode ,result:${
                        data?.getStringExtra("result")
                    },transType:${data?.getStringExtra("transType")},transData:${
                        data?.getStringExtra(
                            "transData"
                        )
                    }"
            } else {
                Log.e(TAG, "-1")
                tv_result_preauth_complete.text =
                    "requestCode:$requestCode,resultCode:$resultCode ,result:${
                        data?.getStringExtra("result")
                    },resultMsg:${data?.getStringExtra("resultMsg")},transType:${
                        data?.getStringExtra(
                            "transType"
                        )
                    }"
            }
        }


    }
}