package com.app.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.ProductDAO;
import com.app.entity.Category;
import com.app.entity.Products;
import com.app.utils.Constant;
import com.app.utils.Paging;
@Service
public class ProductService implements BaseService<Products>{
	@Autowired
	private ProductDAO<Products> productDAO;
	@Autowired
	ServletContext context;
	
	public void add(Products e) throws IOException {
		// TODO Auto-generated method stub
		e.setActiveFlag(Constant.ACTIVE);
		e.setCategory(new Category(e.getCateId()));
		if(!e.getMultipartFile().isEmpty()) {
			String images = System.currentTimeMillis()+"_"+e.getMultipartFile().getOriginalFilename();
			uploadFile(e.getMultipartFile(),images);
			e.setImageUrl("/upload/"+images);
		}
		productDAO.insert(e);
	}

	public void delete(Products e) {
		// TODO Auto-generated method stub
		e.setActiveFlag(Constant.NOT_ACTIVE);
		productDAO.delete(e);
	}

	public void update(Products e) throws IOException {
		// TODO Auto-generated method stub
		e.setCategory(new Category(e.getCateId()));
		if(!e.getMultipartFile().isEmpty()) {
			String images = System.currentTimeMillis()+"_"+e.getMultipartFile().getOriginalFilename();
			uploadFile(e.getMultipartFile(),images);
			e.setImageUrl("/upload/"+images);
		}else {			
			e.setImageUrl(e.getImageUrl());
		}
		e.setCategory(new Category(e.getCateId()));
		productDAO.update(e);
	}

	public List<Products> findAll(Paging paging) {
		// TODO Auto-generated method stub
		return productDAO.findAll(paging);
	}

	public Products findById(long id) {
		// TODO Auto-generated method stub
		Products products =  productDAO.findById(Products.class,id);
		products.setCateId(products.getCategory().getId());
		return products;
	}

	public List<Products> findAllByProperty(String property, Object value) {
		// TODO Auto-generated method stub
		return productDAO.findByProperty(property, value);
	}
 
	public void uploadFile(MultipartFile multipartFile,String images ) throws IOException {
		File file = new File(Constant.PATH_UPLOAD + images);
		String realPath = context.getRealPath("/")+"upload\\"+images;
		byte[] bytes = multipartFile.getBytes();
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		stream.write(bytes);
		stream.close();
		multipartFile.transferTo(new File(realPath));
	}

}
