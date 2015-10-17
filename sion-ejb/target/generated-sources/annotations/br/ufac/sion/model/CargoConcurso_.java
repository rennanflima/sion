package br.ufac.sion.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CargoConcurso.class)
public abstract class CargoConcurso_ {

	public static volatile ListAttribute<CargoConcurso, CargoVaga> vagas;
	public static volatile SingularAttribute<CargoConcurso, BigDecimal> valor;
	public static volatile SingularAttribute<CargoConcurso, Long> id;
	public static volatile SingularAttribute<CargoConcurso, Cargo> cargo;
	public static volatile SingularAttribute<CargoConcurso, Concurso> concurso;

}

