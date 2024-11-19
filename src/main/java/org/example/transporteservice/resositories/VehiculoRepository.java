package org.example.transporteservice.resositories;

import org.example.transporteservice.models.vehiculos.Vehiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {
}
