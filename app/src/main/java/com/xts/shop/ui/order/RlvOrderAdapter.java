package com.xts.shop.ui.order;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.model.bean.AddCartBean;
import com.xts.shop.ui.fragment.CartFragment;

import java.util.List;

public class RlvOrderAdapter extends BaseQuickAdapter<AddCartBean.DataBean.CartListBean, BaseViewHolder> {

    private String mRmb = BaseApp.getRes().getString(R.string.rmb);
    private String mX = BaseApp.getRes().getString(R.string.x);
    public RlvOrderAdapter(int layoutResId, @Nullable List<AddCartBean.DataBean.CartListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddCartBean.DataBean.CartListBean item) {
        CheckBox cb = helper.getView(R.id.cb);
        cb.setVisibility(View.GONE);
        ImageView ivGoods = helper.getView(R.id.iv_goods);
        TextView tvGoodsName = helper.getView(R.id.tv_goods_name);
        TextView tvGoodsPrice = helper.getView(R.id.tv_goods_price);
        TextView tvCount = helper.getView(R.id.tv_count);
        LinearLayout llContainer = helper.getView(R.id.ll_container);

        Glide.with(mContext).load(item.getList_pic_url()).into(ivGoods);
        tvGoodsName.setText(item.getGoods_name());
        tvGoodsPrice.setText(mRmb + item.getMarket_price());
        tvCount.setText(mX + item.getNumber());
        llContainer.setVisibility(View.GONE);
    }
}
