package com.mensal.controllers;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.DespesaEntity;
import com.mensal.entities.ReceitaEntity;
import com.mensal.services.CarteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/save/{id}")
    public ResponseEntity<CarteiraEntity> save(@RequestBody CarteiraEntity carteira, @PathVariable Long id ) {
        CarteiraEntity savedCarteira = carteiraService.save(carteira, id);
        return ResponseEntity.ok(savedCarteira);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<CarteiraEntity> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(carteiraService.findById(id));
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listDespesasTotal/{id}")
    public ResponseEntity<List<DespesaEntity>> listDespesas(@PathVariable Long id){
        try{
            CarteiraEntity carteira = carteiraService.findById(id);
            return ResponseEntity.ok(carteiraService.listDespesasTotal(carteira));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listReceitasTotal/{id}")
    public ResponseEntity<List<ReceitaEntity>> listReceitas(@PathVariable Long id){
        try{
            CarteiraEntity carteira = carteiraService.findById(id);
            return ResponseEntity.ok(carteiraService.listReceitasTotal(carteira));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<Float> getSaldo(@PathVariable Long id){
        try{
            CarteiraEntity carteira = carteiraService.findById(id);
            return ResponseEntity.ok(carteiraService.getCaixaSaldo(carteira));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
