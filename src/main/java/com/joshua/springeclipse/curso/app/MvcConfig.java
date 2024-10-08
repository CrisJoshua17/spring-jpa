package com.joshua.springeclipse.curso.app;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    WebMvcConfigurer.super.addResourceHandlers(registry);
	    
	    registry.addResourceHandler("/uploads/**")
	        .addResourceLocations("file:C:/Users/crist/Desktop/Trabajos/Programas java/uploads/");
	    registry.addResourceHandler("/css/**")
        .addResourceLocations("classpath:/static/css/");
	}
	
		@Override
	    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	        converters.add(new MappingJackson2HttpMessageConverter());
	    }
		
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/error_403").setViewName("error_403");
		}
		
		@Bean
		public LocaleResolver localeResolver() {
			SessionLocaleResolver localeResolver = new SessionLocaleResolver();
			localeResolver.setDefaultLocale(new Locale("es","ES"));
			return localeResolver;
		}
		
		
		@Bean
		public LocaleChangeInterceptor localeChangeInterceptor() {
			LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
			localeInterceptor.setParamName("lang");
			return localeInterceptor;
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			
			registry.addInterceptor(localeChangeInterceptor());
		}
	
	
		@Bean
		public Jaxb2Marshaller jaxb2() {
			Jaxb2Marshaller  marshaller = new Jaxb2Marshaller();
			marshaller.setClassesToBeBound(new Class[] {com.joshua.springeclipse.curso.app.view.xml.ClienteList.class});
			return marshaller;
		}
		
}
