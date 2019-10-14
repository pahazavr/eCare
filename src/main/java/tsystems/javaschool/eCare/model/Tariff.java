package tsystems.javaschool.eCare.model;

import javax.persistence.*;

@Entity
@Table(name = "tariffs")
public class Tariff {

    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    public long getId() {
        return id;
    }

    public void setId(int id) {
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
