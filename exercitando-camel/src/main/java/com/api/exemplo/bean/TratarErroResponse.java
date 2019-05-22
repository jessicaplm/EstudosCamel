package com.api.exemplo.bean;

import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.api.exemplo.dominio.ErroResponse;

@Component
public class TratarErroResponse {

	public void erroResponse(Exchange exchange) {
		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		exchange.getIn().getBody(ErroResponse.class);
		ErroResponse erro = new ErroResponse();
		erro.setCodigo(HttpStatus.BAD_REQUEST.toString());
		erro.setMensagem(e.getMessage());
		exchange.getIn().setBody(erro);
	}
	
}
