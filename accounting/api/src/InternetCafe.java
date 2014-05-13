import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class InternetCafe {
	 public static void main(String[] args) throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("springAop.xml");
		Customer customer = (Customer) ctx.getBean("customerProxy");
		customer.browse();
	}
}
