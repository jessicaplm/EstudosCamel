package com.api.exemplo.rest.from;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.api.exemplo.bean.TratarErroResponse;
import com.api.exemplo.bean.TratarRetornoDBBean;
import com.api.exemplo.processor.ClienteValidateProcess;
@Component
public class ClienteEnderecoDBRout extends RouteBuilder {

	public static final String DIRECT_ROUT = "direct:cliente_endereco_db_rout"; 
	@Override
	public void configure() throws Exception {
	
				from(DIRECT_ROUT)
				.routeId("id_rout_db_cliente_endereco")
				.doTry()
					.process(new ClienteValidateProcess())
					.to("sql-stored:SP_TESTE_3(INTEGER ${headers.id} )")
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
