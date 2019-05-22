package com.api.exemplo.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import com.api.exemplo.bean.TratarErroResponse;
import com.api.exemplo.dominio.Cliente;
import com.api.exemplo.dominio.Response;
import com.api.exemplo.rest.from.ClienteDBRout;
import com.api.exemplo.rest.from.ClienteEnderecoDBRout;

@Component
public class ClienteRest extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		restConfiguration()
		.component("restlet")
		.bindingMode(RestBindingMode.json)
		.dataFormatProperty("prettyPrint", "true")
		.host("localhost")
		.port("8090")
		.apiProperty("cors", "true"); //define se a aplicação vai ser visivel para outras
		//tratar exceção (forma - 1)
//		onException(Exception.class).handled(true).bean(new TratarErroResponse(), "erroResponse").marshal().json(JsonLibrary.Jackson);
		rest("/cliente")
			.description("Consultar Clientes")
			.consumes("application/json")
			.produces("application/json")
			.get("/{id}")
//				.outType(Response.class)
				.to(ClienteDBRout.DIRECT_ROUT);
		rest("/cliente/endereco")
			.description("Retorna os dados da pessoa + endereço")
			.consumes("application/json")
			.produces("application/json")
			.get("/{id}")
				.to(ClienteEnderecoDBRout.DIRECT_ROUT);
	}
	
}
