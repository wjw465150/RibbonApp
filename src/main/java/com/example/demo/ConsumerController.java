package com.example.demo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	RestTemplate restTemplate;

	@Value("${my.servicename}")
	String servicename;

	BasicAuthRestTemplate aa;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		//@wjw_note: 在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名
		String result = restTemplate.getForEntity("http://" + servicename + "/add?a=10&b=20", String.class).getBody();
		logger.info("result:" + result);
		return result;
	}
}
