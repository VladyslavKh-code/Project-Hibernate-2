package project.movie.dao;

import project.movie.Actor;
import org.hibernate.SessionFactory;

public class ActorDAO extends GenericDAO<Actor>{

    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
