package org.example.transporteservice.models.vehiculos;

public class VehiculoDTO {
    private String placa;         // Placa del camión
    private String marca;         // Marca del fabricante
    private String modelo;        // Modelo específico
    private int anioFabricacion;   // Año de fabricación
    private String numeroChasis;  // Número de chasis
    private String color;
    

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

    public int getAnoFabricacion() {
        return anioFabricacion;
    }

    public void setAnoFabricacion(int anioFabricacion) {
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
