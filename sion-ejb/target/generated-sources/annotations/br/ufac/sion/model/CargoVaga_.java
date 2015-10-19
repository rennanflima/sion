package br.ufac.sion.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CargoVaga.class)
public abstract class CargoVaga_ {

	public static volatile SingularAttribute<CargoVaga, Integer> quatidade;
	public static volatile SingularAttribute<CargoVaga, Vaga> tipoVaga;
	public static volatile SingularAttribute<CargoVaga, Long> id;
	public static volatile SingularAttribute<CargoVaga, CargoConcurso> cargo;

}

