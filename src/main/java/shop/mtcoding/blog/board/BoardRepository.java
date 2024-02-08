package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

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

    public List<Board> findAll(int page) {
        final int COUNT = 5;
        int value = page * COUNT ;
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?",Board.class);
        query.setParameter(1,value);
        query.setParameter(2,COUNT);
        List<Board> boardList = query.getResultList();
        return boardList;
    }
    public Board find(int id){
        Query query = em.createNativeQuery("select * from board_tb where id = ?",Board.class);
        query.setParameter(1,id);
        Board board = (Board) query.getSingleResult();
        return board;
    }

    @Transactional
    public void delete(int id) {
            Query query = em.createNativeQuery("delete from board_tb where id =?");
            query.setParameter(1,id);
            query.executeUpdate();
    }

    @Transactional
    public void update(BoardRequest.saveAgainDTO requestDTO,int id) {
        Query query = em.createNativeQuery("update board_tb set author =?, title = ?, content = ? where id = ? ");
        query.setParameter(1,requestDTO.getAuthor());
        query.setParameter(2,requestDTO.getTitle());
        query.setParameter(3,requestDTO.getContent());
        query.setParameter(4,id);
        query.executeUpdate();

    }

    public BigInteger count(){
        Query query = em.createNativeQuery("select count(*) from board_tb");
        BigInteger count = (BigInteger) query.getSingleResult();
        return count ;

    }

}
