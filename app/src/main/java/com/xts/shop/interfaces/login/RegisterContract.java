package com.xts.shop.interfaces.login;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.LoginBean;

public interface RegisterContract {
    interface View extends IBaseView{
        void registerBack(LoginBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void register(String name, String psw);
    }
}
