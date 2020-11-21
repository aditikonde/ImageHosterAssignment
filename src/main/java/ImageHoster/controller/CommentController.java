package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@Controller
@SessionAttributes("name")
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/images/{imageId}/{title}/comments", method = RequestMethod.POST)
    public String createComment(Comment newCommet, @RequestParam("file") MultipartFile file,
                                @RequestParam("tags") String tags, Image image,
                                HttpSession session)
            throws IOException {

        User user = (User) session.getAttribute("loggeduser");

        newCommet.setUser(user);
        newCommet.setImage(image);
        Date date = new Date();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        newCommet.setDate(instant.atZone(defaultZoneId).toLocalDate());
        commentService.addComment(newCommet);


        System.out.println("--------------------------------------------");
        System.out.println(newCommet.getText());
        System.out.println("--------------------------------------------");
        return "redirect:/images/image";
    }

}
