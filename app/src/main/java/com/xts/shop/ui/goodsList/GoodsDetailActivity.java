package com.xts.shop.ui.goodsList;

import android.content.Context;
import android.content.Intent;
import android.media.MediaDescrambler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xts.shop.MainActivity;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.common.Constant;
import com.xts.shop.common.GlideImageLoader;
import com.xts.shop.interfaces.goods.GoodsDetailContract;
import com.xts.shop.model.bean.AddCartBean;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.GoodsDetailBean;
import com.xts.shop.presenter.goods.GoodsDetailPresenter;
import com.xts.shop.ui.adapter.GoodsDetailAdapter;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

public class GoodsDetailActivity extends BaseActivity<GoodsDetailContract.Presenter>
        implements GoodsDetailContract.View {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.iv_like)
    ImageView mIvLike;
    @BindView(R.id.iv_cart)
    ImageView mIvCart;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_buy)
    TextView mTvBuy;

    private GoodsDetailAdapter mAdapter;
    private ArrayList<GoodsDetailBean.DataBeanX.GalleryBean> mBannerImages;
    private float mRetail_price;
    private String mTitle;
    private String mDesc;
    private int mId;
    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvPrice;
    private Banner mBanner;
    private GoodsDetailBean mGoodDetailBean;

    @Override
    protected void initData() {
        mPresenter.getGoodsDetail(mId);
        mPresenter.getCart();
    }

    @Override
    protected void initView() {
        mId = getIntent().getIntExtra(Constant.DATA, 0);

        ArrayList<GoodsDetailBean.GoodsDetailListBean> list = new ArrayList<>();
        mAdapter = new GoodsDetailAdapter(list);
        mRlv.setLayoutManager(new GridLayoutManager(this, 2));
        mRlv.setAdapter(mAdapter);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int itemType = list.get(i).getItemType();
                switch (itemType) {
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

    }

    private void addHeader() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_goods_detail_top, null);
        mBanner = inflate.findViewById(R.id.banner);
        mTvTitle = inflate.findViewById(R.id.tv_title);
        mTvDesc = inflate.findViewById(R.id.tv_desc);
        mTvPrice = inflate.findViewById(R.id.tv_price);

        setHeaderData();


        mAdapter.addHeaderView(inflate);
    }

    private void setHeaderData() {
        mTvTitle.setText(mTitle);
        mTvDesc.setText(mDesc);
        mTvPrice.setText(mRetail_price + " 元");
        mBanner.setImages(mBannerImages)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        GoodsDetailBean.DataBeanX.GalleryBean bean = (GoodsDetailBean.DataBeanX.GalleryBean) path;
                        Glide.with(context).load(bean.getImg_url()).into(imageView);
                    }
                })
                .start();
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
    public void setGoodsDetail(GoodsDetailBean goodsDetailBean) {
        this.mGoodDetailBean = goodsDetailBean;
        ArrayList<GoodsDetailBean.GoodsDetailListBean> list = new ArrayList<>();
        //bannner+名称价格
        GoodsDetailBean.GoodsDetailListBean banner = new GoodsDetailBean.GoodsDetailListBean();
        banner.currentType = GoodsDetailBean.GoodsDetailListBean.TYPE_BANNER;
        banner.data = goodsDetailBean.getData();
        list.add(banner);

        //html
        GoodsDetailBean.GoodsDetailListBean html = new GoodsDetailBean.GoodsDetailListBean();
        html.currentType = GoodsDetailBean.GoodsDetailListBean.TYPE_HTML;
        html.data = goodsDetailBean.getData().getInfo();
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

        GoodsDetailBean.GoodsDetailListBean bean = list.remove(0);
        GoodsDetailBean.DataBeanX data = (GoodsDetailBean.DataBeanX) bean.data;
        mRetail_price = data.getInfo().getRetail_price();
        mTitle = data.getInfo().getName();
        mDesc = data.getInfo().getGoods_brief();
        mBannerImages = new ArrayList<>();
        mBannerImages.addAll(data.getGallery());
        addHeader();
        mAdapter.addData(list);
    }

    @Override
    public void addCartResult(AddCartBean bean) {

        int goodsCount = bean.getData().getCartTotal().getGoodsCount();
        new QBadgeView(this).bindTarget(mIvCart).setBadgeNumber(goodsCount);
    }

    @OnClick({R.id.iv_like, R.id.iv_cart, R.id.tv_buy, R.id.tv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_like:
                break;
            case R.id.iv_cart:
                go2Main();
                break;
            case R.id.tv_add:
                addCart();
                break;
            case R.id.tv_buy:
                break;
        }
    }

    private void addCart() {
        List<GoodsDetailBean.DataBeanX.ProductListBean> productList = mGoodDetailBean.getData().getProductList();
        if (productList != null && productList.size() > 0) {
            GoodsDetailBean.DataBeanX.ProductListBean bean = productList.get(0);
            mPresenter.addCart(bean.getGoods_id(),bean.getId(),1);
        }
    }

    private void go2Main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
