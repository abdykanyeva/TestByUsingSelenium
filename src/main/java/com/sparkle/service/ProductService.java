package com.sparkle.service;

import com.sparkle.dto.ProductDTO;
import com.sparkle.enums.ProductUnit;

import java.util.List;


public interface ProductService {


    ProductDTO findById(Long id);
    List<ProductDTO> listAllProducts();
    void save(ProductDTO dto);
    ProductDTO update(ProductDTO dto);
    void delete(Long id);
    List<ProductUnit> listAllEnums();

}
