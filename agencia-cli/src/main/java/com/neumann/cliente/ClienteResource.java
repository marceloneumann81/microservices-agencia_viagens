package com.neumann.cliente;

import java.time.temporal.ChronoUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/cliente-cli")
public class ClienteResource {

    @Inject
    @RestClient
    ClienteService clienteService;

    @GET
    @Path("findById")
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(unit = ChronoUnit.SECONDS, value = 3)
    @Fallback(fallbackMethod = "fallback")
    @CircuitBreaker(
        requestVolumeThreshold = 4,
        failureRatio = .5,
        delay = 6000,
        successThreshold = 1       
    )
    public Cliente findById(@QueryParam("id") long id){
        return clienteService.findById(id);
    }
    
    @GET
    @Path("newCliente")
    public String newCliente(){
        Cliente cliente = Cliente.of(90, "Remoto");
        
        return clienteService.newCliente(cliente);
    }

    private Cliente fallback(long id){
        return Cliente.of(0, "");
    }

    
}
