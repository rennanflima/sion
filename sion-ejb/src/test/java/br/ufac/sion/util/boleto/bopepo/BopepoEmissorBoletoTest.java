/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.boleto.bopepo;

import br.ufac.sion.model.BancosSuportados;
import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.ContaBancaria;
import br.ufac.sion.model.Empresa;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.util.boleto.EmissorBoleto;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificador;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificadorBradesco;
import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rennan.lima
 */
public class BopepoEmissorBoletoTest {

    private EmissorBoleto emissorBoleto;

    @Before
    public void init() {
        GeradorDigitoVerificador geradorDigitoVerificador = new GeradorDigitoVerificadorBradesco();
        emissorBoleto = new BopepoEmissorBoleto(geradorDigitoVerificador);
    }

    @Test
    public void deve_gerar_boleto_em_arquivo() throws Exception {
        Empresa cedenteSistema = new Empresa();
        cedenteSistema.setNomeFantasia("AlgaWorks");
        cedenteSistema.setSigla("AW");
        cedenteSistema.setCnpj("10.687.566/0001-97");
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setAgencia(1111);
        contaBancaria.setDigitoAgencia("0");
        contaBancaria.setNumero(2222);
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
        
        Cargo cargo =  new Cargo();
        cargo.setDescricao("teste");
        
        CargoConcurso cc = new CargoConcurso();
        cc.setCargo(cargo);
        cc.setConcurso(concurso);
        
        Inscricao sacado = new Inscricao();
        sacado.setNumero("201511");
        sacado.setCargoConcurso(cc);
        sacado.setCandidato(candidato);
        cobrancaSistema.setSacado(sacado);

        File boleto = this.emissorBoleto.gerarBoletoEmArquivo("boletoTeste1.pdf", cedenteSistema, cobrancaSistema);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(boleto);
    }

}
