package filters;

import common.Result;
import common.ResultCodeEnum;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JwtHelper;
import utils.WebUtil;

import java.io.IOException;

@WebFilter("/headline/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("token");
        Result result = null;
        if(token != null && !JwtHelper.isExpiration(token)){
            filterChain.doFilter(request,response);
        }else {
            WebUtil.writeJson(response,Result.build(null, ResultCodeEnum.NOTLOGIN));
        }
    }
}
