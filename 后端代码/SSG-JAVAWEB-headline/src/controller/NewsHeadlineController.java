package controller;

import common.Result;
import common.ResultCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.NewsHeadline;
import service.NewsHeadlineService;
import service.impl.NewsHeadlineServiceImpl;
import utils.JwtHelper;
import utils.WebUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来控制新闻对象的增删改查
 */
@WebServlet("/headline/*")
public class NewsHeadlineController extends BaseController {
     private NewsHeadlineService newsHeadlineService =  new NewsHeadlineServiceImpl();

     /**
      * 根据传入的hid，删除对应新闻
      * @param req
      * @param resp
      * @throws ServletException
      * @throws IOException
      */
     protected void removeByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          int hid = Integer.parseInt(req.getParameter("hid"));
          newsHeadlineService.deleteByHid(hid);
          WebUtil.writeJson(resp,Result.ok(null));
     }

     /**
      * 更新头条的业务接口实现
      * @param req
      * @param resp
      * @throws ServletException
      * @throws IOException
      */
     protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          NewsHeadline headline = WebUtil.readJson(req,NewsHeadline.class);
          Result result = Result.ok(null);
          newsHeadlineService.updateHeadLine(headline);
          WebUtil.writeJson(resp,result);
     }

     /**
      *根据发来的hid，返回指定文章
      * @param req
      * @param resp
      * @throws ServletException
      * @throws IOException
      */
     protected void findHeadlineByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          int hid =Integer.parseInt(req.getParameter("hid"));
          NewsHeadline headline = newsHeadlineService.findHeadLineByHid(hid);
          Map data = new HashMap();
          data.put("headline",headline);
          WebUtil.writeJson(resp,Result.ok(data));
     }

     /**Head中带着请求头token:
      * 根据用户登录状态是否还存在
      * @param req
      * @param resp
      * @throws ServletException
      * @throws IOException
      */
     protected void publish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          String token =  req.getHeader("token");
          Result result = Result.ok(null);
          if(JwtHelper.isExpiration(token)){
               result = Result.build(null, ResultCodeEnum.NOTLOGIN);
               WebUtil.writeJson(resp,result);
          }else {
               NewsHeadline headLine = WebUtil.readJson(req, NewsHeadline.class);
               int uid = JwtHelper.getUserId(token).intValue();
               headLine.setPublisher(uid);
               newsHeadlineService.addNewsHeadline(headLine);
               WebUtil.writeJson(resp,result);
          }


     }
}
