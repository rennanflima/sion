/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.report;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author rennan.lima
 */
public class ExecutorRelatorio implements Work {

    private String caminhoRelatorio;
    private HttpServletResponse response;
    private Map<String, Object> parametros;
    private String nomeArquivoSaida;

    private boolean relatorioGerado;

    public ExecutorRelatorio(String caminhoRelatorio, HttpServletResponse response, Map<String, Object> parametros, String nomeArquivoSaida) {
        this.caminhoRelatorio = caminhoRelatorio;
        this.response = response;
        this.parametros = parametros;
        this.nomeArquivoSaida = nomeArquivoSaida;

        this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
    }

    @Override
    public void execute(Connection connection) throws SQLException {
        try {
            InputStream relatorioStream = this.getClass().getResourceAsStream(caminhoRelatorio);

            JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
            this.relatorioGerado = print.getPages().size() > 0;

            if (this.relatorioGerado) {
                JRExporter exportador = new JRPdfExporter();
                exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);
 
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + this.nomeArquivoSaida);

                exportador.exportReport();
            
            }
        } catch (Exception ex) {
            Logger.getLogger(ExecutorRelatorio.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Erro ao executar o relatório " + this.caminhoRelatorio, ex);
        }
    }

    public boolean isRelatorioGerado() {
        return relatorioGerado;
    }

}
