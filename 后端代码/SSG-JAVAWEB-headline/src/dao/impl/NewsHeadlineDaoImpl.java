package dao.impl;

import dao.BaseDao;
import dao.NewsHeadLineDao;
import pojo.NewsHeadline;
import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlinePageVo;
import pojo.Vo.HeadlineQueryVo;

import java.util.ArrayList;
import java.util.List;

public class NewsHeadlineDaoImpl extends BaseDao implements NewsHeadLineDao {
    @Override
    public List findPageWithPageNum(HeadlineQueryVo input) {
        String sql = """
                    SELECT 
                        hid,
                        title,
                        type,
                        page_views AS pageViews,
                        TIMESTAMPDIFF(HOUR,create_time,NOW()) AS pastHours,
                        publisher
                    FROM news_headline
                    WHERE
                        is_deleted = 0
                """;
        StringBuilder sb = new StringBuilder(sql);
        List args = new ArrayList();
        //接下来判断一些参数
        if(input.getKeyWords() != null && !input.getKeyWords().isEmpty() ){
            //如果关键字不为空
            sb.append(" AND title LIKE ? ");
            args.add("%"+input.getKeyWords()+"%");
        }
        if(input.getType()!=null&& input.getType() != 0){
            //表示有所指类型
            sb.append(" AND type = ? ");
            args.add(input.getType());
        }
        sb.append(" ORDER BY pastHours ASC, pageViews DESC ");
        int pageNum = input.getPageNum();
        int size = input.getPageSize();
        sb.append(" LIMIT ?,?");
        args.add((pageNum-1)*size);
        args.add(size);

        return baseQuery(HeadlinePageVo.class,sb.toString(),args.toArray());
    }

    @Override
    public int countTotalNews(HeadlineQueryVo input) {
        String sql = """
                    SELECT count(1)
                    FROM news_headline
                    WHERE is_deleted = 0
                """;
        StringBuilder sb = new StringBuilder(sql);
        List args = new ArrayList();
        if(input.getType()!= null && input.getType() != 0){
            sb.append(" AND type =  ?");
            args.add(input.getType());
        }
        if(input.getKeyWords()!= null && !input.getKeyWords().isEmpty()){
            sb.append(" AND title LIKE ? ");
            args.add("%"+input.getKeyWords()+"%");
        }
        return baseQueryObject(Long.class,sb.toString(),args.toArray()).intValue();

    }



    @Override
    public HeadlineDetailVo getDetailById(int hid) {
        String sql = """
                SELECT 
                    h.hid,
                    h.title,
                    h.article,
                    h.type,
                    t.tname AS typeName,
                    h.page_views AS pageViews,
                    TIMESTAMPDIFF(HOUR,h.create_time,NOW()) AS pastHours,
                    h.publisher,
                    u.nick_name AS author
                FROM news_headline h
                JOIN news_type t ON t.tid = h.type
                JOIN news_user u ON u.uid = h.publisher
                WHERE is_deleted = 0 AND hid = ?
                """;
            List<HeadlineDetailVo> list = baseQuery(HeadlineDetailVo.class,sql,hid);
            if(list != null && !list.isEmpty()){
                return list.get(0);
            }
            return null;
    }

    /**
     * 根据输入的新闻hid，增加对应的浏览量
     * @param hid
     * @return
     */
    @Override
    public int increasePageView(int hid) {
        String sql = "UPDATE news_headline SET page_views = page_views + 1 WHERE hid = ?";
        return baseUpdate(sql,hid);
    }

    @Override
    public int addNewsHeadline(NewsHeadline headLine) {

        String sql = """
                    INSERT INTO news_headline
                    VALUES
                    (DEFAULT,
                    ?,
                    ?,
                    ?,
                    ?,
                    0,
                    NOW(),
                    NOW(),
                    0)
                """;
        return baseUpdate(sql,headLine.getTitle(),headLine.getArticle(),headLine.getType(),headLine.getPublisher());
    }

    @Override
    public NewsHeadline findHeadLineByHid(int hid) {
        String sql = """
                SELECT 
                    hid,
                    title,
                    article,
                    type,
                    publisher,
                    page_views AS pageViews,
                    create_time AS createTime,
                    update_time AS updateTime,
                    is_deleted AS isDeleted
                FROM news_headline 
                WHERE hid = ?
                """;
        List<NewsHeadline> list = baseQuery(NewsHeadline.class,sql,hid);
        if(list!= null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int update(NewsHeadline headline) {
        String sql = """
                UPDATE news_headline
                SET
                    title = ?,
                    article = ?,
                    type = ?,
                    update_time = NOW()
                WHERE 
                    hid = ?
                """;
        return baseUpdate(sql, headline.getTitle(),headline.getArticle(),headline.getType(),headline.getHid());
    }

    @Override
    public int deleteByHid(int hid) {
        String sql = "UPDATE news_headline SET is_deleted = 1 WHERE hid = ?";
        return baseUpdate(sql,hid);
    }
}
