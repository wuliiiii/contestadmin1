package org.algotithmcontestdatacollect.displaybackend.Interceptor;

import org.algotithmcontestdatacollect.displaybackend.Utils.JWTUtil;
import org.algotithmcontestdatacollect.displaybackend.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class JWTInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(JWTInterceptor.class);

    @Autowired
    private JWTUtil JWTutil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            return JWTutil.checkToken(request);
        }catch (Exception err){
            logger.error(err.getMessage());
            response.setStatus(200);
            response.setCharacterEncoding("UTF8");
            response.getWriter().write(ResponseUtil.JSONReturn(401,"token过期或错误"));
            return false;
        }

    }
}