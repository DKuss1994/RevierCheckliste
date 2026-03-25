package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.StandardConfiguration;
import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
@Table(name = "security_objects")
public class SecurityObjectEntity {
    @Id
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;

    @Embedded
    private AddressEmbeddable address;

    @Embedded
    private StandardConfigurationEmbeddable standardConfiguration;

    protected SecurityObjectEntity(){}

    public SecurityObjectEntity(long id,String name,ZoneEntity zone,
                                AddressEmbeddable address,StandardConfigurationEmbeddable standardConfiguration   ){
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.address = address;
        this.standardConfiguration = standardConfiguration;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZoneEntity getZone() {
        return zone;
    }

    public AddressEmbeddable getAddress() {
        return address;
    }

    public StandardConfigurationEmbeddable getStandardConfiguration() {
        return standardConfiguration;
    }
}
