package bai.bai.bai.demoinvokeconsume.util

import android.content.Context
import android.text.TextUtils
import android.widget.EditText
import com.example.addpayinvokedemo.utils.ToastUtil

object ViewUtil {

    /**
     * 检查输入框字符串是否为空
     */
    public fun checkTextIsEmpty(context: Context, editText: EditText): Boolean {
        return if (TextUtils.isEmpty(editText.text)) {
            ToastUtil.showShort(context, editText.hint)
            true
        } else {
            false
        }
    }

}