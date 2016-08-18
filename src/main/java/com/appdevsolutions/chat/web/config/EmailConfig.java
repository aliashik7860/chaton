package com.appdevsolutions.chat.web.config;

import org.springframework.context.annotation.PropertySource;

/*@Configuration*/
@PropertySource(value = { "classpath:email.properties" })
public class EmailConfig {
	
	/*private static final String SMTP_HOST="smtp.host";
	private static final String SMTP_PORT="smtp.port";
	private static final String EMAIL_USERNAME="email.username";
	private static final String EMAIL_PASSWORD="email.password";
	private static final String MAIL_TRANSPORT_PROTOCOL="mail.transport.protocol";
	private static final String MAIL_SMTP_AUTH="mail.smtp.auth";
	private static final String MAIL_SMTP_STARTTLS_ENABLE= "mail.smtp.starttls.enable";
	private static final String MAIL_DEBUG="mail.debug";
	
	
	@Autowired
    private Environment environment;
	
	@Bean(name="mailSender")
	public MailSender getMailSender(){
		final JavaMailSenderImpl javaMailSenderImpl=new JavaMailSenderImpl();
		javaMailSenderImpl.setHost(environment.getRequiredProperty(SMTP_HOST));
		javaMailSenderImpl.setPort(Integer.parseInt(environment.getRequiredProperty(SMTP_PORT)));
		javaMailSenderImpl.setUsername(environment.getRequiredProperty(EMAIL_USERNAME));
		javaMailSenderImpl.setPassword(environment.getRequiredProperty(EMAIL_PASSWORD));
		javaMailSenderImpl.setJavaMailProperties(getJavaMailProperties());
		return javaMailSenderImpl;
	}
	
	private Properties getJavaMailProperties(){
		final Properties properties=new Properties();
		properties.put(MAIL_TRANSPORT_PROTOCOL, environment.getRequiredProperty(MAIL_TRANSPORT_PROTOCOL));
		properties.put(MAIL_SMTP_AUTH, environment.getRequiredProperty(MAIL_SMTP_AUTH));
		properties.put(MAIL_SMTP_STARTTLS_ENABLE, environment.getProperty(MAIL_SMTP_AUTH));
		properties.put(MAIL_DEBUG, environment.getProperty(MAIL_DEBUG));
		return properties;
	}*/
}