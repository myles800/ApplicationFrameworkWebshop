package ehb.applicationframeworkwebshop.Model;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String productNummer;
    private double prijs;
    @ManyToOne()
    @JoinColumn(name="categorie_id")
    private Categorie categorie;
    @OneToOne(mappedBy = "product")
    private Products products;
}
