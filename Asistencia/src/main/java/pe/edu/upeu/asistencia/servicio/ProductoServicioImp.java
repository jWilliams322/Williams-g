package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Producto;
import pe.edu.upeu.asistencia.repositorio.ProductoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicioImp implements ProductoServicioI {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        System.out.println("Guardando producto: " + producto.getNombre());
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> findAll() {
        System.out.println("Obteniendo todos los productos");
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        System.out.println("Buscando producto por ID: " + id);
        return productoRepository.findById(id);
    }

    @Override
    public Optional<Producto> findByCodigo(String codigo) {
        System.out.println("Buscando producto por código: " + codigo);
        return productoRepository.findByCodigo(codigo);
    }

    @Override
    public Producto update(Long id, Producto producto) {
        System.out.println("Actualizando producto con ID: " + id);
        producto.setId(id);
        return productoRepository.save(producto);
    }

    @Override
    public void delete(Long id) {
        System.out.println("Eliminando producto con ID: " + id);
        productoRepository.deleteById(id);
    }
}
