package com.example.crappBackend.Controller;

import com.example.crappBackend.model.Collector;
import com.example.crappBackend.repository.CollectrorRepository;
import com.example.crappBackend.useCase.ResourceNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
@RequestMapping("/api/collector")
public class CollectorController {
    @Autowired
    private CollectrorRepository collectrorRepository;

    @GetMapping
    private List<Collector> getAllCollector(){
        return collectrorRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Collector> getCollectorById(@PathVariable Long id){
        Collector collector = collectrorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Collector con la siguiente id no existe: " + id));
        return ResponseEntity.ok(collector);
    }

    @PostMapping
    public Collector createCollector(@RequestBody Collector collector){
        return collectrorRepository.save(collector);
    }

    @PutMapping("{id}")
    public ResponseEntity<Collector> updateCollector(@PathVariable Long id, @RequestBody Collector collectorDetails){
        Collector updateCollector = collectrorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Collector con siguiente id no existe: " + id));

        updateCollector.setFirstName(collectorDetails.getFirstName());
        updateCollector.setLastName(collectorDetails.getLastName());
        updateCollector.setEmail(collectorDetails.getEmail());
        updateCollector.setPassword(collectorDetails.getPassword());

        collectrorRepository.save(updateCollector);

        return ResponseEntity.ok(updateCollector);
    }


    @DeleteMapping("{id}")
    public void deleteCollector(@PathVariable Long id){
        collectrorRepository.deleteById(id);
    }
}
