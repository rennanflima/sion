/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.util.WebServiceCep;
import br.ufac.sion.dao.CidadeFacadeLocal;
import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.exception.NegocioException;
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

    public Endereco consultarCep(String cep) throws NegocioException {
        webServiceCep = WebServiceCep.searchCep(cep);

        Endereco endereco = new Endereco();
        if (webServiceCep.wasSuccessful()) {
            endereco.setCep(webServiceCep.getCep());
            endereco.setLogradouro(webServiceCep.getLogradouroFull());
            endereco.setBairro(webServiceCep.getBairro());
            Estado e = estadoFacade.findByUf(webServiceCep.getUf());
            Cidade c = cidadeFacade.findByNomeAndIdEstado(webServiceCep.getCidade(), e.getId());
            endereco.setCidade(c);
        } else {
            throw  new NegocioException("Erro ao consultar o cep: " + webServiceCep.getCep() + " - " + webServiceCep.getResultText());
        }
        return endereco;
    }
}
