package com.mgic.rules.decision.engine;


import org.apache.commons.lang.StringUtils;
//import org.kie.api.KieBase;
import org.kie.api.definition.type.FactType;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
//import com.mgic.rules.decision.utils.PropertyBean;
import com.mgic.rules.decision.utils.DecisionConstant;


@Component
@DependsOn("kieBaseLoader")
public class DecisionRulesEngine implements FactTypeProvider {

	 	@Autowired
	    private KieBaseLoader kieBaseLoader;

	    private boolean enableDecisionLogger = true;

	    private boolean enableDecisionConsoleLogger = true;

	    @Override
	    public FactType getFactType(String pkgName, String typeName){
	        return this.kieBaseLoader.getKieBase(pkgName).getFactType(pkgName, typeName);
	    }

	    private KieSession getNewDecisionStatefulSession(String pkgName) {
	        return this.kieBaseLoader.getKieBase(pkgName).newKieSession();
	    }

	    public void executeRules(KieSessionHandler<KieSession> callback) {
	    	executeRules(callback, null);
	    }

	    public void executeRules(KieSessionHandler<KieSession> kieSessionHandler, String packageName) {
	        KieSession kieSession = null;
	        KieRuntimeLogger kieRuntimeLogger = null;
	        long startTime = System.currentTimeMillis();
	        try {
	        	packageName = StringUtils.isEmpty(packageName) ? DecisionConstant.MGIC_PACKAGE : packageName;
	        	kieSession = getNewDecisionStatefulSession(packageName);
	            enableDecisionDebugLogger(kieSession, kieRuntimeLogger);

	            kieSessionHandler.preFiring(kieSession);
	            startRuleFlowProcess(kieSession, packageName);

	            kieSession.fireAllRules();
	            kieSessionHandler.postFiring(kieSession);
	            long endTime = System.currentTimeMillis();
	            long totalTimeTaken = endTime - startTime;
	        } finally {
	            if (kieSession != null) {
	            	kieSession.dispose();
	            }
	            if (kieRuntimeLogger != null) {
	            	kieRuntimeLogger.close();
	            }
	        }
	    }

	    private void startRuleFlowProcess(KieSession kieSession, String packageName) {
	        if(StringUtils.equals(DecisionConstant.MGIC_PACKAGE, packageName)) {
	            kieSession.startProcess("com.myteam.mgic.decisionflow");
	        //    kieSession.startProcess("MGIC.DecisionFlow");
	           
	        }
	    }

	    private KieRuntimeLogger enableDecisionDebugLogger(KieSession kieSession, KieRuntimeLogger kieRuntimeLogger) {
	        if (enableDecisionLogger) {
	        	
	        	System.out.println("Debug logs---"+KnowledgeRuntimeLoggerFactory.newFileLogger(kieSession, DecisionRulesEngine.class.getName()));
	        
	        }
	        if (enableDecisionConsoleLogger) {
	        	
	        	System.out.println("Debug logs---"+KnowledgeRuntimeLoggerFactory.newConsoleLogger(kieSession));
	            
	        }
	        return kieRuntimeLogger;
	    }

	}
