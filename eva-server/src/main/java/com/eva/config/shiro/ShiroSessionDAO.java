package com.eva.config.shiro;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * 自定义Shiro SessionDAO，将会话信息存入缓存中
 * 技术参考：shiro-redis
 * @author Eva.Caesar Liu
 * @date 2021-05-28 00:24
 */
@Data
@Slf4j
@Component
public class ShiroSessionDAO implements SessionDAO {

    private static final String KEY_PREFIX = "shiro:session:";

    @Autowired
    private ShiroCache shiroCache;

    private int expireTime = 1800;

    @Autowired
    private ShiroTokenManager shiroTokenManager;

    @Override
    public Serializable create(Session session) {
        if (session == null) {
            log.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = shiroTokenManager.build();
        ((SimpleSession)session).setId(sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException{
        if (sessionId == null) {
            log.warn("session id is null");
            return null;
        }
        if (sessionId instanceof String) {
            // 对SessionId进行验证（可用于防止Session捕获、暴力捕捉等一系列安全问题，最终安全性取决于check如何实现）
            shiroTokenManager.check((String) sessionId);
        }
        log.debug("read session from cache");
        Session session = getSessionFromCache(sessionId);
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
            shiroCache.remove(KEY_PREFIX + session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        Set<Object> keys = shiroCache.keys();
        if (keys != null && keys.size() > 0) {
            Iterator iter = keys.iterator();
            while(iter.hasNext()) {
                sessions.add((Session) shiroCache.get(iter.next()));
            }
        }
        return sessions;
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        shiroCache.put(KEY_PREFIX + session.getId(), (SimpleSession)session, expireTime);
    }

    private Session getSessionFromCache (Serializable sessionId) {
        Serializable object = shiroCache.get(KEY_PREFIX + sessionId);
        Session session = null;
        if (object != null) {
            session = (Session)shiroCache.get(KEY_PREFIX + sessionId);
        }
        return session;
    }

    public void setExpireTime (int expireTime) {
        this.expireTime = expireTime;
    }
}
