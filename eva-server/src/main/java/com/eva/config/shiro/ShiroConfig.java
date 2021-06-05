package com.eva.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 * @author Eva.Caesar Liu
 * @date 2021-03-30 23:10
 */
@Configuration
public class ShiroConfig {

    @Value("${cache.session.expire}")
    private int sessionExpireTime;

    @Autowired
    private ShiroCredentialsMatcher shiroCredentialsMatcher;

    @Autowired
    private ShiroSessionDAO shiroSessionDAO;

    @Autowired
    private ShiroCacheManager shiroCacheManager;

    @Autowired
    private ShiroRealm shiroRealm;

    @Bean("sessionRedisTemplate")
    public RedisTemplate<Object, Serializable> sessionRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 默认序列化方式
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        // 值序列化方式
        ShiroSessionSerializer serializer = new ShiroSessionSerializer();
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    @Bean
    public SessionManager sessionManager() {
        ShiroHeaderSessionManager sessionManager = new ShiroHeaderSessionManager();
        sessionManager.setSessionDAO(shiroSessionDAO);
//        sessionManager.setSessionFactory(new ShiroSessionFactory());
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(this.sessionManager());
        securityManager.setCacheManager(shiroCacheManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        // 路径拦截配置
        map.put("/system/login", "anon");
        map.put("/system/logout", "anon");
        map.put("/common/captcha", "anon");
        // - 放行swagger
        map.put("/doc.html", "anon");
        map.put("/webjars/**", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/v2/api-docs/**", "anon");
        // - 其他接口统一拦截
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        // 添加认证过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new ShiroAuthFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroSessionDAO getShiroSessionDAO () {
        shiroSessionDAO.setExpireTime(sessionExpireTime);
        return shiroSessionDAO;
    }

    @Bean
    public ShiroRealm getShiroRealm () {
        shiroRealm.setCredentialsMatcher(shiroCredentialsMatcher);
        return shiroRealm;
    }
}
