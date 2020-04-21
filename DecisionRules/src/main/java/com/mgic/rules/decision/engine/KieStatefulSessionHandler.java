package com.mgic.rules.decision.engine;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieSession;

public class KieStatefulSessionHandler implements KieSessionHandler<KieSession> {
	private List<Object> inputFacts;
    private List<Object> allSessionObjects = new ArrayList<Object>();

    public KieStatefulSessionHandler(final List<Object> inputFacts) {
        this.inputFacts = inputFacts;
    }

    @Override
    public void preFiring(final KieSession session) {
        for (Object fact : inputFacts) {
            session.insert(fact);
        }
    }

    @Override
    public void postFiring(final KieSession session) {
        allSessionObjects.addAll(session.getObjects());
    }

    public List<Object> getAllSessionObjects() {
        return allSessionObjects;
    }
}
