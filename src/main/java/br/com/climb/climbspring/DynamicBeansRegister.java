package br.com.climb.climbspring;

import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Component
public class DynamicBeansRegister implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

        Reflections reflections = new Reflections("");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Repository.class);

        annotated.stream().forEach(classe -> {

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(classe);
            enhancer.setCallback(NoOp.INSTANCE);

            BeanDefinition dynamicBean = BeanDefinitionBuilder.rootBeanDefinition(enhancer.create().getClass()) // here you define the class
                    .setScope(BeanDefinition.SCOPE_SINGLETON)
                    .getBeanDefinition();

            registry.registerBeanDefinition(classe.getName(), dynamicBean);
        });

        // here you can fire your logic to get definition for your beans at runtime and
        // then register all beans you need (possibly inside a loop)



    }
}
