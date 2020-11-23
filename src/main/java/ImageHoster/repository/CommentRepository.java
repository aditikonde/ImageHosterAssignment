package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
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

    //The method receives the Comment object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public Comment addComment(Comment comment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction  transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(comment);
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
        }
        return comment;
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch the image from the database with corresponding id
    //Returns all comments for a given image fetched from the database
    public List<Comment> getAllComments(Image image){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Comment> query = em.createQuery("SELECT c from Comment c where c.image =:image"
                        ,Comment.class);
        List<Comment> resultList = query.setParameter("image",image).getResultList();
        return resultList;
    }
}
