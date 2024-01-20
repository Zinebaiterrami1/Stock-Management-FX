package app;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private final SimpleIntegerProperty idf;
    private final SimpleStringProperty nomf;
    private final SimpleStringProperty prenomf;
    private final SimpleStringProperty tele;

    public Person(int idf, String nomf, String prenomf, String tele) {
        this.idf = new SimpleIntegerProperty(idf);
        this.nomf = new SimpleStringProperty(nomf);
        this.prenomf = new SimpleStringProperty(prenomf);
        this.tele = new SimpleStringProperty(tele);
    }

    // Méthodes d'accès (getters)
    public int getIdf() {
        return idf.get();
    }

    public String getNomf() {
        return nomf.get();
    }

    public String getPrenomf() {
        return prenomf.get();
    }

    public String getTele() {
        return tele.get();
    }

    // Méthodes de propriété (Property methods)
    public SimpleIntegerProperty idfProperty() {
        return idf;
    }

    public SimpleStringProperty nomfProperty() {
        return nomf;
    }

    public SimpleStringProperty prenomfProperty() {
        return prenomf;
    }

    public SimpleStringProperty teleProperty() {
        return tele;
    }

    public void setNomf(String newNom) {
        nomf.set(newNom);
    }

    public void setPrenomf(String newPrenom) {
        prenomf.set(newPrenom);
    }

    public void setTele(String newTele) {
        tele.set(newTele);
    }
}
