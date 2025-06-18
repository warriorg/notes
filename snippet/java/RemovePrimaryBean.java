public class RemovePrimaryBean {

    @Bean
    public static BeanFactoryPostProcessor removePrimaryBean() {
        return (beanFactory) -> {
            if (beanFactory instanceof DefaultListableBeanFactory) {
                DefaultListableBeanFactory registry = (DefaultListableBeanFactory)beanFactory;
                String beanName = "redisCacheManager";
                if (registry.containsBeanDefinition(beanName)) {
                    BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
                    if (beanDefinition.isPrimary()) {
                        beanDefinition.setPrimary(false);
                        LOGGER.error("remove bean [{}] primary", beanName);
                    }
                }
            }

        };
    }
	
}
