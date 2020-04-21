package com.mgic.rules.decision.engine;

import org.kie.api.definition.type.FactType;

public interface FactTypeProvider {
	FactType getFactType(String packageName, String typeName);

}
