package com.eva.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 自定义会话管理器，实现从Header中读取SessionId，自定义SessionId名称
 * 技术参考：https://blog.csdn.net/weixin_30273931/article/details/96202820
 * @author Caesar Liu
 * @date 2021-05-28 14:04
 */
@Slf4j
public class ShiroHeaderSessionManager extends DefaultSessionManager implements WebSessionManager {

    private static final String AUTH_TOKEN = "eva-auth-token";

    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
        if (!WebUtils.isHttp(context)) {
            log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response pair. No session ID cookie will be set.");
            return;
        }
        HttpServletRequest request = WebUtils.getHttpRequest(context);
        HttpServletResponse response = WebUtils.getHttpResponse(context);
        Serializable sessionId = session.getId();
        this.storeSessionId(sessionId, request, response);
        request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
    }

    @Override
    public Serializable getSessionId(SessionKey key) {
        Serializable sessionId = super.getSessionId(key);
        if (sessionId == null && WebUtils.isWeb(key)) {
            ServletRequest servletRequest = WebUtils.getRequest(key);
            if (!(servletRequest instanceof HttpServletRequest)) {
                log.trace("Can not get sessionId from header, the request is not HttpServletRequest");
                return null;
            }
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            return request.getHeader(AUTH_TOKEN);
        }
        return sessionId;
    }

    @Override
    public boolean isServletContainerSessions() {
        return false;
    }

    private void storeSessionId(Serializable currentId, HttpServletRequest request, HttpServletResponse response) {
        if (currentId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        }
        Cookie cookie = new SimpleCookie(AUTH_TOKEN);
        cookie.setHttpOnly(false);
        String idString = currentId.toString();
        cookie.setValue(idString);
        cookie.saveTo(request, response);
        log.trace("Set session ID cookie for session with id {}", idString);
    }
}
