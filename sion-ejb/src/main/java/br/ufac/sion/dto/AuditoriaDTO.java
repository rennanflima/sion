/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dto;

import java.time.LocalDateTime;
import org.hibernate.envers.RevisionType;

/**
 *
 * @author Rennan
 */
public class AuditoriaDTO implements Comparable<AuditoriaDTO> {

    private Long entityId;

    private RevisionType tipoAlterecao;

    private LocalDateTime dataAlteracao;

    private String entity;

    private String nomeUsuario;

    private String ipUsuario;

    public AuditoriaDTO() {
    }

    public AuditoriaDTO(Long entityId, RevisionType tipoAlterecao, LocalDateTime dataAlteracao, String entity, String nomeUsuario, String ipUsuario) {
        this.entityId = entityId;
        this.tipoAlterecao = tipoAlterecao;
        this.dataAlteracao = dataAlteracao;
        this.entity = entity;
        this.nomeUsuario = nomeUsuario;
        this.ipUsuario = ipUsuario;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public RevisionType getTipoAlterecao() {
        return tipoAlterecao;
    }

    public void setTipoAlterecao(RevisionType tipoAlterecao) {
        this.tipoAlterecao = tipoAlterecao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getIpUsuario() {
        return ipUsuario;
    }

    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }

    @Override
    public int compareTo(AuditoriaDTO o) {
        return getDataAlteracao().compareTo(o.getDataAlteracao());
    }
}
