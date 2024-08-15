package project.movie.dao;

import project.movie.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class FilmDAO extends GenericDAO<Film>{

    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getValidFilm() {
        String hql = "select f from Film f " +
                "left join Inventory i on i.film = f " +
                "left join Rental r on r.inventory = i " +
                "where r.returnDate is not null or r.rentalId is null";

        Query<Film> query = getCurrentSession().createQuery(hql);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
