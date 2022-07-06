package com.prog2.registrofazenda.controller;

import com.prog2.registrofazenda.model.Animal;
import com.prog2.registrofazenda.model.MetricasModel;
import com.prog2.registrofazenda.model.exception.NegocioException;
import com.prog2.registrofazenda.service.AnimalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
@AllArgsConstructor
@RequestMapping("/animais")
public class AnimalController {
    private AnimalService animalService;

    @GetMapping
    public List<Animal> listar(@RequestParam(required = false) boolean mamando) {
        return animalService.listar(mamando);
    }

    @PostMapping
    public ResponseEntity<Animal> registrar(@Valid @RequestBody Animal animal) {
        try {
            Animal animals = animalService.salvar(animal);
            return ResponseEntity.status(HttpStatus.CREATED).body(animals);
        } catch (NegocioException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Animal> buscarId(@PathVariable Long id) {
        try {
            Animal animal = animalService.buscarId(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(animal);
        } catch (NegocioException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<List<Animal>> findByNumero(@PathVariable int numero) {
        try {
            List<Animal> animal = animalService.buscarNumero(numero);
            return ResponseEntity.status(HttpStatus.FOUND).body(animal);
        } catch (NegocioException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteid/{id}")
    public ResponseEntity<Void> deletarId(@PathVariable Long id) {
        try {
            Void animal = animalService.deletarId(id);
            return ResponseEntity.status(HttpStatus.OK).body(animal);
        } catch (NegocioException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deletenumero/{numero}")
    public ResponseEntity<Void> deletarNumero(@PathVariable int numero) {
        try {
            Void animal = animalService.deletarNumero(numero);
            return ResponseEntity.status(HttpStatus.OK).body(animal);
        } catch (NegocioException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/periodonascimento")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<Animal>> buscarPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fim) {
        try {
            List<Animal> animals = animalService.buscarPorPeriodo(inicio, fim);
            return ResponseEntity.status(HttpStatus.FOUND).body(animals);
        } catch (NegocioException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/metricas")
    public ResponseEntity<MetricasModel> metricas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fim) {
        MetricasModel metricas = animalService.metricas(inicio, fim);
        return ResponseEntity.status(HttpStatus.OK).body(metricas);

    }

    @PutMapping("{id}/desmama")
    public ResponseEntity<Object> desmama(@PathVariable Long id) {

        try {
            animalService.desmama(id);
            return ResponseEntity.ok().build();
        } catch (NegocioException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }
}
