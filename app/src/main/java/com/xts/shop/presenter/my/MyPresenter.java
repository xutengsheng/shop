package com.xts.shop.presenter.my;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.interfaces.my.MyContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.MainPageBean;
import com.xts.shop.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;

public class MyPresenter extends BasePresenter<MyContract.View> implements MyContract.Presenter {
    @Override
    public void getHomeData() {

    }
}
