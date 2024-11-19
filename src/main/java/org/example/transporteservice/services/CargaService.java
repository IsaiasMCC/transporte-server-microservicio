package org.example.transporteservice.services;

import org.example.transporteservice.models.cargas.Carga;
import org.example.transporteservice.resositories.CargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargaService {

    @Autowired
    private CargaRepository cargaRepository;

    public List<Carga> findAll() {
        return cargaRepository.findAll();
    }

    public Optional<Carga> findById(String id) {
        return cargaRepository.findById(id);
    }

    public Carga create(Carga carga) {
        return cargaRepository.save(carga);
    }
    public Carga update(String id, Carga carga) {
        if (cargaRepository.existsById(id)) {
            carga.setId(id);
            return cargaRepository.save(carga);
        } else {
            return null;
        }
    }

    public void deleteById(String id) {
        cargaRepository.deleteById(id);
    }
}
