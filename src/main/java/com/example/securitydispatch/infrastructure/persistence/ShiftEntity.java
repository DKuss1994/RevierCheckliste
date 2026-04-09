package com.example.securitydispatch.infrastructure.persistence;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "shifts")
public class ShiftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private DriverEntity driver;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;

    private LocalDate deploymentDate;
    private LocalTime startTime;
    private LocalTime endTime;
protected ShiftEntity(){}
    public ShiftEntity(DriverEntity driver, ZoneEntity zone,LocalDate deploymentDate
    ,LocalTime startTime,LocalTime endTime){
    this.driver = driver;
    this.zone = zone;
    this.deploymentDate = deploymentDate;
    this.startTime = startTime;
    this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public DriverEntity getDriver() {
        return driver;
    }

    public ZoneEntity getZone() {
        return zone;
    }

    public LocalDate getDeploymentDate() {
        return deploymentDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
