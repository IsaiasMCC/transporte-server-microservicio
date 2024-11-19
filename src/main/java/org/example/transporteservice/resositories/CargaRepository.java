package org.example.transporteservice.resositories;

import org.example.transporteservice.models.cargas.Carga;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargaRepository extends MongoRepository<Carga, String> {
}
