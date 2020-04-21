package com.mgic.rules.decision.engine;

public interface KieSessionHandler<T> {

    
    public void preFiring(T session);

    
    public void postFiring(T session);

}
