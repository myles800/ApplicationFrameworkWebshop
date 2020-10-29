package ehb.applicationframeworkwebshop.Model;

import org.springframework.format.datetime.joda.ReadableInstantPrinter;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "email is required")
    @Column(name = "email")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password needs to be at least 8 characters")
    private String password;
    @OneToOne(mappedBy = "user")
    private Cart cart;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection <Role> roles;
    public User(int id,String name,String email,String password) {
        this.id=id;
        this.name=name;
        this.email=email;
        this.password=password;
    }
    public User(String name, String email, String password, Collection < Role > roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
