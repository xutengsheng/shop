package com.xts.shop.presenter.sort;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.interfaces.sort.SortContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.SortTabBean;
import com.xts.shop.utils.RxUtils;

public class SortPresenter extends BasePresenter<SortContract.View> implements SortContract.Presenter {
    @Override
    public void getTabBean() {
        addDisposable(
                HttpManager.getInstance().getApiService()
                    .getSortTab()
                    .compose(RxUtils.<SortTabBean>rxScheduler())
                    .subscribeWith(new ResponceSubscriber<SortTabBean>(mView){
                        @Override
                        public void onNext(SortTabBean sortTabBean) {
                            if (sortTabBean.getErrno() == Constant.SUCCESS_CODE){
                                mView.setTabBean(sortTabBean);
                            }
                        }
                    })
        );
    }
}
