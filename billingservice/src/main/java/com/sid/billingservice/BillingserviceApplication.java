package com.sid.billingservice;

import com.sid.billingservice.entities.Bill;
import com.sid.billingservice.entities.ProductItem;
import com.sid.billingservice.feign.CustomerRestClient;
import com.sid.billingservice.feign.ProductItemRestClient;
import com.sid.billingservice.model.Customer;
import com.sid.billingservice.model.Product;
import com.sid.billingservice.repository.BillRepository;
import com.sid.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;


@SpringBootApplication
@EnableFeignClients
public class BillingserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingserviceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BillRepository billRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient,ProductItemRepository productItemRepository){
        return args -> {
            Customer customer=customerRestClient.getCustomerById(1L);

           Bill bill1=billRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
            PagedModel<Product> productPagedModel=productItemRestClient.pageProducts();
            productPagedModel.forEach(p->{
                ProductItem productItem=new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+new Random().nextInt(100));
                productItem.setBill(bill1);
                productItemRepository.save(productItem);
                productItem.setProductID(p.getId());
            });
            System.out.println("-----------------");
            System.out.println(customer.getEmail());
            System.out.println(customer.getName());
            System.out.println(customer.getId());

        };
    }

}
