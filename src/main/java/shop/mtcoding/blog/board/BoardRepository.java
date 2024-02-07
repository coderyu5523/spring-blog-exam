package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Controller
public class BoardRepository {


    private final EntityManager em;
    @Transactional
    public void save(BoardRequest.saveDTO requestDTO) {
        Query query = em.createNativeQuery("insert into board_tb(author,title,content) values (?,?,?)");
        query.setParameter(1,requestDTO.getAuthor());
        query.setParameter(2,requestDTO.getTitle());
        query.setParameter(3,requestDTO.getContent());
        query.executeUpdate();

    }
}
