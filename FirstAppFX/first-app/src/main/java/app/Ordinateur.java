package app;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Ordinateur {
    private final SimpleIntegerProperty ido;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty ram;
    private final SimpleStringProperty rom;
    private final SimpleStringProperty brand;
    private final SimpleStringProperty graphic;
    private final SimpleStringProperty numserie;
    private final SimpleStringProperty prix;

    public Ordinateur(int ido, String nom, String ram, String rom, String brand, String graphic, String numserie, String prix) {
        this.ido = new SimpleIntegerProperty(ido);
        this.nom = new SimpleStringProperty(nom);
        this.ram = new SimpleStringProperty(ram);
        this.rom = new SimpleStringProperty(rom);
        this.brand = new SimpleStringProperty(brand);
        this.graphic = new SimpleStringProperty(graphic);
        this.numserie = new SimpleStringProperty(numserie);
        this.prix = new SimpleStringProperty(prix);
    }

    // Méthodes d'accès (getters)
    public int getIdo() {
        return ido.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getRam() {
        return ram.get();
    }

    public String getRom() {
        return rom.get();
    }

    public String getBrand() {
        return brand.get();
    }

    public String getGraphic() {
        return graphic.get();
    }

    public String getNumserie() {
        return numserie.get();
    }

    public String getPrix() {
        return prix.get();
    }

    // Méthodes de propriété (Property methods)
    public SimpleIntegerProperty idoProperty() {
        return ido;
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public SimpleStringProperty ramProperty() {
        return ram;
    }

    public SimpleStringProperty romProperty() {
        return rom;
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public SimpleStringProperty graphicProperty() {
        return graphic;
    }

    public SimpleStringProperty numserieProperty() {
        return numserie;
    }

    public SimpleStringProperty prixProperty() {
        return prix;
    }

    public void setNom(String newNom) {
        nom.set(newNom);
    }

    public void setRam(String newRam) {
        ram.set(newRam);
    }

    public void setRom(String newRom) {
        rom.set(newRom);
    }

    public void setBrand(String newBrand) {
        brand.set(newBrand);
    }

    public void setGraphic(String newGraphic) {
        graphic.set(newGraphic);
    }

    public void setNumserie(String newNumSerie) {
        numserie.set(newNumSerie);
    }

    public void setPrix(String newPrix) {
        prix.set(newPrix);
    }
}
