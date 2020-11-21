package ImageHoster.repository;

import ImageHoster.model.Comment;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class CommentRepository {

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Comment addComment(Comment comment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction  transaction = em.getTransaction();

        try {
            transaction.begin();

        } catch(Exception e) {
            transaction.rollback();
            em.persist(comment);
            transaction.rollback();
        }
        return comment;
    }

//    public List<Comment> getAllComments()
}
