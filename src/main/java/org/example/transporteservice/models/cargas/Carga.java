package org.example.transporteservice.models.cargas;

import org.example.transporteservice.models.users.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cargas")
public class Carga {
    @Id
    private String id;
    private String fecha;
    private String nombre;
    private Ubicacion origen;
    private Ubicacion destino;
    private int peso;
    private String descripcion;
    private User conductor;

    // Getters y Setters

    public class Ubicacion {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public Ubicacion getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public User getConductor() {
        return conductor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setOrigen(Ubicacion origen) {
        this.origen = origen;
    }

    public void setDestino(Ubicacion destino) {
        this.destino = destino;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setConductor(User conductor) {
        this.conductor = conductor;
    }
}
