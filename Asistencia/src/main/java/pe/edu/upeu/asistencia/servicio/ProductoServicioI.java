package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoServicioI {
    
    Producto save(Producto producto);
    List<Producto> findAll();
    Optional<Producto> findById(Long id);
    Optional<Producto> findByCodigo(String codigo);
    Producto update(Long id, Producto producto);
    void delete(Long id);
}
