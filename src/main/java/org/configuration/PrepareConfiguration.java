package org.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import project.movie.*;
import project.movie.dao.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PrepareConfiguration {
    private final SessionFactory sessionFactory;

    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public PrepareConfiguration() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "password");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        sessionFactory = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Store.class)
                .addAnnotatedClass(Staff.class)
                .addProperties(properties)
                .buildSessionFactory();
        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public void newFilmAvailable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction(); // first we need to create language because it depends on it
            Language language = languageDAO.getItems(0, 20).stream().unordered().findAny().get();

            List<Category> categories = categoryDAO.getItems(0, 5);
            List<Actor> actors = actorDAO.getItems(0, 7);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setCategories(new HashSet<>(categories));
            film.setTitle("Hunger Games 3");
            film.setLanguage(language);
            film.setRentalDuration((byte) 12);
            film.setRentalRate(BigDecimal.valueOf(14));
            film.setReplacementCost(BigDecimal.valueOf(15));
            film.setYear(Year.now());
            film.setSpecialFeature(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
            film.setRating(Rating.NC17);
            filmDAO.save(film);

            session.getTransaction().commit();
        }
    }

    public void customerBoughtInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = filmDAO.getValidFilm();
            filmDAO.save(film);
            Store store = storeDAO.getItems(0, 1).get(0); // it is enough just to get them no need to set or modify
            Inventory inventory = new Inventory();
            inventory.setStore(store);
            inventory.setFilm(film);

            inventoryDAO.save(inventory);

            Staff staff = store.getStoreStaff();

            staffDAO.save(staff);

            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setAmount(BigDecimal.valueOf(20));
            payment.setDate(LocalDateTime.now());
            payment.setRental(rental);
            payment.setCustomer(customer);
            payment.setStaff(staff);
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    public void customerReturnFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getNotReturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    public Customer addNewCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Store store = storeDAO.getItems(0, 1).get(0);

            City city = cityDAO.getByName("La Paz");

            Address address = createAddress("Main street, 12", "999-555-111-222", city, "Main District 2");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setActive(true);
            customer.setFirstName("John");
            customer.setLastName("Smith");
            customer.setStore(store);
            customer.setAddress(address);

            customerDAO.save(customer);
            session.getTransaction().commit();
            return customer;
        }
    }

    public Address createAddress(String street, String phone, City city, String district) {
        Address address = new Address();
        address.setAddress(street);
        address.setPhone(phone);
        address.setCity(city);
        address.setDistrict(district);
        return address;
    }
}
