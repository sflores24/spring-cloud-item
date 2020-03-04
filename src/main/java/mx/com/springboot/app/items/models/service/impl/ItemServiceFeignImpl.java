package mx.com.springboot.app.items.models.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import mx.com.springboot.app.items.clientes.IProductoClienteRest;
import mx.com.springboot.app.items.models.Item;
import mx.com.springboot.app.items.models.Producto;
import mx.com.springboot.app.items.models.service.IItemService;

@Service("serviceFeign")
@Primary //Este porque hay dos servicios con la implementeación de IItemService
public class ItemServiceFeignImpl implements IItemService {

	@Autowired
	IProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {
		return clienteFeign.listar().stream().map(prod -> new Item(prod, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(clienteFeign.findById(id), cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		return clienteFeign.crear(producto);
	}

	@Override
	public Producto update(Long id, Producto producto) {
		return clienteFeign.editar(id, producto);
	}

	@Override
	public void delete(Long id) {
		clienteFeign.eliminar(id);
	}

}
