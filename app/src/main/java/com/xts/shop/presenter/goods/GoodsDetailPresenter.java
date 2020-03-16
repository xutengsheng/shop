package com.xts.shop.presenter.goods;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.goods.GoodsDetailContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.GoodsDetailBean;
import com.xts.shop.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.View>
        implements GoodsDetailContract.Presenter {
    @Override
    public void getGoodsDetail(int id) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                    .getGoodDetail(id)
                    .compose(RxUtils.<GoodsDetailBean>rxScheduler())
                        .map(new Function<GoodsDetailBean, List<GoodsDetailBean.GoodsDetailListBean>>() {
                            @Override
                            public List<GoodsDetailBean.GoodsDetailListBean> apply(GoodsDetailBean goodsDetailBean) throws Exception {
                                ArrayList<GoodsDetailBean.GoodsDetailListBean> list = new ArrayList<>();
                                //bannner+名称价格
                                GoodsDetailBean.GoodsDetailListBean banner = new GoodsDetailBean.GoodsDetailListBean();
                                banner.currentType = GoodsDetailBean.GoodsDetailListBean.TYPE_BANNER;
                                banner.data = goodsDetailBean.getData();
                                list.add(banner);
                                
                                //html
                                GoodsDetailBean.GoodsDetailListBean html = new GoodsDetailBean.GoodsDetailListBean();
                                html.currentType = GoodsDetailBean.GoodsDetailListBean.TYPE_HTML;
                                html.data = goodsDetailBean.getData().getInfo().getGoods_desc();
                                list.add(html);

                                //issue
                                for (int i = 0; i < goodsDetailBean.getData().getIssue().size(); i++) {
                                    GoodsDetailBean.GoodsDetailListBean issue = new GoodsDetailBean.GoodsDetailListBean();
                                    issue.currentType = GoodsDetailBean.GoodsDetailListBean.TYPE_ISSUE;
                                    issue.data = goodsDetailBean.getData().getIssue().get(i);
                                    list.add(issue);
                                }
                                //title
                                GoodsDetailBean.GoodsDetailListBean title = new GoodsDetailBean.GoodsDetailListBean();
                                title.currentType = GoodsDetailBean.GoodsDetailListBean.TYPE_TITLE;
                                title.data = "大家都在看";
                                list.add(title);

                                return list;
                            }
                        })
                    .subscribeWith(new ResponceSubscriber<List<GoodsDetailBean.GoodsDetailListBean> >(mView){
                        @Override
                        public void onNext(List<GoodsDetailBean.GoodsDetailListBean> list ) {
                            mView.setGoodsDetail(list);
                        }
                    })

        );
    }

}
