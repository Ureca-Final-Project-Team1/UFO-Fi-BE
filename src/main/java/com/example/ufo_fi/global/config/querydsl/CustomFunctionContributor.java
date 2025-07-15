package com.example.ufo_fi.global.config.querydsl;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;

import static org.hibernate.type.StandardBasicTypes.INTEGER;

public class CustomFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        SqmFunctionRegistry registry = functionContributions.getFunctionRegistry();

        // bitand(a, b) → a & b
        registry.registerPattern(
                "bitand",
                "(?1 & ?2)",
                functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(INTEGER)
        );
    }
}
