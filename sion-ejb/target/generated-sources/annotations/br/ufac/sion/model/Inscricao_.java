package br.ufac.sion.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Inscricao.class)
public abstract class Inscricao_ {

	public static volatile SingularAttribute<Inscricao, Insencao> insencao;
	public static volatile SingularAttribute<Inscricao, Integer> numero;
	public static volatile SingularAttribute<Inscricao, LocalDate> dataInscricao;
	public static volatile SingularAttribute<Inscricao, Boleto> boleto;
	public static volatile SingularAttribute<Inscricao, CargoConcurso> cargoConcurso;
	public static volatile SingularAttribute<Inscricao, Long> id;
	public static volatile SingularAttribute<Inscricao, Candidato> candidato;
	public static volatile SingularAttribute<Inscricao, NecessidadeEspecial> NecessidadeEspecial;

}

