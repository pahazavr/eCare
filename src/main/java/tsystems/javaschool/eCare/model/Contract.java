package tsystems.javaschool.eCare.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contracts")
@NamedQueries(
        {
                @NamedQuery(name = "Contract.getAllContracts", query = "SELECT cnt FROM Contract cnt"),
                @NamedQuery(name = "Contract.findContractByNumber", query = "SELECT cnt FROM Contract cnt WHERE cnt.number = :number"),
                @NamedQuery(name = "Contract.getAllContractsForClient", query = "SELECT cnt FROM Contract cnt WHERE cnt.client.id = :id"),
                @NamedQuery(name = "Contract.deleteAllContracts", query="DELETE FROM Contract"),
                @NamedQuery(name = "Contract.deleteAllContractsForClient", query = "DELETE FROM Contract WHERE client.id = ?1"),
                @NamedQuery(name = "Contract.size", query="SELECT count(cnt) FROM Contract cnt")
        })
public class Contract {
    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Long number;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "blocked_by_client")
    private boolean isBlockedByClient = false;

    @Column(name = "blocked_by_operator")
    private boolean isBlockedByOperator = false;

    @ManyToMany
    @JoinTable(name = "connected_options",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options = new HashSet<>();

    public Contract() {
    }

    public Contract(Long number, Tariff tariff, Client client, boolean isBlockedByClient, boolean isBlockedByOperator,
                    Set<Option> options) {
        this.number = number;
        this.tariff = tariff;
        this.client = client;
        this.isBlockedByClient = isBlockedByClient;
        this.isBlockedByOperator = isBlockedByOperator;
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isBlockedByClient() {
        return isBlockedByClient;
    }

    public void setBlockedByClient(boolean blockedByClient) {
        isBlockedByClient = blockedByClient;
    }

    public boolean isBlockedByOperator() {
        return isBlockedByOperator;
    }

    public void setBlockedByOperator(boolean blockedByOperator) {
        isBlockedByOperator = blockedByOperator;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public void addOption(Option op) {
        this.options.add(op);
    }

    public void deleteOption(Option op) {
        this.options.remove(op);
    }
}
