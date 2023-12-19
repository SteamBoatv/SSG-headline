package controller;

import jakarta.servlet.annotation.WebServlet;
import service.NewsTypeService;
import service.impl.NewsTypeServiceImpl;

/**
 * 用来控制新闻种类的增删改查
 */
@WebServlet("/type/*")
public class NewsTypeController extends BaseController{
    private NewsTypeService newsTypeService = new NewsTypeServiceImpl();
}
