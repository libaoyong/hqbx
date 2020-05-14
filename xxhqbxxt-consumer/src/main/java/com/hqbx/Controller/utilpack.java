package com.hqbx.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class utilpack {
    public String getopenid(String appid,String key,String code){
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        String grant_type="authorization_code";//固定值
        //使用登录凭证 code 获取 session_key 和 openid。
        Map<String,String> map=new HashMap<String,String>();
        map.put("appid", appid);//小程序的appid
        map.put("secret",key);//miniapp secret
        map.put("js_code",code);//传入得用户code
        map.put("grant_type",grant_type);
        String result="";
        try {
            result= sendGet(requestUrl,map);
//            System.out.println("获取openid结果为："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析返回的json数据，获得OPENID
        Map maps= JSONObject.parseObject(result);
        String openid=String.valueOf(maps.get("openid"));
//        String errmsg=String.valueOf(maps.get("errmsg"));
        String session_key=String.valueOf(maps.get("session_key"));
        if(openid!=null){
            /*在此处添加自己的逻辑代码，将openid保存在数据库，或是，使用session_key去微信服务器换取用户头像、昵称等信息。我在这里并没有用到，因此我只保存了用户的openid*/

        }
        return openid;
    }
    /**
     * 发送GET请求
     *
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) {
        String result = "";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;
            System.out.println(full_url);
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(full_url);
            // 打开URL连接(建立了一个与服务器的tcp连接,并没有实际发送http请求！)
            URLConnection urlConnection=connURL.openConnection();
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) urlConnection;
            // 设置通用请求属性(如果已存在具有该关键字的属性，则用新值改写其值。)
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接(远程对象变为可用。远程对象的头字段和内容变为可访问)
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
