package mx.com.springboot.app.items.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mx.com.springboot.app.items.models.Producto;

@FeignClient(name = "servicio-productos")
public interface IProductoClienteRest {

	@GetMapping("/listar")
	public List<Producto> listar();

	@GetMapping("/ver/{id}")
	public Producto findById(@PathVariable Long id);
}
