# jsonboot
仿springboot的框架，用于更好了解springboot的内核

## 完成进度:
1.使用netty编写http服务器 	<br/>
2.完成注解@GetMapping @PostMapping的路由映射 	<br/>
3.完成@RestController scan包功能 	<br/>
4.完成@RequestParam 参数映射功能与get请求(暂时只能单个参数映射，后续映射到实体)	<br/>
5.使用Jackson序列化进行response的封装	<br/>
6.封装httpResponse异常实体    <br/>
7.实现@RequestBody的post请求(暂时只实现了json格式传输，表单格式后面补充。有RequestBody标注的实体与json内容对应，没有标注的与uri后缀对应) <br/>
8.完成@PathVariable的功能，调节代码结构，使用简单工厂实现每个参数注解的映射 <br/>
9.构建beanFactory IOC容器，实现基础的Autowired依赖注入功能  <br/>
10.完成@Qulifier的功能 </br>
11.重构IOC模块实现方式，解决循环依赖 </br>
12.完成基于jdk和cglib的aop实现  </br>
13.重构启动方式，完成@ComponentScan </br>