package com.xts.shop.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseRlvAdapter;
import com.xts.shop.model.bean.MainPageBean;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvMainPageAdapter extends RecyclerView.Adapter {

    public static final int TYPE_BANNER = 0;
    public static final int TYPE_CHANNEL = 1;
    public static final int TYPE_BRAND = 3;
    public static final int TYPE_NEWS_GOOD = 4;
    public static final int TYPE_HOT = 5;
    public static final int TYPE_TITLE = 6;
    public static final int TYPE_TOPIC = 7;
    public static final int TYPE_CATEGORY = 8;
    private Context mContext;
    private ArrayList<MainPageBean.DataBean.BannerBean> mBanner;
    private ArrayList<MainPageBean.DataBean.ChannelBean> mChannel;
    private ArrayList<MainPageBean.DataBean.NewGoodsListBean> mNewGoodsList;
    private ArrayList<MainPageBean.DataBean.HotGoodsListBean> mHotGoodsList;
    private ArrayList<MainPageBean.DataBean.BrandListBean> mBrandList;
    private ArrayList<MainPageBean.DataBean.TopicListBean> mTopicList;
    private ArrayList<MainPageBean.DataBean.CategoryListBean> mCategoryList;

    public RlvMainPageAdapter(Context context, ArrayList<MainPageBean.DataBean.BannerBean> banner, ArrayList<MainPageBean.DataBean.ChannelBean> channel, ArrayList<MainPageBean.DataBean.NewGoodsListBean> newGoodsList, ArrayList<MainPageBean.DataBean.HotGoodsListBean> hotGoodsList, ArrayList<MainPageBean.DataBean.BrandListBean> brandList, ArrayList<MainPageBean.DataBean.TopicListBean> topicList, ArrayList<MainPageBean.DataBean.CategoryListBean> categoryList) {

        mContext = context;
        mBanner = banner;
        mChannel = channel;
        mNewGoodsList = newGoodsList;
        mHotGoodsList = hotGoodsList;
        mBrandList = brandList;
        mTopicList = topicList;
        mCategoryList = categoryList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_BANNER;
        }else if (position == 1){
            return TYPE_CHANNEL;
        }else if (position == 2){
            return TYPE_TITLE;
        }else if (position == 3){
            return TYPE_BRAND;
        }else if (position == 4){
            return TYPE_TITLE;
        }else if (position == 5){
            return TYPE_NEWS_GOOD;
        }else if (position == 6){
            return TYPE_TITLE;
        }else if (position == 7){
            return TYPE_HOT;
        }else if (position == 8){
            return TYPE_TITLE;
        }else if (position == 9){
            return TYPE_TOPIC;
        }else {
            if (position % 2 == 0){
                return TYPE_TITLE;
            }else {
                return TYPE_CATEGORY;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_BANNER){
            return new BannerVH(inflater.inflate(R.layout.item_banner,parent,false));
        }else if (viewType == TYPE_CHANNEL){
            return new RlvVH(inflater.inflate(R.layout.item_rlv,parent,false));
        }else if (viewType == TYPE_TITLE){
            return new TitleVH(inflater.inflate(R.layout.item_title,parent,false));
        }else if (viewType == TYPE_BRAND){
            return new RlvVH(inflater.inflate(R.layout.item_rlv,parent,false));
        }else if (viewType == TYPE_NEWS_GOOD){
            return new RlvVH(inflater.inflate(R.layout.item_rlv,parent,false));
        }else if (viewType == TYPE_HOT){
            return new RlvVH(inflater.inflate(R.layout.item_rlv,parent,false));
        }else if (viewType == TYPE_TOPIC){
            return new RlvVH(inflater.inflate(R.layout.item_rlv,parent,false));
        }else {
           //分类
            return new RlvVH(inflater.inflate(R.layout.item_rlv,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_BANNER){
            BannerVH bannerVH = (BannerVH) holder;
            bannerVH.mBanner.setImages(mBanner)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            MainPageBean.DataBean.BannerBean bean = (MainPageBean.DataBean.BannerBean) path;
                            Glide.with(mContext).load(bean.getImage_url()).into(imageView);
                        }
                    })
                    .start();
        }else if (viewType == TYPE_CHANNEL){
            RlvVH rlvVH = (RlvVH) holder;
            rlvVH.mRlv.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
            rlvVH.mRlv.setAdapter(new BaseRlvAdapter<MainPageBean.DataBean.ChannelBean>(mContext,mChannel) {
                @Override
                protected int getLayout() {
                    return R.layout.item_item_channel;
                }

                @Override
                protected void bindData(VH vh, MainPageBean.DataBean.ChannelBean bean) {
                    TextView tvChannel = (TextView) vh.getViewById(R.id.tv_channel);
                    tvChannel.setText(bean.getName());
                }
            });
        }else if (viewType == TYPE_TITLE){
            TitleVH titleVH = (TitleVH) holder;
            if (position == 2){
                titleVH.mTitle.setText("品牌制造商直供");
            }else if (position == 4){
                titleVH.mTitle.setText("周一周四 . 新品首发");
            }else if (position == 6){
                titleVH.mTitle.setText("人气推荐");
            }else if (position == 8){
                titleVH.mTitle.setText("专题精选");
            }else {
                int newPosition = (position - 10)/2;
                //10 ---> 0
                //12 ---- > 1
                //14 ----> 2
                MainPageBean.DataBean.CategoryListBean bean = mCategoryList.get(newPosition);
                titleVH.mTitle.setText(bean.getName());
            }
        }else if (viewType == TYPE_BRAND){
            RlvVH rlvVH = (RlvVH) holder;
            rlvVH.mRlv.setLayoutManager(new GridLayoutManager(mContext,2));
            rlvVH.mRlv.setAdapter(new BaseRlvAdapter<MainPageBean.DataBean.BrandListBean>(mContext,mBrandList) {
                @Override
                protected int getLayout() {
                    return R.layout.item_item_brand;
                }

                @Override
                protected void bindData(VH vh, MainPageBean.DataBean.BrandListBean bean) {
                    TextView tvVendor = (TextView) vh.getViewById(R.id.tv_vendor);
                    tvVendor.setText(bean.getName());
                    TextView tvPrice = (TextView) vh.getViewById(R.id.tv_price);
                    tvPrice.setText(bean.getFloor_price()+ BaseApp.getRes().getString(R.string.yuan));
                    ImageView iv = (ImageView) vh.getViewById(R.id.iv);
                    Glide.with(mContext).load(bean.getPic_url()).into(iv);
                }
            });
        }else if (viewType == TYPE_NEWS_GOOD){
            RlvVH rlvVH = (RlvVH) holder;
            rlvVH.mRlv.setLayoutManager(new GridLayoutManager(mContext,2));
            rlvVH.mRlv.setAdapter(new BaseRlvAdapter<MainPageBean.DataBean.NewGoodsListBean>(mContext,mNewGoodsList) {
                @Override
                protected int getLayout() {
                    return R.layout.item_item_news_good;
                }

                @Override
                protected void bindData(VH vh, MainPageBean.DataBean.NewGoodsListBean bean) {
                    TextView tvVendor = (TextView) vh.getViewById(R.id.tv_vendor);
                    tvVendor.setText(bean.getName());
                    TextView tvPrice = (TextView) vh.getViewById(R.id.tv_price);
                    tvPrice.setText(BaseApp.getRes().getString(R.string.rmb)+bean.getRetail_price());
                    ImageView iv = (ImageView) vh.getViewById(R.id.iv);
                    Glide.with(mContext).load(bean.getList_pic_url()).into(iv);
                }
            });
        }else if (viewType == TYPE_HOT){
            RlvVH rlvVH = (RlvVH) holder;
            rlvVH.mRlv.setLayoutManager(new LinearLayoutManager(mContext));
            rlvVH.mRlv.setAdapter(new BaseRlvAdapter<MainPageBean.DataBean.HotGoodsListBean>(mContext,mHotGoodsList) {
                @Override
                protected int getLayout() {
                    return R.layout.item_item_hot;
                }

                @Override
                protected void bindData(VH vh, MainPageBean.DataBean.HotGoodsListBean bean) {
                    TextView tvVendor = (TextView) vh.getViewById(R.id.tv_vendor);
                    tvVendor.setText(bean.getName());
                    TextView tvDesc = (TextView) vh.getViewById(R.id.tv_desc);
                    tvDesc.setText(bean.getGoods_brief());
                    TextView tvPrice = (TextView) vh.getViewById(R.id.tv_price);
                    tvPrice.setText(BaseApp.getRes().getString(R.string.rmb)+bean.getRetail_price());
                    ImageView iv = (ImageView) vh.getViewById(R.id.iv);
                    Glide.with(mContext).load(bean.getList_pic_url()).into(iv);
                }
            });
        }else if (viewType == TYPE_TOPIC){
            RlvVH rlvVH = (RlvVH) holder;
            rlvVH.mRlv.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
            rlvVH.mRlv.setAdapter(new BaseRlvAdapter<MainPageBean.DataBean.TopicListBean>(mContext,mTopicList) {
                @Override
                protected int getLayout() {
                    return R.layout.item_item_topic;
                }

                @Override
                protected void bindData(VH vh, MainPageBean.DataBean.TopicListBean bean) {
                    TextView tvVendor = (TextView) vh.getViewById(R.id.tv_vendor);
                    tvVendor.setText(bean.getTitle());
                    TextView tvDesc = (TextView) vh.getViewById(R.id.tv_desc);
                    tvDesc.setText(bean.getSubtitle());
                    TextView tvPrice = (TextView) vh.getViewById(R.id.tv_price);
                    tvPrice.setText(BaseApp.getRes().getString(R.string.rmb)+bean.getPrice_info()+
                            BaseApp.getRes().getString(R.string.yuan));
                    ImageView iv = (ImageView) vh.getViewById(R.id.iv);
                    Glide.with(mContext).load(bean.getScene_pic_url()).into(iv);
                }
            });
        }else {
            //分类
            RlvVH rlvVH = (RlvVH) holder;
            int newPosition = (position - 11)/2;
            //11 ---> 0
            //13 ---- > 1
            //15 ----> 2
            MainPageBean.DataBean.CategoryListBean bean = mCategoryList.get(newPosition);
            rlvVH.mRlv.setLayoutManager(new GridLayoutManager(mContext,2));
            rlvVH.mRlv.setAdapter(new BaseRlvAdapter<MainPageBean.DataBean.CategoryListBean.GoodsListBean>(mContext, (ArrayList<MainPageBean.DataBean.CategoryListBean.GoodsListBean>) bean.getGoodsList()) {
                @Override
                protected int getLayout() {
                    return R.layout.item_item_news_good;
                }

                @Override
                protected void bindData(VH vh, MainPageBean.DataBean.CategoryListBean.GoodsListBean bean) {
                    TextView tvVendor = (TextView) vh.getViewById(R.id.tv_vendor);
                    tvVendor.setText(bean.getName());
                    TextView tvPrice = (TextView) vh.getViewById(R.id.tv_price);
                    tvPrice.setText(BaseApp.getRes().getString(R.string.rmb)+bean.getRetail_price());
                    ImageView iv = (ImageView) vh.getViewById(R.id.iv);
                    Glide.with(mContext).load(bean.getList_pic_url()).into(iv);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //前面10个布局直接写死了,就10个,到时候没有的可以考虑隐藏
        return 10+mCategoryList.size()*2;
    }

    public void addData(MainPageBean bean) {
        MainPageBean.DataBean data = bean.getData();
        mBanner.clear();
        mBanner.addAll(data.getBanner());
        mChannel.clear();
        mChannel.addAll(data.getChannel());
        mNewGoodsList.clear();
        mNewGoodsList.addAll(data.getNewGoodsList());
        mHotGoodsList.clear();
        mHotGoodsList.addAll(data.getHotGoodsList());
        mBrandList.clear();
        mBrandList.addAll(data.getBrandList());
        mTopicList.clear();
        mTopicList.addAll(data.getTopicList());
        mCategoryList.clear();
        mCategoryList.addAll(data.getCategoryList());

        notifyDataSetChanged();

    }

    class BannerVH extends RecyclerView.ViewHolder{
        @BindView(R.id.banner)
        Banner mBanner;
        public BannerVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class RlvVH extends RecyclerView.ViewHolder{
        @BindView(R.id.rlv)
        RecyclerView mRlv;
        public RlvVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class TitleVH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView mTitle;
        public TitleVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
