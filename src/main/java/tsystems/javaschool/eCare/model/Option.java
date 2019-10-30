package tsystems.javaschool.eCare.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "options")
//@NamedQueries(
//        {
//                @NamedQuery(name = "Option.getAllOptions", query = "SELECT o FROM Option o"),
//                @NamedQuery(name = "Option.findOptionByTitleAndTariffId", query = "SELECT o FROM Option o WHERE o.title = :title AND o.tariff.id = :id"),
//                @NamedQuery(name = "Option.getAllOptionsForTariff", query = "SELECT o FROM Option o WHERE o.tariff.id = :id"),
//                @NamedQuery(name = "Option.deleteAllOptions", query = "DELETE FROM Option"),
//                @NamedQuery(name = "Option.deleteAllOptionsForTariff", query = "DELETE FROM Option WHERE tariff.id = :id"),
//                @NamedQuery(name = "Option.size", query="SELECT count(op) FROM Option op")
//        })
public class Option {
    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_title")
    private String title;

    @Column(name = "option_price")
    private int price;

    @Column(name = "option_connection_price")
    private int costOfConnection;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToMany(mappedBy = "options")
    private Set<Contract> contracts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name="dependent_options",
                    joinColumns={ @JoinColumn(name="option_id", referencedColumnName="option_id") },
                    inverseJoinColumns={ @JoinColumn(name="dependent_option_id", referencedColumnName="option_id") }
            )
    private Set<Option> dependentOptions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name= "incompatible_options",
                    joinColumns={ @JoinColumn(name="option_id", referencedColumnName="option_id") },
                    inverseJoinColumns={ @JoinColumn(name="incompatible_option_id", referencedColumnName="option_id") }
            )
    private Set<Option> incompatibleOptions = new HashSet<>();

    public Option() {
    }

    public Option(String title, int price, int costOfConnection, Tariff tariff, Set<Contract> contracts,
                  Set<Option> dependentOptions, Set<Option> incompatibleOptions) {
        this.title = title;
        this.price = price;
        this.costOfConnection = costOfConnection;
        this.tariff = tariff;
        this.contracts = contracts;
        this.dependentOptions = dependentOptions;
        this.incompatibleOptions = incompatibleOptions;
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

    public int getCostOfConnection() {
        return costOfConnection;
    }

    public void setCostOfConnection(int costOfConnection) {
        this.costOfConnection = costOfConnection;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public Set<Option> getDependentOptions() {
        return dependentOptions;
    }

    public void setDependentOptions(Set<Option> dependentOptions) {
        this.dependentOptions = dependentOptions;
    }

    public Set<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(Set<Option> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }
}
