package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardRepository boardRepository ;


    @GetMapping("/")
    public String index(HttpServletRequest request,@RequestParam(defaultValue = "0")int page) {
        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList", boardList);

        int currentPage = page;
        int nextPage = currentPage + 1;
        int prevPage = currentPage - 1;

        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);


        boolean first = (currentPage == 0 ? true : false);
        request.setAttribute("first", first);

        int totalPage= boardRepository.count();
        int totalCount = totalPage / 5;
        boolean last = (currentPage == totalCount ? true : false);


        List<Integer> numberList = new ArrayList<>();
        int allPage;
        if(totalPage%5==0){
            allPage = totalCount -1 ;
            for(int i=1;i<=allPage;i++){
              numberList.add(i);
              request.setAttribute("numberList",numberList);
            }
        }else if(totalPage%5!=0){
            allPage = totalCount ;
            for(int i=1;i<=allPage;i++){
                numberList.add(i);
                request.setAttribute("numberList",numberList);
            }

        }


        request.setAttribute("last", last);

        return "index";

    }
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id,HttpServletRequest request) {

        Board board = boardRepository.find(id);
        request.setAttribute("board",board);

        return "board/updateForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.saveDTO requestDTO,HttpServletRequest request){

        if(requestDTO.getTitle().length()>20){
            request.setAttribute("status",400);
            request.setAttribute("msg","제목은 20자를 넘을 수 없습니다.");
            return "error/40x";
        }

        if(requestDTO.getContent().length()>20){
            request.setAttribute("status",400);
            request.setAttribute("msg","내용은 20자를 넘을 수 없습니다.");
            return "error/40x";
        }


        boardRepository.save(requestDTO);

        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id,BoardRequest.saveAgainDTO requestDTO,HttpServletRequest request){

        if(requestDTO.getTitle().length()>20){
            request.setAttribute("status",400);
            request.setAttribute("msg","제목은 20자를 넘을 수 없습니다.");
            return "error/40x";
        }

        if(requestDTO.getContent().length()>20){
            request.setAttribute("status",400);
            request.setAttribute("msg","내용은 20자를 넘을 수 없습니다.");
            return "error/40x";
        }
        boardRepository.update(requestDTO,id);

        return "redirect:/";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id){

        boardRepository.delete(id);
        return "redirect:/";
    }


}
