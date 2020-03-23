package com.xts.shop.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.cart.CartContract;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.model.bean.AddCartBean;
import com.xts.shop.model.bean.UpdateCartEvent;
import com.xts.shop.presenter.cart.CartPresenter;
import com.xts.shop.presenter.mainpage.MainPagePresenter;
import com.xts.shop.ui.adapter.CartAdapter;
import com.xts.shop.ui.order.OrderActivity;
import com.xts.shop.utils.LogUtils;
import com.xts.shop.utils.NumberUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.time.chrono.MinguoDate;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CartFragment extends BaseFragment<CartContract.Presenter> implements CartContract.View {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.cb)
    CheckBox mCbChooseAll;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_edit)
    TextView mTvEdit;
    @BindView(R.id.tv_order)
    TextView mTvOrder;
    String mStrChooseAll = BaseApp.getRes().getString(R.string.choose_all);
    String mRmb = BaseApp.getRes().getString(R.string.rmb);
    String mEdit = BaseApp.getRes().getString(R.string.edit);
    String mComplete = BaseApp.getRes().getString(R.string.complete);
    String mDelete = BaseApp.getRes().getString(R.string.delete_choose);
    String mOrder = BaseApp.getRes().getString(R.string.order);

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_EDIT = 1;


    private AddCartBean mCarBean;
    private CartAdapter mAdapter;
    private int mChooseCount;//选中商品的数量
    private String mTotalPrice;

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        mPresenter.getCart();
    }

    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ArrayList<AddCartBean.DataBean.CartListBean> list = new ArrayList<>();
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CartAdapter(R.layout.item_cart, list);
        mAdapter.bindToRecyclerView(mRlv);

        mCbChooseAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (TYPE_NORMAL == mAdapter.mCurrentType) {
                        //普通状态
                        List<AddCartBean.DataBean.CartListBean> cartList = mCarBean.getData().getCartList();
                        StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < cartList.size(); i++) {
                            AddCartBean.DataBean.CartListBean cartListBean = cartList.get(i);
                            cartListBean.setChecked(isChecked ? 1 : 0);
                            if (i == cartList.size() - 1) {
                                sb.append(cartListBean.getProduct_id());
                            } else {
                                sb.append(cartListBean.getProduct_id() + ",");
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        //全选为0 ，非全选 1
                        mPresenter.modifyCartChecked(isChecked ? 1 : 0, sb.toString());

                        if (isChecked) {
                            mTotalPrice = NumberUtil.parseFloat2String(mCarBean.getData().getCartTotal().getGoodsAmount());
                        } else {
                            mTotalPrice = "0.0";
                        }
                        mTvPrice.setText(mRmb + mTotalPrice);
                    } else {
                        //编辑状态
                        //todo
                        for (int i = 0; i < mAdapter.getData().size(); i++) {
                            mAdapter.getData().get(i).setEditChecked(isChecked);
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    if (isChecked) {
                        mChooseCount = mCarBean.getData().getCartTotal().getGoodsCount();
                    } else {
                        mChooseCount = 0;
                    }
                    mCbChooseAll.setText(mStrChooseAll + "(" + mChooseCount + ")");
                }
            }
        });
    }

    @Override
    protected CartContract.Presenter initPresenter() {
        return new CartPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    public void setCart(AddCartBean bean) {
        this.mCarBean = bean;
        mAdapter.getData().clear();
        mAdapter.addData(bean.getData().getCartList());
        setBottom();
    }

    @Override
    public void setDelete(AddCartBean bean) {
        this.mCarBean = bean;
    }

    private void setBottom() {
        List<AddCartBean.DataBean.CartListBean> cartList = mCarBean.getData().getCartList();
        AddCartBean.DataBean.CartTotalBean cartTotal = mCarBean.getData().getCartTotal();

        if (mAdapter.mCurrentType == TYPE_NORMAL) {
            //普通状态
            setNormal(cartTotal);
        } else {
            //编辑状态
            setEdit();
        }
    }

    private void setEdit() {
        //todo
        mTvPrice.setVisibility(View.GONE);
        mTvEdit.setText(mComplete);
        mTvOrder.setText(mDelete);
        mCbChooseAll.setChecked(false);
        mCbChooseAll.setText(mStrChooseAll + "(" + 0 + ")");
    }

    private void setNormal(AddCartBean.DataBean.CartTotalBean cartTotal) {
        mChooseCount = cartTotal.getCheckedGoodsCount();
        boolean checked = cartTotal.getCheckedGoodsCount() == cartTotal.getGoodsCount()
                && cartTotal.getCheckedGoodsCount() > 0;
        mCbChooseAll.setChecked(checked);
        mCbChooseAll.setText(mStrChooseAll + "(" + mChooseCount + ")");
        mTvPrice.setVisibility(View.VISIBLE);
        mTotalPrice = cartTotal.getCheckedGoodsAmount()+"";
        mTvPrice.setText(mRmb + mTotalPrice);
        mTvEdit.setText(mEdit);
        mTvOrder.setText(mOrder);
    }

    @OnClick({R.id.tv_edit, R.id.tv_order})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit:
                switchState();
                break;
            case R.id.tv_order:
                go2Order();
                break;
        }
    }

    private void switchState() {
        mAdapter.mCurrentType = mAdapter.mCurrentType == TYPE_NORMAL ? TYPE_EDIT : TYPE_NORMAL;
        if (mAdapter.mCurrentType == TYPE_NORMAL) {
            mTvEdit.setText(mEdit);
            mTvPrice.setVisibility(View.VISIBLE);
            mTvPrice.setText(mTotalPrice);
            mPresenter.getCart();

        } else {
            setEdit();
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                mAdapter.getData().get(i).setEditChecked(false);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void go2Order() {
        if (mAdapter.mCurrentType == TYPE_NORMAL) {
            if (mChooseCount > 0 && !"0.0".equals(mTotalPrice)) {
                ArrayList<AddCartBean.DataBean.CartListBean> list = new ArrayList<>();
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    AddCartBean.DataBean.CartListBean cartListBean = mAdapter.getData().get(i);
                    if (cartListBean.getChecked() == 1) {
                        list.add(cartListBean);
                    }
                }
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra(Constant.DATA,list);
                intent.putExtra(Constant.TOTAL_PRICE,mTotalPrice);
                startActivity(intent);
            }else {
                showTips("请至少选中一种商品");
            }

        } else {
            deleteChooseGoods();
        }
    }

    private void deleteChooseGoods() {
        StringBuilder sb = new StringBuilder();
        int deleteCount = 0;
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            AddCartBean.DataBean.CartListBean cartListBean = mAdapter.getData().get(i);
            boolean editChecked = cartListBean.isEditChecked();
            int product_id = cartListBean.getProduct_id();
            if (editChecked) {
                deleteCount += cartListBean.getNumber();
                if (i == mAdapter.getData().size() - 1) {
                    sb.append(product_id);
                } else {
                    sb.append(product_id + ",");
                }
                mAdapter.getData().remove(cartListBean);
                i--;
            }
        }

        if (deleteCount == 0) {
            showTips("请至少选中一种商品");
            return;
        }
        mAdapter.notifyDataSetChanged();
        mPresenter.deleteCart(sb.toString());
        mCbChooseAll.setText(mStrChooseAll + "(" + 0 + ")");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onReceive(Object object) {
        mChooseCount = 0;
        float totalPrice = 0;
        if (mAdapter.mCurrentType == CartFragment.TYPE_NORMAL) {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                if (mAdapter.getData().get(i).getChecked() == 1) {
                    int number = mAdapter.getData().get(i).getNumber();
                    mChooseCount += number;
                    totalPrice += number * mAdapter.getData().get(i).getMarket_price();
                }
            }
            mTotalPrice = NumberUtil.parseFloat2String(totalPrice);
            mTvPrice.setText(mRmb + mTotalPrice);
        } else {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                if (mAdapter.getData().get(i).isEditChecked()) {
                    mChooseCount += mAdapter.getData().get(i).getNumber();
                }
            }
        }
        LogUtils.print("chooseCount:" + mChooseCount);
        mCbChooseAll.setText(mStrChooseAll + "(" + mChooseCount + ")");
        if (mChooseCount == mCarBean.getData().getCartTotal().getGoodsCount() && mChooseCount > 0) {
            mCbChooseAll.setChecked(true);
        } else {
            mCbChooseAll.setChecked(false);
        }

    }

    @Subscribe
    public void onReceiveUpdateCart(UpdateCartEvent event){
        mPresenter.updateCart(event.goodsId,event.productId,event.id,event.number);
    }
}
