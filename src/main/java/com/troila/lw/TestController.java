package com.troila.lw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

/**
 * 這個類中所有抓取並獲取服務列表的操作，默認都是獲取本地的
 * 若想獲取服務器端提供的所有服務列表，需要在配置文件中配置registry-fetch-interval-seconds這個參數。以及時間
 * @author liwei
 *
 */
@RestController
@Configuration
public class TestController {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@GetMapping("/router")
	@ResponseBody
	public String router() {
		RestTemplate rtl = getRestTemplate();
		String json = rtl.getForObject("http://ek-provider/call/10", String.class);
		return json;
	}
	
	@GetMapping("/list")
	@ResponseBody
	public String serviceCount() {
		//獲取eureka服務端提供的所有服務的列表（注意是所有服務，也就是服務的種類，而非服務的實例）
		//相當於對所有服務做了一次distinct。
		//下面的那個方法getMeteData是獲取所有某個服務的所有實例的。
		List<String> names = discoveryClient.getServices();
		for(String serviceId : names) {
			List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
			System.out.println(serviceId + " : " + instances.size());
		}
		return null;
	}
	
	@GetMapping("/mete")
	@ResponseBody
	public String getMeteData() {
		String names = "";
		//獲取eureka中某個服務提供者的所有實例（因為某個服務提供者可能是多副本的）
		//實例名中，填寫的是服務提供者的應用名。
		//本例中，ek-provider就是服務提供者的服務名，而companyName就是服務提供者的配置文件中的一個屬性
		//這個屬性比較特殊，是元數據。貌似用來定義表的字段名
		List<ServiceInstance> instances = discoveryClient.getInstances("ek-provider");
		for(ServiceInstance instance : instances) {
//			names += instance.getMetadata().get("companyName");
			names += instance.getMetadata();
		}
		
		return names;
	}
	
	//自動注入一個負載均衡的客戶端
	@Autowired
	private LoadBalancerClient client;
	
	@RequestMapping(value = "/lb" ,method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceInstance lb() {
		//這裡返回的是一個服務實例
		//在分佈式應用中，一個服務（工程）會部署多個實例在運行，這裡的LoadBalancerClient會幫我們選取一個合適的實例返回給我們使用
		//可以將返回的服務實例看做這個工程的一個個的副本
		ServiceInstance sl = client.choose("ek-provider");
		return sl;
	}
	
	@Autowired
	private SpringClientFactory factory;
	
	@RequestMapping(value = "/factory" ,method = RequestMethod.GET )
	public String factory() {
//		ILoadBalancer lb = factory.getLoadBalancer("default");
		ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer)factory.getLoadBalancer("default");
		System.out.println(lb.getClass().getName());
		System.out.println(lb.getRule().getClass().getName());
		
		return lb.getClass().getName() + "==" + lb.getRule().getClass().getName();
	}
}
