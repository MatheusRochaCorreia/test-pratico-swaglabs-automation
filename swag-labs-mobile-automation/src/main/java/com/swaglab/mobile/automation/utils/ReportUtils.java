package com.swaglab.mobile.automation.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.swaglab.mobile.automation.core.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportUtils.class);
    private static final String REPORT_DIR = "target/evidence";
    private static Document document;
    private static final Map<String, List<ScreenshotData>> scenarioScreenshots = new HashMap<>();
    private static String currentScenario;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    static {
        createReportDirectory();
    }

    /**
     * Classe para armazenar dados do screenshot
     */
    private static class ScreenshotData {
        private final byte[] screenshot;
        private final String description;

        public ScreenshotData(byte[] screenshot, String description) {
            this.screenshot = screenshot;
            this.description = description;
        }

        public byte[] getScreenshot() {
            return screenshot;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Cria o diretório para armazenar os relatórios
     */
    private static void createReportDirectory() {
        try {
            Path path = Paths.get(REPORT_DIR);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                LOGGER.info("Diretório de relatórios criado: {}", path.toAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.error("Erro ao criar diretório de relatórios: {}", e.getMessage());
        }
    }

    /**
     * Define o cenário atual
     * @param scenarioName Nome do cenário
     */
    public static void setCurrentScenario(String scenarioName) {
        currentScenario = scenarioName;
        if (!scenarioScreenshots.containsKey(scenarioName)) {
            scenarioScreenshots.put(scenarioName, new ArrayList<>());
        }
        LOGGER.info("Cenário atual definido: {}", scenarioName);
    }

    /**
     * Tira um screenshot e armazena para o relatório
     * @param description Descrição do screenshot
     */
    public void takeScreenshot(String description) {
        try {
            // Certifique-se de que temos um cenário atual definido
            if (currentScenario == null) {
                LOGGER.warn("Nenhum cenário definido para o screenshot. Usando 'undefined'");
                currentScenario = "undefined";
                scenarioScreenshots.put(currentScenario, new ArrayList<>());
            }
            
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            
            // Armazena o screenshot no mapa do cenário atual
            scenarioScreenshots.get(currentScenario).add(new ScreenshotData(screenshot, description));
            LOGGER.info("Screenshot capturado para o cenário '{}': {}", currentScenario, description);
        } catch (Exception e) {
            LOGGER.error("Erro ao capturar screenshot: {}", e.getMessage());
        }
    }

    /**
     * Gera o relatório PDF para um cenário específico
     * @param scenarioName Nome do cenário
     */
    public static void generatePDFReportForScenario(String scenarioName) {
        if (!scenarioScreenshots.containsKey(scenarioName) || scenarioScreenshots.get(scenarioName).isEmpty()) {
            LOGGER.warn("Não há screenshots para gerar o relatório PDF do cenário: {}", scenarioName);
            return;
        }

        // Normaliza o nome do cenário para uso em nome de arquivo
        String normalizedName = scenarioName.replaceAll("[^a-zA-Z0-9.-]", "_");
        String reportFileName = normalizedName + "_" + DATE_FORMAT.format(new Date());
        String pdfPath = REPORT_DIR + "/" + reportFileName + ".pdf";
        
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

            // Adiciona título ao relatório
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph title = new Paragraph("Relatório de Teste - SwagLabs Mobile", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            // Adiciona nome do cenário
            Font scenarioFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
            Paragraph scenarioTitle = new Paragraph("Cenário: " + scenarioName, scenarioFont);
            scenarioTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(scenarioTitle);
            
            // Adiciona data ao relatório
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);
            Paragraph date = new Paragraph("Data de execução: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), dateFont);
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);
            
            document.add(Chunk.NEWLINE);
            
            // Adiciona os screenshots ao relatório
            List<ScreenshotData> screenshots = scenarioScreenshots.get(scenarioName);
            for (ScreenshotData data : screenshots) {
                // Adiciona descrição do screenshot
                Font descFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
                Paragraph desc = new Paragraph(data.getDescription(), descFont);
                desc.setSpacingBefore(10);
                document.add(desc);
                
                // Adiciona o screenshot com tamanho ajustado
                Image image = Image.getInstance(data.getScreenshot());
                // Ajusta a escala para capturar mais da tela (reduzindo o zoom)
                float scalePercent = 30; 
                image.scalePercent(scalePercent);
                image.setAlignment(Element.ALIGN_CENTER);
                document.add(image);
                
                document.add(Chunk.NEWLINE);
            }
            
            document.close();
            LOGGER.info("Relatório PDF para o cenário '{}' gerado com sucesso: {}", scenarioName, pdfPath);
        } catch (Exception e) {
            LOGGER.error("Erro ao gerar relatório PDF para o cenário '{}': {}", scenarioName, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gera relatórios PDF para todos os cenários que tiveram screenshots
     */
    public static void generatePDFReportsForAllScenarios() {
        LOGGER.info("Gerando relatórios PDF para todos os cenários...");
        
        if (scenarioScreenshots.isEmpty()) {
            LOGGER.warn("Não há screenshots para gerar relatórios PDF");
            return;
        }
        
        for (String scenarioName : scenarioScreenshots.keySet()) {
            generatePDFReportForScenario(scenarioName);
        }
        
        LOGGER.info("Relatórios PDF gerados para {} cenários", scenarioScreenshots.size());
    }
    
    /**
     * Método original para compatibilidade com o código existente
     * @deprecated Use generatePDFReportsForAllScenarios() em vez disso
     */
    @Deprecated
    public static void generatePDFReport() {
        generatePDFReportsForAllScenarios();
    }
}