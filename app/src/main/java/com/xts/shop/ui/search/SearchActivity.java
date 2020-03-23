package com.xts.shop.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.common.FlowLayout;
import com.xts.shop.db.HistorySearchBeanDao;
import com.xts.shop.interfaces.search.SearchContract;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.HistorySearchBean;
import com.xts.shop.presenter.search.SearchPresenter;
import com.xts.shop.ui.adapter.RlvGoodsListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.internal.operators.maybe.MaybeLift;
import retrofit2.http.Query;

public class SearchActivity extends BaseActivity<SearchContract.Presenter> implements SearchContract.View {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_multiple)
    TextView mTvMultiple;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_sort)
    TextView mTvSort;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.cl_result)
    ConstraintLayout mClResult;
    @BindView(R.id.tv_history)
    TextView mTvHistory;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.fl)
    FlowLayout mFl;
    @BindView(R.id.cl_history)
    ConstraintLayout mClHistory;
    private HistorySearchBeanDao mSearchBeanDao;
    int mPage = 1;
    int mSize = 10;
    String mSort = "default";
    String mOrder = "desc";
    int mCategoryId = 0;
    private List<GoodListBean.DataBeanX.DataBean> mData;
    private RlvGoodsListAdapter mAdapter;

    @Override
    protected void initData() {
        getDataFromDb();
    }

    private void getDataFromDb() {
        List<HistorySearchBean> list = mSearchBeanDao.queryBuilder().list();

        mFl.removeAllViews();
        if (list != null && list.size() > 0) {
            Collections.sort(list);

            for (int i = 0; i < list.size(); i++) {
                TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_label, null);
                String content = list.get(i).content;
                tv.setText(content);
                mFl.addView(tv);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        search(content);
                        getDataFromDb();
                    }
                });
            }
        }
    }

    @Override
    protected void initView() {
        mSearchBeanDao = BaseApp.sBaseApp.getDaoSession().getHistorySearchBeanDao();

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(mEtSearch.getText().toString().trim());
                    getDataFromDb();
                }
                return false;
            }
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mClHistory.getVisibility() == View.GONE) {
                    mClHistory.setVisibility(View.VISIBLE);
                    mClResult.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayList<GoodListBean.DataBeanX.DataBean> list = new ArrayList<>();
        //mRlv.setLayoutManager(new GridLayoutManager(this,2));
        mRlv.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new RlvGoodsListAdapter(this, list);
        mRlv.setAdapter(mAdapter);

    }

    private void search(String content) {
        mSearchBeanDao.insertOrReplace(new HistorySearchBean(content, System.currentTimeMillis()));

        mPresenter.search(content, mPage, mSize, mSort, mOrder, mCategoryId);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }


    @OnClick({R.id.tv_cancel, R.id.tv_multiple, R.id.tv_price, R.id.tv_sort, R.id.iv_delete})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_multiple:
                break;
            case R.id.tv_price:
                break;
            case R.id.tv_sort:
                break;
            case R.id.iv_delete:
                mSearchBeanDao.deleteAll();
                mFl.removeAllViews();
                break;
        }
    }

    @Override
    public void setSearchData(GoodListBean bean) {
        mClHistory.setVisibility(View.GONE);
        mClResult.setVisibility(View.VISIBLE);
        mData = bean.getData().getData();

        List<GoodListBean.DataBeanX.DataBean> list = bean.getData().getData();
        mAdapter.addData(list);
    }
}
