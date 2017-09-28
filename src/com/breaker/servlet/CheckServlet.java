package com.breaker.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*@WebServlet��һ���̳���javax.servlet.http.HttpServlet���ඨ��ΪServlet�����
*��  @WebServlet�кܶ�����ԣ�
*��  asyncSupported��   ����Servlet�Ƿ�֧���첽����ģʽ��
*����description������  Servlet��������
*����displayName��     Servlet����ʾ���ơ�
*����initParams��        Servlet��init������
*����name����������    Servlet�����ơ�
*����urlPatterns������  Servlet�ķ���URL��
*����value��������       Servlet�ķ���URL��
*
*����Servlet�ķ���URL��Servlet�ı�ѡ���ԣ�����ѡ��ʹ��urlPatterns����value���塣
*   ���磺@WebServlet(name="AnnotationServlet",value="/AnnotationServlet")��
*
*����Ҳ���Զ�����URL���ʣ�
*������@WebServlet(name="AnnotationServlet",urlPatterns={"/AnnotationServlet","/AnnotationServlet2"})
*��������@WebServlet(name="AnnotationServlet",value={"/AnnotationServlet","/AnnotationServlet2"})
*/

public class CheckServlet extends HttpServlet{
	private int count=0;
	private ServletConfig serConfig;
	/*@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("init() executed");
		
	}*/
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
		super.init(config);
		System.out.println("init------config"+config.getInitParameter("user"));
		this.serConfig = config;
//		System.out.println("init(ServletConfig config) executed");
//		System.out.println("init"+this.getInitParameter("user"));
//	
//		System.out.println("init(ServletConfig config):"+config.getInitParameter("my_jianshu_url"));
		
	}
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("service(ServletRequest req, ServletResponse res) execute started");
		super.service(req, res);
		System.out.println("service(ServletRequest req, ServletResponse res) executed");
		
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("--------------"+this.serConfig.getInitParameter("my_jianshu_url"));
		System.out.println("service(HttpServletRequest req, HttpServletResponse resp) execute started");
		System.out.println("service"+this.getInitParameter("my_jianshu_url"));
		super.service(req, resp);
		System.out.println("service(HttpServletRequest req, HttpServletResponse resp) executed");
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet"+getServletConfig().getInitParameter("my_jianshu_url"));
		count+=1;
		System.out.println("doGet() executed"+"---"+count);
		

		String[] number=req.getParameterValues("number");
		for(int i=0;i<7;i++){
			
			System.out.print(number[i]);
		}
		System.out.println("---------");
		String[] awardNum=new String[7];
		Random random=new Random();
		for(int i=0;i<7;i++){
			
//			awardNum[i] = random.nextInt(10)+"";
			awardNum[i] = "0";
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
		String[] result = {"���Ƚ�","��Ƚ�","�ĵȽ�","���Ƚ�","���Ƚ�","һ�Ƚ�"};
		if(max >1){
			req.setAttribute("bool", true);
			req.setAttribute("result","��ϲ������" +result[max-2]);
			req.setAttribute("begin", position-max+1);
			req.setAttribute("end", position);
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("/showResult.jsp"); 
		dispatcher.forward(req, resp); 
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		System.out.println("doPost() executed");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("destroy() executed");
	}
	
	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		System.out.println("getServletConfig() executed");
		
		return super.getServletConfig();
		
	}
	
	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		System.out.println("getServletInfo() executed");
		System.out.println("ServletInfo:"+super.getServletInfo());
		return super.getServletInfo();
	}
	
	
}
