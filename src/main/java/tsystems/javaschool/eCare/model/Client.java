package tsystems.javaschool.eCare.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedQueries(
        {
                @NamedQuery(name = "Client.getAllClients", query = "SELECT cl FROM Client cl WHERE Role.title = 'ROLE_USER'"),
                @NamedQuery(name = "Client.findClientByLoginAndPassword", query = "SELECT cl FROM Client cl WHERE cl.email = :login AND cl.password = :password"),
                @NamedQuery(name = "Client.findClientByNumber", query = "SELECT cnt.client FROM Contract cnt WHERE cnt.number = :number"),
                @NamedQuery(name = "Client.findClientByEmail", query = "SELECT cl FROM Client cl WHERE cl.email = :email"),
                @NamedQuery(name = "Client.deleteAllClients", query = "DELETE FROM Client WHERE Role.title = 'ROLE_USER'"),
                @NamedQuery(name = "Client.size", query="SELECT count(cl) FROM Client cl WHERE Role.title = 'ROLE_USER'")
        })
public class Client {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "passport")
    private int passport;

    @Column(name = "address")
    private String address;

    @Column(name = "balance")
    private int balance;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Contract> contracts = new HashSet<>();

    public Client() {
    }

    public Client(String name, String surname, Date birthDate, int passport, String address, int balance, String email,
                  String password, String confirmPassword, Set<Role> roles, Set<Contract> contracts) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.balance = balance;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
        this.contracts = contracts;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getPassport() {
        return passport;
    }

    public void setPassport(int passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName(){
        return name+" "+surname;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return "Client [name=" + name + ", surname=" + surname + ", birthdate=" + birthDate + ", passport="+passport+"," +
                " address=" + address + ", email=" + email + ", password="+password+"]";
    }
}
