package com.github.rjansem.microservices.training.transaction.mapper.commun;

import org.springframework.hateoas.PagedResources;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;

/**
 * Utilitaire pour la gestion de la pagination
 *
 * @author rjansem
 * @author aazzerrifi
 */
public class PaginationUtils {

    /**
     * Renvoie les informations de pagination HATEOAS à partir d'une liste
     *
     * @param objects    : liste des éléments
     * @param pageNumber : numéro de la page
     * @param pageSize   : nombre d'éléments par page
     * @return un {@link org.springframework.hateoas.PagedResources.PageMetadata}
     */
    public static <T> PagedResources.PageMetadata generatePaginationMetadata(List<T> objects, int pageNumber, int pageSize) {
        int totalElements = objects == null ? 0 : objects.size();
        int totalPages = 0;
        if (totalElements == 0 || pageSize == 0) {
            return new PagedResources.PageMetadata(pageSize, pageNumber, 0, 0);
        } else {
            int nbrePages = totalElements / pageSize;
            if (totalElements % pageSize == 0) {
                totalPages = nbrePages;
            } else {
                totalPages = nbrePages + 1;
            }
        }
        return new PagedResources.PageMetadata(pageSize, pageNumber, totalElements, totalPages);
    }

    /**
     * Pagine une liste d'objets
     *
     * @param list       la liste d'objets
     * @param pageNumber : numéro de la page
     * @param pageSize   : nombre d'éléments par page
     * @return la page demandée de la liste.
     */
    public static <T> List<T> paginate(List<T> list, int pageNumber, int pageSize) {
        if (pageNumber == 0 || pageSize == 0) {
            throw new ValidationException("page/size ne peut pas égale à 0");
        }
        int rest = list.size() % pageSize;
        int max = rest == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
        if (pageNumber > max && pageNumber != 1) {
            throw new ValidationException("page demandée n'existe pas");
        }

        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        int firstElement = (pageNumber - 1) * pageSize;
        int lastElement = pageNumber * pageSize;
        int size = list.size();
        if (lastElement > size) {
            lastElement = size;
        }
        Objects.requireNonNull((size < firstElement) || firstElement == lastElement ? null : size);
        return list.subList(firstElement, lastElement);
    }
}
