package ch.fwesterath.logisticsapi.models.transport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transports")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @GetMapping
    public Iterable<Transport> getAllTransports() {
        return transportService.getAllTransports();
    }

    @GetMapping("/{id}")
    public Transport getTransportById(@PathVariable("id") Long id) {
        return transportService.getTransportById(id);
    }

    @PostMapping
    public Transport createTransport(@RequestBody Transport transport) {
        return transportService.createTransport(transport);
    }

    @PutMapping("/{id}")
    public Transport updateTransport(@RequestBody Transport transport, @PathVariable("id") Long id) {
        transport.setId(id);
        return transportService.updateTransport(transport);
    }

    @DeleteMapping("/{id}")
    public void deleteTransport(@PathVariable("id") Long id) {
        transportService.deleteTransport(id);
    }
}
