package service.impl;

import dao.NewsHeadLineDao;
import dao.impl.NewsHeadlineDaoImpl;
import pojo.NewsHeadline;
import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlineQueryVo;
import service.NewsHeadlineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlineServiceImpl implements NewsHeadlineService {
    private NewsHeadLineDao newsHeadLineDao = new NewsHeadlineDaoImpl();

    @Override
    public Map<String, Object> findPage(HeadlineQueryVo input) {
        List pageData = newsHeadLineDao.findPageWithPageNum(input);
        int totalNews = newsHeadLineDao.countTotalNews(input);
        /*    "pageNum":1,    //页码数
         *    "pageSize":10,  // 页大小
         *    "totalPage":20, // 总页数
         *    "totalSize":200 // 总新闻记录数
         */
        Map data = new HashMap();
        data.put("pageData", pageData);
        data.put("pageNum", input.getPageNum());
        data.put("pageSize", input.getPageSize());
        int totalPage = totalNews % input.getPageSize() == 0 ? totalNews / input.getPageSize() : totalNews / input.getPageSize() + 1;
        data.put("totalPage", totalPage);
        data.put("totalSize", totalNews);
        return data;
    }

    @Override
    public HeadlineDetailVo getHeadLineDetailById(int hid) {
        newsHeadLineDao.increasePageView(hid);
        return newsHeadLineDao.getDetailById(hid);
    }

    @Override
    public int addNewsHeadline(NewsHeadline headLine) {
        return newsHeadLineDao.addNewsHeadline(headLine);
    }

    @Override
    public NewsHeadline findHeadLineByHid(int hid) {
        return newsHeadLineDao.findHeadLineByHid(hid);
    }

    @Override
    public int updateHeadLine(NewsHeadline headline) {
        return newsHeadLineDao.update(headline);
    }

    @Override
    public int deleteByHid(int hid) {
        return newsHeadLineDao.deleteByHid(hid);
    }
}
