package com.xts.shop.presenter.login;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.login.LoginContract;
import com.xts.shop.interfaces.login.RegisterContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.utils.RxUtils;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    @Override
    public void register(String name, String psw) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                .register(name,psw)
                .compose(RxUtils.<LoginBean>rxScheduler())
                .subscribeWith(new ResponceSubscriber<LoginBean>(mView){
                    @Override
                    public void onNext(LoginBean loginBean) {
                        mView.registerBack(loginBean);
                    }
                })

        );
    }

}
