package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


@Entity
@NamedQuery(name = "Country.deleteAllRows", query = "DELETE from Country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int countryCode;
    
    public Country() {
    }

    public Country(String name, int countryCode) {
        this.name = name;
        this.countryCode = countryCode;
    }
       
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCountryCode() {
        return countryCode;
    }    

}
