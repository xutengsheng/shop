package com.xts.shop.interfaces.mainpage;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.MainPageBean;

import java.util.List;

public interface NewMainPageContract {
    interface View extends IBaseView{
        void setData(List<MainPageBean.MainPageListBean> list);
    }

    interface Presenter extends IBasePresenter<View>{
        void getData();
    }
}
