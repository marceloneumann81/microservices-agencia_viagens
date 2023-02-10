package com.neumann.reserva;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
public class ReadinessCheck implements HealthCheck {    
    
    @Override
    public HealthCheckResponse call() {
        
        if (Reserva.listAll() == null){
            return HealthCheckResponse.down("Eu não estou pronto.");
        } else{
            return HealthCheckResponse.up("Eu estou pronto.");
        }        
        
    }
    
}
