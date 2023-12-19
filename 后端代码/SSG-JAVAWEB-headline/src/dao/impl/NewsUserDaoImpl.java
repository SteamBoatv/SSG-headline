package dao.impl;

import dao.BaseDao;
import dao.NewsUserDao;
import lombok.val;
import pojo.NewsUser;
import utils.MD5Util;

import java.util.List;

public class NewsUserDaoImpl extends BaseDao implements NewsUserDao {
    @Override
    public NewsUser findUserByUserName(String username) {
        String sql = "SELECT uid,username,user_pwd AS userPwd,nick_name AS nickName FROM news_user WHERE username = ?";
        List<NewsUser> userList = baseQuery(NewsUser.class, sql, username);
        if(!userList.isEmpty()) {
            return userList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public NewsUser findUserByUserId(int userId) {
        String sql = "SELECT uid,username,user_pwd AS userPwd,nick_name AS nickName FROM news_user WHERE uid = ?";
        List<NewsUser> list = baseQuery(NewsUser.class,sql,userId);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    @Override
    public int registerUser(NewsUser registerUser) {
        String sql = "INSERT INTO news_user (uid,username,user_pwd,nick_name) VALUES (DEFAULT,?,?,?)";

        return baseUpdate(sql,registerUser.getUsername(),registerUser.getUserPwd(),registerUser.getNickName());
    }
}
