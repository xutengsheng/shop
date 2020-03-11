package com.xts.shop.presenter.sort;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.sort.SortItemContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.model.bean.SortCurrentBean;
import com.xts.shop.utils.RxUtils;

public class SortItemPresenter extends BasePresenter<SortItemContract.View>
        implements SortItemContract.Presenter {
    @Override
    public void getSortItemData(int id) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                        .getSortCurrentBean(id)
                        .compose(RxUtils.<SortCurrentBean>rxScheduler())
                        .subscribeWith(new ResponceSubscriber<SortCurrentBean>(mView) {
                            @Override
                            public void onNext(SortCurrentBean bean) {
                                if (bean.getErrno() == Constant.SUCCESS_CODE) {
                                    if (mView != null) {
                                        mView.setSortItemData(bean);
                                    }
                                }
                            }
                        })

        );
    }
}
