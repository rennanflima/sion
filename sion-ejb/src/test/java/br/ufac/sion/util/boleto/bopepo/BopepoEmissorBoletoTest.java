/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.boleto.bopepo;

import br.ufac.sion.model.enuns.BancosSuportados;
import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.ContaBancaria;
import br.ufac.sion.model.Empresa;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.util.boleto.EmissorBoleto;
import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rennan.lima
 */
public class BopepoEmissorBoletoTest {

    private EmissorBoleto emissorBoleto;

    @Before
    public void init() {
        emissorBoleto = new BopepoEmissorBoleto();
    }

    @Test
    public void deve_gerar_boleto_em_arquivo_bradesco() throws Exception {
        Empresa cedenteSistema = new Empresa();
        cedenteSistema.setNomeFantasia("AlgaWorks");
        cedenteSistema.setSigla("AW");
        cedenteSistema.setCnpj("10.687.566/0001-97");
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setAgencia(1111);
        contaBancaria.setDigitoAgencia("0");
        contaBancaria.setNumeroConta(2222);
        contaBancaria.setDigitoConta("9");
        contaBancaria.setCodigoCarteira(6);
        contaBancaria.setBanco(BancosSuportados.BANCO_BRADESCO);
        contaBancaria.setCedente(cedenteSistema);

        Boleto cobrancaSistema = new Boleto();
        cobrancaSistema.setId(1L);
        cobrancaSistema.setDataVencimento(LocalDate.now());
        cobrancaSistema.setValor(new BigDecimal("200.22"));

        Candidato candidato = new Candidato();
        candidato.setNome("Maria Santos");
        candidato.setCpf("866.646.623-53");

        Concurso concurso = new Concurso();
        concurso.setContaBancaria(contaBancaria);

        Cargo cargo = new Cargo();
        cargo.setDescricao("teste");

        CargoConcurso cc = new CargoConcurso();
        cc.setCodigo("COD01");
        cc.setCargo(cargo);
        cc.setConcurso(concurso);

        Inscricao sacado = new Inscricao();
        sacado.setNumero("201511");
        sacado.setCargoConcurso(cc);
        sacado.setCandidato(candidato);
        cobrancaSistema.setSacado(sacado);

        File boleto = this.emissorBoleto.gerarBoletoEmArquivo("boletoTeste1.pdf", cedenteSistema, cobrancaSistema);
        assertTrue(boleto.isFile());
    }

    @Test
    public void deve_gerar_boleto_em_arquivo_banco_do_brasil() throws Exception {
        Empresa cedenteSistema = new Empresa();
        cedenteSistema.setNomeFantasia("AlgaWorks");
        cedenteSistema.setSigla("AW");
        cedenteSistema.setCnpj("10.687.566/0001-97");
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setAgencia(3022);
        contaBancaria.setDigitoAgencia("8");
        contaBancaria.setNumeroConta(3374);
        contaBancaria.setDigitoConta("x");
        contaBancaria.setCodigoCarteira(18);
        contaBancaria.setBanco(BancosSuportados.BANCO_DO_BRASIL);
        contaBancaria.setCedente(cedenteSistema);
        contaBancaria.setConvenio(1379859);

        Boleto cobrancaSistema = new Boleto();
        cobrancaSistema.setId(1L);
        cobrancaSistema.setDataVencimento(LocalDate.now());
        cobrancaSistema.setValor(new BigDecimal("200.22"));

        Candidato candidato = new Candidato();
        candidato.setNome("Maria Santos");
        candidato.setCpf("866.646.623-53");

        Concurso concurso = new Concurso();
        concurso.setContaBancaria(contaBancaria);

        Cargo cargo = new Cargo();
        cargo.setDescricao("teste");

        CargoConcurso cc = new CargoConcurso();
        cc.setCodigo("COD01");
        cc.setCargo(cargo);
        cc.setConcurso(concurso);

        Inscricao sacado = new Inscricao();
        sacado.setNumero("20152921");
        sacado.setCargoConcurso(cc);
        sacado.setCandidato(candidato);
        cobrancaSistema.setSacado(sacado);

        File boleto = this.emissorBoleto.gerarBoletoEmArquivo("boletoTeste2.pdf", cedenteSistema, cobrancaSistema);
        assertTrue(boleto.isFile());
    }
}
