package com.mensal.services;

import com.mensal.entities.*;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public CarteiraEntity save(CarteiraEntity carteira, Long id) {
        PessoaEntity pessoa = pessoaRepository.findById(id).get();
        carteira.setPessoa(pessoa);
        CarteiraEntity novaCarteira = carteiraRepository.save(carteira);

        CaixaEntity novoCaixa = new CaixaEntity();
        novoCaixa.setConta("Caixa de " + pessoa.getNome());
        novoCaixa.setValor(0);
        pessoa.setCarteira(novaCarteira);
        novoCaixa.setCarteira(novaCarteira);
        novaCarteira.setCaixa(novoCaixa);
        caixaRepository.save(novoCaixa);

        return novaCarteira;
    }

    public CarteiraEntity findById(Long id) {
        return carteiraRepository.findById(id).orElseThrow();
    }

    public List<DespesaEntity> listDespesasTotal(CarteiraEntity carteira) {
        return carteiraRepository.listDespesas(carteira);
    }

    public List<ReceitaEntity> listReceitasTotal(CarteiraEntity carteira) {
        return carteiraRepository.listReceitas(carteira);
    }

    public float getCaixaSaldo(CarteiraEntity carteira) {
        return carteira.getCaixa().getValor();
    }

    public String getPessoaSaldo(CarteiraEntity carteira) {
        return carteira.getPessoa().getNome();
    }

    public List<DespesaEntity> listDespesasMensal(CarteiraEntity carteira) {
        Date today = new Date();
        return carteiraRepository.listDespesasMensal(today, carteira);
    }

    public List<ReceitaEntity> listReceitasMensal(CarteiraEntity carteira) {
        Date today = new Date();
        return carteiraRepository.listReceitasMensal(today, carteira);
    }

    public float somaDespesasMensal(CarteiraEntity carteira) {
        List<DespesaEntity> despesas = listDespesasMensal(carteira);
        float soma = 0;
        for (DespesaEntity despesa : despesas) {
            soma += despesa.getValor();
        }
        return soma;
    }

    public float somaReceitasMensal(CarteiraEntity carteira) {
        List<ReceitaEntity> receitas = listReceitasMensal(carteira);
        float soma = 0;
        for (ReceitaEntity receita : receitas) {
            soma += receita.getValor();
        }
        return soma;
    }

    public List<MetaEntity> listMetas(CarteiraEntity carteira) {
        return carteiraRepository.listMetas(carteira);
    }
}
