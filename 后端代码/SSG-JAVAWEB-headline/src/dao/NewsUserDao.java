package dao;

import pojo.NewsUser;

public interface NewsUserDao {
    /**
     * 根据输入的用户名 去查找是否有用户对象
     * @param username
     * @return 有的话返回用户对象NewsUser 没有则返回null
     */
    NewsUser findUserByUserName(String username);

    /**
     * 根据用户Id 查找用户对象
     * @param userId
     * @return 如果存在 返回用户对象，否则返回null
     */
    NewsUser findUserByUserId(int userId);

    /**
     * 输入要注册用户的信息，去数据库注册，返回注册影响行数
     * @param registerUser
     * @return
     */
    int registerUser(NewsUser registerUser);
}
