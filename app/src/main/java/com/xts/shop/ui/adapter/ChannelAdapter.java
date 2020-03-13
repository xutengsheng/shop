package com.xts.shop.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.shop.R;
import com.xts.shop.model.bean.MainPageBean;

import java.util.List;

public class ChannelAdapter extends BaseQuickAdapter<MainPageBean.DataBean.ChannelBean, BaseViewHolder> {

    public ChannelAdapter(int layoutResId, @Nullable List<MainPageBean.DataBean.ChannelBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainPageBean.DataBean.ChannelBean item) {
        helper.setText(R.id.tv_channel,item.getName());
    }
}
