package com.api.exemplo.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
@Component
public class ClienteValidateProcess implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		String id = exchange.getIn().getHeader("id").toString();
		
		if (!isNum(id)) {
			throw new Exception(" Informar um Id numérico");
		}
	}

	private boolean isNum(String id) {
		try {
			Integer.parseInt(id);
			
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
