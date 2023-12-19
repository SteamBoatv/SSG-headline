package controller;

import common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.NewsType;
import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlineQueryVo;
import service.NewsHeadlineService;
import service.NewsTypeService;
import service.impl.NewsHeadlineServiceImpl;
import service.impl.NewsTypeServiceImpl;
import utils.WebUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门户控制器
 * 那些不需要登录就可以做的请求，都放在这里
 */
@WebServlet("/portal/*")
public class PortalController extends BaseController{
    private NewsTypeService newsTypeService = new NewsTypeServiceImpl();
    private NewsHeadlineService newsHeadlineService = new NewsHeadlineServiceImpl();

    /**
     * 根据传入的uid，来查询符合格式的头条详情
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showHeadlineDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid = Integer.parseInt(req.getParameter("hid"));
        HeadlineDetailVo detailVo = newsHeadlineService.getHeadLineDetailById(hid);
        Map data = new HashMap();
        data.put("headline",detailVo);
        WebUtil.writeJson(resp,Result.ok(data));
    }

    /**
     * 客户端向服务端发送查询关键字,新闻类别,页码数,页大小
     * @param req
     * {
     *     "keyWords":"马斯克", // 搜索标题关键字
     *     "type":0,           // 新闻类型
     *     "pageNum":1,        // 页码数
     *     "pageSize":"10"     // 页大小
     * }
     * @param resp
     * 返回一堆东西
     * "data":{
     *     	"pageInfo":{
     *     	    pageData:[xxx],
     *     	    "pageNum":1,    //页码数
     * 		    "pageSize":10,  // 页大小
     * 		    "totalPage":20, // 总页数
     * 		    "totalSize":200 // 总记录数
     *     	}
     * }
     * @throws ServletException
     * @throws IOException
     */
    protected void findNewsPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HeadlineQueryVo input = WebUtil.readJson(req, HeadlineQueryVo.class);
        Map<String,Object> pageInfo =  newsHeadlineService.findPage(input);
        Map data = new HashMap();
        data.put("pageInfo",pageInfo);
        WebUtil.writeJson(resp,Result.ok(data));
    }

    /**
     * 查询所有新闻类型，返回给客户端
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllTypes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<NewsType> list = newsTypeService.findAll();
        WebUtil.writeJson(resp, Result.ok(list));
    }
}
