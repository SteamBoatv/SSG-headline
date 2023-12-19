package service;

import pojo.NewsType;

import java.util.List;

public interface NewsTypeService {
    /**
     * 查询所有头条类型
     * @return 返回多个头条的List集合
     */
    List<NewsType> findAll();
}
