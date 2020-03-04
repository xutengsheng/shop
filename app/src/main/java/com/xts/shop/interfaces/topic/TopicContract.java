package com.xts.shop.interfaces.topic;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.TopicBean;

public interface TopicContract {
    interface View extends IBaseView{
        void setData(TopicBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getTopicData(int page,int size);
    }
}
