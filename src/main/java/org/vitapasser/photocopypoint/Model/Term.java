package org.vitapasser.photocopypoint.Model;

import java.util.List;

public record Term(Integer term) {

    public static Term getTerm(TypeList typeList, List<Type> types) {
        return types.stream()
                .map(type -> new Term(typeList.getTimePerType(type.name()).getValue() * type.count().count()))
                .reduce((term1, term2) -> new Term(term1.getValue() + term2.getValue()))
                .orElse(new Term(0));
    }

    public Integer getValue() {
        return term;
    }
}
