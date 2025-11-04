package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "zapatillas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    private String marca;
    private String talla;
    private String color;
    private String tipo; // Deportiva, Casual, Formal, etc.
    
    @Column(nullable = false)
    private Double precio;
    
    private Integer stock;
    private Double costo;
}
