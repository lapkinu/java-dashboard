package example.neontest_db.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @GetMapping("/**") // Изменяем endpoint для обработки любых путей после /api/images/
    public ResponseEntity<Resource> getImage(HttpServletRequest request) { // Добавляем HttpServletRequest
        try {
            String requestURI = request.getRequestURI();
            String imageName = requestURI.substring(requestURI.lastIndexOf("/") + 1); // Извлекаем имя файла

            String imageDirectory = "src/main/resources/static/customers/";
            Path file = Paths.get(imageDirectory).resolve(imageName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // Укажите тип изображения
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}