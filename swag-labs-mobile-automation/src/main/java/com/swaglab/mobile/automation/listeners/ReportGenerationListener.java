package com.swaglab.mobile.automation.listeners;

import com.swaglab.mobile.automation.utils.ReportUtils;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener para eventos do Cucumber
 * Gerencia a geração de relatórios PDF por cenário ao final da execução dos testes
 */
public class ReportGenerationListener implements ConcurrentEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportGenerationListener.class);

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        // Registra para eventos de cenário individual
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
        
        // Mantém também o evento original para finalização completa dos testes
        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
    }
    
    private void handleTestCaseFinished(TestCaseFinished event) {
        String scenarioName = event.getTestCase().getName();
        LOGGER.info("Cenário finalizado: {}. Status: {}", scenarioName, event.getResult().getStatus());
        
        // Só é necessário se a geração dentro do hook não estiver funcionando
        // ReportUtils.generatePDFReportForScenario(scenarioName);
    }

    private void handleTestRunFinished(TestRunFinished event) {
        LOGGER.info("Execução de testes finalizada. Garantindo que todos os relatórios PDF foram gerados.");
        ReportUtils.generatePDFReportsForAllScenarios();
    }
}