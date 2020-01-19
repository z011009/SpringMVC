



# SpringMVC

ssm：spring+mybatis+springmvc   **MVC三层架构**

框架：研究官方文档，锻炼自学能力，锻炼笔记能力，锻炼项目能力



SpringMVC + Vue + SpringBoot + SpringCloud + Linux

SSM=javaweb做项目



MVC：模型（dao，service）视图（jsp）控制层（servlet）

dao

service

servlet：转发/重定向

jsp/html

# 1.回顾MVC

## 1.1.什么是MVC

* MVC是模型(Model)、视图(View)、控制器(Controller)的简写，是一种软件设计规范。

* 是将业务逻辑、数据、显示分离的方法来组织代码。

* MVC主要作用是**降低了视图与业务逻辑间的双向偶合**。

* MVC不是一种设计模式，**MVC是一种架构模式**。当然不同的MVC存在差异。

  

**Model（模型）：**数据模型，提供要展示的数据，因此包含数据和行为，可以认为是领域模型或JavaBean组件（包含数据和行为），不过现在一般都分离开来：Value Object（数据Dao） 和 服务层（行为Service）。也就是模型提供了模型数据查询和模型数据的状态更新等功能，包括数据和业务。

**View（视图）：**负责进行模型的展示，一般就是我们见到的用户界面，客户想看到的东西。

**Controller（控制器）：**接收用户请求，委托给模型进行处理（状态改变），处理完毕后把返回的模型数据返回给视图，由视图负责展示。 也就是说控制器做了个调度员的工作。



**最典型的MVC就是JSP + servlet + javabean的模式。**

![image-20200115163558290](E:\Typora2\笔记图片存放处\image-20200115163558290.png)

## 1.2.Model1

* 在web早期的开发中，通常采用的都是Model1。
* Model1中，主要分为两层，视图层和模型层。

![image-20200115163742564](E:\Typora2\笔记图片存放处\image-20200115163742564.png)



Model1优点：架构简单，比较适合小型项目开发；

Model1缺点：JSP职责不单一，职责过重，不便于维护；

## 1.3.Model2

![image-20200115163821492](E:\Typora2\笔记图片存放处\image-20200115163821492.png)

1. 用户发请求
2. Servlet接收请求数据，并调用对应的业务逻辑方法
3. 业务处理完毕，返回更新后的数据给servlet
4. servlet转向到JSP，由JSP来渲染页面
5. 响应给前端更新后的页面

```
职责分析：
Controller：控制器
1. 取得表单数据
2. 调用业务逻辑
3. 转向指定的页面

Model：模型
1. 业务逻辑
2. 保存数据的状态

View：视图
1. 显示页面
```

```
Model2这样不仅提高的代码的复用率与项目的扩展性，且大大降低了项目的维护成本。Model 1模式的实现比较简单，适用于快速开发小规模项目，Model1中JSP页面身兼View和Controller两种角色，将控制逻辑和表现逻辑混杂在一起，从而导致代码的重用性非常低，增加了应用的扩展性和维护的难度。Model2消除了Model1的缺点。
```

## 1.4.回顾Servlet

##### **1.新建maven父项目导入依赖**

```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
</dependencies>
```

##### **2.新建子项目，添加web app支持**

##### **3.导入servlet和jsp依赖**

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
</dependency>
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.2</version>
</dependency>
```

##### **4.编写Servlet类，来处理用户请求**

```java
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取前端参数
        String method = req.getParameter("method");
        if (method.equals("add")){
            req.getSession().setAttribute("msg","执行了add方法");
        }
        if (method.equals("delete")){
            req.getSession().setAttribute("msg","执行了delete方法");
        }
        //2.调用业务层
        //3.视图转发或重定向
        req.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

##### **5.在WEB-INF目录下新建一个jsp的文件夹，新建hello.jsp**

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        ${msg}
    </body>
</html>

```

##### **6.在web.xml中注册Servlet**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>hello</servlet-name>
        <servlet-class>cn.wugou.servlet.HelloServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>hello</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

##### **7.配置Tomcat，并启动测试**

* localhost:8080/hello?method=add
* localhost:8080/hello?method=delete



**MVC框架要做哪些事情**

1. 将url映射到java类或java类的方法 .
2. 封装用户提交的数据 .
3. 处理请求--调用相关的业务处理--封装响应数据 .
4. 将响应的数据进行渲染 . jsp / html 等表示层数据 .

**说明：**

常见的服务器端MVC框架有：Struts、Spring MVC、ASP.NET MVC、Zend Framework、JSF；常见前端MVC框架：vue、angularjs、react、backbone；由MVC演化出了另外一些模式如：MVP、MVVM 等等....



# 2.什么是SpringMVC

Spring MVC是Spring Framework的一部分，**是基于Java实现MVC的轻量级Web框架**。

查看官方文档：

https://docs.spring.io/spring/docs/5.2.0.RELEASE/spring-framework-reference/web.html#spring-web



**我们为什么要学习SpringMVC呢?**

### 2.1.Spring MVC的特点：

1. 轻量级，简单易学
2. 高效 , 基于请求响应的MVC框架
3. 与Spring兼容性好，无缝结合
4. 约定优于配置
5. 功能强大：RESTful、数据验证、格式化、本地化、主题等
6. 简洁灵活

**最重要的一点还是用的人多 , 使用的公司多 .**



### 2.2.中心控制器

Spring的web框架围绕DispatcherServlet设计。 DispatcherServlet的作用是将请求分发到不同的处理器。从Spring 2.5开始，使用Java 5或者以上版本的用户可以采用基于注解的controller声明方式。

 Spring MVC框架像许多其他MVC框架一样, **以请求为驱动** , **围绕一个中心Servlet分派请求及提供其他功能**，**DispatcherServlet是一个实际的Servlet (它继承自HttpServlet 基类)**。

![image-20200115165126507](E:\Typora2\笔记图片存放处\image-20200115165126507.png)



### 2.3.SpringMVC的原理如下图所示：

 当发起请求时被前置的控制器拦截到请求，根据请求参数生成代理请求，找到请求对应的实际控制器，控制器处理请求，创建数据模型，访问数据库，将模型响应给中心控制器，控制器使用模型与视图渲染视图结果，将结果返回给中心控制器，再将结果返回给请求者。

![image-20200115165026076](E:\Typora2\笔记图片存放处\image-20200115165026076.png)



#### SpringMVC执行原理

![image-20200115165443706](E:\Typora2\笔记图片存放处\image-20200115165443706.png)

图为SpringMVC的一个较完整的流程图，实线表示SpringMVC框架提供的技术，不需要开发者实现，虚线表示需要开发者实现。



#### **简要分析执行流程**

1. DispatcherServlet表示前置控制器，是整个SpringMVC的控制中心。用户发出请求，DispatcherServlet接收请求并拦截请求。
   - 我们假设请求的url为 : [http://localhost](http://localhost/):8080/SpringMVC/hello
   - **如上url拆分成三部分：**
   - [http://localhost](http://localhost/):8080服务器域名
   - SpringMVC部署在服务器上的web站点
   - hello表示控制器
   - 通过分析，如上url表示为：请求位于服务器localhost:8080上的SpringMVC站点的hello控制器。
2. HandlerMapping为处理器映射。DispatcherServlet调用HandlerMapping,HandlerMapping根据请求url查找Handler。
3. HandlerExecution表示具体的Handler,其主要作用是根据url查找控制器，如上url被查找控制器为：hello。
4. HandlerExecution将解析后的信息传递给DispatcherServlet,如解析控制器映射等。
5. HandlerAdapter表示处理器适配器，其按照特定的规则去执行Handler。
6. Handler让具体的Controller执行。
7. Controller将具体的执行信息返回给HandlerAdapter,如ModelAndView。
8. HandlerAdapter将视图逻辑名或模型传递给DispatcherServlet。
9. DispatcherServlet调用视图解析器(ViewResolver)来解析HandlerAdapter传递的逻辑视图名。
10. 视图解析器将解析的逻辑视图名传给DispatcherServlet。
11. DispatcherServlet根据视图解析器解析的视图结果，调用具体的视图。
12. 最终视图呈现给用户。

# 3.HelloSpringMVC（配置版）

这个了解一下原理就行，以后开发都是用注解的

## 3.1.代码流程

##### 1.新建一个moudle，添加web依赖，导入SpringMVC依赖

##### 2.配置web.xml,注册DispatcherServlet

```xml
<!--1.注册DispatcherServlet-->
<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--关联一个springmvc的配置文件:【servlet-name】-servlet.xml-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc-servlet.xml</param-value>
    </init-param>
    <!--启动级别-1-->
    <load-on-startup>1</load-on-startup>
</servlet>
<!--/ 匹配所有的请求；（不包括.jsp）-->
<!--/* 匹配所有的请求；（包括.jsp）-->
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

##### 3.编写SpringMVC配置文件------springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">
     <!--处理映射器-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
     <!--处理器适配器-->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

    <!--视图解析器:DispatcherServlet给他的ModelAndView-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

##### 4.编写Controller ，先用接口，以后都用注解

```java
//注意：这里我们先导入Controller接口
public class HelloController implements Controller {
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView 模型和视图
        ModelAndView mv = new ModelAndView();
        //封装对象，放在ModelAndView中。Model
        mv.addObject("msg", "HelloSpringMVC!");
        //封装要跳转的视图，放在ModelAndView中
        mv.setViewName("hello"); //: /WEB-INF/jsp/hello.jsp
        return mv;
    }
}
```

##### 5.将自己的类交给SpringIOC容器，注册bean

```xml
<!--Handler-->
<bean id="/hello" class="cn.wugou.controller.HelloController"/>
```

##### 6.编写jsp页面，显示ModelandView存放的数据

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        ${msg}
    </body>
</html>
```

##### 7.配置Tomcat 启动测试

![image-20200115170606918](E:\Typora2\笔记图片存放处\image-20200115170606918.png)

## **3.2.idea可能遇到的问题：**

**访问出现404，排查步骤：**

1. 看一下是不是缺少了什么jar包。

2. 在IDEA的项目发布中，添加lib依赖！

   ![image-20200115171111329](E:\Typora2\笔记图片存放处\image-20200115171111329.png)

3. 重启Tomcat 

### 小结

看这个估计大部分同学都能理解其中的原理了，但是我们实际开发才不会这么写，不然就疯了，还学这个玩意干嘛！**我们来看个注解版实现，这才是SpringMVC的精髓**，到底有多么简单，看这个图就知道了。

# 4.HelloSpringMVC（注解版）

## 4.1.代码

##### 1.新建一个子项目，添加web依赖

##### 2.由于Maven可能存在资源过滤的问题，我们将配置完善

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

##### 3.pom.xml导入依赖

```xml
<!--依赖-->
<dependencies>
    <!--单元测试的工具-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
    <!--Spring MVC依赖-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
    <!--servlet-->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
</dependencies>
```

##### 4.web.xml配置

```xml
<!--1.注册servlet-->
<servlet>
    <servlet-name>SpringMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--通过初始化参数指定SpringMVC配置文件的位置，进行关联-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc-servlet.xml</param-value>
    </init-param>
    <!-- 启动顺序，数字越小，启动越早 -->
    <load-on-startup>1</load-on-startup>
</servlet>

<!--所有请求都会被springmvc拦截 -->
<servlet-mapping>
    <servlet-name>SpringMVC</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

##### 5.springmvc-servlet.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 自动扫描包，让指定包下的注解生效,由IOC容器统一管理 -->
    <context:component-scan base-package="cn.mvc.controller"/>
    <!-- 让Spring MVC不处理静态资源 -->
    <mvc:default-servlet-handler />
    <mvc:annotation-driven />
    <!--支持mvc注解驱动
        在spring中一般采用@RequestMapping注解来完成映射关系
        要想使@RequestMapping注解生效
        必须向上下文中注册DefaultAnnotationHandlerMapping
        和一个AnnotationMethodHandlerAdapter实例
        这两个实例分别在类级别和方法级别处理。
        而annotation-driven配置帮助我们自动完成上述两个实例的注入。 -->
    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id="internalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

##### 6.编写Contreller类

```java
@Controller
@RequestMapping("/hello")
public class HelloController {
    //真实访问地址 : 项目名/hello/hello
    @RequestMapping("/hello")
    public String Hello(Model model){
        //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
        model.addAttribute("msg","HelloSpringMVC,Annotation");
        //WEB-INF/jsp/hello.jsp
        return "hello";
    }
}
```

##### 7.编写视图层（jsp）

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        ${msg}
    </body>
</html>
```

##### 8.配置Tomcat运行测试！

![image-20200115210831432](E:\Typora2\笔记图片存放处\image-20200115210831432.png)

## 4.2.小结

**实现步骤其实非常的简单：**

1. 新建一个web项目
2. 导入相关jar包
3. 编写web.xml , 注册DispatcherServlet
4. 编写springmvc配置文件
5. 接下来就是去创建对应的控制类 , controller
6. 最后完善前端视图和controller之间的对应
7. 测试运行调试.



使用springMVC必须配置的三大件：

**处理器映射器、处理器适配器、视图解析器**

通常，我们只需要**手动配置视图解析器**，而**处理器映射器**和**处理器适配器**只需要开启**注解驱动**即可，而省去了大段的xml配置

# 5.Controller 及 RestFul风格

## 5.1.Controller

* 控制器复杂提供访问应用程序的行为，通常通过接口定义或注解定义两种方法实现。
* 控制器负责解析用户的请求并将其转换为一个模型。
* 在Spring MVC中一个控制器类可以包含多个方法
* 在Spring MVC中，对于Controller的配置方式有很多种



## 5.2.实现Controller接口

```java
//只要实现了Controller接口的类，说明这就是一个控制器了
public class ControllerTest1 implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }
}
```

#### 测试：

##### 1.新建一个moudle项目，将前面的配置拷贝一份

* 删掉HelloController
* mvc的配置文件只留下 视图解析器！

##### 2.编写一个Controller类

```java
//只要实现了Controller接口的类，说明这就是一个控制器了
public class ControllerTest1 implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","HelloSpringMVC!");
        mv.setViewName("test");
        return mv;
    }
}
```

##### 3.在Spring配置文件注册请求bean

id/name对应请求路径，class对应处理请求的类

```xml
<bean id="/t1" class="cn.wg.controller.ControllerTest1"/>
```

##### 4.编写jsp

##### 5.配置Tomcat测试

![image-20200115214052762](E:\Typora2\笔记图片存放处\image-20200115214052762.png)

#### 说明：

* 实现接口Controller定义控制器是较老的办法
* 缺点是：一个控制器中只有一个方法，如果要多个方法则需要定义多个Controller；定义的方式比较麻烦；

## 5.3.使用注解@Controller

### 5.3.1.步骤：

##### 1.@Controller注解类型用于声明Spring类的实例是一个控制器（在讲IOC时还提到了另外3个注解）

```
@Component             组件
@Repository            dao
@Service               service
@Controller            controller
```

##### 2.Spring可以使用扫描机制来找到应用程序中所有基于注解的控制器类，为了保证Spring能找到你的控制器，需要在配置文件中声明组件扫描。

```xml
<!-- 自动扫描包，让指定包下的注解生效,由IOC容器统一管理 -->
<context:component-scan base-package="cn.wg.controller"/>
```

##### 3.增加一个ControllerTest2类，使用注解实现

```java
//@Controller注解的类会自动添加到Spring上下文中
@Controller
public class ControllerTest2 {
    @RequestMapping("t2")//映射访问路径
    public String Hello(Model model){
        //Spring MVC会自动实例化一个Model对象用于向视图中传值
        model.addAttribute("msg","HelloSpringMVC2");
        return "test";//返回视图位置
    }
}
```

##### 4.运行Tomcat测试

![image-20200115215335772](E:\Typora2\笔记图片存放处\image-20200115215335772.png)

### 5.3.2.小结：

**可以发现，我们的两个请求都可以指向一个视图，但是页面结果的结果是不一样的，从这里可以看出视图是被复用的，而控制器与视图之间是弱偶合关系。**



注解方式是平时使用的最多的方式！除了这两种之外还有其他的方式，大家想要自己研究的话，可以参考狂神的博客：https://www.cnblogs.com/hellokuangshen/p/11270742.html

## 5.4.@RequestMapping

* @RequestMapping注解用于映射url到控制器类或一个特定的处理程序方法。可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
* 测试 

```java
@Controller
public class ControllerTest2 {
    @RequestMapping("t1")
    public String Hello(Model model){
        model.addAttribute("msg","HelloSpringMVC2");
        return "test";
    }
}
访问这个t1
http://localhost:8080/t2/项目名/t1
```

```java
@Controller
@RequestMapping("t3")
public class ControllerTest3 {
    @RequestMapping("t1")
    public String test1(Model model) {
        model.addAttribute("msg", "123456");
        return "test";
    }
}
访问这个t1
http://localhost:8080/t2/项目名/t3/t1
```

## 5.5.RestFul 风格

**概念**

Restful就是一个资源定位及资源操作的风格。不是标准也不是协议，**只是一种风格**。基于这个风格设计的软件可以**更简洁，更有层次，更易于实现缓存等机制**。

**功能**

- 资源：互联网所有的事物都可以被抽象为资源
- 资源操作：使用POST、DELETE、PUT、GET，使用不同方法对资源进行操作。
- 分别对应 添加、 删除、修改、查询。

**传统方式操作资源** ：通过不同的参数来实现不同的效果！方法单一，post 和 get

- http://127.0.0.1/item/queryItem.action?id=1 查询,GET
- http://127.0.0.1/item/saveItem.action 新增,POST
- http://127.0.0.1/item/updateItem.action 更新,POST
- http://127.0.0.1/item/deleteItem.action?id=1 删除,GET或POST

**使用RESTful操作资源** ： 可以通过不同的请求方式来实现不同的效果！如下：请求地址一样，但是功能可以不同！

- http://127.0.0.1/item/1 查询,GET
- http://127.0.0.1/item 新增,POST
- http://127.0.0.1/item 更新,PUT
- http://127.0.0.1/item/1 删除,DELETE

#### 1.测试

1.新建一个controller类

2.在Spring MVC中可以使用 @PathVariable 注解，让方法参数的值对应绑定到一个URI模板变量上。

```java
@Controller
public class RestFulController {
    //原来：http://localhost:8080/add?a=1&b=1
    //RestFul：http://localhost:8080/add/1/1
    @RequestMapping("add/{a}/{b}")
    public String test1(@PathVariable int a,@PathVariable int b, Model model) {
        int res = a + b;
        //Spring MVC会自动实例化一个Model对象用于向视图中传值
        model.addAttribute("msg","结果为:"+res);
        return "test";
    }
}
```

3.运行测试

![image-20200116130339796](E:\Typora2\笔记图片存放处\image-20200116130339796.png)

#### 2.思考：使用路径变量的好处？

* 使路径变得更加简洁；
* 获得参数更加方便，框架会自动进行类型转换。
* 通过路径变量的类型可以约束访问参数，如果类型不一样，则访问不到对应的请求方法，如这里访问是的路径是/add/1/a，则路径与方法不匹配，而不会是参数转换失败。

#### 3.修改类型在测试

```java
@RequestMapping("add/{a}/{b}")
public String test1(@PathVariable int a, @PathVariable String b, Model model) {
    String res = a + b;
    model.addAttribute("msg", "结果为:" + res);
    return "test";
}
```

![image-20200116130853088](E:\Typora2\笔记图片存放处\image-20200116130853088.png)		

#### 4.使用method属性指定请求类型

用于约束请求的类型，可以收窄请求范围。指定请求谓词的类型如GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE等



##### 1.测一下

```java
//映射访问路径,必须是POST请求
@RequestMapping(value = "add/{a}/{b}",method = RequestMethod.POST)
public String test1(@PathVariable int a, @PathVariable int b, Model model) {
    int res = a + b;
    model.addAttribute("msg", "结果为:" + res);
    return "test";
}
```

![image-20200116131325511](E:\Typora2\笔记图片存放处\image-20200116131325511.png)

我们使用浏览器地址栏进行访问默认是GET请求，会报错405：，将POST改为GET

```java
@RequestMapping(value = "add/{a}/{b}",method = RequestMethod.GET)
```

则正常

![image-20200116132504588](E:\Typora2\笔记图片存放处\image-20200116132504588.png)

##### 小结：

Spring MVC 的 @RequestMapping 注解能够处理 HTTP 请求的方法, 比如 GET, PUT, POST, DELETE 以及 PATCH。

**所有的地址栏请求默认都会是 HTTP GET 类型的。**

#### 5.组合注解

```java
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PatchMapping
```

@GetMapping 是一个组合注解

它所扮演的是 @RequestMapping(method =RequestMethod.GET) 的一个快捷方式。

#### 6.小黄鸭调试法

场景一：我们都有过向别人（甚至可能向完全不会编程的人）提问及解释编程问题的经历，但是很多时候就在我们解释的过程中自己却想到了问题的解决方案，然后对方却一脸茫然。

场景二：你的同行跑来问你一个问题，但是当他自己把问题说完，或说到一半的时候就想出答案走了，留下一脸茫然的你。



其实上面两种场景现象就是所谓的小黄鸭调试法（Rubber Duck Debuging），又称橡皮鸭调试法，它是我们软件工程中最常使用调试方法之一。

此概念据说来自《程序员修炼之道》书中的一个故事，传说程序大师随身携带一只小黄鸭，在调试代码的时候会在桌上放上这只小黄鸭，然后详细地向鸭子解释每行代码，然后很快就将问题定位修复了。



# 6.结果跳转方式

## 6.1.ModelAndView

设置ModelAndView对象 , 根据view的名称 , 和视图解析器跳到指定的页面 .

## 6.2.ServletAPI

通过设置ServletAPI , 不需要视图解析器 .

1. 通过HttpServletResponse进行输出
2. 通过HttpServletResponse实现重定向
3. 通过HttpServletResponse实现转发



## 6.3.SpringMVC

**通过SpringMVC来实现转发和重定向 - 无需视图解析器；**

测试前，需要将视图解析器注释掉

```java
@Controller
public class ResultSpringMVC {
    @RequestMapping("/m1/t1")
    public String test1(){
        //转发
        return "test";
    }
    @RequestMapping("/m1/t2")
    public String test2(){
        //转发二
        return "forward:test";
    }
    @RequestMapping("/m1/t3")
    public String test3(){
        //重定向
        return "redirect:test";
    }
}
```

**通过SpringMVC来实现转发和重定向 - 有视图解析器；**

重定向 , 不需要视图解析器 , 本质就是重新请求一个新地方嘛 , 所以注意路径问题.

可以重定向到另外一个请求实现 .

```java
@Controller
public class ModelAndViewTest {
    //输入http://localhost:8080/m1/t1
    @RequestMapping("/m1/t1")
    public String test1() {
        return "redirect:/hello";//重定向到hello这个请求，
    }
    //结果http://localhost:8080/hello
    @RequestMapping("hello")
    public String hello() {
        return "test";
    }
}
```

# 7.接受请求参数及数据回显

## 7.1处理提交数据

#### 1.提交的域名称和处理方法的参数名一致

```java
@RequestMapping("user")
public class UserController {
    //http://localhost:8080/user/t1?name=admin      一致
    //http://localhost:8080/user/t1?username=admin  不一致@RequestParam("username")
    @GetMapping("t1")
    public String test(@RequestParam("name")String name, Model model) {
        System.out.println("接受到了前端参数:" + name);   //1.接受前端参数
        model.addAttribute("msg", name);   //2.将返回的结果传递给前端，Model
        return "test";   //3.视图跳转
    }
后台输出:接受到了前端参数:admin
```



#### 2.提交的域名称和处理方法的参数名不一致

###### @RequestParam用于将请求参数区数据映射到功能处理方法的参数上，参数有：

value：参数名，即入参的请求参数名字，如username表示请求的参数区中的名字为username的参数的值将入；
required：是否必须，默认是true，表示请求中一定要有相应的参数，否则将报404错误码；
defaultValue：默认值，表示如果请求中没有同名参数时的默认值，默认值可以是SpEL表达式。



#### 3.提交的是一个对象

```java
//前端接收的是一个对象，id，name，age
//http://localhost:8080/user/t2?id=1&name=admin&age=10
/* 1.接收前端用户传递的参数，判断参数饿名字，假设名字直接在方法上，可以直接使用
    2.假设传递的是一个User对象，匹配User对象中的字段名，如果名字一样则ok，否则，匹配不到
    后台输出 User(id=1, name=admin, age=10) */
@GetMapping("t2")
public String test2(User user) {
    System.out.println(user);
    return "test";
}
```

**说明：如果使用对象的话，前端传递的参数名和对象名必须一致，否则就是null。**



#### 4.数据显示到前端

* ModelAndView

```java
public ModelAndView Test1(){
    //返回一个模型视图对象
    ModelAndView mv = new ModelAndView();
    mv.addObject("msg","ModelAndView");
    mv.setViewName("test");
    return mv;
} 
```

* ModelMap

```java
public String Test2(ModelMap modelMap){
    //封装要显示到视图中的数据
    //相当于req.setAttribute("msg","ModelMap");
    modelMap.addAttribute("msg","ModelMap");
    return "test";
}
```

* Model

```java
public String Test3(Model model){
    //封装要显示到视图中的数据
    //相当于req.setAttribute("msg","model");
    model.addAttribute("msg","model");
    return "test";
}
```

### 对比:

```
Model 只有寥寥几个方法只适合用于储存数据，简化了新手对于Model对象的操作和理解；

ModelMap 继承了 LinkedMap ，除了实现了自身的一些方法，同样的继承 LinkedMap 的方法和特性；

ModelAndView 可以在储存数据的同时，可以进行设置返回的逻辑视图，进行控制展示层的跳转。
```

当然更多的以后开发考虑的更多的是性能和优化，就不能单单仅限于此的了解。

**请使用80%的时间打好扎实的基础，剩下18%的时间研究框架，2%的时间去学点英文，框架的官方文档永远是最好的教程。**



## 7.2乱码问题

#### 1.测试

1.编写一个提交的表单

2.编写Controller

3.输入中文测试

![image-20200116150945875](E:\Typora2\笔记图片存放处\image-20200116150945875.png)

不得不说，乱码问题是在我们开发中十分常见的问题，也是让我们程序猿比较头大的问题！

以前乱码问题通过过滤器解决 , 而SpringMVC给我们提供了一个过滤器 , 可以在web.xml中配置 .

修改了xml文件需要重启服务器！

```xml
<filter>
    <filter-name>encoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>encoding</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

有些极端情况下.这个过滤器对get的支持不好 

#### 2.Tomcat乱码的问题

在tomcat安装的地方conf文件下的server.xml加入这段

```xml
<Connector URIEncoding="utf-8" port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```



# 8.JSON

前后端分离时代：

后端部署后端，提供接口，提供数据：

​                   json

前端独立部署，负责渲染后端的数据：

## 8.1.什么是JSON?

* JSON(JavaScript Object Notation, JS 对象标记) 是一种轻量级的数据交换格式，目前使用特别广泛。
* 采用完全独立于编程语言的**文本格式**来存储和表示数据。
* 简洁和清晰的层次结构使得 JSON 成为理想的数据交换语言。
* 易于人阅读和编写，同时也易于机器解析和生成，并有效地提升网络传输效率。

在 JavaScript 语言中，一切都是对象。因此，任何JavaScript 支持的类型都可以通过 JSON 来表示，例如字符串、数字、对象、数组等。看看他的要求和语法格式：

* 对象表示为键值对，数据由逗号分隔
* 花括号保存对象
* 方括号保存数组

**JSON 键值对**是用来保存 JavaScript 对象的一种方式，和 JavaScript 对象的写法也大同小异，键/值对组合中的键名写在前面并用双引号 "" 包裹，使用冒号 : 分隔，然后紧接着值：

```json
{"name": "wugou"}
{"age": "3"}
{"sex": "男"}
```

很多人搞不清楚 JSON 和 JavaScript 对象的关系，甚至连谁是谁都不清楚。其实，可以这么理解：

* JSON 是 JavaScript 对象的字符串表示法，它使用文本表示一个 JS 对象的信息，本质是一个字符串。

```
var obj = {a: 'Hello', b: 'World'}; //这是一个对象，注意键名也是可以使用引号包裹的
var json = '{"a": "Hello", "b": "World"}'; //这是一个 JSON 字符串，本质是一个字符串
```

#### JSON 和 JavaScript 对象互转

* 要实现从JSON字符串转换为JavaScript 对象，使用 JSON.parse() 方法：

* 要实现从JavaScript 对象转换为JSON字符串，使用 JSON.stringify() 方法：

##### 实例：

```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
        <script type="text/javascript">
            //编写一个javascript对象 ES6
            var user={
                name:"无垢",
                age:3,
                sex:"男"
            };
            //将js对象转换为json对象
            var json=JSON.stringify(user);
            console.log(json);
            //将json对象转换为JavaScript对象
            var obj=JSON.parse(json);
            console.log(obj);
        </script>
    </head>
    <body>
    </body>
</html>
```

![image-20200116171110992](E:\Typora2\笔记图片存放处\image-20200116171110992.png)



## 8.2.Controller返回JSON数据

* Jackson应该是目前比较好的json解析工具了
* 当然工具不止这一个，比如还有阿里巴巴的 fastjson 等等。
* 我们这里使用Jackson，使用它需要导入它的jar包；

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.10.0</version>
</dependency>
```

1.新建项目，添加web依赖，导包，配置SpringMVC需要的配置:web.xml , springmvc-servlet.xml

2.编写User实体类，然后去Controller测试

3.我们需要两个新东西，@ResponseBody，ObjectMapper对象，我们看下具体的用法

```java
@Controller
public class UserController {
    @RequestMapping("/j1")
    @ResponseBody
    public String json1() throws JsonProcessingException {
        //创建一个jackson的对象映射器，用来解析数据
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user = new User("无垢", 3, "男");
        //将我们的对象解析成为json格式
        String str = mapper.writeValueAsString(user);
        //由于@ResponseBody注解，这里会将str转成json格式返回；十分方便
        return str;
    }
}
```

4.配置tomcat，运行！    

出现乱码解决

```java
//produces:指定响应体返回类型和编码
@RequestMapping(value = "/json1",produces = "application/json;charset=utf-8")
```

5.再次测试,页面应该是

```
{"name":"无垢","age":3,"sex":"男"}
```

### 代码优化

**乱码统一解决:**在 springmvc-servlet.xml中配置

```xml
<!-- 处理请求返回json字符串的乱码问题 -->
<mvc:annotation-driven>
    <mvc:message-converters register-defaults="true">
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <constructor-arg value="UTF-8"/>
        </bean>
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
            <property name="objectMapper">
                <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                    <property name="failOnEmptyBeans" value="false"/>
                </bean>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

**返回json字符串统一解决：**

在类上直接使用 @RestController ，这样子，里面所有的方法都只会返回 json 字符串了，不用再每一个都添加@ResponseBody ！我们在前后端分离开发中，一般都使用 @RestController ，十分便捷！

```java
@RestController
public class UserController {
```

### 测试集合输出

增加一个新的方法

```java
@RequestMapping("/j2")
public String json2() throws JsonProcessingException {
    //创建一个jackson的对象映射器，用来解析数据
    ObjectMapper mapper = new ObjectMapper();
    //创建一个对象
    User user1 = new User("无垢1", 3, "男");
    User user2 = new User("无垢2", 3, "男");
    List<User> userList = new ArrayList<User>();
    list.add(user1);
    list.add(user2);
    //将我们的对象解析成为json格式
    return mapper.writeValueAsString(userList);
}
```

### 输出时间对象

增加一个新的方法

```java
@RequestMapping("/j3")
public String json3() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    //创建时间一个对象，java.util.Date
    Date date = new Date();
    //自定义日期格式对象
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //将我们的对象解析成为json格式
    return mapper.writeValueAsString(sdf.format(date));
}
```

### 抽取为工具类

如果要经常使用的话，这样是比较麻烦的，我们可以将这些代码封装到一个工具类中；我们去编写下

```java
public class JSONUtil {
    public static String getJson(Object object){
        return getJson(object,"yyyy-MM-dd HH:mm:ss");//方法复用
    }
    public static String getJson(Object object,String dateFormat){
        ObjectMapper mapper=new ObjectMapper();
        //不使用时间戳打的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //自定义日期格式
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        mapper.setDateFormat(sdf);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

```

我们使用工具类，代码就更加简洁了！

```java
@RestController
public class UserController {
    @RequestMapping("j1")
    public String json1(){
        User user = new User("wugou", 18, "男");
        return JSONUtil.getJson(user);
    }
    @RequestMapping("j2")
    public String json2() {
        //创建一个对象
        List<User> users = new ArrayList<User>();
        User user1 = new User("wugou1", 18, "男");
        User user2 = new User("wugou2", 18, "男");
        users.add(user1);
        users.add(user2);
        return JSONUtil.getJson(users);
    }
    @RequestMapping("j3")
    public String json3() {
        Date date = new Date();
        return JSONUtil.getJson(date);
        //return JSONUtil.getJson(date,"yyyy-MM-dd");//自定义时间格式
    }
}
```



## 8.3.Fastjson

fastjson.jar是阿里开发的一款专门用于Java开发的包，可以方便的实现json对象与JavaBean对象的转换，实现JavaBean对象与json字符串的转换，实现json对象与json字符串的转换。实现json的转换方法很多，最后的实现结果都是一样的。

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.62</version>
</dependency>
```

##### fastjson 三个主要的类：

- 【JSONObject 代表 json 对象 】
  - JSONObject实现了Map接口, 猜想 JSONObject底层操作是由Map实现的。
  - JSONObject对应json对象，通过各种形式的get()方法可以获取json对象中的数据，也可利用诸如size()，isEmpty()等方法获取"键：值"对的个数和判断是否为空。其本质是通过实现Map接口并调用接口中的方法完成的。
- 【JSONArray 代表 json 对象数组】
  - 内部是有List接口中的方法来完成操作的。
- 【JSON 代表 JSONObject和JSONArray的转化】
  - JSON类源码分析与使用
  - 仔细观察这些方法，主要是实现json对象，json对象数组，javabean对象，json字符串之间的相互转化。

##### 代码测试:

```java
public class FastJsonDemo {
    public static void main(String[] args) {
        //创建一个对象
        User user1 = new User("无垢1", 3, "男");
        User user2 = new User("无垢2", 3, "男");
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        
        System.out.println("*******Java对象 转 JSON字符串*******");
        String str1 = JSON.toJSONString(list);
        System.out.println("JSON.toJSONString(list)==>"+str1);
        String str2 = JSON.toJSONString(user1);
        System.out.println("JSON.toJSONString(user1)==>"+str2);

        System.out.println("\n****** JSON字符串 转 Java对象*******");
        User jp_user1=JSON.parseObject(str2,User.class);
        System.out.println("JSON.parseObject(str2,User.class)==>"+jp_user1);

        System.out.println("\n****** Java对象 转 JSON对象 ******");
        JSONObject jsonObject1 = (JSONObject) JSON.toJSON(user2);
        System.out.println("(JSONObject) JSON.toJSON(user2)==>"+jsonObject1.getString("name"));

        System.out.println("\n****** JSON对象 转 Java对象 ******");
        User to_java_user = JSON.toJavaObject(jsonObject1, User.class);
        System.out.println("JSON.toJavaObject(jsonObject1, User.class)==>"+to_java_user);
    }
}
```

这种工具类，我们只需要掌握使用就好了，在使用的时候在根据具体的业务去找对应的实现。和以前的commons-io那种工具包一样，拿来用就好了！

# 9.整合SSM

#### 1.新建一个项目，添加web支持，导入整合所需依赖

```xml
<dependencies>
    <!--Junit-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
    <!--数据库驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.47</version>
    </dependency>
    <!-- 数据库连接池 -->
    <dependency>
        <groupId>com.mchange</groupId>
        <artifactId>c3p0</artifactId>
        <version>0.9.5.2</version>
    </dependency>
    <!--Servlet - JSP -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
    <!--Mybatis-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.2</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>2.0.2</version>
    </dependency>
    <!--Spring-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.10</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

#### 2.Maven资源过滤设置

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

#### 3.建立基本结构和配置框架，web.xml

* cn.wugpu.pojo
* cn.wugpu.dao
* cn.wugpu.service
* cn.wugpu.controller
* mybatis-config.xml

* applicationContext.xml
* web.xml

```xml
<!--dispatchServlet-->
<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
<!--乱码过滤-->
<filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
<!--session-->
<session-config>
    <session-timeout>15</session-timeout>
</session-config>
```

#### 4.Mybatis，Spring，SpringMVC配置

##### 4.1.数据库配置文件 database.properties

```pro
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssmbuild?useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf8
jdbc.username=root
jdbc.password=123456
```

##### 4.2.编写MyBatis的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <!--配置数据源，交给Spring去做-->
    <!--别名-->
    <typeAliases>
        <package name="cn.wugou.pojo"/>
    </typeAliases>
    <mappers>
        <mapper resource="mapper/BookMapper.xml" />
    </mappers>
</configuration>
```

##### 4.3.编写spring-dao.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <!--1.关联数据库配置文件-->
    <context:property-placeholder location="classpath:database.properties"/>
    <!--2.连接池
    dbcp:半自动化操作，不能自动连接
    c3p0:自动化操作（自动化的加载配置文件，并且可以自动设置到对象中）
    druid:hikari: -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <!-- c3p0连接池的私有属性 -->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!-- 关闭连接后不自动commit -->
        <property name="autoCommitOnClose" value="false"/>
        <!-- 获取连接超时时间 -->
        <property name="checkoutTimeout" value="10000"/>
        <!-- 当获取连接失败重试次数 -->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>
    <!--3.sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--绑定mybatis配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>
    <!--配置dao接口扫描包，动态实现了dao接口可以注入到spring容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--注入sqlsessionfactory-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!--要扫描的dao包-->
        <property name="basePackage" value="cn.wugou.dao"/>
    </bean>
</beans>
```

##### 4.4.spring-service.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <!--1.扫描service下的包-->
    <context:component-scan base-package="cn.wugou.service"/>
    <!--2.将我们的所有业务类注入到Spring容器，可以通过配置或者注解实现-->
    <bean id="BookServiceImpl" class="cn.wugou.service.impl.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>
    <!--3.声明式事务-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--4.aop事务支持-->
</beans>
```

##### 4.5.配置springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <!--注解驱动-->
    <mvc:annotation-driven/>
    <!--静态资源过滤-->
    <mvc:default-servlet-handler/>
    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    <!--扫描包-->
    <context:component-scan base-package="cn.wugou.controller"/>
</beans>
```

##### 4.6.集中导入在主配置文件applicationContext.xml文件中

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    <import resource="classpath:spring-dao.xml"/>
    <import resource="classpath:spring-service.xml"/>
    <import resource="classpath:springmvc-servlet.xml"/>
</beans>
```

#### 5.编写实体类，dao层，service层

##### 5.1.dao层，使用了lombok

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {
    private int bookID; //书id
    private String bookName;//书名
    private int bookCounts;//书数量
    private String detail; //书详情
}
```

##### 5.2.dao/mapper层

```java
public interface BookMapper {
    public int addBook(Books books);    //添加
    public int delBookById(@Param("bookId") int id);    //删除
    public int editBook(Books books);    //修改
    public Books getBookById(@Param("bookId") int id);    //查询一本
    public List<Books> getBooks();    //查询全部
    public List<Books> getBooksByName(@Param("bookName")String bookName);    //根据名称查询
}
```

##### 5.3.service接口

```java
public interface BookService {
    public int addBook(Books books);    //添加
    public int delBookById(int id);    //删除
    public int editBook(Books books);    //修改
    public Books getBookById(int id);    //查询一本书
    public List<Books> getBooks();    //查询全部书
    public List<Books> getBooksByName(String bookName); //根据名称查询
}
```

##### 5.4.service接口实现类

```java
public class BookServiceImpl implements BookService {
    private BookMapper bookMapper;
    //调用dao层的操作，设置一个set接口，方便Spring管理
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    @Override
    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }
    @Override
    public int delBookById(int id) {
        return bookMapper.delBookById(id);
    }
    @Override
    public int editBook(Books books) {
        return bookMapper.editBook(books);
    }
    @Override
    public Books getBookById(int id) {
        return bookMapper.getBookById(id);
    }
    @Override
    public List<Books> getBooks() {
        return bookMapper.getBooks();
    }
    @Override
    public List<Books> getBooksByName(String bookName) {
        return bookMapper.getBooksByName(bookName);
    }
}

```

##### 5.5.编写xxxMapper.xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.wugou.dao.BookMapper">
    <!--插入-->
    <insert id="addBook" parameterType="books">
        insert into books(bookName, bookCounts, detail)
        values(#{bookName},#{bookCounts},#{detail})
    </insert>
    <!--删除-->
    <delete id="delBookById" parameterType="int">
        delete from books where bookID=#{bookId}
    </delete>
    <!--更新-->
    <update id="editBook" parameterType="books">
        update books
        set bookName=#{bookName},bookCounts=#{bookCounts},detail=#{detail}
        where bookID=#{bookID}
    </update>
    <!--根基id查询数据-->
    <select id="getBookById" resultType="books" parameterType="books">
        select * from books where bookID=#{bookId}
    </select>
    <!--查询所有书籍-->
    <select id="getBooks" resultType="Books" parameterType="Books">
        select * from books
    </select>
    <!--根基书名查询数据-->
    <select id="getBooksByName" resultType="Books" parameterType="Books">
        select * from books where bookName=#{bookName}
    </select>
</mapper>
```

#### 6.Controller层

```java
@Controller
@RequestMapping("book")
public class BookController {
    //controller调service层
    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;
    //查询所有的书，并且返回到一个书籍展示页面
    @RequestMapping("allBooks")
    public String list(Model model){
        List<Books> books = bookService.getBooks();
        model.addAttribute("list",books);
        return "allBooks";
    }
    //跳转到书籍增加页面
    @RequestMapping("toAddBook")
    public String toAddPaper(){
        return "addBook";
    }
    //添加
    @RequestMapping("addBook")
    public String addBook(Books books){
        System.out.println("add:"+books);
        bookService.addBook(books);
        return "redirect:/book/allBooks";
    }
    //跳转到更新页面
    @RequestMapping("toEditBook")
    public String toEditBook(int id,Model model){
        Books bookById = bookService.getBookById(id);
        model.addAttribute("ABook",bookById);
        return "editBook";
    }
    //修改书籍
    @RequestMapping("editBook")
    public String editBook(Books books){
        System.out.println("edit:"+books);
        bookService.editBook(books);
        return "redirect:/book/allBooks";
    }
    //删除
    @RequestMapping("delBook/{bookId}")
    public String delBook(@PathVariable("bookId")int id){
        bookService.delBookById(id);
        return "redirect:/book/allBooks";
    }
    //根据书名查询信息
    @RequestMapping("selectBooks")
    public String selectBooks(String selectBookName,Model model){
        List<Books> booksByName = bookService.getBooksByName(selectBookName);
        model.addAttribute("nameEcho",selectBookName);
        if(booksByName.size()==0){//查到书籍为0进入
            booksByName = bookService.getBooks();
            if(selectBookName!="") {//查询信息不等于空进入
                model.addAttribute("notFind", "未查到");
            }
        }
        model.addAttribute("list",booksByName);
        return "allBooks";
    }
}
```

#### 7.JSP页面编写

##### 1.首页（index.jsp）

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <style>
        a{
            text-decoration: none;
            color: black;
            font-size: 21px;
        }
        h3{
            width: 230px;
            height: 40px;
            margin: 150px auto;
            text-align: center;
            line-height: 38px;
            background: deepskyblue;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<h3>
    <a href="${pageContext.request.contextPath}/book/allBooks">进入书籍页面</a>
</h3>
</body>
</html>
```

##### 2.书籍列表页面（allBooks.jsp）

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>书籍列表————————显示所有书籍</small>
                </h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 column">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增</a>
            </div>
            <div class="col-md-8 column">
                <%--查询书籍--%>
                <form action="${pageContext.request.contextPath}/book/selectBooks" method="post" class="form-inline" style="float: right">
                    <span style="color: red">${notFind}</span>
                    <input type="text" name="selectBookName" class="form-control" value="${nameEcho}" placeholder="请输入要查询的书籍名称">
                    <input type="submit" value="查询" class="btn btn-primary">
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>书籍编号</th>
                    <th>书籍名称</th>
                    <th>书籍数量</th>
                    <th>书籍详情</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <%--书籍从数据库中查询出来，从这个list中遍历出来：foreach--%>
                <tbody>
                <c:forEach items="${list}" var="book">
                    <tr>
                        <td>${book.bookID}</td>
                        <td>${book.bookName}</td>
                        <td>${book.bookCounts}</td>
                        <td>${book.detail}</td>
                        <td><a href="${pageContext.request.contextPath}/book/toEditBook?id=${book.bookID}">修改</a></td>
                        <td><a href="${pageContext.request.contextPath}/book/delBook/${book.bookID}">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
```

##### 3.添加页面（addBook.jsp）

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加页面</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>新增书籍</small>
                </h1>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/book/addBook" method="post">
        <div class="form-group">
            <label>书籍名称</label>
            <input type="text" name="bookName" class="form-control" required>
        </div>
        <div class="form-group">
            <label>书籍数量</label>
            <input type="text" name="bookCounts" class="form-control" required>
        </div>
        <div class="form-group">
            <label>书籍描述</label>
            <input type="text" name="detail" class="form-control" required>
        </div>
        <div class="form-group">
            <input type="submit"  class="form-control" value="添加">
        </div>
    </form>
</div>
</body>
</html>
```

##### 4.修改页面（editBook.jsp）

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改页面</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改书籍</small>
                </h1>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/book/editBook" method="post">
        <input type="hidden" name="bookID" value="${ABook.bookID}" />
        <div class="form-group">
            <label>书籍名称</label>
            <input type="text" name="bookName" class="form-control" value="${ABook.bookName}" required>
        </div>
        <div class="form-group">
            <label>书籍数量</label>
            <input type="text" name="bookCounts" class="form-control" value="${ABook.bookCounts}" required>
        </div>
        <div class="form-group">
            <label>书籍描述</label>
            <input type="text" name="detail" class="form-control" value="${ABook.detail}" required>
        </div>
        <div class="form-group">
            <input type="submit"  class="form-control" value="修改">
        </div>
    </form>
</div>
</body>
</html>

```

**配置Tomcat，进行运行！**

到目前为止，这个SSM项目整合已经完全的OK了，可以直接运行进行测试！这个练习十分的重要，大家需要保证，不看任何东西，自己也可以完整的实现出来！

#### 8.项目截图

![image-20200117212313591](E:\Typora2\笔记图片存放处\image-20200117212313591.png)

![image-20200117212334388](E:\Typora2\笔记图片存放处\image-20200117212334388.png)

![image-20200117212409129](E:\Typora2\笔记图片存放处\image-20200117212409129.png)

![image-20200117212423420](E:\Typora2\笔记图片存放处\image-20200117212423420.png)

![image-20200117212436521](E:\Typora2\笔记图片存放处\image-20200117212436521.png)

![image-20200117212450764](E:\Typora2\笔记图片存放处\image-20200117212450764.png)



# 10.Ajax技术

## 10.1.简介

* AJAX = Asynchronous JavaScript and XML（异步的 JavaScript 和 XML）。
* AJAX 是一种在无需重新加载整个网页的情况下，能够更新部分网页的技术。
* Ajax 不是一种新的编程语言，而是一种用于创建更好更快以及交互性更强的Web应用程序的技术。
* 在 2005 年，Google 通过其 Google Suggest 使 AJAX 变得流行起来。Google Suggest能够自动帮你完成搜索单词。
* Google Suggest 使用 AJAX 创造出动态性极强的 web 界面：当您在谷歌的搜索框输入关键字时，JavaScript 会把这些字符发送到服务器，然后服务器会返回一个搜索建议的列表。
* 就和国内百度的搜索框一样：
* 传统的网页(即不用ajax技术的网页)，想要更新内容或者提交一个表单，都需要重新加载整个网页。
* 使用ajax技术的网页，通过在后台服务器进行少量的数据交换，就可以实现异步局部更新。
* 使用Ajax，用户可以创建接近本地桌面应用的直接、高可用、更丰富、更动态的Web用户界面。

## 10.2.伪造Ajax

我们可以使用前端的一个标签来伪造一个ajax的样子。 iframe标签

```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>iframe测试页面无刷新</title>
        <script>
            function go(){
                //所有的值变量，提前获取
                var url=document.getElementById("url").value;
                document.getElementById("iframe1").src=url;
            }
        </script>
    </head>
    <body>
        <div>
            <p>请输入地址：</p>
            <p>
                <input type="text" id="url" value="https://blog.kuangstudy.com/">
                <input type="button" value="提交" onclick="go()">
            </p>
        </div>
        <div>
            <iframe  id="iframe1" style="width: 100%;height: 450px"></iframe>
        </div>
    </body>
</html>
```

使用IDEA开浏览器测试一下！

#### 利用AJAX可以做：

- 注册，输入用户名自动检测用户是否存在。
- 登陆，提示用户名密码错误
- 删除数据行时，将行ID发送到后台，后台在数据库中删除，数据库删除成功后，在页面DOM中将数据行也删除......

## 10.3.jQuery.ajax

* 纯JS原生实现Ajax我们不去讲解这里，直接使用jquery提供的，方便学习和使用，避免重复造轮子，有兴趣的同学可以去了解下JS原生XMLHttpRequest ！
* Ajax的核心是XMLHttpRequest对象(XHR)。XHR为向服务器发送请求和解析服务器响应提供了接口。能够以异步方式从服务器获取新数据。
* jQuery 提供多个与 AJAX 有关的方法。
* 通过 jQuery AJAX 方法，您能够使用 HTTP Get 和 HTTP Post 从远程服务器上请求文本、HTML、XML 或 JSON – 同时您能够把这些外部数据直接载入网页的被选元素中。
* jQuery 不是生产者，而是大自然搬运工。
* jQuery Ajax本质就是 XMLHttpRequest，对他进行了封装，方便调用！

```jQuery.ajax
jQuery.ajax(...)
       部分参数：
              url：请求地址
             type：请求方式，GET、POST（1.9.0之后用method）
          headers：请求头
             data：要发送的数据
      contentType：即将发送信息至服务器的内容编码类型(默认: "application/x-www-form-urlencoded; charset=UTF-8")
            async：是否异步
          timeout：设置请求超时时间（毫秒）
       beforeSend：发送请求前执行的函数(全局)
         complete：完成之后执行的回调函数(全局)
          success：成功之后执行的回调函数(全局)
            error：失败之后执行的回调函数(全局)
          accepts：通过请求头发送给服务器，告诉服务器当前客户端课接受的数据类型
         dataType：将服务器端返回的数据转换成指定类型
            "xml": 将服务器端返回的内容转换成xml格式
           "text": 将服务器端返回的内容转换成普通文本格式
           "html": 将服务器端返回的内容转换成普通文本格式，在插入DOM中时，如果包含JavaScript标签，则会尝试去执行。
         "script": 尝试将返回值当作JavaScript去执行，然后再将服务器端返回的内容转换成普通文本格式
           "json": 将服务器端返回的内容转换成相应的JavaScript对象
          "jsonp": JSONP 格式使用 JSONP 形式调用函数时，如 "myurl?callback=?" jQuery 将自动替换 ? 为正确的函数名，以执行回调函数
```

##### 我们来个简单的测试，使用最原始的HttpServletResponse处理 , 最简单 , 最通用

1.配置web.xml 和 springmvc的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <!--静态资源过滤-->
    <mvc:default-servlet-handler/>

    <!--视图解析器-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    <!--扫描包-->
    <context:component-scan base-package="cn.wugou.controller"/>

    <!-- 处理请求返回json字符串的乱码问题 -->
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
</beans>
```

2.编写controller

```java
@RequestMapping("a1")
public void a1(String name, HttpServletResponse response) throws IOException {
    System.out.println("name:"+name);
    if("wugou".equals(name)){
        response.getWriter().print(true);
    }else{
        response.getWriter().print(false);
    }
}
```

3.导入jquery ，编写index.jsp测试

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>$Title$</title>
        //导入jquery
        <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-3.4.1.min.js"></script>
        <script>
            function a() {
                $.post({
                    url: "${pageContext.request.contextPath}/a1",
                    data: {"name": $("#username").val()},
                    success: function (data) {
                        alert(data);
                    }
                });
            }
        </script>
    </head>
    <body>
        <%--失去焦点的时候，发起一个请求到后台--%>
        用户名：<input type="text" id="username" onblur="a()"/>
    </body>
</html>
```

4.启动tomcat测试！ 打开浏览器的控制台，当我们鼠标离开输入框的时候，可以看到发出了一个ajax的请求！是后台返回给我们的结果！测试成功！

## 10.4.Springmvc实现

##### 1.实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private  String name;
    private  int age;
    private String sex;
}
```

##### 2.获取一个集合对象，展示到前端

```java
@RequestMapping("a2")
public List<User> a2(){
    List<User> users = new ArrayList<>();
    users.add(new User("wugou1",1,"男"));
    users.add(new User("wugou2",1,"男"));
    return users;
}
```

##### 3.前端页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <script src="${pageContext.request.contextPath}/statics/js/jquery-3.4.1.min.js"></script>
        <script>
            $(function () {
                $("#btn").click(function () {
                    //简写 $.post(url,param[可以省略],success)
                    $.post("${pageContext.request.contextPath}/a2", function (data) {
                        var html = "";
                        for (let i = 0; i < data.length; i++) {
                            html += "<tr>" +
                                "<td>" + data[i].name + "</td>" +
                                "<td>" + data[i].age + "</td>" +
                                "<td>" + data[i].sex + "</td>" +
                                "</tr>"
                        }
                        $("#content").html(html);
                    });
                });
            });
        </script>
    </head>
    <body>
        <input type="button" value="加载数据" id="btn">
        <table>
            <tr>
                <td>姓名</td>
                <td>年龄</td>
                <td>性别</td>
            </tr>
            <tbody id="content"></tbody>
        </table>
    </body>
</html>
```

成功实现了数据回显！可以体会一下Ajax的好处！



## 10.5.注册提示效果

##### 1.写一个Controller

```java
@RequestMapping("a3")
public String a3(String name,String pwd){
    String msg="";
    if(name!=null){
        if("admin".equals(name)){
            msg="ok";
        }else{
            msg="用户名有误";
        }
    }
    if(pwd!=null){
        if("123456".equals(pwd)){
            msg="ok";
        }else{
            msg="密码有误";
        }
    }
    return msg;
}
```

##### 2.前端页面 login.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.4.1.min.js"></script>
    <script>
        function a1() {
            $.post({
               url:"${pageContext.request.contextPath}/a3",
               data:{"name":$("#name").val()},
               success: function (data) {
                   if (data.toString()=='ok'){
                       $("#userInfo").css("color","green");
                   }else{
                       $("#userInfo").css("color","red");
                   }
                   $("#userInfo").html(data);
               }
            });
        }
        function a2() {
            $.post({
                url:"${pageContext.request.contextPath}/a3",
                data:{"pwd":$("#pwd").val()},
                success: function (data) {
                    if (data.toString()=='ok'){
                        $("#pwdInfo").css("color","green");
                    }else{
                        $("#pwdInfo").css("color","red");
                    }
                    $("#pwdInfo").html(data);
                }
            });
        }
    </script>
</head>
<body>
<p>
    用户名：<input type="text" id="name" onblur="a1()">
    <span id="userInfo"></span>
</p>
<p>
    密码：<input type="text" id="pwd" onblur="a2()">
    <span id="pwdInfo"></span>
</p>
</body>
</html>
```

##### 3.测试一下效果

动态请求响应，局部刷新，就是如此！

![image-20200118165331970](E:\Typora2\笔记图片存放处\image-20200118165331970.png)

# 11.拦截器

SpringMVC的处理器拦截器类似于Servlet开发中的过滤器Filter,用于对处理器进行预处理和后处理。开发者可以自己定义一些拦截器来实现特定的功能。

**过滤器与拦截器的区别：**拦截器是AOP思想的具体应用。

**过滤器**

- servlet规范中的一部分，任何java web工程都可以使用
- 在url-pattern中配置了/*之后，可以对所有要访问的资源进行拦截

**拦截器**

- 拦截器是SpringMVC框架自己的，只有使用了SpringMVC框架的工程才能使用
- 拦截器只会拦截访问的控制器方法， 如果访问的是jsp/html/css/image/js是不会进行拦截的

### 验证用户是否登录 (认证用户)

##### 思路：

1.有一个登陆页面，需要写一个controller访问页面。

2.登陆页面有一提交表单的动作。需要在controller中处理。判断用户名密码是否正确。如果正确，向session中写入用户信息。*返回登陆成功。*

3.拦截用户请求，判断用户是否登陆。如果用户已经登陆。放行， 如果用户未登陆，跳转到登陆页面

##### 代码

1.登录页面login.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>登录页面</h1>
<form action="${pageContext.request.contextPath}/user/login" method="post">
    用户名：<input type="text" name="username">
    密码：<input type="text" name="password">
    <input type="submit" value="提交">
</form>
</body>
</html>
```

2.controller

```java
@Controller
@RequestMapping("user")
public class LoginController {
    @RequestMapping("main")
    public String main(){
        return "main";
    }
    @RequestMapping("toLogin")
    public String login(){
        return "login";
    }
    @RequestMapping("login")
    public String login(String username, String password, HttpSession session){
        //把用户得到信息存在session
        session.setAttribute("loginName",username);
        return "success";
    }
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginName");
        return "login";
    }
}
```

3.登陆成功的页面 success.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h1>${loginName},登录成功</h1>
        <a href="${pageContext.request.contextPath}/user/logout">注销</a>
    </body>
</html>
```

4.在 index 页面上测试跳转！启动Tomcat 测试，未登录也可以进入主页！

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>$Title$</title>
    </head>
    <body>
        <h1><a href="${pageContext.request.contextPath}/user/toLogin">登录页面</a></h1>
        <h1><a href="${pageContext.request.contextPath}/user/main">首页</a></h1>
    </body>
</html>
```

5.编写用户登录拦截器

```java
public class LoginIntercepter implements HandlerInterceptor {
    //return true;执行下一个拦截器，放行
    //return false;不执行下一个拦截器，放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        //用户名不为空放行
        if(session.getAttribute("loginName")!=null){
            return true;
        }
        //没有登录跳转到登录页面
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
        return false;
    }
}
```

6.在Springmvc的配置文件中注册拦截器

```xml
<mvc:interceptors>
    <mvc:interceptor>
        <!--包括这个请求下面的所有的请求-->
        <mvc:mapping path="/user/**/"/>
        <!--设置直接放行路径-->
        <mvc:exclude-mapping path="/user/login" />
        <mvc:exclude-mapping path="/user/toLogin" />
        <bean class="cn.wugou.config.LoginIntercepter" />
    </mvc:interceptor>
</mvc:interceptors>
```

# 12.文件上传和下载

 文件上传是项目开发中最常见的功能之一 ,springMVC 可以很好的支持文件上传，但是SpringMVC上下文中默认没有装配MultipartResolver，因此默认情况下其不能处理文件上传工作。如果想使用Spring的文件上传功能，则需要在上下文中配置MultipartResolver。

 前端表单要求：为了能上传文件，必须将表单的method设置为POST，并将enctype设置为multipart/form-data。浏览器才会把用户选择的文件以二进制数据发送给服务器；

**对表单中的 enctype 属性做个详细的说明：**

- application/x-www=form-urlencoded：默认方式，只处理表单域中的 value 属性值，采用这种编码方式的表单会将表单域中的值处理成 URL 编码方式。
- multipart/form-data：这种编码方式会以二进制流的方式来处理表单数据，这种编码方式会把文件域指定文件的内容也封装到请求参数中，不会对字符编码。
- text/plain：除了把空格转换为 "+" 号外，其他字符都不做编码处理，这种方式适用直接通过表单发送邮件。

```jsp
<form action="" enctype="multipart/form-data" method="post">
    <input type="file" name="file"/>
    <input type="submit">
</form>
```

一旦设置了enctype为multipart/form-data，浏览器即会采用二进制流的方式来处理表单数据，而对于文件上传的处理则涉及在服务器端解析原始的HTTP响应。在2003年，Apache Software Foundation发布了开源的Commons FileUpload组件，其很快成为Servlet/JSP程序员上传文件的最佳选择。

- Servlet3.0规范已经提供方法来处理文件上传，但这种上传需要在Servlet中完成。
- 而Spring MVC则提供了更简单的封装。
- Spring MVC为文件上传提供了直接的支持，这种支持是用即插即用的MultipartResolver实现的。
- Spring MVC使用Apache Commons FileUpload技术实现了一个MultipartResolver实现类：CommonsMultipartResolver。因此，==SpringMVC的文件上传还需要依赖Apache Commons FileUpload的组件==。

## 12.1.文件上传

导入文件上传的jar包，commons-fileupload ， Maven会自动帮我们导入他的依赖包 commons-io包；

```xml
<!--文件上传-->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.3.3</version>
</dependency>
<!--servlet-api导入高版本的-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
</dependency>
```

配置bean：multipartResolver

这个bena的id必须为：multipartResolver 

```xml
<!--文件上传配置-->
<bean id="multipartResolver"  class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <!-- 请求的编码格式，必须和jSP的pageEncoding属性一致，以便正确读取表单的内容，默认为ISO-8859-1 -->
    <property name="defaultEncoding" value="utf-8"/>
    <!-- 上传文件大小上限，单位为字节（10485760=10M） -->
    <property name="maxUploadSize" value="10485760"/>
    <property name="maxInMemorySize" value="40960"/>
</bean>
```

CommonsMultipartFile 的 常用方法：

- String getOriginalFilename()：获取上传文件的原名
- InputStream getInputStream()：获取文件流
- void transferTo(File dest)：将上传文件保存到一个目录文件中

## 12.2.代码多，看项目源码

##### 文件上传

* 导入文件上传的jar包，commons-fileupload ， Maven会自动帮我们导入他的依赖包 commons-io包；
* 配置bean：multipartResolver
* 编写前端页面
* Controller
* 测试上传文件，OK！

##### 采用file.Transto 来保存上传的文件

* 编写Controller
* 前端表单提交地址修改
* 访问提交测试，OK！

##### 文件下载

* 设置 response 响应头
* 读取文件 -- InputStream
* 写出文件 -- OutputStream
* 执行操作
* 关闭流 （先开后关）
* 前端

测试，文件下载OK，大家可以和我们之前学习的JavaWeb原生的方式对比一下，就可以知道这个便捷多了!





