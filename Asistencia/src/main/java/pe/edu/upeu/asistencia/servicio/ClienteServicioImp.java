package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Cliente;
import pe.edu.upeu.asistencia.repositorio.ClienteRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImp implements ClienteServicioI {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente save(Cliente cliente) {
        System.out.println("Guardando cliente: " + cliente.getNombre());
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        System.out.println("Obteniendo todos los clientes");
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        System.out.println("Buscando cliente por ID: " + id);
        return clienteRepository.findById(id);
    }

    @Override
    public Optional<Cliente> findByCedula(String cedula) {
        System.out.println("Buscando cliente por cédula: " + cedula);
        return clienteRepository.findByCedula(cedula);
    }

    @Override
    public Cliente update(Long id, Cliente cliente) {
        System.out.println("Actualizando cliente con ID: " + id);
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(Long id) {
        System.out.println("Eliminando cliente con ID: " + id);
        clienteRepository.deleteById(id);
    }
}
