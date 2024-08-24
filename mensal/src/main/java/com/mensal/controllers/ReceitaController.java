package com.mensal.controllers;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.ReceitaEntity;
import com.mensal.services.CarteiraService;
import com.mensal.services.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/save")
    public ResponseEntity<ReceitaEntity> save(@RequestBody ReceitaEntity receita) {
        ReceitaEntity savedReceita = receitaService.save(receita);
        return ResponseEntity.ok(savedReceita);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ReceitaEntity> findById(@PathVariable Long id) {
        try {
            ReceitaEntity receita = receitaService.findById(id);
            return ResponseEntity.ok(receita);
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllByCarteira/{id}")
    public ResponseEntity<List<ReceitaEntity>> findAllByCarteira(@PathVariable Long id) {
        try {
            CarteiraEntity carteira = carteiraService.findById(id);
            List<ReceitaEntity> receitas = receitaService.findAllByCarteira(carteira);
            return ResponseEntity.ok(receitas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<ReceitaEntity> edit(@RequestBody ReceitaEntity receita) {
        if (receita != null) {
            ReceitaEntity updatedReceita = receitaService.edit(receita);
            return ResponseEntity.ok(updatedReceita);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
