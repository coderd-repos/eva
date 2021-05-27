package com.eva.config.shiro;

import com.eva.service.proxy.CacheProxy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.crazycake.shiro.SessionInMemory;
import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.RedisSerializer;
import org.crazycake.shiro.serializer.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ShiroSessionDAO extends AbstractSessionDAO {

    private static ThreadLocal<HashMap> sessionsInThread = new ThreadLocal<>();

    private static final String KEY_PREFIX = "shiro:session:";

    private static final long SESSION_IN_MEMORY_TIMEOUT = 1000L;

    private int expire = -2;

    private RedisSerializer keySerializer = new StringSerializer();

    private RedisSerializer valueSerializer = new ObjectSerializer();

    @Autowired
    private CacheProxy cacheProxy;

    @Override
    protected Serializable doCreate(Session session) {
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
    protected Session doReadSession(Serializable serializable) {
        return null;
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        if (sessionId == null) {
            log.warn("session id is null");
            return null;
        }
        Session session = this.getSessionFromThreadLocal(sessionId);
        if (session == null) {
            log.debug("read session from cache");
            try {
                session = (Session)this.valueSerializer.deserialize(cacheProxy.get(this.getSessionKey(sessionId)));
                this.setSessionToThreadLocal(sessionId, session);
            } catch (SerializationException var4) {
                log.error("read session error. sessionId=" + sessionId);
            }
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

    private void setSessionToThreadLocal(Serializable sessionId, Session s) {
        HashMap<Serializable, SessionInMemory> sessionMap = sessionsInThread.get();
        if (sessionMap == null) {
            sessionMap = new HashMap();
            sessionsInThread.set(sessionMap);
        }
        SessionInMemory sessionInMemory = new SessionInMemory();
        sessionInMemory.setCreateTime(new Date());
        sessionInMemory.setSession(s);
        sessionMap.put(sessionId, sessionInMemory);
    }

    private Session getSessionFromThreadLocal(Serializable sessionId) {
        Session s = null;
        if (sessionsInThread.get() == null) {
            return null;
        }
        HashMap<Serializable, SessionInMemory> sessionMap = sessionsInThread.get();
        SessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (sessionInMemory == null) {
            return null;
        }
        Date now = new Date();
        long duration = now.getTime() - sessionInMemory.getCreateTime().getTime();
        if (duration < SESSION_IN_MEMORY_TIMEOUT) {
            s = sessionInMemory.getSession();
            log.debug("read session from memory");
        } else {
            sessionMap.remove(sessionId);
        }

        return s;
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        String key = this.getSessionKey(session.getId());
        cacheProxy.set(key, session, (int)(session.getTimeout() / 1000L));
    }

    private String getSessionKey(Serializable sessionId) {
        return KEY_PREFIX + sessionId;
    }
}
