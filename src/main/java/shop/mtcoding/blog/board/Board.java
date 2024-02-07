package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Data;

@Data //게터세터
@Entity  // 이게 붙으면 테이블 생성됨
@Table(name="board_tb") // 테이블명

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int id ;
    private String author ;
    private String title;
    private String content;

}
