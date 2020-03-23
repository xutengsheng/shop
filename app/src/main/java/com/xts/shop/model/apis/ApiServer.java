package com.xts.shop.model.apis;


import com.xts.shop.model.bean.AddCartBean;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.GoodsDetailBean;
import com.xts.shop.model.bean.MainPageBean;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.model.bean.SortCurrentBean;
import com.xts.shop.model.bean.SortTabBean;
import com.xts.shop.model.bean.TopicBean;

import java.util.function.DoubleUnaryOperator;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {

    @GET("index")
    Flowable<MainPageBean> getHome();

    //专题数据请求接口
    @GET("topic/list")
    Flowable<TopicBean> getTopic(@Query("page") int page, @Query("size") int size);

    //登录接口
    @POST("auth/login")
    @FormUrlEncoded
    Flowable<LoginBean> login(@Field("nickname") String name, @Field("password") String psw);

    //注册接口
    @POST("auth/register")
    @FormUrlEncoded
    Flowable<LoginBean> register(@Field("nickname") String name, @Field("password") String psw);

    //分类竖直导航
    @GET("catalog/index")
    Flowable<SortTabBean> getSortTab();

    //分类右边对应的当前分类的数据
    @GET("catalog/current")
    Flowable<SortCurrentBean> getSortCurrentBean(@Query("id") int id);

    //商品列表数据
    @GET("goods/list")
    Flowable<GoodListBean> getGoodsList(@Query("categoryId") int categoryId,
                                        @Query("page") int page,
                                        @Query("size") int size);
    //商品详情数据
    @GET("goods/detail")
    Flowable<GoodsDetailBean> getGoodDetail(@Query("id") int id);

    //添加购物车
    @POST("cart/add")
    @FormUrlEncoded
    Flowable<AddCartBean> addCart(@Field("goodsId") int goodsId,
                                  @Field("productId") int productId,
                                  @Field("number") int number);

    //添加购物车
    @GET("cart/index")
    Flowable<AddCartBean> getCart();

    /**
     * 修改商品选中状态
     * @param checked 全选为1 ，非全选 0
     * @param productIds 多个商品id以逗号隔开
     * @return
     */
    @POST("cart/checked")
    @FormUrlEncoded
    Flowable<AddCartBean> modifyCartChecked(@Field("isChecked") int checked,
                                            @Field("productIds") String productIds);

    /**
     * 删除购物车商品
     * @param productIds 多个商品id以逗号隔开
     * @return
     */
    @POST("cart/delete")
    @FormUrlEncoded
    Flowable<AddCartBean> deleteCart(@Field("productIds") String productIds);

    /**
     * 更新购物车商品数量
     * @param goodsId
     * @param productId
     * @param id
     * @param number
     * @return
     */
    @POST("cart/update")
    @FormUrlEncoded
    Flowable<AddCartBean> updateCart(@Field("goodsId") int goodsId,
                                     @Field("productId") int productId,
                                     @Field("id") int id,
                                     @Field("number") int number);


    /**
     * 搜索
     * @param keyword 关键字
     * @param page 页码
     * @param size 每页数量
     * @param sort 分类,没有给 default
     * @param order 排序 desc降序 ,asce升序
     * @param categoryId 分类id
     * @return
     */
    @GET("goods/list")
    Flowable<GoodListBean> search(@Query("keyword") String keyword,
                                     @Query("page") int page,
                                     @Query("size") int size,
                                     @Query("sort") String sort,
                                     @Query("order") String order,
                                     @Query("categoryId") int categoryId);

}
