package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteServicioI {
    
    Cliente save(Cliente cliente);
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByCedula(String cedula);
    Cliente update(Long id, Cliente cliente);
    void delete(Long id);
}
