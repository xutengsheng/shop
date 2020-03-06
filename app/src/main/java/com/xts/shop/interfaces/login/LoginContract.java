package com.xts.shop.interfaces.login;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.LoginBean;

public interface LoginContract {
    interface View extends IBaseView{
        void loginBack(LoginBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void login(String name,String psw);
    }
}
