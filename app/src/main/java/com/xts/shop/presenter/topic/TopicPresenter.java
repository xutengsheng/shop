package com.xts.shop.presenter.topic;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.interfaces.topic.TopicContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.TopicBean;
import com.xts.shop.utils.LogUtils;
import com.xts.shop.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.http.HTTP;

public class TopicPresenter extends BasePresenter<TopicContract.View> implements TopicContract.Presenter {
    @Override
    public void getTopicData(int page, int size) {
        addDisposable(
                HttpManager.getInstance().getApiService().getTopic(page, size)
                        .compose(RxUtils.<TopicBean>rxScheduler())
                        .subscribeWith(new ResponceSubscriber<TopicBean>(mView) {
                            @Override
                            public void onNext(TopicBean topicBean) {
                                LogUtils.print(topicBean.toString());
                                if (topicBean != null) {
                                    if (topicBean.getErrno() == Constant.SUCCESS_CODE) {
                                        mView.setData(topicBean);
                                    } else {
                                        super.onNext(topicBean);
                                    }
                                }
                            }
                        })
        );
    }
}
