/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.proyectofinal.business;

/**
 * Clase de los atributos de asiento (getters y setters)
 * @author Evelyn Guzman
 */
public class Asiento {
    
    private int numero;
    private boolean ocupado;
    private Pasajero pasajero;

    //Constructor
    public Asiento(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.pasajero = null;
    }

    

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    

    public boolean isOcupado() {
        return ocupado;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void ocupar(Pasajero pasajero) {
        this.ocupado = true;
        this.pasajero = pasajero;
    }

    public void liberar() {
        this.ocupado = false;
        this.pasajero = null;
    }

    
}
