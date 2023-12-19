package dao;

import pojo.NewsHeadline;
import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlineQueryVo;

import java.util.List;

public interface NewsHeadLineDao {
    /**
     * 根据指定的页面标题关键字/新闻类型/页码数/页面大小，返回指定的新闻集合
     * @param input
     * @return
     */
    List findPageWithPageNum(HeadlineQueryVo input);

    /**
     * 获取数据库中所有指定类型新闻数量
     * @return
     */
    int countTotalNews(HeadlineQueryVo input);

    /**
     * 根据输入的新闻uid，返回对应的HeadlineDetailVo对象。如不存在返回null
     * @param hid
     * @return
     */
    HeadlineDetailVo getDetailById(int hid);

    /**
     * 根据id增加浏览量 page_views
     * @param hid
     * @return
     */
    int increasePageView(int hid);

    /**
     *根据头条内容，新增数据库中一条新闻信息
     * @param headLine
     * @return
     */
    int addNewsHeadline(NewsHeadline headLine);

    /**
     * 根据hid，查找对应新闻
     * @param hid
     * @return
     */
    NewsHeadline findHeadLineByHid(int hid);

    /**
     * 根据传来的headline对象hid，对数据库中内容进行更改
     * @param headline
     * @return
     */
    int update(NewsHeadline headline);

    /**
     * 根据hid，删除新闻对象
     * @param hid
     * @return
     */
    int deleteByHid(int hid);
}
