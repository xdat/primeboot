package com.orakoglu.electiontracer;

import java.util.Locale;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import com.google.common.collect.ImmutableMap;

@SpringBootApplication
@Configuration
@ComponentScan("com.orakoglu")
public class ElectiontracerApplication implements ServletContextAware {

	public static Locale tr = new Locale("tr", "TR");

	private static ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(ElectiontracerApplication.class, args);
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Autowired
	public void setContext(ApplicationContext context) {
		ElectiontracerApplication.context = context;
	}

	@Bean
	public static CustomScopeConfigurer viewScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		configurer.setScopes(new ImmutableMap.Builder<String, Object>().put("view", new ViewScope()).build());
		return configurer;
	}

	@Bean
	public ServletRegistrationBean<FacesServlet> servletRegistrationBean() {
		ServletRegistrationBean<FacesServlet> servletRegistrationBean = new ServletRegistrationBean<>(
				new FacesServlet(), "*.xhtml");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
	}

	public void setServletContext(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
	}

}
