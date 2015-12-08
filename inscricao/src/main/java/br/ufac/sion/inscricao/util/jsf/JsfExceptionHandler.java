 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.jsf;

import br.ufac.sion.exception.ArquivoRetornoException;
import br.ufac.sion.exception.NegocioException;
import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Rennan
 */
public class JsfExceptionHandler extends ExceptionHandlerWrapper {
    
    private static Log log = LogFactory.getLog(JsfExceptionHandler.class);

    private ExceptionHandler wrapped;

    public JsfExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

        while (events.hasNext()) {
            ExceptionQueuedEvent event = events.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable exception = context.getException();
            NegocioException negocioException = getNegocioException(exception);
            ArquivoRetornoException arquivoRetornoException = getArquivoRetornoException(exception);

            boolean handled = false;

            try {
                if (exception instanceof ViewExpiredException) {
                    handled = true;
                    redirect("/");
                } else if (negocioException != null) {
                    handled = true;
                    FacesUtil.addErrorMessage(negocioException.getMessage());
                } else if (arquivoRetornoException != null) {
                    handled = true;
                    FacesUtil.addErrorMessage(arquivoRetornoException.getMessage());
                } else {
                    handled = true;
                    log.error("Erro de sistema (inscricao): " + exception.getMessage(), exception);
                    redirect("/Erro.xhtml");
                }
            } finally {
                if (handled) {
                    events.remove();
                }
            }
        }

        getWrapped().handle();
    }

    private NegocioException getNegocioException(Throwable exception) {
        if (exception instanceof NegocioException) {
            return (NegocioException) exception;
        } else if (exception.getCause() != null) {
            return getNegocioException(exception.getCause());
        }

        return null;
    }

    private ArquivoRetornoException getArquivoRetornoException(Throwable exception) {
        if (exception instanceof ArquivoRetornoException) {
            return (ArquivoRetornoException) exception;
        } else if (exception.getCause() != null) {
            return getArquivoRetornoException(exception.getCause());
        }

        return null;
    }
    
    private void redirect(String page) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String contextPath = externalContext.getRequestContextPath();

            externalContext.redirect(contextPath + page);
            facesContext.responseComplete();
        } catch (IOException e) {
            throw new FacesException(e);
        }
    }
}
