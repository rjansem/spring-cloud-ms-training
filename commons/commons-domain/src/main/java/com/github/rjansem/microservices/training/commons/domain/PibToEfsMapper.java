package com.github.rjansem.microservices.training.commons.domain;

/**
 * Mapper mappant les beans PBI vers des beans EFS
 *
 * @param <I> type du bean en entrée
 * @param <O> type du bean en sortie
 * @author rjansem
 */
@FunctionalInterface
public interface PibToEfsMapper<I extends PbiBean, O extends EfsBean> {

    /**
     * Mappe un bean provenant d'PBI vers un bean à destination de EFS
     *
     * @param input bean PBI
     * @return bean EFS
     */
    O map(I input);

}
