package com.eva.config.shiro;

import com.eva.service.proxy.CacheProxy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * 自定义Shiro SessionDAO，实现与代理缓存的结合
 * 技术参考：shiro-redis
 * @author Caesar Liu
 * @date 2021-05-28 00:24
 */
@Data
@Slf4j
@Component
public class ShiroSessionDAO implements SessionDAO {

    private static ThreadLocal<HashMap> sessionsInThread = new ThreadLocal<>();

    private static final String KEY_PREFIX = "shiro:session:";

    private static final long SESSION_IN_MEMORY_TIMEOUT = 1000L;

    @Lazy
    @Autowired
    private CacheProxy<String, Session> cacheProxy;

    @Override
    public Serializable create(Session session) {
        if (session == null) {
            log.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = UUID.randomUUID().toString();
        ((SimpleSession)session).setId(sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        if (sessionId == null) {
            log.warn("session id is null");
            return null;
        }
        log.debug("read session from cache");
        Session session = cacheProxy.get(this.getSessionKey(sessionId));
        if (session == null) {
            throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            cacheProxy.remove(this.getSessionKey(session.getId()));
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        String key = this.getSessionKey(session.getId());
        cacheProxy.put(key, session, (int)(session.getTimeout() / 1000L));
    }

    private String getSessionKey(Serializable sessionId) {
        return KEY_PREFIX + sessionId;
    }
}
