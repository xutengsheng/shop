package com.xts.shop.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xts.shop.R;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.login.RegisterContract;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.presenter.login.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R.id.edit_nickname)
    EditText mEditNickname;
    @BindView(R.id.edit_pw)
    EditText mEditPw;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBtnLogin.setText(R.string.register);
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void registerBack(LoginBean bean) {
        if (bean.getErrno() == Constant.SUCCESS_CODE) {
            showTips(getResources().getString(R.string.register_success));
            finish();
        }else {
            showTips(bean.getErrmsg());
        }
    }


    @OnClick({R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login:
                register();
                break;
        }
    }

    private void register() {
        String name = mEditNickname.getText().toString().trim();
        String psw = mEditPw.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psw)) {
            showTips(getResources().getString(R.string.name_or_psw_not_null));
            return;
        }

        mPresenter.register(name, psw);
    }
}
