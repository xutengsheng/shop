package com.xts.shop.apps;

import android.app.Application;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.xts.shop.common.Constant;
import com.xts.shop.db.DaoMaster;
import com.xts.shop.db.DaoSession;
import com.xts.shop.model.MyHelper;
import com.xts.shop.utils.SpUtils;

public class BaseApp extends Application {
    public static BaseApp sBaseApp;
    public static boolean sLogined;
    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApp = this;
        String token = SpUtils.getInstance().getString(Constant.TOKEN);
        if (TextUtils.isEmpty(token)) {
            sLogined = false;
        }else {
            sLogined = true;
        }
        setDatabase();
    }

    public static Resources getRes(){
       return sBaseApp.getResources();
    }


    private MyHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        //通过DaoMaster内部类DevOpenHelper可以获取一个SQLiteOpenHelper 对象
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // 此处MyDb表示数据库名称 可以任意填写

        //mHelper = new DaoMaster.DevOpenHelper(this, "MyDb", null);
        //数据库升级需要换成升级的那个helper
        mHelper = new MyHelper(this, "MyDb",null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Android 9 使用了wal模式,关闭wal模式
        db.disableWriteAheadLogging();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
