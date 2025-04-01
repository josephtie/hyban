package com.nectux.mizan.hyban.securite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesView;


@Configuration
public class TilesConfig {

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/front-end-tiles.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }




    @Bean
    public TilesViewResolver tilesViewResolver() {
        TilesViewResolver resolver = new TilesViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }

//    @Bean
//    public TilesViewResolver tilesViewResolver() {
//        TilesViewResolver tilesViewResolver = new TilesViewResolver();
//        tilesViewResolver.setOrder(1);
//        return tilesViewResolver;
//    }
}