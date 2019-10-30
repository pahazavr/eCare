package tsystems.javaschool.eCare.model;

import javax.persistence.*;

@Entity
@Table(name = "tariffs")
//@NamedQueries(
//        {
//                @NamedQuery(name = "Tariff.getAllTariffs", query = "SELECT t FROM Tariff t"),
//                @NamedQuery(name = "Tariff.deleteAllTariffs", query = "DELETE FROM Tariff"),
//                @NamedQuery(name = "Tariff.size", query="SELECT count(t) FROM Tariff t")
//        })
public class Tariff {

    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tariff_title")
    private String title;

    @Column(name = "tariff_price")
    private int price;

    public Tariff() {
    }

    public Tariff(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Tariff [title=" + title + ", price=" + price + "]";
    }
}
