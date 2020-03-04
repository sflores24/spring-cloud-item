package mx.com.springboot.app.items.models.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.springboot.app.commons.models.entities.Producto;
import mx.com.springboot.app.items.models.Item;
import mx.com.springboot.app.items.models.service.IItemService;

@Service("serviceRestTemp")
public class ItemServiceImpl implements IItemService {

	@Autowired
	@Qualifier("cteProductoRest")
	private RestTemplate cteRest;

	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays
				.asList(cteRest.getForObject("http://servicio-productos/listar", Producto[].class));
		return productos.stream().map(prod -> new Item(prod, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		Producto producto = cteRest.getForObject("http://servicio-productos/ver/{id}", Producto.class,
				getIdPathVariableInMap(id));
		return new Item(producto, cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		HttpEntity<Producto> bodyRequest = new HttpEntity<Producto>(producto);

		ResponseEntity<Producto> response = cteRest.exchange("http://servicio-productos/crear", HttpMethod.POST,
				bodyRequest, Producto.class);

		return getProductoFromResponse(response);
	}

	private Producto getProductoFromResponse(ResponseEntity<Producto> response) {
		return response.getBody();
	}

	private Map<String, String> getIdPathVariableInMap(Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());

		return pathVariables;
	}

	@Override
	public Producto update(Long id, Producto producto) {
		HttpEntity<Producto> bodyRequest = new HttpEntity<Producto>(producto);

		ResponseEntity<Producto> response = cteRest.exchange("http://servicio-productos/editar/{id}", HttpMethod.PUT,
				bodyRequest, Producto.class, getIdPathVariableInMap(id));

		return getProductoFromResponse(response);
	}

	@Override
	public void delete(Long id) {
		cteRest.delete("http://servicio-productos/eliminar/{id}", getIdPathVariableInMap(id));
	}

}
