package br.ufac.sion.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Concurso.class)
public abstract class Concurso_ {

	public static volatile SingularAttribute<Concurso, LocalDate> dataTerminoIncricao;
	public static volatile SingularAttribute<Concurso, String> titulo;
	public static volatile SingularAttribute<Concurso, LocalDate> dataInicioInscricao;
	public static volatile SingularAttribute<Concurso, String> localInscricao;
	public static volatile SingularAttribute<Concurso, Long> id;
	public static volatile ListAttribute<Concurso, CargoConcurso> cargos;
	public static volatile SingularAttribute<Concurso, String> descricao;

}

