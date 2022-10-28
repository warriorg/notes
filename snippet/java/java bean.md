bean 覆盖

```
 * @author gao shiyong
 * @since 2022/10/24 14:58
 */
@Configuration
public class PubParamAutoConfiguration implements BeanDefinitionRegistryPostProcessor {
    public RequestInterceptor interceptor() {
        return template -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!Objects.isNull(authentication) && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof AuthHeader) {
                    AuthHeader authHeader = (AuthHeader)principal;
                    template.header("Authorization", authHeader.getBearerToken());
                    template.header("x-user-ass", authHeader.getUserAss());
                } else {
                    template.header("Authorization", "Bearer " + generateAccessToken());
                }
            }
            template.header("X-Request-ID", UUID.randomUUID().toString());
        };
    }

    /***
     * 生成访问token
     */
    public final String generateAccessToken () {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String jwtToken = JWT.create()
                .withJWTId(appId)
                .withIssuer("PUB_PARAM-SDK")
                .withIssuedAt(new Date())
                .sign(algorithm);
        return jwtToken;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String beanName = StringUtils.uncapitalize(PubParamClient.class.getSimpleName());
        registry.removeBeanDefinition(beanName);

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(PubParamClient.class);
        // 如果有构造函数参数, 有几个构造函数的参数就设置几个  没有就不用设置
        beanDefinitionBuilder.addConstructorArgValue(interceptor());
        // 设置 init方法 没有就不用设置
        beanDefinitionBuilder.setInitMethodName("init");
        // 设置 destory方法 没有就不用设置
        beanDefinitionBuilder.setDestroyMethodName("destory");
        // 将Bean 的定义注册到Spring环境
        registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}

```
