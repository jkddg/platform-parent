package com.platform.admin.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author jkddg
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public ApplicationContext getContext() {
        return context;
    }
    /**
     * 通过name获取 Bean.
     * @param name
     * @return
     */
    public Object getBean(String name){
        return getContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> clazz){
        return getContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(String name,Class<T> clazz){
        return getContext().getBean(name, clazz);
    }
}
