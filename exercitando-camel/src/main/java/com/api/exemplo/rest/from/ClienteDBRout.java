package com.api.exemplo.rest.from;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.Constant;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.api.exemplo.bean.TratarErroResponse;
import com.api.exemplo.bean.TratarRetornoDBBean;
import com.api.exemplo.processor.ClienteValidateProcess;
import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class ClienteDBRout extends RouteBuilder{
   public static final String DIRECT_ROUT = "direct:cliente_db_rout"; 
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from(DIRECT_ROUT)
		.routeId("id_rout_db_cliente")
		.doTry()
			.process(new ClienteValidateProcess())
			.to("sql-stored:SP_TESTE_2(INTEGER ${headers.id} )")
			.bean(new TratarRetornoDBBean(), "convertDadosRetorno")
		.endDoTry()
		//tratar exceção (forma - 2)
		.doCatch(Exception.class)
			.bean(new TratarErroResponse(), "erroResponse")
			.marshal().json(JsonLibrary.Jackson)
		.end()
		.endRest();
	}

	
}
