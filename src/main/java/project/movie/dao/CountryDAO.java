package project.movie.dao;

import project.movie.Country;
import org.hibernate.SessionFactory;

public class CountryDAO extends GenericDAO<Country> {

    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }

}
