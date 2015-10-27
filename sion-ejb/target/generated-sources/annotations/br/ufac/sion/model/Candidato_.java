package br.ufac.sion.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Candidato.class)
public abstract class Candidato_ {

	public static volatile SingularAttribute<Candidato, Endereco> endereco;
	public static volatile SingularAttribute<Candidato, String> nome;
	public static volatile SingularAttribute<Candidato, EstadoCivil> estadoCivil;
	public static volatile SingularAttribute<Candidato, String> permissao;
	public static volatile ListAttribute<Candidato, Telefone> telefones;
	public static volatile SingularAttribute<Candidato, String> mae;
	public static volatile SingularAttribute<Candidato, BracoDominante> bracoDominante;
	public static volatile SingularAttribute<Candidato, String> senha;
	public static volatile ListAttribute<Candidato, Inscricao> inscricoes;
	public static volatile SingularAttribute<Candidato, RG> rg;
	public static volatile SingularAttribute<Candidato, String> cpf;
	public static volatile SingularAttribute<Candidato, String> pai;
	public static volatile SingularAttribute<Candidato, Long> id;
	public static volatile SingularAttribute<Candidato, Escolaridade> escolaridade;
	public static volatile SingularAttribute<Candidato, Sexo> sexo;
	public static volatile SingularAttribute<Candidato, LocalDate> dataNascimento;
	public static volatile SingularAttribute<Candidato, String> email;

}

