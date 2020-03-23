package com.xts.shop.presenter.search;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.search.SearchContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.utils.RxUtils;

public class SearchPresenter extends BasePresenter<SearchContract.View>
        implements SearchContract.Presenter {
    @Override
    public void search(String content, int page, int size, String sort, String order, int categoryId) {
        addDisposable(
                HttpManager.getInstance()
                    .getApiService()
                    .search(content,page,size,sort,order,categoryId)
                    .compose(RxUtils.<GoodListBean>rxScheduler())
                    .subscribeWith(new ResponceSubscriber<GoodListBean>(mView){
                        @Override
                        public void onNext(GoodListBean goodListBean) {
                            if (goodListBean.getErrno() == Constant.SUCCESS_CODE){
                                mView.setSearchData(goodListBean);
                            }
                        }
                    })
        );
    }
}
