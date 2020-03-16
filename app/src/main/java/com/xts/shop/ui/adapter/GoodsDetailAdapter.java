package com.xts.shop.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.shop.R;
import com.xts.shop.model.bean.GoodsDetailBean;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

public class GoodsDetailAdapter extends BaseMultiItemQuickAdapter<GoodsDetailBean.GoodsDetailListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GoodsDetailAdapter(List<GoodsDetailBean.GoodsDetailListBean> data) {
        super(data);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_BANNER, R.layout.item_goods_detail_top);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_GOODS_LIST, R.layout.item_item_news_good);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_TITLE, R.layout.layout_item_title);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_HTML, R.layout.item_html);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_ISSUE, R.layout.layout_item_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailBean.GoodsDetailListBean item) {
        int itemType = item.currentType;
        switch (itemType){
            case GoodsDetailBean.GoodsDetailListBean.TYPE_BANNER:
                break;
            case GoodsDetailBean.GoodsDetailListBean.TYPE_HTML:
                initHtml(helper,item);
                break;
            case GoodsDetailBean.GoodsDetailListBean.TYPE_TITLE:
                initTitle(helper,item);
                break;
            case GoodsDetailBean.GoodsDetailListBean.TYPE_ISSUE:
                initIssue(helper,item);
                break;
            case GoodsDetailBean.GoodsDetailListBean.TYPE_GOODS_LIST:
                break;

        }
    }

    private void initIssue(BaseViewHolder helper, GoodsDetailBean.GoodsDetailListBean item) {
        GoodsDetailBean.DataBean.IssueBean issue = (GoodsDetailBean.DataBean.IssueBean) item.data;
        helper.setText(R.id.txt_title,issue.getQuestion()+","+issue.getAnswer());
    }

    private void initTitle(BaseViewHolder helper, GoodsDetailBean.GoodsDetailListBean item) {
        helper.setText(R.id.txt_title,(String)item.data);
    }

    private void initHtml(BaseViewHolder helper, GoodsDetailBean.GoodsDetailListBean item) {
        HtmlTextView htmlTextView = helper.getView(R.id.html);

        // loads html from string and displays http://www.example.com/cat_pic.png from the Internet
        htmlTextView.setHtml((String) item.data,
                new HtmlHttpImageGetter(htmlTextView));
    }
}
