# Spring Security

## Archiitechure

![](../assets/spring/securityfilterchain.png)

* `DelegatingFilterProxy` Spring provides a Filter implementation named DelegatingFilterProxy that allows bridging between the Servlet container's lifecycle and Spring's ApplicationContext
* `FilterChainProxy` FilterChainProxy is a special Filter provided by Spring Security that allows delegating to many Filter instances through SecurityFilterChain.
* `SecurityFilterChain` SecurityFilterChain is used by FilterChainProxy to determine which Spring Security Filter instances should be invoked for the current request.

