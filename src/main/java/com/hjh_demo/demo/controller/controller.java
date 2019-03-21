package com.hjh_demo.demo.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.hjh_demo.demo.entity.Goods;
import com.hjh_demo.demo.entity.User;
import com.hjh_demo.demo.entity.UserGoods;
import com.hjh_demo.demo.mapper.GoodsMapper;
import com.hjh_demo.demo.mapper.UserGoodsMapper;
import com.hjh_demo.demo.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Wrapper;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.util.SerializationUtils.deserialize;
import static org.springframework.util.SerializationUtils.serialize;


@Controller
@RequestMapping("/")
@Api(tags = "api")
@Slf4j
public class controller {

    private static   int cnt;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserGoodsMapper userGoodsMapper;

    private static String UsercodeT;

    @RequestMapping("/")
    public String index()
    {
        log.info("登录主界面！");
        return "index";

    }

   @RequestMapping(value = "/login")
   @ApiOperation(value = "登录")
   public String hello(@RequestParam (name = "UserCode") String Usercode, @RequestParam(name = "Password") String Password, ModelMap m1, HttpSession httpSession) {

        User user1=new User();
        user1.setUserCode(Usercode);
        User user2 = userMapper.selectOne(user1);

        Jedis jedis =new Jedis ("127.0.0.1",6379);


        System.out.println("连接成功");



        if (user2==null) {
            m1.put("msg", "用户名不存在！");
            return "failure";
        }
        else {


            if(jedis.get(Usercode) == null) {
                cnt=0;
                jedis.set(Usercode,String.valueOf(cnt));
            }
            else cnt=Integer.parseInt(jedis.get(Usercode));



            if(cnt>3){
                jedis.expire(Usercode,60);
                m1.put("msg", "错误次数过多，锁定60s！");
                return "failure";
            }


         if(user2.getPassword().equals(Password)) {
             UsercodeT=user2.getUserCode();
             m1.put("UserCode",user2.getUserCode());
             m1.put("Password",user2.getPassword());
             m1.put("Gender",user2.getGender());
             m1.put("Status",user2.getStatus());
             m1.put("Birthday",user2.getBirthday());
             jedis.set(Usercode,String.valueOf(0));

             jedis.set((Usercode+"info").getBytes(),serialize(user2));

             httpSession.setAttribute("userInfo",user2);
             System.out.println(deserialize(jedis.get((Usercode+"info").getBytes())));
              return  "hello1";
         }


         else {
             cnt++;
             jedis.set(Usercode,String.valueOf(cnt));
             System.out.println(cnt);
             m1.put("msg","密码不正确！你还有"+(4-cnt)+"次机会");
             return "failure";
         }
        }
   }

   @RequestMapping(value = "found" , method =RequestMethod.POST)
   @ApiOperation(value = "查询")
   @ResponseBody
   public Object found(@RequestBody  HashMap param) throws Exception{
//       int pageNum, int startPage ,

       PageHelper.startPage(Integer.parseInt(param.get("startPage").toString()),20);
       List<Goods> l1= goodsMapper.selectbyName(param.get("searchName").toString());

//       Collections.sort(l1);
       PageInfo<Goods> goodsPageInfo = new PageInfo<>(l1);

        Map<String,Object> map = new HashMap();
        map.put("goodsPageInfo",goodsPageInfo);
        System.out.println(UsercodeT);
        User user = new User();
        user.setUserCode(UsercodeT);
        User u2=userMapper.selectOne(user);
        Map<String,Object> info =new HashMap<>();
        if((u2.getInfo())==null){
            Double a =new Double(1);
            info.put(param.get("searchName").toString(),a);
        }
        else {
            Gson gson = new Gson();
            info = gson.fromJson(u2.getInfo(), map.getClass());
            if(info.get(param.get("searchName"))==null){

                Double a =new Double(1);
                info.put(param.get("searchName").toString(),a);
            }
            else {

//                System.out.println(info);
//                System.out.println(param.get("searchName"));
//                System.out.println(info.get(param.get("searchName")));
                double b = (Double) info.get(param.get("searchName"));
                b = b + 1;
                System.out.println(b);
//                System.out.println(info.get("112"));
                info.put(param.get("searchName").toString(), b);
//            double cntt;
//            cntt=Double.valueOf(info.get(param.get("searchName").toString()));
//            System.out.println(cntt);
//            info.put(param.get("searchName").toString(),String.valueOf(++cntt));
            }
        }

//       EntityWrapper<User> entityWrapper =new EntityWrapper<>();
//       entityWrapper.eq("info",info);
        userMapper.updateinfo(info.toString(),UsercodeT);
        return map;

   }

   @RequestMapping(value = "/pa"  )
   @ApiOperation(value = "爬虫")
   @Scheduled(cron = "0 0 1 * * ?")
    public String pa(HttpServletRequest request) throws Exception {

       String a[]={"冰箱","洗衣机","电视机","空调","洗碗机","热水器","微波炉","吸尘器","电风扇","电烤箱","吸尘器","电饭锅"};
       for(int k=0;k<a.length;k++) {

            String gd =a[k];
           String keyword = java.net.URLEncoder.encode(gd, "utf-8");
           int i1 = 1;
           System.out.println(keyword);
           while (i1 < 5) {


               String url = "https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8&page=" + i1;

               Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36").get();
               // File f1 =new File("C:\\Users\\HP\\Desktop\\1.txt");
               // FileOutputStream fileOutputStream =new FileOutputStream(f1);
               //  fileOutputStream.write(doc.toString().getBytes());
                System.out.println(doc);
               Elements priDiv = doc.select("div");
               for (Element i : priDiv) {
                    System.out.println(i.attr("class"));
                   if (i.attr("class").equals("gl-i-wrap")) {

                       Elements span = i.select("div");
                       Goods goods = new Goods();
                       for (Element spans : span) {


                           if (spans.attr("class").equals("p-price")) {
                               Elements price = spans.select("strong");


                               if (price.attr("data-price").equals("")) {

                                   goods.setGoodsPrice(spans.select("i").text());

                               } else {
                                   goods.setGoodsPrice(price.attr("data-price"));

                               }

                           }


                           if (spans.attr("class").contains("p-name")) {
                               goods.setGoodsName(spans.select("em").text());
                               goods.setGoodsUrl(spans.select("a").first().attr("href"));
                               String goodsurl = spans.select("a").first().attr("href");
                               if (goodsurl.indexOf("http") == -1) {
                                   goodsurl = "http:" + goodsurl;
                               }
                               Document document = Jsoup.connect(goodsurl).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36").get();
                               Elements elements = document.select("div");
                               for (Element el : elements) {
//                            System.out.println(el.attr("class"));
                                   if (el.attr("class").equals("percent-con")) {
//                               System.out.println(el.text());
                                   }
                               }
//                        System.out.println(document);

                           }
                           if (spans.attr("class").equals("p-commit")) {


                               goods.setGoodsIndex(spans.select("em").text());
                                   System.out.println("这里"+goods);
                               Goods gs = new Goods();
                               gs.setGoodsName(goods.getGoodsName());
                               //System.out.println(goodsMapper.selectOne(gs));
                               if (goodsMapper.selectOne(gs) == null) {
                                   if (goods.getGoodsIndex().equals("")) {

                                       Double s = Double.valueOf( goods.getGoodsPrice())*0+Math.random() * 10;
                                       DecimalFormat df = new DecimalFormat("0.0");
                                       String str = df.format(s);
                                       System.out.println(str);

                                       goods.setGoodsIndex(str);
//                                 System.out.println(s.toString());
                                   }
                                   System.out.println(goods);
                                   goodsMapper.insertinto(goods.getGoodsName(), goods.getGoodsPrice(), goods.getGoodsUrl(), goods.getGoodsIndex());
                               }
                           }


                       }
                   }
               }
               i1 += 2;
               Thread.sleep(1000);
           }
//           List<Goods> l1 = goodsMapper.selectbyName(gd);
////       for(Goods goods1:l1)
////       {
////            System.out.println(goods1.getGoodsName()+"      "+goods1.getGoodsPrice());
////       }
//
//           request.setAttribute("goodslist", l1);
       }
       return "goods";

    }


    @RequestMapping("register")

    public String  register (@RequestParam (name = "UserCode") String user, @RequestParam ( name = "Password") String pas
            ,@RequestParam(name = "gender")String gender ,@RequestParam(name = "birthday")String birthday, ModelMap m1) throws Exception{
        User u1 =new User();
        u1.setUserCode(user);
//        System.out.println(user+pas+gender+birthday);
        User u2 =userMapper.selectOne(u1);
        if("".equals(user)) {
            m1.put("msg","用户名不能为空");
            return "registerfailure";
        }
       else if("".equals(pas)){
            m1.put("msg","密码不能为空");
            return "registerfailure";
        }
        else if("".equals(birthday)){
            m1.put("msg","出生日期不能为空");
            return "registerfailure";
        }

        else if (u2!=null){
            m1.put("msg","用户名存在");
            return "registerfailure";
        }
        else  {
            u1.setPassword(pas);
            u1.setGender(gender);
            u1.setBirthday( Date.valueOf(birthday));

            userMapper.insert(u1);
            m1.put("msg1","注册成功！");
            return "registersuccess";
        }

    }

    @RequestMapping(value = "/tuijian")
    @ResponseBody
    public Map<String,Object> tuijian(){
        User user = new User();
        user.setUserCode(UsercodeT);
        User u2=userMapper.selectOne(user);
        Map<String,Object> info =new HashMap<>();
        Gson gson = new Gson();
        Double maxcnt =0.0;
        String maxname="1";
        info = gson.fromJson(u2.getInfo(), info.getClass());
        EntityWrapper<User> entityWrapper =new EntityWrapper<>();
        entityWrapper.ne("UserCode",UsercodeT);
        List<User> userList =userMapper.selectList(entityWrapper);
        List<String> userList1=new ArrayList<>();

        double cnt = 0.0;
        double cnt1 = 0.0;
        double cnt2 = 0.0;
        for (String key1 : info.keySet()){

            cnt2=cnt2+(Double) info.get(key1);
            }
        for (User user1 :userList){
            Map<String,Object> info1 =new HashMap<>();
            Gson gson1 = new Gson();
            Double maxcnt1 =0.0;

            String maxname1="0";
            info1 = gson1.fromJson(user1.getInfo(), info.getClass());
            for (String key : info1.keySet()){
                cnt1=cnt1+(Double) info1.get(key);
                for (String key1 : info.keySet()){

                   if(key.equals(key1)){
                    if((Double)info1.get(key1)>=(Double)info.get(key)){
                        cnt=cnt+(Double)info.get(key);
                    }
                    else {
                        cnt=cnt1+(Double)info1.get(key1);
                    }
                 }
                }
            }

            double res=cnt/Math.sqrt(Double.valueOf(cnt1*cnt2));


            if(res>maxcnt){
                maxcnt=res;
                maxname=user1.getUserCode();

            }

        }
        userList1.add(maxname);
        List<Goods> list =new ArrayList<>();
        for(String user1:userList1){
            List<Goods> goods=goodsMapper.show(user1);

            for(Goods goods1:goods){
                list.add(goods1);
            }
        }

        Map<String,Object>map=new HashMap<>();
        map.put("userlist",list);
        return map;
    }

    @RequestMapping(value = "test")
    @ResponseBody
    public void t1()throws Exception{


    }

    @RequestMapping(value = "repassword")
    @ResponseBody
    public int repassword(@RequestBody  HashMap param) throws Exception{
        User u1=new User();
        u1.setUserCode(UsercodeT);
        User u2=userMapper.selectOne(u1);
//        System.out.println(param.get("oldpassword").toString()+param.get("newpasswrod").toString()+param.get("confirm").toString());
        if(param.get("oldpassword").equals(u2.getPassword())) {
            if (param.get("newpassword").equals(param.get("confirm"))){
                userMapper.updatepassword(param.get("newpassword").toString(),u2.getUserCode());
                return 0;//成功
            }
            else {
                return 1;//两次密码不一致
            }
        }
        else return 2;//原密码有误

    }
    @RequestMapping(value = "addcollection")
    @ResponseBody
    public int addcollection(@RequestBody int goodsid){

       if(userGoodsMapper.select(goodsid,UsercodeT)==null) {
            userGoodsMapper.insertusergoods(UsercodeT,goodsid);
            return 1;
        }
        else return 2;

    }

    @RequestMapping(value = "showcollection")
    @ResponseBody
    public Object  shwocollection(@RequestBody  HashMap param){
        PageHelper.startPage(Integer.parseInt(param.get("startPage").toString()),20);
        List<Goods> userGoods = goodsMapper.show(UsercodeT);
        PageInfo<Goods> collectionPageInfo = new PageInfo<>(userGoods);

        Map<String,Object> map = new HashMap();
        map.put("collection",collectionPageInfo);
        return map;

    }
    @RequestMapping(value = "delcollection")
    @ResponseBody
    public int delete(@RequestBody  int param){
        userGoodsMapper.del(param);
        return 1;
    }
    @RequestMapping(value = "tuijianshow")
    @ResponseBody
    public Object tuijian(@RequestBody  String param){
        List<Goods> userGoods = goodsMapper.show(param);
        Map<String,Object> map = new HashMap();
        map.put("userGoods",userGoods);
       return map;
    }
    @RequestMapping(value = "te")
    public void s() throws Exception{
        String url = "http://www.sohu.com/";


        System.getProperties().setProperty("webdriver.chrome.driver", "");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://item.jd.com/100002381644.html");
        System.out.print(webDriver);
      //  WebElement webElement = webDriver.findElement(By.xpath("/html"));
     //   System.out.println(webElement.getAttribute("outerHTML"));
        webDriver.close();

    }
}
