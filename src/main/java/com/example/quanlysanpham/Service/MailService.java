package com.example.quanlysanpham.Service;

import com.example.quanlysanpham.Dto.CartDto;
import com.example.quanlysanpham.Dto.ProductEntityDto;

import java.util.List;

public interface MailService {
    public void sendMail(String toAddress, String subject, Object model, String filePath, String content, List<CartDto> lstCart);
    public void resetPassWord(String from, String toAddress, String subject, Object model, String filePath, String content,String tokken);
}
