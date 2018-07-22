# security-oauth2
## 基于表单认证源码解读4-6
### 认证流程说明
UsernamePasswordAuthenticationFilter -> AuthenticationManager -> AuthenticationProvider -> UserDetailsService -> UserDetails -> Authentication(已认证)

UsernamePasswordAuthenticationFilter : 
    表单登陆会首先进入这个拦截器
    1 获取 username password
    2 构建一个AuthenticationToken 对象，其实就是一个Authentication接口的实现
        这个时候里面还没有认证，所有有关认证的参数都是空或者是false，表示现在还没有登录
    3 调用 AuthenticationManager 
    4 认证通过后会调用 successHandler 来处理成功后的逻辑，这里并且将认证通过的 authentication 对象传递给成功处理器，
        如果自定义了成功处理器，那么就会调用自定义的处理器
    5 如果认证逻辑中抛出了异常，就会被异常处理器捕获

AuthenticationManager：
    用于管理 AuthenticationProvider，它会收集所有的 provider，当请求来了去循环所有的provider，哪个匹配中了就会调用哪个provider去认证
    1 核心方法就是 authentication ，里面有一个循环，先是获取所有的provider，然后一个个循环
    2 因为不同的登录方式的认证逻辑是不一样的，如果是短信登录，根本就没有用户名和密码了
    3 当循环到一个支持的 provider 了，那么当前的provider 就会调用 authenticate方法进行认证校验
    4 默认的provider 是DAO-provider来实现的，它会调用 UserDetailsService 的 loadUserByUsername 方法，
            这个 UserDetailsService 其实就是我们需要实现的接口，一般都是从数据库查询，所以叫做 dao-provider

AuthenticationProvider：
    真正校验的逻辑是写在 provider 里面的
    1 provider有一个supports方法，就是判断当前的provider是否支持当前的这个请求
        判断依据就是根据 Authentication 接口的实现类型，比如现在有一个LiuYorkAuthentication，那么就会首先获取到这个类型
        LiuYorkAuthentication.class，然后判断循环里面的每一个provider是否支持这个这个类型，很明显这个时候肯定一个都不支持，
        因为这是自定义的，所以需要自己写一个provider支持这个 LiuYorkAuthentication.class 类型

    当所有检查都通过后会创建一个SuccessAuthentication 对象，这个对象就是认证成功的 authentication 

### 认证信息共享
Authentication(已认证) -> SecurityContext -> SecurityContextHolder -> SecurityContextPersistenceFilter

在认证成功处理器中，在调用成功处理器自己实现的方法之前，会调用 SecurityContextHolder.getContext().setAuthentication(authResult);
将自己认证的session 保存在 thread local 里面，正常情况下，一个请求和返回都是一个线程 

SecurityContext：
    其实就是 authentication 封装的一个类
SecurityContextHolder：
    其实就是 threadLocal 的封装

SecurityContextPersistenceFilter：
    是security过滤链中的第一个
    1 请求进来：先是检查session 里面有没有 securityContext ，如果有就把securityContext 拿出来放在线程里
    2 请求结束：检查线程里面是否有 SecurityContext，有就拿出来放在session 里面

### 获取认证信息
    通过 SecurityContextHolder.getContext().getAuthentication() 可以获取用户的认证信息



