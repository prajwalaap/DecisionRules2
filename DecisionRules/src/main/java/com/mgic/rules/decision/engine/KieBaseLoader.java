package com.mgic.rules.decision.engine;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.core.builder.conf.impl.DecisionTableConfigurationImpl;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.conf.MBeansOption;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.mgic.rules.decision.utils.DecisionConstant;



@Component
public class KieBaseLoader {

    
    private KieBase decisionTableKieBase;
   
    private KieBase decisionKieBase;

    private String resourcesPath = "drools/*";

    private String decisionVersion = "1.0.15";
    
    private String groupId="com.myteam";

    private String decisionArtifactId="MGIC";

    private String decisionKiebase = "decisionKieBase";

    private String decisionJarName = "mgic.jar";


    /**
     * Post construct.
     */
    @PostConstruct
    public void postConstruct() {
        try {
            updateResourcesToKieBase();
        } catch (IOException | EncryptedDocumentException | InvalidFormatException | DroolsParserException e) {
            throw new RuntimeException("Error at initializing kie base.", e);
        }
    }

    
    private void updateResourcesToKieBase() throws EncryptedDocumentException, InvalidFormatException, IOException, DroolsParserException {
    	System.out.println("Started loading Decision rules resources to Kie Base:");
        KnowledgeBuilderImpl knowledgeBuilder = new KnowledgeBuilderImpl();
        updateResourcesToKnowledgeBuilder(knowledgeBuilder);
        decisionTableKieBase = knowledgeBuilder.newKnowledgeBase(getKieBaseConfiguration());
        System.out.println("Decision rules resources are loaded successfully: to Kie Base ");
    }

   
    private KieBaseConfiguration getKieBaseConfiguration() {
        KieBaseConfiguration configuration = KieServices.Factory.get().newKieBaseConfiguration();
        configuration.setOption(MBeansOption.ENABLED);
        configuration.setOption(EventProcessingOption.STREAM);
        return configuration;
    }

   
    private void updateResourcesToKnowledgeBuilder(KnowledgeBuilderImpl knowledgeBuilder)
            throws IOException, InvalidFormatException, DroolsParserException {
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

        org.springframework.core.io.Resource[] resources = patternResolver.getResources(resourcesPath);

        for (org.springframework.core.io.Resource resource : resources) {
            if (resource != null 
                    && StringUtils.isNotBlank(FilenameUtils.getExtension(resource.getFilename()))) {
                switch (FilenameUtils.getExtension(resource.getFilename()).toUpperCase()) {               
//                case RulesConstant.EXCEL_DECISION_TABLE_TYPE_XLSX:
//                    updateDecisionTableResource(knowledgeBuilder, resource, DecisionTableInputType.XLSX);
//                    break;
                case DecisionConstant.KIE_JAR:
                    addKieJar(resource);
                    break;
                }
            }
        }
       
    }

   
/*    private void updateDecisionTableResource(KnowledgeBuilderImpl knowledgeBuilder,
                                    org.springframework.core.io.Resource decisionTableResource,
                                    DecisionTableInputType inputType)
                                throws IOException, InvalidFormatException, DroolsParserException {


        KieServices kieServices = KieServices.Factory.get();
        KieResources kieResources = kieServices.getResources();

        Resource resource = kieResources.newFileSystemResource(decisionTableResource.getFile());
        Workbook workbook = WorkbookFactory.create(decisionTableResource.getFile());

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            DecisionTableConfiguration dtc = new DecisionTableConfigurationImpl();
            dtc.setInputType(inputType);
            dtc.setWorksheetName(workbook.getSheetName(i));
            knowledgeBuilder.addPackageFromDecisionTable(resource, dtc);
        }
    }*/

   
    private void addKieJar(org.springframework.core.io.Resource kieJarFile) throws IOException {
        KieServices kieServices = KieServices.Factory.get();
        KieResources kieResources = kieServices.getResources();
        KieRepository kieRepository = kieServices.getRepository();

       // Resource resource = kieResources.newFileSystemResource(kieJarFile.getFile());        
        Resource resource = kieResources.newInputStreamResource(kieJarFile.getInputStream());

        if (StringUtils.equals(kieJarFile.getFilename(), decisionJarName)) {
            ReleaseId releaseId = new ReleaseIdImpl(groupId, decisionArtifactId, decisionVersion);
            kieRepository.addKieModule(resource);
            KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getKieModule(releaseId).getReleaseId());
            decisionKieBase = kieContainer.getKieBase(decisionKiebase);

        }
    }

    
    public KieBase getKieBase(String pkgName) {
        KieBase kieBase = null;
        switch (pkgName) {
        case DecisionConstant.MGIC_PACKAGE:
            kieBase = getDecisionKieBase();
            break;
        default:
            kieBase = getDecisionTableKieBase();
            break;
        }
        return kieBase;
    }

    
    public KieBase getDecisionTableKieBase() {
        return decisionTableKieBase;
    }
   	
 	 public KieBase getDecisionKieBase() {
        return decisionKieBase;
    }

}
