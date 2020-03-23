package com.xts.shop.ui.order;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.order.OrderContract;
import com.xts.shop.model.bean.AddCartBean;
import com.xts.shop.presenter.order.OrderPresenter;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;

public class OrderActivity extends BaseActivity<OrderContract.Presenter>
        implements OrderContract.View, View.OnClickListener {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.tv_fact_price)
    TextView mTvFactPrice;
    @BindView(R.id.tv_pay)
    TextView mTvPay;
    private ArrayList<AddCartBean.DataBean.CartListBean> mData;
    private String mTotalPrice;
    private RlvOrderAdapter mAdapter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvPay.setOnClickListener(this);

        mData = (ArrayList<AddCartBean.DataBean.CartListBean>) getIntent().getSerializableExtra(Constant.DATA);
        mTotalPrice = getIntent().getStringExtra(Constant.TOTAL_PRICE);
        mTvFactPrice.setText("实付:¥"+mTotalPrice);
        mAdapter = new RlvOrderAdapter(R.layout.item_cart, mData);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mRlv);

        addHeader();
    }

    private void addHeader() {
        View header = LayoutInflater.from(this).inflate(R.layout.item_order_header,null);
        View clAddress = header.findViewById(R.id.cl_address);
        View clCoupon = header.findViewById(R.id.cl_coupon);
        TextView tvTotalPrice = header.findViewById(R.id.tv_goods_total_price);
        tvTotalPrice.setText(BaseApp.getRes().getString(R.string.rmb)+mTotalPrice);
        clAddress.setOnClickListener(this);
        clCoupon.setOnClickListener(this);

        mAdapter.addHeaderView(header);
    }

    @Override
    protected OrderContract.Presenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_address:
                showTips("地址啊地址");
                break;
            case R.id.cl_coupon:
                showTips("优惠啊优惠");
                break;
            case R.id.tv_pay:
                showTips("支付啊支付");
                break;
        }
    }
}
