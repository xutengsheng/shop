package com.xts.shop.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseRlvAdapter;
import com.xts.shop.model.bean.TopicBean;

import java.util.ArrayList;

public class RlvTopicAdapter2 extends BaseRlvAdapter<TopicBean.DataBeanX.DataBean> {
    public RlvTopicAdapter2(Context context, ArrayList<TopicBean.DataBeanX.DataBean> list) {
        super(context, list);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_topic;
    }

    @Override
    protected void bindData(BaseRlvAdapter.VH vh, TopicBean.DataBeanX.DataBean dataBean) {
        Glide.with(mContext).load(dataBean.getScene_pic_url()).into((ImageView) vh.getViewById(R.id.iv));
        TextView tvTitle = (TextView) vh.getViewById(R.id.tv_title);
        tvTitle.setText(dataBean.getTitle());
        TextView tvDesc = (TextView) vh.getViewById(R.id.tv_desc);
        tvDesc.setText(dataBean.getSubtitle());
        TextView tvPrice = (TextView) vh.getViewById(R.id.tv_price);
        tvPrice.setText(dataBean.getPrice_info()+ BaseApp.getRes().getString(R.string.yuan));
    }
}
