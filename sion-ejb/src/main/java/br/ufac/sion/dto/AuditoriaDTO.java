/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dto;

import br.ufac.sion.audit.CustomRevisionEntity;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.ArquivoRetorno;
import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.CargoVaga;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.ContaBancaria;
import br.ufac.sion.model.Empresa;
import br.ufac.sion.model.Funcionario;
import br.ufac.sion.model.Grupo;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.Nivel;
import br.ufac.sion.model.Permissao;
import br.ufac.sion.model.Setor;
import br.ufac.sion.model.Telefone;
import br.ufac.sion.model.TokenRecuperacao;
import br.ufac.sion.model.Usuario;
import br.ufac.sion.model.Vaga;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.RevisionType;

/**
 *
 * @author Rennan
 */
public class AuditoriaDTO implements Comparable<AuditoriaDTO> {

    private Object entidade;

    private RevisionType tipoAlterecao;

    private String nomeEntidade;

    private CustomRevisionEntity revisao;

    public AuditoriaDTO() {
    }

    public AuditoriaDTO(Object entidade, RevisionType tipoAlterecao, String nomeEntidade, CustomRevisionEntity revisao) {
        this.entidade = entidade;
        this.tipoAlterecao = tipoAlterecao;
        this.nomeEntidade = nomeEntidade;
        this.revisao = revisao;
    }

    public Object getEntidade() {
        return entidade;
    }

    public Long getEntidadeID() throws NegocioException {
        try {
            Object instancia = Class.forName(entidade.getClass().getName()).cast(entidade);
            Method metodo;
            metodo = instancia.getClass().getMethod("getId");
            Long id = (Long) metodo.invoke(instancia);
            return id;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    public void setEntidade(Object entidade) {
        this.entidade = entidade;
    }

    public RevisionType getTipoAlterecao() {
        return tipoAlterecao;
    }

    public void setTipoAlterecao(RevisionType tipoAlterecao) {
        this.tipoAlterecao = tipoAlterecao;
    }

    public String getNomeEntidade() {
        return nomeEntidade;
    }

    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    public CustomRevisionEntity getRevisao() {
        return revisao;
    }

    public void setRevisao(CustomRevisionEntity revisao) {
        this.revisao = revisao;
    }

    @Override
    public int compareTo(AuditoriaDTO o) {
        return revisao.getRevisionDate().compareTo(o.revisao.getRevisionDate());
    }

    public String getDataAlteracaoFormatada() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy ' às ' HH:mm:ss z");
        return revisao != null ? format.format(revisao.getRevisionDate()) : "";
    }

    @Override
    public String toString() {
        StringBuilder retorno = new StringBuilder("");
        DateTimeFormatter formatador = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("pt", "BR"));
        if (StringUtils.isNotBlank(nomeEntidade)) {
            switch (nomeEntidade) {
                case "Arquivo de Retorno":
                    ArquivoRetorno ar = (ArquivoRetorno) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(ar.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome do Arquivo</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(ar.getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Número</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(ar.getNumero()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Data do Upload</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(ar.getDataUpload().format(formatador)).append("</div>")
                            .append("</div>");
                    break;
                case "Boleto":
                    Boleto bt = (Boleto) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(bt.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Sacado</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(bt.getSacado()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nosso Número</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(bt.getNossoNumero()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Valor</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\"> R$").append(bt.getValor()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Data de Vencimento</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(bt.getDataVencimento().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Situação</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(bt.getSituacao()).append("</div>")
                            .append("</div>");
                    retorno.append("<fieldset class=\"ui-fieldset ui-widget ui-widget-content ui-corner-all ui-hidden-container\" style=\"margin-bottom:20px\">")
                            .append("<legend class=\"ui-fieldset-legend ui-corner-all ui-state-default\">Informações de Pagamento</legend>")
                            .append("<div class=\"ui-panelgrid ui-widget ui-panelgrid-blank form-group ui-fluid\" style=\"border:0px none; background-color:transparent; padding-top: 15px;\">")
                            .append("<div class=\"ui-panelgrid-content ui-widget-content ui-grid ui-grid-responsive\">");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Data de Pagamento</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(bt.getDataPagamento().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Valor Pago</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\"> R$").append(bt.getValorPago()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome do Arquivo de Retorno</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\"> R$").append(bt.getArquivo().getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("</div>")
                            .append("</div>")
                            .append("</fieldset>");
                    break;
                case "Candidato":
                    Candidato cd = (Candidato) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Mãe</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getMae()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Pai</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getPai()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">CPF</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getCpf()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">RG</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getRg()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Data de Nascimento</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getDataNascimento().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Estado Civil</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getEstadoCivil()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Braço Dominante</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getBracoDominante()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Escolaridade</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getEscolaridade()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Sexo</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getSexo()).append("</div>")
                            .append("</div>");

                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">E-mail</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getUsuario().getEmail()).append("</div>")
                            .append("</div>");

                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Telefones</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">");
                    if (!cd.getTelefones().isEmpty()) {
                        retorno.append("<ul>");
                        for (Telefone tel : cd.getTelefones()) {
                            retorno.append("<li>").append(tel).append("</li>");
                        }
                        retorno.append("</ul>");
                    } else {
                        retorno.append("O concurso não possui cargos.");
                    }
                    retorno.append("</div>")
                            .append("</div>");

                    retorno.append("<fieldset class=\"ui-fieldset ui-widget ui-widget-content ui-corner-all ui-hidden-container\" style=\"margin-bottom:20px\">")
                            .append("<legend class=\"ui-fieldset-legend ui-corner-all ui-state-default\">Endereço</legend>")
                            .append("<div class=\"ui-panelgrid ui-widget ui-panelgrid-blank form-group ui-fluid\" style=\"border:0px none; background-color:transparent; padding-top: 15px;\">")
                            .append("<div class=\"ui-panelgrid-content ui-widget-content ui-grid ui-grid-responsive\">");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">CEP</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getEndereco().getCep()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Logradouro</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getEndereco().getLogradouro()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Número</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getEndereco().getNumero()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Complemento</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getEndereco().getComplemento()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Bairro</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getEndereco().getBairro()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Cidade/Estado</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getEndereco().getCidade())
                            .append("/").append(cd.getEndereco().getCidade().getEstado().getSigla()).append("</div>")
                            .append("</div>");
                    retorno.append("</div>")
                            .append("</div>")
                            .append("</fieldset>");

                    retorno.append("<fieldset class=\"ui-fieldset ui-widget ui-widget-content ui-corner-all ui-hidden-container\" style=\"margin-bottom:20px\">")
                            .append("<legend class=\"ui-fieldset-legend ui-corner-all ui-state-default\">Informações de Login</legend>")
                            .append("<div class=\"ui-panelgrid ui-widget ui-panelgrid-blank form-group ui-fluid\" style=\"border:0px none; background-color:transparent; padding-top: 15px;\">")
                            .append("<div class=\"ui-panelgrid-content ui-widget-content ui-grid ui-grid-responsive\">");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Login</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getUsuario().getLogin()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Último Acesso</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getUsuario().getUltimoAcesso().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Candidato ativo ?</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cd.getUsuario().getAtivo()).append("</div>")
                            .append("</div>");
                    retorno.append("</div>")
                            .append("</div>")
                            .append("</fieldset>");
                    break;
                case "Cargo":
                    Cargo cg = (Cargo) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cg.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Descrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cg.getDescricao()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nível</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cg.getNivel()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Carga Horária</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cg.getCargaHoraria()).append("h</div>")
                            .append("</div>");
                    break;
                case "Concurso":
                    Concurso cn = (Concurso) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cn.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Título</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cn.getTitulo()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Descrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cn.getDescricao()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Período de Inscrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cn.getPeriodoInscricao()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Local de Inscrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cn.getLocalInscricao()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Status</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cn.getStatus()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Data de Vencimento do Boleto</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cn.getDataVencimentoBoleto().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Descrição da Conta Bancária</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cn.getContaBancaria().getDescricao()).append("</div>")
                            .append("</div>");

                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Cargos do Concurso</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">");
                    if (!cn.getCargos().isEmpty()) {
                        retorno.append("<ul>");
                        for (CargoConcurso cc : cn.getCargos()) {
                            retorno.append("<li>").append(cc).append("</li>");
                        }
                        retorno.append("</ul>");
                    } else {
                        retorno.append("O concurso não possui cargos.");
                    }
                    retorno.append("</div>")
                            .append("</div>");

                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Vagas dos Cargos do Concurso</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">");
                    if (!cn.getCargos().isEmpty()) {
                        retorno.append("<ul>");
                        for (CargoConcurso cc : cn.getCargos()) {
                            if (!cc.getVagas().isEmpty()) {
                                for (CargoVaga vaga : cc.getVagas()) {
                                    retorno.append("<li>").append(vaga).append("</li>");
                                }
                            } else {
                                retorno.append("O concurso não possui vagas definidas para o cargo: " + cc);
                            }
                        }
                        retorno.append("</ul>");
                    } else {
                        retorno.append("O concurso não possui cargos.");
                    }
                    retorno.append("</div>")
                            .append("</div>");
                    break;
                case "Contas Bancárias":
                    ContaBancaria cb = (ContaBancaria) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cb.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Banco</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cb.getBanco()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Cedente</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cb.getCedente().getRazaoSocial()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Descrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cb.getDescricao()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Agência</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cb.getAgencia()).append("-").append(cb.getDigitoAgencia()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Conta</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cb.getNumeroConta()).append("-").append(cb.getDigitoConta()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Código da Carteira</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cb.getCodigoCarteira()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Convenio</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(cb.getConvenio()).append("</div>")
                            .append("</div>");
                    break;
                case "Empresa":
                    Empresa emp = (Empresa) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Razão Social</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getRazaoSocial()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome Fantasia</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getNomeFantasia()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Sigla</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getSigla()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">CNPJ</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getCnpj()).append("</div>")
                            .append("</div>");

                    retorno.append("<fieldset class=\"ui-fieldset ui-widget ui-widget-content ui-corner-all ui-hidden-container\" style=\"margin-bottom:20px\">")
                            .append("<legend class=\"ui-fieldset-legend ui-corner-all ui-state-default\">Endereço</legend>")
                            .append("<div class=\"ui-panelgrid ui-widget ui-panelgrid-blank form-group ui-fluid\" style=\"border:0px none; background-color:transparent; padding-top: 15px;\">")
                            .append("<div class=\"ui-panelgrid-content ui-widget-content ui-grid ui-grid-responsive\">");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">CEP</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getEndereco().getCep()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Logradouro</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getEndereco().getLogradouro()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Número</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getEndereco().getNumero()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Complemento</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getEndereco().getComplemento()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Bairro</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getEndereco().getBairro()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Cidade/Estado</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(emp.getEndereco().getCidade())
                            .append("/").append(emp.getEndereco().getCidade().getEstado().getSigla()).append("</div>")
                            .append("</div>");
                    retorno.append("</div>")
                            .append("</div>")
                            .append("</fieldset>");
                    break;
                case "Funcionário":
                    Funcionario f = (Funcionario) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Matrícula</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getMatricula()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getUsuario().getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">E-mail</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getUsuario().getEmail()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Setor</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getSetor()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Cargo</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getCargo()).append("</div>")
                            .append("</div>");

                    retorno.append("<fieldset class=\"ui-fieldset ui-widget ui-widget-content ui-corner-all ui-hidden-container\" style=\"margin-bottom:20px\">")
                            .append("<legend class=\"ui-fieldset-legend ui-corner-all ui-state-default\">Informações de Login</legend>")
                            .append("<div class=\"ui-panelgrid ui-widget ui-panelgrid-blank form-group ui-fluid\" style=\"border:0px none; background-color:transparent; padding-top: 15px;\">")
                            .append("<div class=\"ui-panelgrid-content ui-widget-content ui-grid ui-grid-responsive\">");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Login</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getUsuario().getLogin()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Último Acesso</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getUsuario().getUltimoAcesso().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Funcionário ativo ?</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(f.getUsuario().getAtivo()).append("</div>")
                            .append("</div>");
                    retorno.append("</div>")
                            .append("</div>")
                            .append("</fieldset>");

                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Grupos</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">");
                    if (!f.getUsuario().getGrupos().isEmpty()) {
                        retorno.append("<ul>");
                        for (Grupo g : f.getUsuario().getGrupos()) {
                            retorno.append("<li>").append(g.getNome()).append(" (").append(g.getDescricao()).append(")</li>");
                        }
                        retorno.append("</ul>");
                    } else {
                        retorno.append("O funcionario não possui grupos.");
                    }
                    retorno.append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Permissões do Usuário</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">");
                    if (!f.getUsuario().getPermissoes().isEmpty()) {
                        retorno.append("<ul>");
                        for (Permissao p : f.getUsuario().getPermissoes()) {
                            retorno.append("<li>").append(p.getNome()).append("</li>");
                        }
                        retorno.append("</ul>");
                    } else {
                        retorno.append("O funcionario não possui permissão.");
                    }
                    retorno.append("</div>")
                            .append("</div>");
                    break;
                case "Grupo":
                    Grupo g = (Grupo) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(g.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(g.getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Decrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(g.getDescricao()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Permissões</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">");
                    if (!g.getPermissoes().isEmpty()) {
                        retorno.append("<ul>");
                        for (Permissao p : g.getPermissoes()) {
                            retorno.append("<li>").append(p.getNome()).append("</li>");
                        }
                        retorno.append("</ul>");
                    } else {
                        retorno.append("O grupo não possui permissão.");
                    }
                    retorno.append("</div>")
                            .append("</div>");
                    break;
                case "Inscrição":
                    Inscricao insc = (Inscricao) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Candidato</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getCandidato().getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Número Inscrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getNumero()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Concurso</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getCargoConcurso().getConcurso().getTitulo()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Cargo</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getCargoConcurso().getCargo()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Data da Inscrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getDataInscricao().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Status</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getStatus()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Justificativa do Status</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getJustificativaStatus()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Data da Justificativa do Status</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getDataJustificativaStatus().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Portador de Necessidades Especial?</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getNecessidadeEspecial().isPortador()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Qual Necessidade Especial</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getNecessidadeEspecial().getQualNecessidadeEspecial()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nessita de Antedimento?</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getNecessidadeEspecial().isNecessitaAtendimento()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Qual atendimento?</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.getNecessidadeEspecial().getQualAtendimento()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Concorda com o Edital?</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(insc.isConcordoEdital()).append("</div>")
                            .append("</div>");
                    break;
                case "Localidade":
                    Localidade l = (Localidade) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(l.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(l.getNome()).append("</div>")
                            .append("</div>");
                    break;
                case "Nível":
                    Nivel n = (Nivel) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(n.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Descrição</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(n.getDescricao()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Valor:</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\"> R$").append(n.getValor()).append("</div>")
                            .append("</div>");
                    break;
                case "Setor":
                    Setor s = (Setor) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(s.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(s.getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Sigla</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(s.getSigla()).append("</div>")
                            .append("</div>");
                    break;
                case "Tipo de Vaga":
                    Vaga v = (Vaga) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(v.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Tipo de Vaga</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(v.getTipo()).append("</div>")
                            .append("</div>");
                    break;
                case "Token de Recuperação":
                    TokenRecuperacao tr = (TokenRecuperacao) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome do Candidato</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(tr.getCandidato().getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Data de Vencimento</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(tr.getDataVencimento().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Token</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(tr.getToken()).append("</div>")
                            .append("</div>");
                    break;
                case "Usuário":
                    Usuario usr = (Usuario) entidade;
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Id</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(usr.getId()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Nome do Usuário</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(usr.getNome()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">E-mail</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(usr.getEmail()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Login</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(usr.getLogin()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Último Acesso</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(usr.getUltimoAcesso().format(formatador)).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Usuário ativo ?</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">").append(usr.getAtivo()).append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Grupos</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">");
                    if (!usr.getGrupos().isEmpty()) {
                        retorno.append("<ul>");
                        for (Grupo gg : usr.getGrupos()) {
                            retorno.append("<li>").append(gg.getNome()).append(" (").append(gg.getDescricao()).append(")</li>");
                        }
                        retorno.append("</ul>");
                    } else {
                        retorno.append("O funcionario não possui grupos.");
                    }
                    retorno.append("</div>")
                            .append("</div>");
                    retorno.append("<div class=\"ui-grid-row\">")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-5\">")
                            .append("<label class=\"ui-outputlabel ui-widget\" style=\"font-weight: bold\">Permissões do Usuário</label>")
                            .append("</div>")
                            .append("<div class=\"ui-panelgrid-cell ui-grid-col-6\">");
                    if (!usr.getPermissoes().isEmpty()) {
                        retorno.append("<ul>");
                        for (Permissao p : usr.getPermissoes()) {
                            retorno.append("<li>").append(p.getNome()).append("</li>");
                        }
                        retorno.append("</ul>");
                    } else {
                        retorno.append("O funcionario não possui permissão.");
                    }
                    retorno.append("</div>")
                            .append("</div>");
                    break;
            }
        }
        return retorno.toString();
    }

}
