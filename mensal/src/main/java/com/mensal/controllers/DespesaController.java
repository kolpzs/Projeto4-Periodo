package com.mensal.controllers;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.DespesaEntity;
import com.mensal.services.CarteiraService;
import com.mensal.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/save")
    public ResponseEntity<DespesaEntity> save(@RequestBody DespesaEntity despesa) {
        DespesaEntity savedDespesa = despesaService.save(despesa);
        return ResponseEntity.ok(savedDespesa);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<DespesaEntity> findById(@PathVariable Long id) {
        try {
            DespesaEntity despesa = despesaService.findById(id);
            return ResponseEntity.ok(despesa);
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllByCarteira/{id}")
    public ResponseEntity<List<DespesaEntity>> findAllByCarteira(@PathVariable Long id) {
        try {
            CarteiraEntity carteira = carteiraService.findById(id);
            List<DespesaEntity> despesas = despesaService.findAllByCarteira(carteira);
            return ResponseEntity.ok(despesas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<DespesaEntity> edit(@RequestBody DespesaEntity despesa) {
        if (despesa != null) {
            DespesaEntity updatedDespesa = despesaService.edit(despesa);
            return ResponseEntity.ok(updatedDespesa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
