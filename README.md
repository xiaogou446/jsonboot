# jsonboot
JsonBoot是一款以Maven为基础，以Netty作为Http服务器的仿Springboot框架，完成了Springboot大部分的功能，旨在学习Spring的编程思想以及提升自己的编程水平。

## 特性
 - 放弃原内嵌式的tomcat，使用netty编写http服务器
 - 实现了@RestController、@GetMapping、@PostMapping等一系列SpringMVC路由注解功能
 - 使用Jackson序列化对response进行封装
 - 实现基于json格式的@RequestBody post请求
 - 实现Spring的IOC容器，依赖注入，通过三级缓存解决循环依赖-Aop等问题
 - 与SpringBoot一致完备的启动方式

## 项目结构

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021040114151038.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzYyNTk0,size_16,color_FFFFFF,t_70)
 - annotation —— 注解包，存放Spring、SpringMVC等相关注解
 - common —— 一些公共文件包，如打印等banner
 - constant —— 常量文件包
 - core —— 核心代码包，内涵IOC、AOP、SpringMVC等的具体实现
 - entity —— 对象实体包
 - exception —— 异常包，定义在项目运行过程中可能出现的异常
 - serialize —— 序列化包，使用的是json序列化
 - utils —— 工具类包，包括反射工具类等
 - JsonBootApplication —— run启动类，相当于是SpringBoot的SpringBootApplication，内置run方法启动

## 目前完成的功能

 1. 使用netty编写http服务器
 2. 完成注解@GetMapping @PostMapping的路由映射
 3. 完成@RestController scan包功能
 4. 完成@RequestParam 参数映射功能与get请求(暂时只能单个参数映射，后续映射到实体)
 5. 使用Jackson序列化进行response的封装
 6. 封装httpResponse异常实体
 7. 实现@RequestBody的post请求(暂时只实现了json格式传输，表单格式后面补充。有RequestBody标注的实体与json内容对应，没有标注的与uri后缀对应)
 8. 完成@PathVariable的功能，调节代码结构，使用简单工厂实现每个参数注解的映射
 9. 构建beanFactory IOC容器，实现基础的Autowired依赖注入功能
 10. 完成@Qulifier的功能
 11. 重构IOC模块实现方式，解决循环依赖
 12. 完成基于jdk和cglib的aop实现
 13. 重构启动方式，完成@ComponentScan
 14. ...后续完成随时更新

  

## 使用
在demo文件包中通过 **@componentScan** 注解扫描路径，启动即可。
**默认定义的端口就是8080**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210401141724126.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzYyNTk0,size_16,color_FFFFFF,t_70)
调用这是 **@RestController**标注的Controller类，进行设置路由访问即可，和SpringBoot的访问方式一致。
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021040114231752.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzYyNTk0,size_16,color_FFFFFF,t_70)
测试 **@PathVariable** 注解的作用
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210401143203838.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzYyNTk0,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210401143238687.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzYyNTk0,size_16,color_FFFFFF,t_70)

## 待续
