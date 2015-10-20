package br.ufac.sion.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Boleto.class)
public abstract class Boleto_ {

	public static volatile SingularAttribute<Boleto, SituacaoBoleto> situacao;
	public static volatile SingularAttribute<Boleto, LocalDate> dataPagamento;
	public static volatile SingularAttribute<Boleto, Inscricao> sacado;
	public static volatile SingularAttribute<Boleto, LocalDate> dataVencimento;
	public static volatile SingularAttribute<Boleto, BigDecimal> valor;
	public static volatile SingularAttribute<Boleto, BigDecimal> valorPago;
	public static volatile SingularAttribute<Boleto, ArquivoRetorno> arquivo;
	public static volatile SingularAttribute<Boleto, Long> id;

}

