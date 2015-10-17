package br.ufac.sion.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Insencao.class)
public abstract class Insencao_ {

	public static volatile SingularAttribute<Insencao, Boolean> requerInsencao;
	public static volatile SingularAttribute<Insencao, String> motivoNegacao;
	public static volatile SingularAttribute<Insencao, PercentualInsencao> percentualInsencao;
	public static volatile SingularAttribute<Insencao, Boolean> insencaoConfirmada;

}

