package br.ufac.sion.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cargo.class)
public abstract class Cargo_ {

	public static volatile SingularAttribute<Cargo, Long> id;
	public static volatile SingularAttribute<Cargo, Nivel> nivel;
	public static volatile SingularAttribute<Cargo, Integer> cargaHoraria;
	public static volatile SingularAttribute<Cargo, String> descricao;
	public static volatile ListAttribute<Cargo, Setor> setores;

}

