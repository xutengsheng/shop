package com.xts.shop.presenter.mainpage;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.interfaces.mainpage.NewMainPageContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.MainPageBean;
import com.xts.shop.utils.LogUtils;
import com.xts.shop.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainPagePresenter extends BasePresenter<NewMainPageContract.View> implements NewMainPageContract.Presenter {
    @Override
    public void getData() {
        addDisposable(
                HttpManager.getInstance()
                        .getApiService()
                        .getHome()
                        .compose(RxUtils.rxScheduler())
                        .map(new Function<MainPageBean, ArrayList<MainPageBean.MainPageListBean>>() {
                            @Override
                            public ArrayList<MainPageBean.MainPageListBean> apply(MainPageBean bean) throws Exception {
                                ArrayList<MainPageBean.MainPageListBean> list = new ArrayList<>();

                                //banner
                                MainPageBean.MainPageListBean banner = new MainPageBean.MainPageListBean();
                                banner.data = bean.getData().getBanner();
                                banner.currentType = MainPageBean.MainPageListBean.TYPE_BANNER;
                                list.add(banner);

                                //channel
                                MainPageBean.MainPageListBean channel = new MainPageBean.MainPageListBean();
                                channel.data = bean.getData().getChannel();
                                channel.currentType = MainPageBean.MainPageListBean.TYPE_CHANNEL;
                                list.add(channel);

                                //分割线
                                MainPageBean.MainPageListBean line1 = new MainPageBean.MainPageListBean();
                                line1.currentType = MainPageBean.MainPageListBean.TYPE_VIEW_LINE;
                                list.add(line1);

                                //title:品牌
                                MainPageBean.MainPageListBean brandTitle = new MainPageBean.MainPageListBean();
                                brandTitle.currentType = MainPageBean.MainPageListBean.TYPE_TITLE;
                                brandTitle.title = "品牌制造商直供";
                                list.add(brandTitle);
                                
                                //品牌
                                for (int i = 0; i < bean.getData().getBrandList().size(); i++) {
                                    MainPageBean.MainPageListBean brand = new MainPageBean.MainPageListBean();
                                    brand.data = bean.getData().getBrandList().get(i);;
                                    brand.currentType = MainPageBean.MainPageListBean.TYPE_BRAND;
                                    list.add(brand);
                                }

                                //title:新品
                                MainPageBean.MainPageListBean newGoodsTitle = new MainPageBean.MainPageListBean();
                                newGoodsTitle.currentType = MainPageBean.MainPageListBean.TYPE_TITLE;
                                newGoodsTitle.title = "周一周四 . 新品首发";
                                list.add(newGoodsTitle);

                                //newgood
                                for(MainPageBean.DataBean.NewGoodsListBean item:bean.getData().getNewGoodsList()) {
                                    MainPageBean.MainPageListBean newGood = new MainPageBean.MainPageListBean();
                                    newGood.currentType = MainPageBean.MainPageListBean.TYPE_NEWGOOD;
                                    newGood.data = item;
                                    list.add(newGood);
                                }
                                //分割线
                                MainPageBean.MainPageListBean lineHot = new MainPageBean.MainPageListBean();
                                lineHot.currentType = MainPageBean.MainPageListBean.TYPE_VIEW_LINE;
                                list.add(lineHot);
                                //hottitle
                                MainPageBean.MainPageListBean hotTitle = new MainPageBean.MainPageListBean();
                                hotTitle.currentType = MainPageBean.MainPageListBean.TYPE_TITLE;
                                hotTitle.title = "人气推荐";
                                list.add(hotTitle);
                                //hot
                                for(MainPageBean.DataBean.HotGoodsListBean item:bean.getData().getHotGoodsList()) {
                                    MainPageBean.MainPageListBean hot = new MainPageBean.MainPageListBean();
                                    hot.currentType = MainPageBean.MainPageListBean.TYPE_HOTGOOD;
                                    hot.data = item;
                                    list.add(hot);
                                }
                                //分割线
                                MainPageBean.MainPageListBean lineTopic = new MainPageBean.MainPageListBean();
                                lineTopic.currentType = MainPageBean.MainPageListBean.TYPE_VIEW_LINE;
                                list.add(lineTopic);
                                //topictitle
                                MainPageBean.MainPageListBean topicTitle = new MainPageBean.MainPageListBean();
                                topicTitle.currentType = MainPageBean.MainPageListBean.TYPE_TITLE;
                                topicTitle.title = "专题精选";
                                list.add(topicTitle);
                                //topic
                                MainPageBean.MainPageListBean topic = new MainPageBean.MainPageListBean();
                                topic.currentType = MainPageBean.MainPageListBean.TYPE_TOPIC;
                                topic.data = bean.getData().getTopicList();
                                list.add(topic);
                                //分割线
                                MainPageBean.MainPageListBean lineCategory = new MainPageBean.MainPageListBean();
                                lineCategory.currentType = MainPageBean.MainPageListBean.TYPE_VIEW_LINE;
                                list.add(lineCategory);
                                //category
                                for(MainPageBean.DataBean.CategoryListBean item:bean.getData().getCategoryList()) {
                                    MainPageBean.MainPageListBean category = new MainPageBean.MainPageListBean();
                                    category.currentType = MainPageBean.MainPageListBean.TYPE_TITLE;
                                    category.title = item.getName();
                                    list.add(category);
                                    for(MainPageBean.DataBean.CategoryListBean.GoodsListBean data:item.getGoodsList()){
                                        MainPageBean.MainPageListBean good = new MainPageBean.MainPageListBean();
                                        good.currentType = MainPageBean.MainPageListBean.TYPE_CATEGORY;
                                        good.data = data;
                                        list.add(good);
                                    }
                                    //分割线
                                    MainPageBean.MainPageListBean line = new MainPageBean.MainPageListBean();
                                    line.currentType = MainPageBean.MainPageListBean.TYPE_VIEW_LINE;
                                    list.add(line);
                                }

                                return list;
                            }
                        })
                        .subscribeWith(new ResponceSubscriber<ArrayList<MainPageBean.MainPageListBean>>(mView) {
                            @Override
                            public void onNext(ArrayList<MainPageBean.MainPageListBean> list) {
                                LogUtils.print("size:"+list.size());
                                mView.setData(list);
                            }
                        })
        );
    }
}
