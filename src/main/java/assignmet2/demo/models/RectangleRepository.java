package assignmet2.demo.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RectangleRepository extends JpaRepository<Rectangle, Integer> {
    Rectangle findById(int rid);
    void deleteById(int rid);
    List<Rectangle> findAll();
}
