package com.xts.shop.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.model.bean.TopicBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvTopicAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    public final ArrayList<TopicBean.DataBeanX.DataBean> mList;

    public RlvTopicAdapter(Context context, ArrayList<TopicBean.DataBeanX.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_topic, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TopicBean.DataBeanX.DataBean bean = mList.get(position);
        VH vh = (VH) holder;
        Glide.with(mContext).load(bean.getScene_pic_url()).into(vh.mIv);
        vh.mTvTitle.setText(bean.getTitle());
        vh.mTvDesc.setText(bean.getSubtitle());
        vh.mTvPrice.setText(bean.getPrice_info()+ BaseApp.getRes().getString(R.string.yuan));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<TopicBean.DataBeanX.DataBean> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.iv)
        ImageView mIv;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
