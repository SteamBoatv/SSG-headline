package dao.impl;

import dao.BaseDao;
import dao.NewsTypeDao;
import pojo.NewsType;

import java.util.List;

public class NewsTypeDaoImpl extends BaseDao implements NewsTypeDao {
    @Override
    public List<NewsType> findAll() {
        String sql = "SELECT tid,tname FROM news_type ";
        return baseQuery(NewsType.class,sql);
    }
}
