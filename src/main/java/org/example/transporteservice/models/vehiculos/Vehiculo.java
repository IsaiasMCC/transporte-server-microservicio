package org.example.transporteservice.models.vehiculos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehiculos")
public class Vehiculo {

    @Id
    private String id;  // ID del vehículo (identificador único)

    private String placa;         // Placa del camión
    private String marca;         // Marca del fabricante
    private String modelo;        // Modelo específico
    private int anioFabricacion;   // Año de fabricación
    private String numeroChasis;  // Número de chasis
    private String color;         // Color del camión

    // Getters y Setters

    public Vehiculo(String id, String placa, String marca, String modelo, int anioFabricacion, String numeroChasis, String color) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.numeroChasis = numeroChasis;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public String getNumeroChasis() {
        return numeroChasis;
    }

    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
