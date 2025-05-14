package com.swaglab.mobile.automation.hooks;

import com.swaglab.mobile.automation.core.DriverManager;
import com.swaglab.mobile.automation.utils.ReportUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);
    private final ReportUtils reportUtils = new ReportUtils();

    @Before
    public void setUp(Scenario scenario) {
        LOGGER.info("Iniciando cenário: {}", scenario.getName());
        DriverManager.startDriver();
        // Define o cenário atual no ReportUtils
        ReportUtils.setCurrentScenario(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        LOGGER.info("Finalizando cenário: {}", scenario.getName());
        
        // Captura screenshot em caso de falha
        if (scenario.isFailed()) {
            LOGGER.warn("Cenário falhou. Capturando screenshot final.");
            byte[] screenshot = DriverManager.getDriver().getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot de falha");
            reportUtils.takeScreenshot("Falha no cenário: " + scenario.getName());
        }
        
        // Gera o relatório PDF para o cenário atual após sua conclusão
        ReportUtils.generatePDFReportForScenario(scenario.getName());
        
        DriverManager.quitDriver();
    }
    
    @AfterAll
    public static void afterAllTests() {
        LOGGER.info("Finalizando todos os testes");
        // Agora é opcional, pois já estamos gerando relatórios individuais por cenário
        // Mas mantemos para compatibilidade com código existente
        ReportUtils.generatePDFReportsForAllScenarios();
    }
}