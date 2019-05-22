package com.api.exemplo.bean;

import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.api.exemplo.dominio.Cliente;
import com.api.exemplo.dominio.Response;

@Component
public class TratarRetornoDBBean {

	public void convertDadosRetorno(Exchange exchange) {
		Map<String, Object> mapBody = exchange.getIn().getBody(Map.class);

		// Filtrar apenas os -resultset
		mapBody = mapBody.entrySet().stream().filter(x -> x.getKey().startsWith("#result") && x.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		Response response = new Response();
		List<Cliente> listClientes = new ArrayList<>();
		// Concatenar todos os -resultset da procedure
		for (Object clienteMap : mapBody.values()) {
			listClientes.addAll((Collection<? extends Cliente>) clienteMap);
		}

		response.setClientes(listClientes);

		exchange.getIn().setBody(response);

	}

}
