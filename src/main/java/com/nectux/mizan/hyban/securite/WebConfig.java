package com.nectux.mizan.hyban.securite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/jsp"); // Dossier contenant les fichiers JSP
        resolver.setSuffix(".jsp"); // Extension des fichiers JSP
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Pour les fichiers CSS, JS et images dans le dossier 'back-office'
        registry.addResourceHandler("/static/back-office/css/**")
                .addResourceLocations("classpath:/static/back-office/css/")
                .setCachePeriod(3600); // Cache des fichiers CSS

        registry.addResourceHandler("/static/back-office/js/**")
                .addResourceLocations("classpath:/static/back-office/js/")
                .setCachePeriod(3600); // Cache des fichiers JS

        registry.addResourceHandler("/static/back-office/img/**")
                .addResourceLocations("classpath:/static/back-office/img/");

        registry.addResourceHandler("/static/back-office/assert/**")
                .addResourceLocations("classpath:/static/back-office/assert/");



        registry.addResourceHandler("/static/front-end/css/**")
                .addResourceLocations("classpath:/static/front-end/css/")
                .setCachePeriod(3600); // Cache des fichiers CSS

        registry.addResourceHandler("/static/front-end/js/**")
                .addResourceLocations("classpath:/static/front-end/js/")
                .setCachePeriod(3600); // Cache des fichiers JS

        registry.addResourceHandler("/static/front-end/images/**")
                .addResourceLocations("classpath:/static/front-end/images/");

        registry.addResourceHandler("/static/front-end/font/**")
                .addResourceLocations("classpath:/static/front-end/font/");

        registry.addResourceHandler("/static/logo/**")
                .addResourceLocations("classpath:/static/logo/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Maps the /login URL to the login.jsp view
        registry.addViewController("/login").setViewName("login");
    }

}


