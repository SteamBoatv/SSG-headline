package dao;

import pojo.NewsType;

import java.util.List;

public interface NewsTypeDao {
    /**
     * 遍历数据库 展示结果
     * @return 返回List集合类型的 新闻种类
     */
    List<NewsType> findAll();
}
