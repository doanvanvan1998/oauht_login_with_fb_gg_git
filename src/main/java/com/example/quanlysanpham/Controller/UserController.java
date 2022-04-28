package com.example.quanlysanpham.Controller;

import com.example.quanlysanpham.Configuration.Oauth2.CustomOauth2User;
import com.example.quanlysanpham.Configuration.Security.UserDetailsCustom;
import com.example.quanlysanpham.Dto.CartDto;
import com.example.quanlysanpham.Dto.ProductEntityDto;
import com.example.quanlysanpham.Entity.ProductEntity;
import com.example.quanlysanpham.Repository.CategoryRepository;
import com.example.quanlysanpham.Repository.ProductRepository;
import com.example.quanlysanpham.Repository.RoleRepository;
import com.example.quanlysanpham.Repository.UserRepository;
import com.example.quanlysanpham.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MailService mailService;


    @GetMapping(value = {"/index", "/"})
    public String indexView(Model model){
        List<ProductEntity> productEntityList = productRepository.findAll();
        model.addAttribute("lstProductModal", productEntityList);
        return "user/userIndex";
    }

    @RequestMapping(value = "/listCart", method = RequestMethod.GET)
    public String redirectTOcart(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<CartDto> lstCart =(List<CartDto>) session.getAttribute("cart");
        if(lstCart == null){
            return "redirect:/index";
        }
        else{
            List<ProductEntityDto> lstCartDto = new ArrayList<>();
            ProductEntityDto p = null;
            for (CartDto cart : lstCart){
                p = new ProductEntityDto(productRepository.findById(cart.getId()).get());
                p.setSl(cart.getSl());
                lstCartDto.add(p);
            }
            model.addAttribute("lstCartDto",lstCartDto);
            return "user/cart";
        }
    }

    @GetMapping("/addCart/{id}")
    public String addCart(@PathVariable("id")Long id, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        List<CartDto> lstCart =(List<CartDto>) session.getAttribute("cart");
        if(lstCart == null){
            lstCart = new ArrayList<>();
            CartDto cartDto = new CartDto();
            cartDto.setId(id);
            cartDto.setSl(1);
            lstCart.add(cartDto);
        }
        else{
            Boolean isHas = false;
            for(CartDto cd : lstCart){
                if(cd.getId() == id){
                    cd.setSl(cd.getSl() + 1);
                    isHas = true;
                    break;
                }
            }
            if(!isHas){
                CartDto contain = new CartDto();
                contain.setId(id);
                contain.setSl(1);
                lstCart.add(contain);
            }
        }
        session.setAttribute("cart", lstCart);
        return "redirect:/index";
    }

    @GetMapping("/pay")
    public String pay(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().toString() == "anonymousUser")
            return "user/payError";
        else{
            HttpSession session = request.getSession();
            List<CartDto> lstCart =(List<CartDto>) session.getAttribute("cart");
            CustomOauth2User oAuth2User = null;
            UserDetailsCustom userDetailsCustom = null;
            String email = "";
            try{
                userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
                email = userDetailsCustom.getUsername();
            }
            catch (Exception e){
                if(userDetailsCustom == null){
                    oAuth2User = (CustomOauth2User) authentication.getPrincipal();
                    email = oAuth2User.getAttribute("email");
                }
            }
            mailService.sendMail(email, "Thanh toán thành công!!!",null,null,null,lstCart);
            session.setAttribute("cart", null);
            return "user/paySuccess";
        }
    }
}
