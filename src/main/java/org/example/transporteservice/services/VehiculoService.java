package org.example.transporteservice.services;

import org.example.transporteservice.models.vehiculos.Vehiculo;
import org.example.transporteservice.resositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    // Crear un nuevo vehículo
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    // Obtener todos los vehículos
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return vehiculoRepository.findAll();
    }

    // Obtener un vehículo por ID
    public Optional<Vehiculo> obtenerVehiculoPorId(String id) {
        return vehiculoRepository.findById(id);
    }

    // Actualizar un vehículo
    public Vehiculo actualizarVehiculo(String id, Vehiculo vehiculo) {
        if (vehiculoRepository.existsById(id)) {
            vehiculo.setId(id);
            return vehiculoRepository.save(vehiculo);
        } else {
            return null; // O manejar de otra forma si el vehículo no existe
        }
    }

    // Eliminar un vehículo por ID
    public void eliminarVehiculo(String id) {
        vehiculoRepository.deleteById(id);
    }
}
