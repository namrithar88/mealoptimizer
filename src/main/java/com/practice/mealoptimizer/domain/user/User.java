package com.practice.mealoptimizer.domain.user;

import com.practice.mealoptimizer.domain.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name="users")
@NamedEntityGraph(name = "User.roles",
        attributeNodes = { @NamedAttributeNode("roles"),
                           @NamedAttributeNode("nutrientMinLimits"),
                           @NamedAttributeNode("nutrientMaxLimits") }
)
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique=true)
    private String username;

    private String password;

    @Column(unique=true)
    private String email;

    private String preferredDietType;

    private String firstName;

    private String lastName;

    private String address;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_nutrient_min_limits",
            joinColumns = { @JoinColumn(name="user_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name="nutrient_name")
    @Column(name="nutrient_min_limit")
    private Map<String, Integer> nutrientMinLimits;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_nutrient_max_limits",
            joinColumns = { @JoinColumn(name="user_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name="nutrient_name")
    @Column(name="nutrient_max_limit")
    private Map<String, Integer> nutrientMaxLimits;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Order order;

    public User(String username, String password, String email, String preferredDietType, String firstName, String lastName, String address, Map<String, Integer> nutrientMinLimits, Map<String, Integer> nutrientMaxLimits, Set<Role> roles, Order order) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.preferredDietType = preferredDietType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.nutrientMinLimits = nutrientMinLimits;
        this.nutrientMaxLimits = nutrientMaxLimits;
        this.roles = roles;
        this.order = order;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferredDietType() {
        return preferredDietType;
    }

    public void setPreferredDietType(String preferredDietType) {
        this.preferredDietType = preferredDietType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Integer> getNutrientMinLimits() {
        return nutrientMinLimits;
    }

    public void setNutrientMinLimits(Map<String, Integer> nutrientMinLimits) {
        this.nutrientMinLimits = nutrientMinLimits;
    }

    public Map<String, Integer> getNutrientMaxLimits() {
        return nutrientMaxLimits;
    }

    public void setNutrientMaxLimits(Map<String, Integer> nutrientMaxLimits) {
        this.nutrientMaxLimits = nutrientMaxLimits;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}