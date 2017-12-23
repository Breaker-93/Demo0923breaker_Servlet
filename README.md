# 一、What.
    Servlet 为何物？两种配置方式是什么？
Servlet狭义指的就是一个叫做“Servlet”的接口，广义指实现了这个接口的Java程序，运行在服务端，用来处理任何类型的请求、扩展基于HTTP协议的Web服务器。
 两种配置方式为XML标签式和注解式。
# 二、Why.
      编写Servlet为什么还要配置？
当我们要开发编写一个针对请求的处理类，只实现Servlet接口是不行的。就像你造的酒很香，但是客人不知道去哪买一样。这是你要贴出标语、路标作为指示说明。类似的，你编写的“香喷喷”的Servlet实现类，也要配置说明下（“打个广告”），让外来的请求能够找到对应的处理类。
# 三、How.
     两种方式如何配置？
知识点其实不多，但为了让我们学习知识更有兴趣，我们每练习一个Demo应该给它赋予一个场景需求。
本次Demo练习，我们用两种配置方式做一个Servlet相关的查看兑奖号码的例子。
##1、XML标签式配置.
在Servlet 3.0版本之前，Servlet的配置说明只能通过在WEB-INF  目录下的web.xml文件中添加标签的形式。
```
<web-app>
      <servlet>
        <!--servlet的昵称、代号，共下文中的引用-->
		<servlet-name>CheckNumber</servlet-name>

        <!--servlet对应的类文件，包名+类名-->
		<servlet-class>com.breaker.servlet.CheckServlet</servlet-class>

        <!--servlet内部的初始化参数，非必需配置-->
		<init-param>
			<param-name>user</param-name>
			<param-value>Breaker93</param-value>
		</init-param>
		<init-param>
			<param-name>my_jianshu_url</param-name>
			<param-value>http://www.jianshu.com/u/187408ee66d4</param-value>
		</init-param>

		<!--通知Servlet容器（“tomcat”）在启动的时候初始化创建该Servlet的实例，非必需配置 -->
		<load-on-startup>1</load-on-startup>

	</servlet>


	<servlet-mapping>

        <!--为代号为“CheckNumber”的Servlet设置浏览器访问地址(要与上文中的Serlet
        的代号名称一致，否则会找不到的)，
        例如http://192.168.0.100:8080/DemoProject/checkNumber -->
		<servlet-name>CheckNumber</servlet-name>

        <!--此处的名称与servlet-name的值无关，随便设置的，
                  只为了浏览器中请求方便 -->
		<url-pattern>/checkNumber</url-pattern>

	</servlet-mapping>
</web-app>
```

此种配置中的Servlet实现类是这样编写的：
```
//HttpServlet继承了GenericServlet类，GenericServlet继承了Servlet接口，
//他们都是Servlet API提供，非个人编写的；所以CheckServlet是间接实现的Servlet接口。
public class CheckServlet extends HttpServlet{
	
    //处理get请求，在此方法内编写处理逻辑。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("我的简书链接："+getServletConfig().getInitParameter("my_jianshu_url"));
		String[] number=req.getParameterValues("number");
		for(int i=0;i<7;i++){
			System.out.print(number[i]);
		}
		System.out.println("---------");
		String[] awardNum=new String[7];
		Random random=new Random();
		for(int i=0;i<7;i++){			
			awardNum[i] = random.nextInt(10)+"";
			System.out.print(awardNum[i]);
		}
		System.out.println("------");
		
		int count = 0,max = 0,position = 0;
		for(int i= 0;i<awardNum.length;i++){
			if(awardNum[i].equals(number[i])){
				count++;
				if (count>max) {
					position=i;
					max=count;
				}
			}else{
				count=0;
			}
		}
		req.setAttribute("number", number);
		req.setAttribute("awardNum", awardNum);
		req.setAttribute("bool", "");
		req.setAttribute("begin",8 );
		req.setAttribute("end", 8);
		String[] result = {"六等奖","五等奖","四等奖","三等奖","二等奖","一等奖"};
		if(max >1){
			req.setAttribute("bool", true);
			req.setAttribute("result","恭喜你中了" +result[max-2]);
			req.setAttribute("begin", position-max+1);
			req.setAttribute("end", position);
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("/showResult.jsp"); 
		dispatcher.forward(req, resp); 
	}
	//处理post请求，在此方法内编写处理逻辑。
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		System.out.println("doPost() executed");
	}
	
}
```
Jsp页面中是这样请求的Servlet：
```
//checkNumber 与<url-pattern>/checkNumber</url-pattern>对应一致才能请求到；
//此种请求等同于action="http://127.0.0.1:8080/DemoProject/checkNumber"
<form  action="checkNumber">
	...
    ...	
</form>
```

##2、注解式.
在Servlet 3.0版本之后，WEB-INF路径下的web.xml文件不再是必需的，但通常还是建议保留。提供的注解(annotation)，使得不再需要在web.xml文件中进行Servlet的部署描述，简化开发流程。
>开发Servlet 3.0的程序需要一定的环境支持。Servlet3是Java EE6规范的一部分,Tomcat需要Tomcat7才支持Java EE6，Tomcat7需要使用JDK6。

直接在Servlet实现类里配置：
```
//HttpServlet继承了GenericServlet类，GenericServlet继承了Servlet接口，
//他们都是Servlet API提供，非个人编写的；所以CheckServlet是间接实现的Servlet接口。

/*@WebServlet将一个继承于javax.servlet.http.HttpServlet的类定义为Servlet组件。
*　  @WebServlet有很多的属性：
*　  asyncSupported：   声明Servlet是否支持异步操作模式。
*　　description：　　  Servlet的描述。
*　　displayName：     Servlet的显示名称。
*　　initParams：        Servlet的init参数。
*　　name：　　　　    Servlet的名称。
*　　urlPatterns：　　  Servlet的访问URL。
*　　value：　　　       Servlet的访问URL。
*
*　　Servlet的访问URL是Servlet的必选属性，可以选择使用urlPatterns或者value定义。
*   例如：@WebServlet(name="AnnotationServlet",value="/AnnotationServlet")。
*
*　　也可以定义多个URL访问：
*　　如@WebServlet(name="AnnotationServlet",urlPatterns={"/AnnotationServlet","/AnnotationServlet2"})
*　　或者@WebServlet(name="AnnotationServlet",value={"/AnnotationServlet","/AnnotationServlet2"})
*/
@WebServlet(name="CheckNumber",urlPatterns={"/checkNumber"},initParams= {@WebInitParam(name = "my_jianshu_url", value = "http://www.jianshu.com/u/187408ee66d4")},loadOnStartup = 1)
public class CheckServlet extends HttpServlet{
	
    //处理get请求，在此方法内编写处理逻辑。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("我的简书链接："+getServletConfig().getInitParameter("my_jianshu_url"));
		String[] number=req.getParameterValues("number");
		for(int i=0;i<7;i++){
			System.out.print(number[i]);
		}
		System.out.println("---------");
		String[] awardNum=new String[7];
		Random random=new Random();
		for(int i=0;i<7;i++){
			
			awardNum[i] = random.nextInt(10)+"";
			System.out.print(awardNum[i]);
		}
		System.out.println("------");
		
		int count = 0,max = 0,position = 0;
		for(int i= 0;i<awardNum.length;i++){
			if(awardNum[i].equals(number[i])){
				count++;
				if (count>max) {
					position=i;
					max=count;
				}
			}else{
				count=0;
			}
		}
		req.setAttribute("number", number);
		req.setAttribute("awardNum", awardNum);
		req.setAttribute("bool", "");
		req.setAttribute("begin",8 );
		req.setAttribute("end", 8);
		String[] result = {"六等奖","五等奖","四等奖","三等奖","二等奖","一等奖"};
		if(max >1){
			req.setAttribute("bool", true);
			req.setAttribute("result","恭喜你中了" +result[max-2]);
			req.setAttribute("begin", position-max+1);
			req.setAttribute("end", position);
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("/showResult.jsp"); 
		dispatcher.forward(req, resp); 
	}
	//处理post请求，在此方法内编写处理逻辑。
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		System.out.println("doPost() executed");
	}
	
}
```
# 四、Summary.
- 两种配置方式不要混用，避免出现同时配置@WebServlet注解标记和web.xml中<servlet-name>等标签的现象。
- 两种配置方式比较：
  XML配置形式，在需要修改Servlet的配置信息（比如初始化参数的值）时，只需在服务器访问目录直接修改web.xml文件而不需要修改java文件然后再重新编译成class文件；
  但是当web.xml文件中出现大量的Servlet配置时，就会显得冗杂，而注解方式让配置更方便、简练。
  
>本节文章来源：http://www.jianshu.com/writer#/notebooks/16799351/notes/17659490/preview
>
>（欢迎关注个人简书，精彩文章等你来品。）

