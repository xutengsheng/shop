package com.xts.shop.ui.goodsList;

import android.media.MediaDescrambler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xts.shop.R;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.common.GlideImageLoader;
import com.xts.shop.interfaces.goods.GoodsDetailContract;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.GoodsDetailBean;
import com.xts.shop.presenter.goods.GoodsDetailPresenter;
import com.xts.shop.ui.adapter.GoodsDetailAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsDetailActivity extends BaseActivity<GoodsDetailContract.Presenter>
    implements GoodsDetailContract.View{
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private GoodsDetailAdapter mAdapter;
    private ArrayList<GoodsDetailBean.DataBean.GalleryBean> mBannerImages;
    private float mRetail_price;
    private String mTitle;
    private String mDesc;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ArrayList<GoodsDetailBean.GoodsDetailListBean> list = new ArrayList<>();
        mAdapter = new GoodsDetailAdapter(list);
        mRlv.setLayoutManager(new GridLayoutManager(this,2));
        mRlv.setAdapter(mAdapter);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int itemType = list.get(i).getItemType();
                switch (itemType){
                    case GoodsDetailBean.GoodsDetailListBean.TYPE_BANNER:
                    case GoodsDetailBean.GoodsDetailListBean.TYPE_HTML:
                    case GoodsDetailBean.GoodsDetailListBean.TYPE_TITLE:
                    case GoodsDetailBean.GoodsDetailListBean.TYPE_ISSUE:
                        return 2;
                    case GoodsDetailBean.GoodsDetailListBean.TYPE_GOODS_LIST:
                        return 1;

                }
                return 0;
            }
        });
        mAdapter.bindToRecyclerView(mRlv);
        addHeader();
    }

    private void addHeader() {
        mBannerImages = new ArrayList<>();
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_goods_detail_top, null);
        Banner banner = inflate.findViewById(R.id.banner);
        TextView tvTitle = inflate.findViewById(R.id.tv_title);
        TextView tvDesc = inflate.findViewById(R.id.tv_desc);
        TextView tvPrice = inflate.findViewById(R.id.tv_price);

        tvTitle.setText(mTitle);
        tvDesc.setText(mDesc);
        tvPrice.setText(mRetail_price+" 元");
        banner.setImages(mBannerImages)
                .setImageLoader(new GlideImageLoader())
                .start();


        mAdapter.addHeaderView(inflate);
    }

    @Override
    protected GoodsDetailContract.Presenter initPresenter() {
        return new GoodsDetailPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void setGoodsDetail(List<GoodsDetailBean.GoodsDetailListBean> list) {
        GoodsDetailBean.GoodsDetailListBean bean = list.get(0);
        GoodsDetailBean.DataBean data = (GoodsDetailBean.DataBean) bean.data;
        mRetail_price = data.getInfo().getRetail_price();
        mTitle = data.getInfo().getName();
        mDesc = data.getInfo().getGoods_brief();
        mBannerImages.addAll(data.getGallery());
        mAdapter.addData(list);

    }
}
