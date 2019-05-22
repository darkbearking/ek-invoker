package com.troila.lw;

import java.util.List;
import java.util.Random;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

/**
 * 自定義規則類，實現了IRule接口
 * @author liwei
 *
 */
public class MyRule implements IRule {

	private ILoadBalancer lb;

	/**
	 * 當隨機數大於7的情況下，我們才讓8080端口的那個服務幹活兒，否則就讓8081那個干
	 */
	public Server choose(Object key) {
		Random r = new Random();
		int rNum = r.nextInt(10);
		
		List<Server> servers = lb.getAllServers();
		if(rNum > 7 ) {
			return getServerByPort( servers , 8080);
		}
		
		return getServerByPort( servers , 8081);
	}
	
	/**
	 * 自定義方法，獲取當前服務的端口
	 * @param servers
	 * @param port
	 * @return
	 */
	private Server getServerByPort(List<Server> servers ,int port) {
		for(Server s : servers) {
			if(s.getPort() == port)
				return s;
		}
		return null;
	}

	public void setLoadBalancer(ILoadBalancer lb) {
		this.lb = lb;
	}

	public ILoadBalancer getLoadBalancer() {
		return null;
	}

}
