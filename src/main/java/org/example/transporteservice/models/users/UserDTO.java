package org.example.transporteservice.models.users;

public class UserDTO {
    private String nombre="";
    private String apellido="";
    private String ci="";
    private String telefono="";
    private String licencia="";
    private String direccion="";
    private String email="";
    private String password="";
    private String rol="";

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCi() {
        return ci;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getLicencia() {
        return licencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
