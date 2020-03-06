package com.xts.shop.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.login.LoginContract;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.presenter.login.LoginPresenter;
import com.xts.shop.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {


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
        mTvRegister.setVisibility(View.VISIBLE);
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void loginBack(LoginBean bean) {
        if (bean.getData().getCode() == 200){
            String token = bean.getData().getToken();
            SpUtils.getInstance().setValue(Constant.TOKEN, token);
            BaseApp.sLogined = true;
            LoginBean.DataBean.UserInfoBean userInfo = bean.getData().getUserInfo();
            SpUtils.getInstance().setValue(Constant.USER_NAME, userInfo.getNickname());
            SpUtils.getInstance().setValue(Constant.AVATAR, userInfo.getAvatar());
            showTips(getResources().getString(R.string.login_success));

            EventBus.getDefault().post(userInfo);
            finish();
        }else {
            showTips(bean.getData().getMsg());
        }
    }

    @OnClick({R.id.btn_login,R.id.tv_register})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                go2Register();
                break;
        }
    }

    private void go2Register() {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    private void login() {
        String name = mEditNickname.getText().toString().trim();
        String psw = mEditPw.getText().toString().trim();
        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(psw)){
            showTips(getResources().getString(R.string.name_or_psw_not_null));
            return;
        }

        mPresenter.login(name,psw);
    }
}
