package com.example.quanlysanpham.Controller;

import com.example.quanlysanpham.Configuration.Security.UserDetailsCustom;
import com.example.quanlysanpham.Dto.ProductEntityDto;
import com.example.quanlysanpham.Entity.CategoryEntity;
import com.example.quanlysanpham.Entity.ProductEntity;
import com.example.quanlysanpham.Entity.RoleEntity;
import com.example.quanlysanpham.Entity.UserEntity;
import com.example.quanlysanpham.Repository.CategoryRepository;
import com.example.quanlysanpham.Repository.ProductRepository;
import com.example.quanlysanpham.Repository.RoleRepository;
import com.example.quanlysanpham.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Value("${config.upload_folder}")
    String UPLOAD_FILE;

    @GetMapping("/index")
    public String adminIndex(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        HttpSession session = request.getSession();
        session.setAttribute("userDetail", userDetailsCustom);
        List<ProductEntity> lstProduct =productRepository.findAll();
        List<CategoryEntity> lstCategory = categoryRepository.findAll();
        model.addAttribute("Products",lstProduct);
        model.addAttribute("categories",lstCategory);
        model.addAttribute("productDto",new ProductEntityDto());
        return "admin/adminIndex";
    }
    @GetMapping("/getProduct/{id}")
    public String getProduct(Model model, @PathVariable("id") Long id){
        ProductEntity getProduct = productRepository.findById(id).get();
        if(getProduct == null){
            return "redirect:/admin/index";
        }
        model.addAttribute("product",getProduct);
        List<CategoryEntity> lstCategory = categoryRepository.findAll();
        model.addAttribute("categories",lstCategory);
        return "admin/productDetail";
    }
    @PostMapping("/add-update-product")
    public String addUpdateProduct(Model model, ProductEntityDto productEntity) throws IOException {
        ProductEntity productEntity1 = null;
        if(productEntity.getId()!= null){
            productEntity1 = productRepository.findById(productEntity.getId()).get();
        }
        if(productEntity1 == null){
            productEntity1 = new ProductEntity();
        }
        productEntity1.setName(productEntity.getName());
        productEntity1.setPrice(productEntity.getPrice());
        productEntity1.setPriceSale(productEntity.getPriceSale());
        productEntity1.setPercentSale(productEntity.getPercentSale());
        if(!productEntity.getFile().isEmpty()){
            String realativeFilePath = null;
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            String subFolder = month +"_"+year;
            String fullUploadDir = UPLOAD_FILE + subFolder;
            File checkDir = new File(fullUploadDir);
            if(checkDir.exists() == true || checkDir.isFile() == true){
                checkDir.mkdir();
            }
            realativeFilePath = subFolder + Instant.now().getEpochSecond() + productEntity.getFile().getOriginalFilename();
            Files.write(Paths.get(UPLOAD_FILE + realativeFilePath), productEntity.getFile().getBytes());
            productEntity1.setImage(realativeFilePath);
        }
        CategoryEntity ce = null;
        if(productEntity.getCategoryId() != null){
            ce = categoryRepository.getById(productEntity.getCategoryId());
            productEntity1.setCategoryEntity(ce);
        }
        productRepository.save(productEntity1);
        return "redirect:/admin/index";
    }

    @GetMapping("removeProduct/{id}")
    public String removeProduct(Model model, @PathVariable("id") Long id){
        productRepository.deleteById(id);
        return "redirect:/admin/index";
    }

    @GetMapping("/category")
    public String categoryIndex(Model model){
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        model.addAttribute("Categories", categoryEntities);
        model.addAttribute("categoryDto", new CategoryEntity());
        return "admin/categoryView";
    }

    @PostMapping("/add-update-category")
    public String createCategory(CategoryEntity categoryEntity){
        CategoryEntity category = null;
        if(categoryEntity.getId() != null){
            category = categoryRepository.findById(categoryEntity.getId()).get();
        }
        if(category == null){
            category = new CategoryEntity();
        }
        category.setName(categoryEntity.getName());
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/removeCateogry/{id}")
    public String removeCategory(@PathVariable("id") Long id){
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/getCategory/{id}")
    public String getCategory(@PathVariable("id") Long id, Model model){
        CategoryEntity category = categoryRepository.findById(id).get();
        model.addAttribute("categoryDto", category);
        return "admin/categoryDetail";
    }


}
