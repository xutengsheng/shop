package com.xts.shop.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HistorySearchBean implements Comparable<HistorySearchBean>{
    @Id
    public String content;
    public Long time;

    @Generated(hash = 1985409379)
    public HistorySearchBean(String content, Long time) {
        this.content = content;
        this.time = time;
    }

    @Generated(hash = 954352461)
    public HistorySearchBean() {
    }

    @Override
    public int compareTo(HistorySearchBean o) {
        return o.getTime().compareTo(this.time);//倒序;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
