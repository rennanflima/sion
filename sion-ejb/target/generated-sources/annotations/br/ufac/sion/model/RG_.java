package br.ufac.sion.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RG.class)
public abstract class RG_ {

	public static volatile SingularAttribute<RG, String> numero;
	public static volatile SingularAttribute<RG, String> orgaoExpedidor;
	public static volatile SingularAttribute<RG, LocalDate> dataExpedicao;

}

