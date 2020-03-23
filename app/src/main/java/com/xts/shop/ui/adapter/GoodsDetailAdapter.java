package com.xts.shop.ui.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.shop.R;
import com.xts.shop.model.bean.GoodsDetailBean;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
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
        //addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_BANNER, R.layout.item_goods_detail_top);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_GOODS_LIST, R.layout.item_item_news_good);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_TITLE, R.layout.layout_item_title);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_HTML, R.layout.item_html);
        addItemType(GoodsDetailBean.GoodsDetailListBean.TYPE_ISSUE, R.layout.layout_item_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailBean.GoodsDetailListBean item) {
        int itemType = item.currentType;
        switch (itemType){
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
        GoodsDetailBean.DataBeanX.IssueBean issue = (GoodsDetailBean.DataBeanX.IssueBean) item.data;
        helper.setText(R.id.txt_title,issue.getQuestion()+","+issue.getAnswer());


    }

    private void initTitle(BaseViewHolder helper, GoodsDetailBean.GoodsDetailListBean item) {
        helper.setText(R.id.txt_title,(String)item.data);
    }

    private void initHtml(BaseViewHolder helper, GoodsDetailBean.GoodsDetailListBean item) {
        GoodsDetailBean.DataBeanX.InfoBean infoBean = (GoodsDetailBean.DataBeanX.InfoBean) item.data;
        WebView webView = helper.getView(R.id.html);

        String css_str = mContext.getResources().getString(R.string.css_goods);
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>");
        sb.append("<style>"+css_str+"</style></head><body>");
        sb.append(infoBean.getGoods_desc()+"</body></html>");
        webView.loadData(sb.toString(),"text/html","utf-8");

        /*List<String> urlList = new ArrayList<>();
        String[] arr = infoBean.getGoods_desc().split("<p>");
        String head = "<img src=\"";
        String foot = ".jpg";
        for(int i=0; i<arr.length; i++){
            if(TextUtils.isEmpty(arr[i])) continue;
            int start = arr[i].indexOf(head)+head.length();
            if(start == -1) continue;
            int end = arr[i].indexOf(foot)+foot.length();
            String url = arr[i].substring(start,end);
            urlList.add(url);
            Log.i("url",url);
        }*/
    }
}
