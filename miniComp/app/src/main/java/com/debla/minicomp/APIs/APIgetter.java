package com.debla.minicomp.APIs;

/**
 * Created by Dave on 2017/2/7.
 * 获取API的接口，实现项目中所有的API服务及功能
 */

public interface APIgetter {
    /*初始化方法*/
    public void init();
    /*发送并获取String类型json数据*/
    public String post(String apiurl);
    /*分析JSON数据*/
    public void Analize(String JSON);
}
