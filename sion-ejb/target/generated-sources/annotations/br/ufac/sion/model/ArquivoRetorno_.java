package br.ufac.sion.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ArquivoRetorno.class)
public abstract class ArquivoRetorno_ {

	public static volatile SingularAttribute<ArquivoRetorno, Integer> numero;
	public static volatile SingularAttribute<ArquivoRetorno, LocalDate> dataUpload;
	public static volatile SingularAttribute<ArquivoRetorno, String> nome;
	public static volatile SingularAttribute<ArquivoRetorno, Long> id;

}

