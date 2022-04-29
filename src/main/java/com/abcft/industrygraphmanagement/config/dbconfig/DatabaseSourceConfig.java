package com.abcft.industrygraphmanagement.config.dbconfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * @Author Created by YangMeng on 2021/3/3.
 */
@Configuration
@MapperScan(basePackages = {"com.abcft.industrygraphmanagement.dao.mysql"}, sqlSessionTemplateRef = "industrygraphSqlSessionTemplate")
public class DatabaseSourceConfig {
    @Bean(name = "industrygraphDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.industrygraph")
    public DataSource industrygraphDataSource() {
        //指定使用DruidDataSource
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean
    public SqlSessionFactory industrygraphSqlSessionFactory(@Qualifier("industrygraphDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/graph/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager industrygraphTransactionManager(@Qualifier("industrygraphDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate industrygraphSqlSessionTemplate(@Qualifier("industrygraphSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
