package com.xts.shop.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.shop.R;
import com.xts.shop.common.GlideImageLoader;
import com.xts.shop.model.bean.MainPageBean;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class NewRlvMainPageAdapter extends
        BaseMultiItemQuickAdapter<MainPageBean.MainPageListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewRlvMainPageAdapter(List<MainPageBean.MainPageListBean> data) {
        super(data);
        addItemType(MainPageBean.MainPageListBean.TYPE_BANNER, R.layout.layout_item_banner);
        addItemType(MainPageBean.MainPageListBean.TYPE_CHANNEL,R.layout.layout_item_channel);
        addItemType(MainPageBean.MainPageListBean.TYPE_TITLE,R.layout.layout_item_title);
        addItemType(MainPageBean.MainPageListBean.TYPE_BRAND,R.layout.layout_item_brand);
        addItemType(MainPageBean.MainPageListBean.TYPE_NEWGOOD,R.layout.layout_item_newgood);
        addItemType(MainPageBean.MainPageListBean.TYPE_HOTGOOD,R.layout.layout_item_hotgood);
        addItemType(MainPageBean.MainPageListBean.TYPE_TOPIC,R.layout.layout_topic_recy);
        addItemType(MainPageBean.MainPageListBean.TYPE_CATEGORY,R.layout.layout_item_category);
        addItemType(MainPageBean.MainPageListBean.TYPE_VIEW_LINE,R.layout.layout_view_line);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainPageBean.MainPageListBean item) {
        switch (item.getItemType()){
            case MainPageBean.MainPageListBean.TYPE_BANNER:
                //banner放入到条目中会出现显示不出来
                //initBanner(helper,item);
                break;
            case MainPageBean.MainPageListBean.TYPE_CHANNEL:
                initChannel(helper,item);
                break;
            case MainPageBean.MainPageListBean.TYPE_BRAND:
                initBrand(helper, (MainPageBean.DataBean.BrandListBean) item.data);
                break;
            case MainPageBean.MainPageListBean.TYPE_TITLE:
                initTitle(helper,item);
                break;
            case MainPageBean.MainPageListBean.TYPE_NEWGOOD:
                initNewGood(helper, (MainPageBean.DataBean.NewGoodsListBean) item.data);
                break;
            case MainPageBean.MainPageListBean.TYPE_HOTGOOD:
                initHotGood(helper, (MainPageBean.DataBean.HotGoodsListBean) item.data);
                break;
            case MainPageBean.MainPageListBean.TYPE_TOPIC:
                initTopic(helper, (List<MainPageBean.DataBean.TopicListBean>) item.data);
                break;
            case MainPageBean.MainPageListBean.TYPE_CATEGORY:
                initCategory(helper, (MainPageBean.DataBean.CategoryListBean.GoodsListBean) item.data);
                break;
            case MainPageBean.MainPageListBean.TYPE_VIEW_LINE:

                break;
        }
    }

    //初始化banner
    private void initBanner(BaseViewHolder helper, MainPageBean.MainPageListBean data){
        Banner banner = helper.getView(R.id.banner);
        if(TextUtils.isEmpty(banner.tag)) {
            List<String> imgs = new ArrayList<>();
            for (MainPageBean.DataBean.BannerBean item : (List<MainPageBean.DataBean.BannerBean>) data.data) {
                imgs.add(item.getImage_url());
            }
            banner.tag = "true";
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(imgs);
            banner.start();
        }
    }

    //初始化channel
    private void initChannel(BaseViewHolder helper, MainPageBean.MainPageListBean data){
        RecyclerView rlv = helper.getView(R.id.recy_channel);
        if(rlv.getAdapter() == null) {
            rlv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            ChannelAdapter adapter = new ChannelAdapter(R.layout.item_item_channel, (List<MainPageBean.DataBean.ChannelBean>)data.data);
            adapter.bindToRecyclerView(rlv);
        }
    }

    //初始化title
    private void initTitle(BaseViewHolder helper, MainPageBean.MainPageListBean data){
        helper.setText(R.id.txt_title,data.title);
    }

    //初始化品牌
    private void initBrand(BaseViewHolder helper, MainPageBean.DataBean.BrandListBean data){
        Glide.with(mContext).load(data.getList_pic_url()).into((ImageView) helper.getView(R.id.img_brand));
        helper.setText(R.id.txt_name,data.getName());
        helper.setText(R.id.txt_price,"￥"+data.getFloor_price());
    }

    //初始化新商品数据
    private void initNewGood(BaseViewHolder helper, MainPageBean.DataBean.NewGoodsListBean data){
        Glide.with(mContext).load(data.getList_pic_url()).into((ImageView) helper.getView(R.id.img_newGood));
        helper.setText(R.id.txt_name,data.getName());
        helper.setText(R.id.txt_price,"￥"+data.getRetail_price());
    }

    //初始化热门商品
    private void initHotGood(BaseViewHolder helper, MainPageBean.DataBean.HotGoodsListBean data) {
        Glide.with(mContext).load(data.getList_pic_url()).into((ImageView) helper.getView(R.id.img_hot));
        helper.setText(R.id.txt_name,data.getName());
        helper.setText(R.id.txt_des,data.getGoods_brief());
        helper.setText(R.id.txt_price,"￥"+data.getRetail_price());
    }

    //初始化专题
    private void initTopic(BaseViewHolder helper, List<MainPageBean.DataBean.TopicListBean> data) {
        RecyclerView recyclerView = helper.getView(R.id.recy_topic);
        if(recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            TopicAdapter topicAdapter = new TopicAdapter(R.layout.layout_item_topic, data);
            topicAdapter.bindToRecyclerView(recyclerView);
        }
    }

    //初始化category
    private void initCategory(BaseViewHolder helper, MainPageBean.DataBean.CategoryListBean.GoodsListBean data) {
        Glide.with(mContext).load(data.getList_pic_url()).into((ImageView) helper.getView(R.id.img_category));
        helper.setText(R.id.txt_name,data.getName());
        helper.setText(R.id.txt_price,"￥"+data.getRetail_price());
    }
}
