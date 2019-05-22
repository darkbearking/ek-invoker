package com.troila.lw;

import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;

public class MyConfig {

	@Bean
	public IRule getRule() {
		return new MyRule();
	}
}
