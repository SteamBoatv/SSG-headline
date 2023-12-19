package controller;

import common.Result;
import common.ResultCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.val;
import pojo.NewsUser;
import service.NewsUserService;
import service.impl.NewsUserServiceImpl;
import utils.JwtHelper;
import utils.MD5Util;
import utils.WebUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用来对用户的增删改查
 */
@WebServlet("/user/*")
public class NewsUserController extends BaseController {
    private NewsUserService newsUserService = new NewsUserServiceImpl();

    /**
     * 登录校验，前端的一个不带按钮的功能，检测用户是否在登录状态
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");
        Result result = null;
        if(token != null && !JwtHelper.isExpiration(token)){
            result = Result.ok(null);
        }else {
            result = Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * 传入用户名称 查看是否被占用
     * @param req
     * @param resp 如果查到了用户对象 则表示被占用 返回505代码
     *             否则返回200
     * @throws ServletException
     * @throws IOException
     */
    protected void checkUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        NewsUser user = newsUserService.findUserByUserName(username);
        Result result = Result.ok(null);
        if(user != null ){
            result = Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * 用户提交注册的账号，如果没有相同账号，则进行注册。返回对应响应码即可，无需返回实际数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsUser registerUser = WebUtil.readJson(req,NewsUser.class);
        NewsUser dataBaseUser = newsUserService.findUserByUserName(registerUser.getUsername());
        Result result = null;
        if(dataBaseUser == null){
            //可以进行注册
            int row = newsUserService.registerUser(registerUser);
            if(row == 0)result = Result.build(null,ResultCodeEnum.USERNAME_USED);
            else result = Result.ok(null);
        }else {
            result = Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * 根据前端传来的token，来返回对应的用户信息
     *
     * @param req  传来一个 String类型的 token
     * @param resp 如果token过期 返回过期相应，如果没过期，
     *             则返回成功信号，并在data中放入map传入整个用户对象
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");
        Result result = null;
        if (token!= null && (!token.isEmpty())) {
            if (JwtHelper.isExpiration(token)) {
                result = Result.build(null, ResultCodeEnum.NOTLOGIN);
            } else {
                int userId = JwtHelper.getUserId(token).intValue();
                NewsUser user = newsUserService.findUserByUserId(userId);
                if (user == null) {
                    //避免意外
                    result = Result.build(null, ResultCodeEnum.CANTFINDUSERID);
                } else {
                    Map data = new HashMap();
                    user.setUserPwd("");
                    data.put("loginUser", user);
                    result = Result.ok(data);
                }
            }
        }
        WebUtil.writeJson(resp, result);
    }

    /**
     * 用户登录业务
     *
     * @param req  传递过来JSON字符串，包含有用户姓名和未加密的密码
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsUser User = WebUtil.readJson(req, NewsUser.class);
        NewsUser loginUser = newsUserService.findUserByUserName(User.getUsername());
        if (loginUser == null) {
            WebUtil.writeJson(resp, Result.build(null, ResultCodeEnum.USERNAME_ERROR));
        } else {
            //找到了用户信息
            if (!loginUser.getUserPwd().equals(MD5Util.encrypt(User.getUserPwd()))) {
                WebUtil.writeJson(resp, Result.build(null, ResultCodeEnum.PASSWORD_ERROR));
            } else {
                String token = JwtHelper.createToken(loginUser.getUid().longValue());
                Map data = new HashMap();
                data.put("token", token);
                WebUtil.writeJson(resp, Result.ok(data));
            }
        }
    }
}
