package mx.com.springboot.app.items.models.service;

import java.util.List;

import mx.com.springboot.app.items.models.Item;
import mx.com.springboot.app.items.models.Producto;

public interface IItemService {
	List<Item> findAll();
	Item findById(Long id, Integer cantidad);
	Producto save(Producto producto);
	Producto update(Long id, Producto producto);
	void delete(Long id);
}
