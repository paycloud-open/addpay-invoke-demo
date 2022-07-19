package com.example.addpayinvokedemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.addpayinvokedemo.constant.InvokeConstant
import com.example.addpayinvokedemo.trans.*
import com.example.addpayinvokedemo.utils.DateUtil
import com.example.addpayinvokedemo.utils.LogUtils
import kotlinx.android.synthetic.main.activity_preauth_complete.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : Activity(), View.OnClickListener {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.setLog(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        LogUtils.setLog(TAG, "initView")
        val btnConsume = findViewById<Button>(R.id.btn_consume_trans)
        btnConsume.setOnClickListener(this)
        findViewById<Button>(R.id.btn_refund_trans).setOnClickListener(this)
        findViewById<Button>(R.id.btn_query).setOnClickListener(this)
        findViewById<Button>(R.id.btn_preauth_trans).setOnClickListener(this)
        findViewById<Button>(R.id.btn_preauth_complete_trans).setOnClickListener(this)
        findViewById<Button>(R.id.btn_print).setOnClickListener(this)
        findViewById<Button>(R.id.btn_preauth_cancel).setOnClickListener(this)
        findViewById<Button>(R.id.btn_settlement).setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        LogUtils.setLog(TAG, "onResume")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.setLog(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.setLog(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.setLog(TAG, "onDestroy")
    }

    override fun onClick(view: View) {
        LogUtils.setLog(TAG, "onClick")
        when (view.id) {
            R.id.btn_consume_trans -> {
                startActivity(Intent(this, ConsumeActivity::class.java))
            }

//            R.id.btn_void -> {
//                startActivity(Intent(this, VoidActivity::class.java))
//            }
//
            R.id.btn_refund_trans -> {
                Log.e(TAG, "R.id.btn_refund")
                startActivity(Intent(this, RefundActivity::class.java))
            }
//
            R.id.btn_query -> {
                startActivity(Intent(this, QueryActivity::class.java))
            }

            R.id.btn_preauth_trans -> {
                startActivity(Intent(this, PreAuthActivity::class.java))
            }

            R.id.btn_preauth_complete_trans -> {
                startActivity(Intent(this, PreAuthCompleteActivity::class.java))
            }

            R.id.btn_preauth_cancel -> {
                startActivity(Intent(this, PreAuthCanceleActivity::class.java))
            }

            R.id.btn_print -> {
                startActivity(Intent(this, PrintActivity::class.java))
            }
            R.id.btn_settlement -> {
                var intent = Intent()
                intent.setPackage("com.wiseasy.cashier")
                intent.action = "com.wiseasy.transaction.call"
                intent.putExtra("version", "A01")
                intent.putExtra("appId", "yourappid")
                intent.putExtra("transType", "SETTLEMENT")
                startActivityForResult(intent, InvokeConstant.REQUEST_SETTLEMENT)
            }
//
//            R.id.btn_balance_inquiry -> {
//                startActivity(Intent(this, BalanceInquiryActivity::class.java))
//            }
        }
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

                Log.e(
                    TAG, "requestCode:$requestCode,resultCode:$resultCode ,result:${
                        data?.getStringExtra("result")
                    },transType:${data?.getStringExtra("transType")},transData:${
                        data?.getStringExtra(
                            "transData"
                        )
                    }"
                )
            } else {
                Log.e(TAG, "-1")
                Log.e(
                    TAG,
                    "requestCode:$requestCode,resultCode:$resultCode ,result:${
                        data?.getStringExtra("result")
                    },resultMsg:${data?.getStringExtra("resultMsg")},transType:${
                        data?.getStringExtra(
                            "transType"
                        )
                    }"
                )
            }
        }
    }
}