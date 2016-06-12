package com.uto.servicelink;

/**
 * 项目名称：ServiceLink
 * 类描述：
 * 创建人：steven
 * 创建时间：2016/6/12 14:36
 * 修改人：Administrator
 * 修改时间：2016/6/12 14:36
 * 修改备注：
 */
public class Apiconfig {
    public static String BASE_URL = "http://112.74.133.11";//192.168.10.113
    public static String PORT = ":8090";
    public static String UTO_BASE_URL = BASE_URL + PORT;
    public static final String UTO_HAVA_NEW_MESSAGE = UTO_BASE_URL + "/utoo/common/msg/latest";

}
