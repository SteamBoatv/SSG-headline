package service.impl;


import dao.NewsUserDao;
import dao.impl.NewsUserDaoImpl;
import pojo.NewsUser;
import service.NewsUserService;
import utils.MD5Util;

public class NewsUserServiceImpl implements NewsUserService {
    private NewsUserDao newsUserDao = new NewsUserDaoImpl();

    @Override
    public NewsUser findUserByUserName(String username) {
        return newsUserDao.findUserByUserName(username);
    }

    @Override
    public NewsUser findUserByUserId(int userId) {
        return newsUserDao.findUserByUserId(userId);
    }

    @Override
    public int registerUser(NewsUser registerUser) {
        String MD5Pwd = MD5Util.encrypt(registerUser.getUserPwd());
        registerUser.setUserPwd(MD5Pwd);
        return newsUserDao.registerUser(registerUser);
    }
}
