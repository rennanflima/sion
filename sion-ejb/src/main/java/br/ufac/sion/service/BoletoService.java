/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.enuns.SituacaoBoleto;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificador;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificadorBancoDoBrasil;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificadorBradesco;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificadorCaixa;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class BoletoService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    private GeradorDigitoVerificador geradorDigitoVerificador;

    private BancosSuportados banco;

    public Boleto salvar(Boleto cobranca) throws NegocioException {

        Boleto b = perquisarPorInscricao(cobranca);
        try {
            if (b == null) {
                cobranca.setSituacao(SituacaoBoleto.PENDENTE);
                cobranca.setNossoNumero(geraNossoNumero(cobranca));
                b = em.merge(cobranca);
            } else {
                b.setNossoNumero(geraNossoNumero(b));
                b = em.merge(b);
            }
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }

        return b;
    }

    private Boleto perquisarPorInscricao(Boleto boleto) {
        Boleto b = null;
        try {
            b = em.createQuery("from Boleto b where b.sacado = :inscricao", Boleto.class)
                    .setParameter("inscricao", boleto.getSacado())
                    .getSingleResult();
        } catch (NoResultException e) {
        }
        return b;
    }

    private String geraNossoNumero(Boleto cobranca) throws NegocioException {
        inicializaGeradorDigitoVerificador(cobranca);
        String codigo = this.geradorDigitoVerificador.completarComZeros(cobranca.getSacado().getNumero());
        ContaBancaria conta = criarContaBancaria(cobranca);
        if (banco.equals(BancosSuportados.BANCO_DO_BRASIL)) {
            return cobranca.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getConvenio() + codigo;
        }
        return codigo + this.geradorDigitoVerificador.gerarDigito(conta.getCarteira().getCodigo(), codigo);
    }

    private void inicializaGeradorDigitoVerificador(br.ufac.sion.model.Boleto cobrancaSistema) {
        br.ufac.sion.model.enuns.BancosSuportados bancoSuportado = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getBanco();
        if (bancoSuportado.equals(br.ufac.sion.model.enuns.BancosSuportados.BANCO_BRADESCO)) {
            geradorDigitoVerificador = new GeradorDigitoVerificadorBradesco();
            this.banco = BancosSuportados.BANCO_BRADESCO;
        } else if (bancoSuportado.equals(br.ufac.sion.model.enuns.BancosSuportados.BANCO_DO_BRASIL)) {
            geradorDigitoVerificador = new GeradorDigitoVerificadorBancoDoBrasil();
            this.banco = BancosSuportados.BANCO_DO_BRASIL;
        } else {
            geradorDigitoVerificador = new GeradorDigitoVerificadorCaixa();
            this.banco = BancosSuportados.CAIXA_ECONOMICA_FEDERAL;
        }
    }

    private ContaBancaria criarContaBancaria(Boleto cobrancaSistema) {
        ContaBancaria contaBancaria = new ContaBancaria(banco.create());
        Integer agencia = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getAgencia();
        String digitoAgencia = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getDigitoAgencia();
        Integer numeroConta = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getNumero();
        String digitoConta = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getDigitoConta();
        contaBancaria.setAgencia(new Agencia(agencia, digitoAgencia));
        contaBancaria.setNumeroDaConta(new NumeroDaConta(numeroConta, digitoConta));
        contaBancaria.setCarteira(new Carteira(cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getCodigoCarteira()));

        return contaBancaria;
    }

}
