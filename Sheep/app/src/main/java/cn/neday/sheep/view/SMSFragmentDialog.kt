//package cn.neday.sheep.view
//
//import android.app.AlertDialog
//import android.app.Dialog
//import android.os.Bundle
//import android.text.Editable
//import android.text.method.HideReturnsTransformationMethod
//import android.text.method.PasswordTransformationMethod
//import android.view.View
//import android.widget.CompoundButton
//import androidx.fragment.app.DialogFragment
//import cn.neday.sheep.R
//import kotlinx.android.synthetic.main.fragment_dialog_sms.*
//
//class SMSFragmentDialog : DialogFragment(), View.OnFocusChangeListener, View.OnClickListener,
//    CompoundButton.OnCheckedChangeListener {
//
//    private var phone: String? = null
//
//    internal var adapter: TextWatcherAdapter = object : TextWatcherAdapter() {
//        fun afterTextChanged(s: Editable) {
//            val len = s.length
//            if (len >= 6 && len <= 16) {
//                mBtnNext.setEnabled(true)
//            } else {
//                mBtnNext.setEnabled(false)
//            }
//        }
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(activity)
//        val view = activity!!.layoutInflater.inflate(R.layout.fragment_dialog_sms, null)
//        initView()
//        builder.setView(view)
//        return builder.create()
//    }
//
//    private fun initView() {
//        phone = arguments!!.getString("phone")
//        mEtPhone.setText(phone)
//        loading.setVisibility(View.GONE)
//        mEtPhone.setOnFocusChangeListener(this)
//        et_pwd.addTextChangedListener(adapter)
//        mBtnNext.setOnClickListener(this)
//        tv_login.setOnClickListener(this)
//        btn_close.setOnClickListener(this)
//        iv_display.setOnCheckedChangeListener(this)
//    }
//
//    override fun onFocusChange(view: View, b: Boolean) {
//        val dialog = PhoneDialogFragment()
//        dialog.show(fragmentManager, "phonedailog")
//        dismiss()
//    }
//
//    override fun onClick(view: View) {
//        when (view.id) {
//            R.id.tv_login -> {
//                val dialogFragment = VcodeDialogFragment.newInstance(phone)
//                dialogFragment.show(fragmentManager, "VcodeDialogFragment")
//                dismiss()
//            }
//            R.id.btn_close -> dismiss()
//        }
//    }
//
//    override fun onCheckedChanged(compoundButton: CompoundButton, b: Boolean) {
//        if (b) {
//            et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
//        } else {
//            et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance())
//        }
//    }
//
//    companion object {
//        fun newInstance(phone: String): SMSFragmentDialog {
//            val mFragment = SMSFragmentDialog()
//            val bundle = Bundle()
//            bundle.putString("phone", phone)
//            mFragment.arguments = bundle
//            return mFragment
//        }
//    }
//}
