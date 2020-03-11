package com.xts.shop.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xts.shop.R;
import com.xts.shop.base.BaseRlvAdapter;
import com.xts.shop.model.bean.SortCurrentBean;

import java.util.ArrayList;

public class RlvSortItemAdapter extends BaseRlvAdapter<SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean> {
    public RlvSortItemAdapter(Context context, ArrayList<SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list) {
        super(context, list);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_item_sort_item;
    }

    @Override
    protected void bindData(VH vh, SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean bean) {
        ImageView iv = (ImageView) vh.getViewById(R.id.iv);
        TextView tv = (TextView) vh.getViewById(R.id.tv_vendor);
        tv.setText(bean.getName());
        Glide.with(mContext).load(bean.getWap_banner_url()).into(iv);

    }

}
