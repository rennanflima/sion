package br.ufac.sion.util.boleto;

import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.Empresa;
import java.io.File;
import java.io.Serializable;
import javax.ejb.Local;

@Local
public interface EmissorBoleto extends Serializable{

	public byte[] gerarBoleto(Empresa cedenteSistema, Boleto cobrancaSistema) throws NegocioException;
	
	public File gerarBoletoEmArquivo(String arquivo, Empresa cedenteSistema, Boleto cobrancaSistema);
}
