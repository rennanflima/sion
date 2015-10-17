package br.ufac.sion.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Telefone.class)
public abstract class Telefone_ {

	public static volatile SingularAttribute<Telefone, TipoTelefone> tipo;
	public static volatile SingularAttribute<Telefone, String> numero;
	public static volatile SingularAttribute<Telefone, String> prefixo;
	public static volatile SingularAttribute<Telefone, String> ramal;

}

