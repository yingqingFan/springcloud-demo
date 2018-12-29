package com.example.demo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 40)
public class SpringSessionConfig
//        implements HttpSessionListener
{
    private static final Logger logger = LoggerFactory.getLogger(SpringSessionConfig.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer seriallizer = new DefaultCookieSerializer();
        seriallizer.setCookiePath("/");
        return seriallizer;
    }

    @Bean
    public SessionRepository sessionRepository(){
        RedisOperationsSessionRepository sessionRepository =  new RedisOperationsSessionRepository(redisTemplate);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer(this.getClass().getClassLoader());
        sessionRepository.setDefaultSerializer(jdkSerializationRedisSerializer);
        sessionRepository.setDefaultMaxInactiveInterval(10000);
        return sessionRepository;
    }

//    @Bean(name = "springSessionDefaultRedisSerializer")
//    public RedisSerializer<Object> defaultRedisSerializer() {
//        return new JdkSerializationRedisSerializer() {
//            @Override
//            public Object deserialize(byte[] bytes) {
//                try {
//                    return super.deserialize(bytes);
//                } catch (Exception e) {
//                    if (logger.isDebugEnabled()) {
//                        logger.warn(e.getMessage());
//                    }
//                    return null;
//                }
//            }
//
//            @Override
//            public byte[] serialize(Object object) {
//                return super.serialize(object);
//            }
//        };
//    }

    @EventListener
    public void onSessionExpired(SessionExpiredEvent expiredEvent) {
        String sessionId = expiredEvent.getSessionId();

    }

    @EventListener
    public void onSessionDeleted(SessionDeletedEvent deletedEvent) {
        String sessionId = deletedEvent.getSessionId();

    }

    @EventListener
    public void onSessionCreated(SessionCreatedEvent createdEvent) {
        String sessionId = createdEvent.getSessionId();

    }

//    @Override
//    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
//        System.out.println("Session create!!");
//    }
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//        System.out.println("Session destroyed!!");
//    }
}
