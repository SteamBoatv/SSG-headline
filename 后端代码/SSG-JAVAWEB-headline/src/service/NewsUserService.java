package service;

import pojo.NewsUser;

public interface NewsUserService {
    /**
     * 根据用户输入的用户名 去查找是否有对应用户
     * @param username
     * @return有的话返回用户对象，没有则返回null
     */
    NewsUser findUserByUserName(String username);

    /**
     * 根据输入的用户id 来查找用户对象
     * @param userId
     * @return 如果存在 返回用户对象，
     *          不存在则返回null
     */
    NewsUser findUserByUserId(int userId);

    /**
     *
     * @param registerUser
     * @return 返回影响行数
     */
    int registerUser(NewsUser registerUser);
}
