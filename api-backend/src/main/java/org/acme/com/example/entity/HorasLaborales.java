package org.acme.com.example.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "horas_laborales")
public class HorasLaborales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "horas_trabajadas", nullable = false)
    private double horasTrabajadas;

    @Column(name = "registrado_en", nullable = false)
    private LocalDateTime registradoEn;

    // Constructores
    public HorasLaborales() {
        this.registradoEn = LocalDateTime.now(); // Fecha de registro por defecto
    }

    public HorasLaborales(Empleado empleado, LocalDate fecha, double horasTrabajadas) {
        this.empleado = empleado;
        this.fecha = fecha;
        this.horasTrabajadas = horasTrabajadas;
        this.registradoEn = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public LocalDateTime getRegistradoEn() {
        return registradoEn;
    }

    public void setRegistradoEn(LocalDateTime registradoEn) {
        this.registradoEn = registradoEn;
    }
}
