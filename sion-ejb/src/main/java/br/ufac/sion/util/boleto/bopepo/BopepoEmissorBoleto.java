package br.ufac.sion.util.boleto.bopepo;

import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Empresa;
import br.ufac.sion.util.boleto.EmissorBoleto;
import br.ufac.sion.util.conversor.DateConversor;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificador;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificadorBancoDoBrasil;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificadorBradesco;
import br.ufac.sion.util.modulo11.GeradorDigitoVerificadorCaixa;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.Stateless;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

@Stateless
public class BopepoEmissorBoleto implements EmissorBoleto, Serializable {

    private static final long serialVersionUID = 1L;

    private GeradorDigitoVerificador geradorDigitoVerificador;

    public BopepoEmissorBoleto() {
    }

    public BopepoEmissorBoleto(GeradorDigitoVerificador geradorDigitoVerificador) {
        this.geradorDigitoVerificador = geradorDigitoVerificador;
    }

    @Override
    public byte[] gerarBoleto(Empresa cedenteSistema, br.ufac.sion.model.Boleto cobrancaSistema) throws NegocioException {
        if (geradorDigitoVerificador == null) {
            inicializaGeradorDigitoVerificador(cobrancaSistema);
        }

        try {
            Boleto boleto = criarBoleto(cedenteSistema, cobrancaSistema);

            BoletoViewer boletoViewer = new BoletoViewer(boleto);
            return boletoViewer.getPdfAsByteArray();
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @Override
    public File gerarBoletoEmArquivo(String arquivo, Empresa cedenteSistema, br.ufac.sion.model.Boleto cobrancaSistema) {
        if (geradorDigitoVerificador == null) {
            inicializaGeradorDigitoVerificador(cobrancaSistema);
        }

        Boleto boleto = criarBoleto(cedenteSistema, cobrancaSistema);

        BoletoViewer boletoViewer = new BoletoViewer(boleto);
        return boletoViewer.getPdfAsFile(arquivo);
    }

    private Boleto criarBoleto(Empresa cedenteSistema, br.ufac.sion.model.Boleto cobrancaSistema) {
        ContaBancaria contaBancaria = criarContaBancaria(cobrancaSistema);
        Sacado sacado = new Sacado(cobrancaSistema.getSacado().getCandidato().getNome() + " - CPF: " + cobrancaSistema.getSacado().getCandidato().getCpf()
                + " / Cargo: " + cobrancaSistema.getSacado().getCargoConcurso().getCodigo() + " - " + cobrancaSistema.getSacado().getCargoConcurso().getCargo().getDescricao());
        Cedente cedente = new Cedente(cedenteSistema.getNomeFantasia() + " - " + cedenteSistema.getSigla(), cedenteSistema.getCnpj());

        Titulo titulo = criarTitulo(contaBancaria, sacado, cedente, cobrancaSistema);

        Boleto boleto = new Boleto(titulo);
        boleto.setLocalPagamento("Pagável em qualquer banco até o vencimento");
        boleto.setInstrucao1("1. Senhor(a) caixa, por favor não receba este documento após a data de vencimento.");
        boleto.setInstrucao2("2. Valores expressos em reais");
        boleto.setInstrucao3("3. Não receber por depósito");
        boleto.setInstrucao4("4. Pagamento em cheque, anotar no verso o 'Nosso número'.");
        return boleto;
    }

    private ContaBancaria criarContaBancaria(br.ufac.sion.model.Boleto cobrancaSistema) {
        ContaBancaria contaBancaria = new ContaBancaria(verficaBanco(cobrancaSistema).create());
        Integer agencia = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getAgencia();
        String digitoAgencia = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getDigitoAgencia();
        Integer numeroConta = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getNumeroConta();
        String digitoConta = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getDigitoConta();
        contaBancaria.setAgencia(new Agencia(agencia, digitoAgencia));
        contaBancaria.setNumeroDaConta(new NumeroDaConta(numeroConta, digitoConta));
        contaBancaria.setCarteira(new Carteira(cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getCodigoCarteira()));
        return contaBancaria;
    }

    private Titulo criarTitulo(ContaBancaria contaBancaria, Sacado sacado, Cedente cedente, br.ufac.sion.model.Boleto cobrancaSistema) {
        Titulo titulo = new Titulo(contaBancaria, sacado, cedente);

        br.ufac.sion.model.enuns.BancosSuportados bancoSuportado = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getBanco();
        String codigo = this.geradorDigitoVerificador.completarComZeros(cobrancaSistema.getSacado().getNumero());

        titulo.setNumeroDoDocumento(cobrancaSistema.getSacado().getNumero());
        if (bancoSuportado.equals(br.ufac.sion.model.enuns.BancosSuportados.CAIXA_ECONOMICA_FEDERAL)) {
            titulo.setNossoNumero(contaBancaria.getCarteira().getCodigo() + codigo);
            titulo.setDigitoDoNossoNumero(this.geradorDigitoVerificador.gerarDigito(contaBancaria.getCarteira().getCodigo(), codigo));
        } else if (bancoSuportado.equals(br.ufac.sion.model.enuns.BancosSuportados.BANCO_DO_BRASIL)) {
            titulo.setNossoNumero(cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getConvenio() + codigo);
        } else {
            titulo.setNossoNumero(codigo);
            titulo.setDigitoDoNossoNumero(this.geradorDigitoVerificador.gerarDigito(contaBancaria.getCarteira().getCodigo(), codigo));
        }

        titulo.setValor(cobrancaSistema.getValor());
        titulo.setDataDoDocumento(new Date());
        titulo.setDataDoVencimento(DateConversor.convertLocalDateToDate(cobrancaSistema.getDataVencimento()));
        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        titulo.setAceite(Aceite.N);
        return titulo;
    }

    private void inicializaGeradorDigitoVerificador(br.ufac.sion.model.Boleto cobrancaSistema) {
        br.ufac.sion.model.enuns.BancosSuportados bancoSuportado = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getBanco();
        if (bancoSuportado.equals(br.ufac.sion.model.enuns.BancosSuportados.BANCO_BRADESCO)) {
            geradorDigitoVerificador = new GeradorDigitoVerificadorBradesco();
        } else if (bancoSuportado.equals(br.ufac.sion.model.enuns.BancosSuportados.BANCO_DO_BRASIL)) {
            System.out.println("crira bb");
            geradorDigitoVerificador = new GeradorDigitoVerificadorBancoDoBrasil();
        } else {
            geradorDigitoVerificador = new GeradorDigitoVerificadorCaixa();
        }
    }

    private BancosSuportados verficaBanco(br.ufac.sion.model.Boleto cobrancaSistema) {
        br.ufac.sion.model.enuns.BancosSuportados bancoSuportado = cobrancaSistema.getSacado().getCargoConcurso().getConcurso().getContaBancaria().getBanco();
        if (bancoSuportado.equals(br.ufac.sion.model.enuns.BancosSuportados.BANCO_BRADESCO)) {
            return BancosSuportados.BANCO_BRADESCO;
        } else if (bancoSuportado.equals(br.ufac.sion.model.enuns.BancosSuportados.BANCO_DO_BRASIL)) {
            return BancosSuportados.BANCO_DO_BRASIL;
        } else {
            return BancosSuportados.CAIXA_ECONOMICA_FEDERAL;
        }
    }

}
