
package webproject.dataaccess;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import webproject.Application;
import webproject.Models.Reservable;
import webproject.Models.Reservation;

/**
 *
 * @author Ilmari Mäntysaari
 */
@Component
public class ReservableRepository {
    
    public void save(Reservable reservable) throws Exception{
        
        Session session = Application.sessionFactory.openSession();
        Hibernate.initialize(reservable);
        session.beginTransaction();
        session.save(reservable);
        session.getTransaction().commit();
        
        session.close();
    }
    
    public Reservable get(int id){
        Session session = Application.sessionFactory.openSession();
        Reservable item = session.get(Reservable.class, id);
        Hibernate.initialize(item.getReservations());
        Hibernate.initialize(item.getOwner());
        for(Reservation res : item.getReservations()){
            Hibernate.initialize(res.getReserver());
            Hibernate.initialize(res.getReservationItem());
        }
        session.close();
        return item;
    }
    
    public List<Reservable> getAll(){
        Session session = Application.sessionFactory.openSession();
        List<Reservable> items = session.createCriteria(Reservable.class).list();
        
        items.stream().forEach(x -> Hibernate.initialize(x.getReservations()));
        
        session.close();
        return items;
    }
    
}
