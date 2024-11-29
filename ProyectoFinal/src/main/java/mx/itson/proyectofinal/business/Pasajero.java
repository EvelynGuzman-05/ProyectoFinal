/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.proyectofinal.business;

/**
 * Clase de los attibutos de pasajero (getters y setters)
 * @author Evelyn Guzman
 */
public class Pasajero {

    private String nombre;
    private String destino;
    private double precio;
    private int asiento;
    private String origen; 

  
    // Constructor 
    public Pasajero(String nombre, String origen, String destino, double precio, int asiento) {
        this.nombre = nombre;
        this.origen = origen;  
        this.destino = destino;
        this.precio = precio;
        this.asiento = asiento;
    }


   

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the asiento
     */
    public int getAsiento() {
        return asiento;
    }

    /**
     * @param asiento the asiento to set
     */
    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }
    
   
}
