package com.github.rjansem.microservices.training.commons.domain;

/**
 * Mapper mappant les beans PBI vers des beans PIB
 *
 * @param <I> type du bean en entrée
 * @param <O> type du bean en sortie
 * @author mbouhamyd
 */
@FunctionalInterface
public interface PbiToPbiMapper<I extends PbiBean, O extends PbiBean> {

    /**
     * Mappe un bean provenant d'PBI vers un bean à destination de PBI
     *
     * @param input bean PBI
     * @return bean PBI
     */
    O map(I input);

}
