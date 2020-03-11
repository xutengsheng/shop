package com.xts.shop.presenter.mainpage;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.MainPageBean;
import com.xts.shop.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;

public class MainPagePresenter extends BasePresenter<MainPageContract.View> implements MainPageContract.Presenter {
    @Override
    public void getData() {
        addDisposable(
                HttpManager.getInstance()
                        .getApiService()
                        .getHome()
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new ResourceSubscriber<MainPageBean>() {
                            @Override
                            public void onNext(MainPageBean homeBean) {
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
                        })
        );
    }
}
