/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mx.itson.proyectofinal.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mx.itson.proyectofinal.business.Asiento;
import mx.itson.proyectofinal.business.Pasajero;

/**
 * Clase de interfaz de usuario
 * @author Evelyn Guzman
 */
public class Reservacion extends javax.swing.JFrame {
    
private final List<String> terminales = List.of("Navojoa", "Obregón", "Empalme", "Guaymas", "Hermosillo", "Santa Ana", "Magdalena", "Imuris", "Nogales");
private final List<Asiento> asientos = new ArrayList<>();
private final List<JButton> botonesAsientos = new ArrayList<>();
private final List<Pasajero> pasajerosTotales = new ArrayList<>();
private int terminalActual = 0;

// Componentes del formulario
private JTextField txtNombre;
private JComboBox<String> comboDestino;
private JTextField txtPrecio;
private JButton btnReservar;

public Reservacion() {
    setTitle("Potro Bus");
    setSize(600, 400);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Inicializamos el ComboBox desde el índice 1 (omitimos "Navojoa")
    comboDestino = new JComboBox<>(terminales.subList(1, terminales.size()).toArray(new String[0]));

    // Título
    JLabel titulo = new JLabel("Reservación de asientos de un autobús", JLabel.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 16));
    add(titulo, BorderLayout.NORTH);

    // Panel de asientos con pasillo
    JPanel panelAsientos = new JPanel(new GridLayout(5, 6, 5, 5));
    for (int i = 0; i < 20; i++) {
        Asiento asiento = new Asiento(i + 1);
        asientos.add(asiento);

        JButton boton = new JButton("A" + (i + 1));
        boton.setFont(new Font("Arial", Font.PLAIN, 12));
        boton.setBackground(Color.GREEN); // Inicialmente verde (disponible)
        boton.setPreferredSize(new Dimension(50, 30)); // Tamaño personalizado para los botones

        boton.addActionListener(e -> seleccionarAsiento(asiento, boton));
        botonesAsientos.add(boton);

        // Añadir botones en columnas con un espacio para el "pasillo"
        if (i % 4 == 2) {
            panelAsientos.add(Box.createGlue()); // Espacio vacío para el pasillo
        }
        panelAsientos.add(boton);
    }

    add(panelAsientos, BorderLayout.CENTER);

    // B6oxLayout para alinearlo a la izquierda
    JPanel panelFormulario = new JPanel();
    panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
    panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Componentes del formulario
    panelFormulario.add(new JLabel("Nombre:"));
    txtNombre = new JTextField();
    txtNombre.setPreferredSize(new Dimension(150, 25)); 
    panelFormulario.add(txtNombre);

    panelFormulario.add(Box.createVerticalStrut(5)); 

    panelFormulario.add(new JLabel("Destino:"));
    panelFormulario.add(comboDestino);

    panelFormulario.add(Box.createVerticalStrut(5)); 

    panelFormulario.add(new JLabel("Precio:"));
    txtPrecio = new JTextField();
    txtPrecio.setPreferredSize(new Dimension(150, 25)); 
    panelFormulario.add(txtPrecio);

    panelFormulario.add(Box.createVerticalStrut(10)); 

    btnReservar = new JButton("Reservar");
    btnReservar.setPreferredSize(new Dimension(100, 30)); 
    btnReservar.addActionListener(e -> realizarReserva());
    panelFormulario.add(btnReservar);

    add(panelFormulario, BorderLayout.WEST);

    // Botón de siguiente terminal
    JButton botonSiguiente = new JButton("Siguiente Terminal");
    botonSiguiente.addActionListener(e -> avanzarTerminal());
    add(botonSiguiente, BorderLayout.SOUTH);

    setVisible(true);
}

/**
 * Metodo que verifica si el asiento ya está ocupado y, de no estarlo, lo marca como seleccionado.
 * @param asiento el objeto Asiento seleccionado, que contiene la información sobre el asiento.
 * @param boton el botón de la interfaz gráfica asociado al asiento seleccionado.
 */
private void seleccionarAsiento(Asiento asiento, JButton boton) {
    if (asiento.isOcupado()) {
        JOptionPane.showMessageDialog(this, "Este asiento ya está ocupado.");
        return;
    }

    // Cambiar color a naranja al seleccionar el asiento
    boton.setBackground(Color.ORANGE);

    txtNombre.requestFocus(); //Coloca el cursor automáticamente en este campo
    btnReservar.putClientProperty("asiento", asiento); 
    btnReservar.putClientProperty("boton", boton); 
}


/**
 * Metodo que realiza el proceso de reserva de un asiento en el sistema.
 * Valida los datos ingresados por el usuario, crea la reservación
 * y actualiza el estado del sistema y de la interfaz.
 */
private void realizarReserva() {
    // Obtener los valores ingresados por el usuario
    String nombre = txtNombre.getText().trim();
    String destino = (String) comboDestino.getSelectedItem();
    String precioStr = txtPrecio.getText().trim();

    // Validar que todos los campos estén llenos
    if (nombre.isEmpty() || destino == null || precioStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
        return;
    }

    //Convertir el precio a un double
    double precio;
    try {
        precio = Double.parseDouble(precioStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.");
        return;
    }

    // Obtener el asiento y el botón seleccionados
    Asiento asiento = (Asiento) btnReservar.getClientProperty("asiento");
    JButton boton = (JButton) btnReservar.getClientProperty("boton");

    // Crear el objeto Pasajero y asignarlo al asiento
    if (asiento == null || boton == null) {
        JOptionPane.showMessageDialog(this, "Selecciona un asiento primero.");
        return;
    }

    String origen = terminales.get(terminalActual);
    Pasajero pasajero = new Pasajero(nombre, origen, destino, precio, asiento.getNumero());
    asiento.ocupar(pasajero);
    pasajerosTotales.add(pasajero);
    boton.setBackground(Color.RED); // Cambiar a rojo si el asiento está ocupado

    JOptionPane.showMessageDialog(this, "Reservación realizada:\n" +
            "Pasajero: " + nombre + "\nDestino: " + destino + "\nPrecio: $" + precio);

    
    txtNombre.setText("");
    txtPrecio.setText("");
    comboDestino.setSelectedIndex(0);
}

/**
 * Libera los asientos de los pasajeros que tienen como destino la terminal actual,
 * actualiza el estado de los botones de los asientos y elimina la terminal actual 
 * de la lista de destinos disponibles.
 * Si el autobús llega a la última terminal, se genera un reporte final.
 */
private void avanzarTerminal() {
    
     // Verificar si la terminal actual es la última
    if (terminalActual >= terminales.size() - 1) {
        mostrarReporteFinal();
        return;
    }

    String terminal = terminales.get(terminalActual);
    terminalActual++;
    comboDestino.removeItem(terminal);

    
    // Verificar si el asiento está ocupado y el destino coincide con la terminal actual
    int pasajerosBajados = 0;
    for (int i = 0; i < asientos.size(); i++) {
        Asiento asiento = asientos.get(i);
        // Si el asiento está ocupado y su destino es la terminal actual, se libera.
        if (asiento.isOcupado() && asiento.getPasajero().getDestino().equals(terminal)) {
            asiento.liberar();
            botonesAsientos.get(i).setBackground(Color.GREEN); 
            pasajerosBajados++;
        }
    }

    JOptionPane.showMessageDialog(this, "Terminal actual: " + terminal + "\nSe bajan " + pasajerosBajados + " pasajeros.");
}


/**
 * Genera y muestra un reporte final con información de todos los pasajeros y
 * las ganancias totales.
 */
private void mostrarReporteFinal() {
    String[] columnNames = {"Nombre", "Origen", "Destino", "Precio", "Núm.Asiento"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

    double totalGanancias = 0;
    for (Pasajero pasajero : pasajerosTotales) {
        tableModel.addRow(new Object[]{
                pasajero.getNombre(),
                pasajero.getOrigen(),
                pasajero.getDestino(),
                pasajero.getPrecio(),
                pasajero.getAsiento()
        });
        totalGanancias += pasajero.getPrecio();
    }

    JTable tabla = new JTable(tableModel);
    // Añadir la tabla a un panel
    JScrollPane scrollPane = new JScrollPane(tabla);
    
    // Crear un panel principal para el reporte
    JPanel panelReporte = new JPanel();
    panelReporte.setLayout(new BorderLayout());
    panelReporte.add(scrollPane, BorderLayout.CENTER);

    // Mostrar las ganancias debajo de la tabla
    JLabel lblTotalGanancias = new JLabel("Total de Ganancias: $" + totalGanancias);
    panelReporte.add(lblTotalGanancias, BorderLayout.SOUTH);

    JOptionPane.showMessageDialog(this, panelReporte, "Reporte Final", JOptionPane.INFORMATION_MESSAGE);

    System.exit(0);
}

    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
   
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reservacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
