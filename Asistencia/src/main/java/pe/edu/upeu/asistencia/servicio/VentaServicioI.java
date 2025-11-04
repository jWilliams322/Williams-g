package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Venta;
import java.util.List;
import java.util.Optional;

public interface VentaServicioI {
    
    Venta save(Venta venta);
    List<Venta> findAll();
    Optional<Venta> findById(Long id);
    Optional<Venta> findByNumeroVenta(String numeroVenta);
    Venta update(Long id, Venta venta);
    void delete(Long id);
    double calcularTotalVentas();
}
