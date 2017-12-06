package com.github.rjansem.microservices.training.commons.domain;

/**
 * Mapper mappant deux beans EFS vers un bean PIB
 *
 * @param <I> type du bean en entrée
 * @param <J> type du bean en entrée
 * @param <O> type du bean en sortie
 * @author rjansem
 */
@FunctionalInterface
public interface TwoEfsToPibMapper<I, J extends EfsBean, O extends PbiBean> {

    /**
     * Mappe deux bean provenant d'EFS vers un bean à destination de PIB
     *
     * @param firstInput  bean EFS
     * @param secondInput bean EFS
     * @return bean PIB
     */
    O map(I firstInput, J secondInput);

}
