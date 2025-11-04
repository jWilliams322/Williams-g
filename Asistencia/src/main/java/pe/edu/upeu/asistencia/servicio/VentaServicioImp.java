package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Venta;
import pe.edu.upeu.asistencia.repositorio.VentaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServicioImp implements VentaServicioI {
    
    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public Venta save(Venta venta) {
        System.out.println("Registrando venta: " + venta.getNumeroVenta());
        System.out.println("   Cliente: " + (venta.getCliente() != null ? venta.getCliente().getNombre() : "N/A"));
        System.out.println("   Total: $" + venta.getTotal());
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> findAll() {
        System.out.println("Obteniendo todas las ventas");
        return ventaRepository.findAll();
    }

    @Override
    public Optional<Venta> findById(Long id) {
        System.out.println("Buscando venta por ID: " + id);
        return ventaRepository.findById(id);
    }

    @Override
    public Optional<Venta> findByNumeroVenta(String numeroVenta) {
        System.out.println("Buscando venta por número: " + numeroVenta);
        return ventaRepository.findByNumeroVenta(numeroVenta);
    }

    @Override
    public Venta update(Long id, Venta venta) {
        System.out.println("Actualizando venta con ID: " + id);
        venta.setId(id);
        return ventaRepository.save(venta);
    }

    @Override
    public void delete(Long id) {
        System.out.println("Eliminando venta con ID: " + id);
        ventaRepository.deleteById(id);
    }

    @Override
    public double calcularTotalVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        double total = ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();
        System.out.println("Total de ventas: $" + String.format("%.2f", total));
        return total;
    }
}
