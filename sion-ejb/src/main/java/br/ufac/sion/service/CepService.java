/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.com.sescacre.util.WebServiceCep;
import br.ufac.sion.dao.CidadeFacadeLocal;
import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Endereco;
import br.ufac.sion.model.Estado;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Rennan
 */
@Stateless
public class CepService {

    @EJB
    private CidadeFacadeLocal cidadeFacade;

    @EJB
    private EstadoFacadeLocal estadoFacade;

    private WebServiceCep webServiceCep;

    public Endereco consultarCep(String cep) {
        webServiceCep = WebServiceCep.searchCep(cep);

        Endereco endereco = new Endereco();
        if (webServiceCep.wasSuccessful()) {
            endereco.setCep(webServiceCep.getCep());
            endereco.setLogadouro(webServiceCep.getLogradouroFull());
            endereco.setBairro(webServiceCep.getBairro());
            Estado e = estadoFacade.findByUf(webServiceCep.getUf());
            Cidade c = cidadeFacade.findByNomeAndIdEstado(webServiceCep.getCidade(), e.getId());
            endereco.setCidade(c);
        } else {
            System.err.println("Erro ao consultar o cep: " + webServiceCep.getResulCode() + " - " + webServiceCep.getResultText());
        }
        return endereco;
    }
}
