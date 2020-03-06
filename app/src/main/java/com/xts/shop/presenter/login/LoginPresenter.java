package com.xts.shop.presenter.login;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.login.LoginContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.utils.RxUtils;

import retrofit2.http.HTTP;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Override
    public void login(String name, String psw) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                .login(name,psw)
                .compose(RxUtils.<LoginBean>rxScheduler())
                .subscribeWith(new ResponceSubscriber<LoginBean>(mView){
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getErrno() == Constant.SUCCESS_CODE) {
                            mView.loginBack(loginBean);
                        }
                    }
                })

        );
    }

}
