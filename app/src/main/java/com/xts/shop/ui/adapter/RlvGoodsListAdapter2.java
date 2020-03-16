package com.xts.shop.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.model.bean.GoodListBean;

import java.util.List;

public class RlvGoodsListAdapter2 extends
        BaseQuickAdapter<GoodListBean.DataBeanX.DataBean, BaseViewHolder> {
    public RlvGoodsListAdapter2(int layoutResId,
                                @Nullable List<GoodListBean.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodListBean.DataBeanX.DataBean item) {
        helper.setText(R.id.tv_vendor,item.getName());
        helper.setText(R.id.tv_price, BaseApp.getRes().getString(R.string.rmb)+item.getRetail_price());
        Glide.with(mContext).load(item.getList_pic_url()).into((ImageView) helper.getView(R.id.iv));
    }
}
