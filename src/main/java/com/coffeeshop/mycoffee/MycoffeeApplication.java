package com.coffeeshop.mycoffee;

import com.coffeeshop.mycoffee.entity.Category;
import com.coffeeshop.mycoffee.entity.Product;
import com.coffeeshop.mycoffee.repository.CategoryRepository;
import com.coffeeshop.mycoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing // Kích hoạt Auditing
public class MycoffeeApplication implements CommandLineRunner {
//	private final ProductRepository productRepository;
//	private final CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(MycoffeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Category category = Category.builder()
//				.name("Tea")
//				.build();
//
//		Product product1 = Product.builder()
//				.name("Tra hoa cuc")
//				.price(20000)
//				.category(category)
//				.build();
//
//		Product product2 = Product.builder()
//				.name("Tra sua dau tam")
//				.price(45000)
//				.category(category)
//				.build();
//
//		category.setProducts(List.of(product1, product2));
//
//		categoryRepository.save(category);
//
//		System.out.println("Category: " + category);
//		System.out.println("Products: " + category.getProducts());
	}

}
