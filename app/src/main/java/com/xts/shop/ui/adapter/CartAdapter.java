package com.xts.shop.ui.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.util.List;

import com.xts.shop.model.bean.UpdateCartEvent;
import com.xts.shop.ui.fragment.CartFragment;
import com.xts.shop.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;


public class CartAdapter extends BaseQuickAdapter<AddCartBean.DataBean.CartListBean, BaseViewHolder> {
    public int mCurrentType = CartFragment.TYPE_NORMAL;
    private String mChoosed = BaseApp.getRes().getString(R.string.choosed);
    private String mRmb = BaseApp.getRes().getString(R.string.rmb);
    private String mX = BaseApp.getRes().getString(R.string.x);

    public CartAdapter(int layoutResId, @Nullable List<AddCartBean.DataBean.CartListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddCartBean.DataBean.CartListBean item) {
        CheckBox cb = helper.getView(R.id.cb);
        ImageView ivGoods = helper.getView(R.id.iv_goods);
        TextView tvGoodsName = helper.getView(R.id.tv_goods_name);
        TextView tvGoodsPrice = helper.getView(R.id.tv_goods_price);
        TextView tvCount = helper.getView(R.id.tv_count);
        TextView tvMinus = helper.getView(R.id.tv_minus);
        TextView tvNumber = helper.getView(R.id.tv_number);
        TextView tvPlus = helper.getView(R.id.tv_plus);
        LinearLayout llContainer = helper.getView(R.id.ll_container);

        if (mCurrentType == CartFragment.TYPE_NORMAL) {
            boolean checked = item.getChecked() == 1;
            cb.setChecked(checked);
            Glide.with(mContext).load(item.getList_pic_url()).into(ivGoods);
            tvGoodsName.setText(item.getGoods_name());
            tvGoodsPrice.setText(mRmb + item.getMarket_price());
            tvCount.setText(mX + item.getNumber());
            llContainer.setVisibility(View.GONE);
        } else {
            //编辑状态
            boolean checked = item.isEditChecked();
            cb.setChecked(checked);
            Glide.with(mContext).load(item.getList_pic_url()).into(ivGoods);
            tvGoodsName.setText(item.getGoods_name());
            tvGoodsPrice.setText(mRmb + item.getMarket_price());
            tvCount.setText(mChoosed);
            llContainer.setVisibility(View.VISIBLE);

            tvNumber.setText(item.getNumber() + "");

            tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = item.getNumber();
                    if (number > 0) {
                        item.setNumber(number - 1);
                        notifyCartUpdate(new UpdateCartEvent(item.getGoods_id(),item.getProduct_id(),
                                item.getId(),item.getNumber()));
                    }
                    tvNumber.setText(item.getNumber()+"");

                }
            });
            tvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = item.getNumber();
                    if (number >= 0) {
                        item.setNumber(number + 1);
                        notifyCartUpdate(new UpdateCartEvent(item.getGoods_id(),item.getProduct_id(),
                                item.getId(),item.getNumber()));
                    }
                    tvNumber.setText(item.getNumber()+"");

                }
            });
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (mCurrentType == CartFragment.TYPE_NORMAL) {
                        item.setChecked(isChecked ? 1 : 0);
                    } else {
                        item.setEditChecked(isChecked);
                    }
                    notifyChooseAllState();
                }
            }
        });
    }

    private void notifyCartUpdate(UpdateCartEvent event) {
        EventBus.getDefault().post(event);
    }

    private void notifyChooseAllState() {
        EventBus.getDefault().post(new Object());
    }

}
