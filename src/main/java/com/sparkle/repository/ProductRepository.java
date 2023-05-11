package com.sparkle.repository;

import com.sparkle.entity.Category;
import com.sparkle.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByIsDeletedOrderByCategoryAsc(boolean deleted);


    List<Product> findProductByCategory(Category category);

    Product findByIdAndIsDeleted(Long id, Boolean deleted);

}
