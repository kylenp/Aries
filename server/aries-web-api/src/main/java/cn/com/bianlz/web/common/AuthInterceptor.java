package cn.com.bianlz.web.common;

import cn.com.bianlz.common.utils.GsonUtils;
import cn.com.bianlz.common.vo.Result;
import cn.com.bianlz.user.api.user.User;
import cn.com.bianlz.web.App;
import cn.com.bianlz.web.HttpCodeUtils;
import cn.com.bianlz.web.client.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bianlanzhou on 17/9/13.
 * Description
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserServiceClient userServiceClient = App.getBean(UserServiceClient.class,"cn.com.bianlz.web.client.UserServiceClient");
        if (handler instanceof HandlerMethod){
            try {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Authorizition auth = handlerMethod.getBeanType().getAnnotation(Authorizition.class);
                User user = null;
                String token = request.getHeader("token");
                if(token!=null){
                    Result<User> result = userServiceClient.getUserByToken(token);
                    user = result.getData();
                }
                if(auth!=null&&auth.auth()){
                    if(user==null){
                        HttpCodeUtils.set403(response);
                        return false;
                    }
                }
                request.getSession().setAttribute("user",user);
            }catch (Exception ex){
                ex.printStackTrace();
                logger.debug("auth intercepter exception:"+ex.getMessage());
            }

        }
        return super.preHandle(request, response, handler);
    }

}
