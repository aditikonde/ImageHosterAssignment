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


    @RequestMapping(value = "/images/{imageId}/{title}/comments", method = RequestMethod.POST)
    public String createComment( @RequestParam(name="comment") String comment,
            @PathVariable("imageId") Integer imageId,Comment newComment, HttpSession session, ModelMap model) throws IOException {

        newComment.setText(comment);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(newComment.getText());
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        User user = (User) session.getAttribute("loggeduser");
        newComment.setUser(user);

        Image image = imageService.getImageById(imageId);
        newComment.setImage(image);

        Date date = new Date();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
//        newComment.setDate(instant.atZone(defaultZoneId).toLocalDate());
        newComment.setDate(LocalDate.from(ZonedDateTime.now().toLocalDate()));
        commentService.addComment(newComment);

        System.out.println("**********************************************************");
        commentService.getImageComments(image);
        System.out.println("**********************************************************");


        model.addAttribute("image", image);
        model.addAttribute("comments", image.getComments());
        model.addAttribute("tags", image.getTags());
        List<Comment> comments = commentService.getImageComments(image);
        image.setComments(comments);
        model.addAttribute("comments", image.getComments());

        return "redirect:/images/" +image.getId() +"/"+ image.getTitle();
    }

}
