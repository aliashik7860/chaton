package com.appdevsolutions.chat.web.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.appdevsolutions.chat.dao" })
@PropertySource(value = { "classpath:hibernate.properties" })
public class HibernateConfiguration {
 
	private static final String DAO_PACKAGE_SCAN_PATH="com.appdevsolutions.chat.dao";
	private static final String DRIVER_MANAGER_CONST_CLASS_NAME="jdbc.driverClassName";
	private static final String DRIVER_MANAGER_CONST_URL="jdbc.url";
	private static final String DRIVER_MANAGER_CONST_USERNAME="jdbc.username";
	private static final String DRIVER_MANAGER_CONST_PASSWORD="jdbc.password";
    
	private static final String HIBERNATE_DDL_GENERATION="hibernate.hbm2ddl.auto";
	private static final String HIBERNAME_DIALECT_NAME="org.hibernate.dialect.MySQL5Dialect";
	private static final String HIBERNATE_FORMAT_SQL="format_sql";
	private static final Logger LOGGER=LoggerFactory.getLogger(HibernateConfiguration.class);
	
	@Autowired
    private Environment environment;
 
    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getSessionFactory() {
    	final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean=new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceUnitName("ChatOn");
        LOGGER.debug("hibernate entityManagerFactory bean is being set packagesToScan : "+DAO_PACKAGE_SCAN_PATH);
        entityManagerFactoryBean.setPackagesToScan(new String[] {DAO_PACKAGE_SCAN_PATH });
        //entityManagerFactoryBean.setJpaDialect(getJpaDialect());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdaptor());
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        return entityManagerFactoryBean;
     }
     
    /*@Bean
    public JpaDialect getJpaDialect(){
    	EclipseLinkJpaDialect eclipseLinkJpaDialect=new EclipseLinkJpaDialect();
    	return eclipseLinkJpaDialect;
    }*/
    @Bean
    public DataSource dataSource() {
    	LOGGER.debug("hibernate dataSource bean is being created");
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty(DRIVER_MANAGER_CONST_CLASS_NAME));
        dataSource.setUrl(environment.getRequiredProperty(DRIVER_MANAGER_CONST_URL));
        dataSource.setUsername(environment.getRequiredProperty(DRIVER_MANAGER_CONST_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(DRIVER_MANAGER_CONST_PASSWORD));
        LOGGER.debug("hibernate dataSource bean has been created : "+dataSource);
        return dataSource;
    }
     
    private Properties jpaProperties() {
    	LOGGER.debug("hibernate property bean is being created");
        final Properties properties = new Properties();
        properties.put(HIBERNATE_DDL_GENERATION, environment.getRequiredProperty(HIBERNATE_DDL_GENERATION));
        properties.put(HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(HIBERNATE_FORMAT_SQL));
        return properties;
    }
     
    @Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    	LOGGER.debug("hibernate transactionManager bean is being created");
    	final JpaTransactionManager txManager = new JpaTransactionManager();
    	txManager.setEntityManagerFactory(entityManagerFactory);
    	LOGGER.debug("hibernate transactionManager bean has been created : "+txManager);
    	return txManager;
    }
    
    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdaptor(){
    	final HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
    	jpaVendorAdapter.setShowSql(true);
    	jpaVendorAdapter.setGenerateDdl(true);
    	jpaVendorAdapter.setDatabasePlatform(HIBERNAME_DIALECT_NAME);
    	return jpaVendorAdapter;
    }
}