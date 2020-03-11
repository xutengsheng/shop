package com.xts.shop.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseRlvAdapter;
import com.xts.shop.model.bean.GoodsListBean;

import java.util.ArrayList;

public class RlvGoodsListAdapter extends BaseRlvAdapter<GoodsListBean.DataBeanX.DataBean> {
    public RlvGoodsListAdapter(Context context, ArrayList list) {
        super(context, list);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_item_news_good;
    }

    @Override
    protected void bindData(VH vh, GoodsListBean.DataBeanX.DataBean bean) {
        ImageView iv = (ImageView) vh.getViewById(R.id.iv);
        TextView tvVender = (TextView) vh.getViewById(R.id.tv_vendor);
        TextView tvPrice = (TextView) vh.getViewById(R.id.tv_price);
        Glide.with(mContext).load(bean.getList_pic_url()).into(iv);
        tvVender.setText(bean.getName());
        tvPrice.setText(BaseApp.getRes().getString(R.string.rmb)+bean.getRetail_price());
    }
}
