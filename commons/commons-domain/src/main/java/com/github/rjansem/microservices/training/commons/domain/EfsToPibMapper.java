package com.github.rjansem.microservices.training.commons.domain;

/**
 * Mapper mappant les beans EFS vers des beans PIB
 *
 * @param <I> type du bean en entrée
 * @param <O> type du bean en sortie
 * @author jntakpe
 */
@FunctionalInterface
public interface EfsToPibMapper<I extends EfsBean, O extends PbiBean> {

    /**
     * Mappe un bean provenant d'EFS vers un bean à destination de PIB
     *
     * @param input bean EFS
     * @return bean PIB
     */
    O map(I input);

}
