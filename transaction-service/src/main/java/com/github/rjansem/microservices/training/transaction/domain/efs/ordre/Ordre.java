package com.github.rjansem.microservices.training.transaction.domain.efs.ordre;

import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;
import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Bean représentant un ordre
 *
 * @author mbouhamyd
 */
public class Ordre implements Comparable<Ordre>, EfsBean {

    private String id;

    private CompteOrdre compteEmetteur;

    private String dateExecutionSouhaitee;

    private String codeApplication;

    private String statut;

    private String montant;

    private String codeDevise;

    private String dateCreation;

    private String media;

    private String codeOperation;

    public Ordre(String id) {
        this.id = id;
    }

    public Ordre() {
        //Jackson
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CompteOrdre getCompteEmetteur() {
        return compteEmetteur;
    }

    public void setCompteEmetteur(CompteOrdre compteEmetteur) {
        this.compteEmetteur = compteEmetteur;
    }

    public String getDateExecutionSouhaitee() {
        return dateExecutionSouhaitee;
    }

    public void setDateExecutionSouhaitee(String dateExecutionSouhaitee) {
        this.dateExecutionSouhaitee = dateExecutionSouhaitee;
    }

    public String getCodeApplication() {
        return codeApplication;
    }

    public void setCodeApplication(String codeApplication) {
        this.codeApplication = codeApplication;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordre ordre = (Ordre) o;
        return Objects.equals(id, ordre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Ordre other) {

       LocalDate current = LocalDate.parse(this.getDateExecutionSouhaitee(), DateTimeFormatter.ofPattern(Utils.EFS_DATE_FORMAT));
        LocalDate to = LocalDate.parse(other.getDateExecutionSouhaitee(), DateTimeFormatter.ofPattern(Utils.EFS_DATE_FORMAT));
        int compareDate = to.compareTo(current);
        LocalDate operationcurrent = LocalDate.parse(this.getDateCreation(), DateTimeFormatter.ofPattern(Utils.EFS_DATE_FORMAT));
        LocalDate operationto = LocalDate.parse(other.getDateCreation(), DateTimeFormatter.ofPattern(Utils.EFS_DATE_FORMAT));
        int compareoperationDate = operationto.compareTo(operationcurrent);
        int ClientName=this.getCompteEmetteur().getIntitule().compareTo(other.getCompteEmetteur().getIntitule());
        if (compareDate == 0) {
            if (compareoperationDate == 0) {
                    if (ClientName == 0) {
                    return this.getId().compareTo(other.getId());
                } else {
                    return ClientName;
                }
            } else {
                return compareoperationDate;
            }
        } else {
            return compareDate;
        }
        //return this.getId().compareTo(other.getId());
    }

    public int compareTo(Ordre ordre , Ordre other) {
       return ordre.getId().compareTo(other.getId());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }
}
