package com.bobox.demo.controller;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
@RestController
@RequestMapping("/find")
public class MZiTuBugsController {
    @RequestMapping("/t")
    public String find(@RequestParam Integer count) throws IOException {
        String  f = "E:/code/bobo/src/main/resources/aa/";
        for (int j = count; j > 1; j--) {
            String url = "http://sc.chinaz.com/tupian/index_" + count + ".html";
            Document document = Jsoup.connect(url).get();
            //拿到多少个a标签
            Elements elements = document.select("#container a img");
            String src = "";
            for (int i = 0; i < elements.size(); i++) {
                src = elements.get(i).attr("src2");
                //  System.out.println(src);
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet(src);
                httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
                httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
                httpGet.setHeader("User-Agent", "Mozilla/5.0  AppleWebKit/537.36  Chrome/84.0.4147.135 Safari/537.36 Safari/537.36");
                httpGet.setHeader("Referer", "http://sc.chinaz.com/tupian/");

                //3.使用客户端执行请求,获取响应
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                //4.获取响应体
                HttpEntity entity = httpResponse.getEntity();
                //5.获取响应体的内容
                InputStream is = entity.getContent();
                //创建一个字节输出流，将图片输出到硬盘中"D/aa"目录
                //解析src获取图片的后缀名
                String sub = src.substring(src.lastIndexOf("."));
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
                String imgName = sdf.format(date) + sub;

                FileOutputStream out = new FileOutputStream(f + imgName);
                //将输入流中的内容拷贝到输出流
                IOUtils.copy(is, out);
                //关流
                out.close();
                is.close();
                // System.out.println("下载ing.......");
            }
        }
        return "HELLO WORLD";
    }


}
