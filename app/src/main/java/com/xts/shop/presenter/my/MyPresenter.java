package com.xts.shop.presenter.my;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.interfaces.my.MyContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.HomeBean;
import com.xts.shop.utils.RxUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.ResourceSubscriber;

public class MyPresenter extends BasePresenter<MyContract.View> implements MyContract.Presenter {
    @Override
    public void getHomeData() {
        HttpManager.getInstance()
                .getApiService()
                .getHome()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new ResourceSubscriber<HomeBean>() {
                    @Override
                    public void onNext(HomeBean homeBean) {
                        System.out.println(homeBean.toString());
                        mView.setData(homeBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
