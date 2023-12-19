package service;

import pojo.NewsHeadline;
import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlineQueryVo;

import java.util.Map;

public interface NewsHeadlineService {
    /**
     * 根据输入的HeadlineQueryVo类型数据，返回指定数据
     * @param input
     * @return
     *    pageData:[xxx],
     *    "pageNum":1,    //页码数
     *    "pageSize":10,  // 页大小
     *    "totalPage":20, // 总页数
     *    "totalSize":200 // 总记录数
     */
    Map<String, Object> findPage(HeadlineQueryVo input);

    /**
     * 根据输入的hid编号，获得HeadlineDetailVo对象，如不存在，返回null
     * @param hid
     * @return
     */
    HeadlineDetailVo getHeadLineDetailById(int hid);

    /**
     * 根据 "title":"尚硅谷宣布 ... ...",   // 文章标题
     *     "article":"... ...",          // 文章内容
     *     "type":"1"                    // 文章类别
     *     "publisher":3                 //发布人id
     *     以上信息，发布新闻
     * @param headLine
     * @return
     */
    int addNewsHeadline(NewsHeadline headLine);

    /**
     * 根据输入的新闻id，返回新闻对象
     * @param hid
     * @return
     */
    NewsHeadline findHeadLineByHid(int hid);

    /**
     * 根据传来的新闻对象，对数据库中的对象进行更新
     *
     * @param headline
     */
    int updateHeadLine(NewsHeadline headline);

    /**
     * 根据hid，去删除新闻
     * @param hid
     * @return
     */
    int deleteByHid(int hid);
}
