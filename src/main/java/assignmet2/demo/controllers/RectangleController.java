package assignmet2.demo.controllers;

import assignmet2.demo.models.Rectangle;
import assignmet2.demo.models.RectangleRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class RectangleController {

    @Autowired
    private RectangleRepository rectangleRepo;

    @GetMapping("/")
    public String homePage(Model model){
        //get all rectangles in database
        List<Rectangle> rectangles = rectangleRepo.findAll();
        model.addAttribute("rects", rectangles);
        return "pages/home";
    }

    @GetMapping("rectangle/get/{rid}")
    public String findRectangle(@PathVariable("rid") int rid, Model model, HttpServletResponse response){
        Rectangle rectangle = rectangleRepo.findById(rid);
        model.addAttribute("rect", rectangle);
        return "pages/rectangleView";
    }

    @GetMapping("create-rectangle")
    public String createPage() {
        return "pages/addNewRectangle";
    }


    @PostMapping("rectangle/create")
    public String addRectangle(@RequestParam Map<String, String> rectdata, HttpServletResponse response){
        String name = rectdata.get("name");
        int width = Integer.parseInt(rectdata.get("width"));
        int height = Integer.parseInt(rectdata.get("height"));
        String color = rectdata.get("color");
        rectangleRepo.save(new Rectangle(name, height, width, color));
        response.setStatus(201);
        return "redirect:/";
    }

    @PostMapping("rectangle/update/{rid}")
    public String addRectangle(@PathVariable("rid") int rid, @RequestParam Map<String, String> rectdata, HttpServletResponse response){
        //find the rectangle first
        Rectangle rectangle = rectangleRepo.findById(rid);
        System.out.println(rectangle);//debug
        if(rectangle != null){
            rectangle.setName(rectdata.get("name"));
            rectangle.setWidth(Integer.parseInt(rectdata.get("width")));
            rectangle.setHeight(Integer.parseInt(rectdata.get("height")));
            rectangle.setColor(rectdata.get("color"));
            rectangleRepo.save(rectangle);
            response.setStatus(201);
        }
        return "redirect:/";
    }

    @GetMapping("rectangle/delete/{rid}")
    public String deleteRectangle(@PathVariable("rid") int rid, Model model, HttpServletResponse response){
        rectangleRepo.deleteById(rid);
        model.addAttribute("message", "Sucessfully Deleted");
        response.setStatus(202);
        return "redirect:/";
    }


}
