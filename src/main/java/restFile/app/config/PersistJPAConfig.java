package restFile.app.config;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistJPAConfig 
{
	
	Properties additionalProperties() 
	{
		Properties properties = new Properties();
		
		//properties.setProperty("hibernate.hbm2ddl.auto","create-drop");
		
		return properties;
	}
	
	
	@Bean
   	public LocalContainerEntityManagerFactoryBean entityManagerFactory() 
   	{
	   	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	
	   	em.setDataSource(dataSource());
	   	em.setPackagesToScan(new String[] {"restFile.app.meta.entity"});
 
	   	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	   	vendorAdapter.setShowSql(true);
	    vendorAdapter.setGenerateDdl(true); 
	    vendorAdapter.setDatabase(Database.DERBY);
	   		    
	   	em.setJpaVendorAdapter(vendorAdapter);
	   	em.setJpaProperties(additionalProperties());
	   	
	   	return em;
   	}
 
   	@Bean
   	public DataSource dataSource()
   	{
   		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
   		
   		EmbeddedDatabase db = builder.setName("metadb")
   				.setType(EmbeddedDatabaseType.DERBY) 
   				.addScript("db.sql")
   				.build();
   		
   		return db;
   	}
 
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
	{
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
    	transactionManager.setEntityManagerFactory(emf);
 
    	return transactionManager;
	}
 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}
 
}