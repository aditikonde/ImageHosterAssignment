package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("name")
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    //This controller method lets user add comments.
    @RequestMapping(value = "/images/{imageId}/{title}/comments", method = RequestMethod.POST)
    public String createComment( @RequestParam(name="comment") String comment, @PathVariable(
            "title") String title,
            @PathVariable("imageId") Integer imageId,Comment newComment, HttpSession session, ModelMap model) throws IOException {

        User user = (User) session.getAttribute("loggeduser");
        newComment.setUser(user);

        Image image = imageService.getImageById(imageId);
        newComment.setImage(image);
        newComment.setText(comment);
        newComment.setDate(LocalDate.from(ZonedDateTime.now().toLocalDate()));
        commentService.addComment(newComment);

        return "redirect:/images/" + imageId +"/"+ title;
    }

}
