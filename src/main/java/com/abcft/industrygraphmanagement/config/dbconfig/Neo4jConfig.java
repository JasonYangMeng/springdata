package com.abcft.industrygraphmanagement.config.dbconfig;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManagerFactory;

/**
 * neo4j，支持事务，但是@Transactional注解不支持多个事务管理器，默认使用transactionManager，需要实现@Transactional管理mysql事务
 *
 * @Author Created by YangMeng on 2021/3/3 18:58
 */
@Aspect
@Configuration
@EnableTransactionManagement
@Slf4j
public class Neo4jConfig {
    /**
     * 定义neo4j事务
     *
     * @param sessionFactory
     * @return
     */
    @Bean("neo4jTransactionManager")
    public Neo4jTransactionManager neo4jTransactionManager(SessionFactory sessionFactory) {
        return new Neo4jTransactionManager(sessionFactory);
    }

    /**
     * 定义mysql 事务
     *
     * @param emf
     * @return
     */
    @Bean("transactionManager")
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Autowired
    @Qualifier("neo4jTransactionManager")
    Neo4jTransactionManager neo4jTransactionManager;
    @Autowired
    @Qualifier("transactionManager")
    JpaTransactionManager jpaTransactionManager;

    /**
     * neo4j和mysql混合事务
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around("@annotation(MultiTransaction)")
    public Object multiTransaction(ProceedingJoinPoint proceedingJoinPoint) {
        TransactionStatus neo4jTransactionStatus = neo4jTransactionManager.getTransaction(new DefaultTransactionDefinition());
        TransactionStatus jpaTransactionStatus = jpaTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object obj = proceedingJoinPoint.proceed();
            jpaTransactionManager.commit(jpaTransactionStatus);
            neo4jTransactionManager.commit(neo4jTransactionStatus);
            return obj;
        } catch (Throwable throwable) {
            jpaTransactionManager.rollback(jpaTransactionStatus);
            neo4jTransactionManager.rollback(neo4jTransactionStatus);
            log.error("multiTransaction fail:{}", throwable);
            throw new RuntimeException(throwable);
        }
    }
}
