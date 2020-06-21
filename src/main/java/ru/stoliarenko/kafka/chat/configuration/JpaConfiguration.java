package ru.stoliarenko.kafka.chat.configuration;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Настройки доступа к данным.
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class JpaConfiguration {

    /**
     * Настройка подключения к БД.
     *
     * @param driverClass - класс драйвера, используемого для подкоючения.
     * @param databaseUrl - адрес для подключения.
     * @param databaseLogin - логин для подключения.
     * @param databasePassword - пароль для подключения.
     */
    @Bean(name = "dataSource")
    public DataSource getDataSource(
            @Value("${connector.class}") final String driverClass,
            @Value("${database.url}") final String databaseUrl,
            @Value("${database.login}") final String databaseLogin,
            @Value("${database.password}") final String databasePassword
    ) {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseLogin);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }

    /**
     * Настройка фабрики менеджеров сущностей.
     *
     * @param dataSource - подключение к БД.
     * @param showSql - флаг отображения запросов к БД.
     * @param tableStrategy - стратегия обновления таблиц БД.
     * @param dialect - диалект общения с БД.
     * @param useCache - флаг использования кэша второго уровня.
     * @param useQueryCache - флаг кеширования запросов.
     * @param regionPrefix - префикс региона для кеша второго уровня.
     * @param configFile - расположение файла конфигурации кеша второго уровня.
     * @param cacheFactoryClass - имя класса фабрики кеша второго уровня.
     * @param implicitNamingStrategy - стратегия именования таблиц при отсутствии явного указания.
     * @param physicalNamingStrategy - стратегия именования таблиц при наличии явного указания.
     * @param useMinPuts - флаг минимизации записи за счет более частого чтения.
     * @param useLiteMember - флаг использования в качестве легковесного члена hazelcast.
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(
            final DataSource dataSource,
            @Value("${hibernate.show_sql}") final String showSql,
            @Value("${hibernate.hbm2dll.auto}") final String tableStrategy,
            @Value("${hibernate.dialect}") final String dialect,
            @Value("${hibernate.cache.use_second_level_cache}") final String useCache,
            @Value("${hibernate.cache.use_query_cache}") final String useQueryCache,
            @Value("${hibernate.cache.region_prefix}") final String regionPrefix,
            @Value("${hibernate.cache.provider_configuration_file_resource_path}") final String configFile,
            @Value("${hibernate.cache.region.factory_class}")
            final String cacheFactoryClass,
            @Value("${hibernate.implicit_naming_strategy}")
            final String implicitNamingStrategy,
            @Value("${hibernate.physical_naming_strategy}")
            final String physicalNamingStrategy,
            @Value("${hibernate.cache.use_minimal_puts}") final String useMinPuts,
            @Value("${hibernate.cache.hazelcast.use_lite_member}") final String useLiteMember
    ) {
        final LocalContainerEntityManagerFactoryBean factoryBean;
        factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ru.stoliarenkoas.tm.webserver.model");
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        final Properties properties = new Properties();
        properties.put(Environment.SHOW_SQL, showSql);
        properties.put(Environment.HBM2DDL_AUTO, tableStrategy);
        properties.put(Environment.DIALECT, dialect);

        properties.put(Environment.USE_SECOND_LEVEL_CACHE, useCache);
        properties.put(Environment.USE_QUERY_CACHE, useQueryCache);
        properties.put(Environment.CACHE_REGION_PREFIX, regionPrefix);
        properties.put(Environment.CACHE_PROVIDER_CONFIG, configFile);
        properties.put(Environment.CACHE_REGION_FACTORY, cacheFactoryClass);
        properties.put(Environment.IMPLICIT_NAMING_STRATEGY, implicitNamingStrategy);
        properties.put(Environment.PHYSICAL_NAMING_STRATEGY, physicalNamingStrategy);

        properties.put(Environment.USE_MINIMAL_PUTS, useMinPuts);
        //client does not contain any data
        properties.put("hibernate.cache.hazelcast.use_lite_member", useLiteMember);
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    /**
     * Настройка менеджера транзакций.
     *
     * @param factoryBean - настройка фабрики менеджеров сущностей.
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager(
            final LocalContainerEntityManagerFactoryBean factoryBean
    ) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factoryBean.getObject());
        return transactionManager;
    }

}
