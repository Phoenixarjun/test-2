package com.example.nxttrendz1;

import com.example.nxttrendz1.model.Product;
import com.example.nxttrendz1.model.Review;
import com.example.nxttrendz1.repository.ProductJpaRepository;
import com.example.nxttrendz1.repository.ReviewJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "/schema.sql", "/data.sql" })
public class NxtTrendzControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ProductJpaRepository productJpaRepository;

        @Autowired
        private ReviewJpaRepository reviewJpaRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private HashMap<Integer, Object[]> products = new HashMap<>();
        {
                products.put(1, new Object[] { "Smartphone", 599.99 });
                products.put(2, new Object[] { "Laptop", 1299.99 });
                products.put(3, new Object[] { "Gaming Console", 399.99 });
                products.put(4, new Object[] { "Smartwatch", 199.99 }); // POST
                products.put(5, new Object[] { "Headphones", 99.99 }); // PUT
        }

        private HashMap<Integer, Object[]> reviews = new HashMap<>();
        {
                reviews.put(1, new Object[] { "Great battery life!", 5, 1 });
                reviews.put(2, new Object[] { "Lags sometimes.", 3, 1 });
                reviews.put(3, new Object[] { "Perfect for my daily tasks!", 4, 2 });
                reviews.put(4, new Object[] { "Bit pricey, but worth it.", 4, 2 });
                reviews.put(5, new Object[] { "Awesome gaming experience!", 5, 3 });
                reviews.put(6, new Object[] { "Needs more exclusive games.", 4, 3 });
                reviews.put(7, new Object[] { "Comfortable and lightweight.", 5, 4 }); // POST
                reviews.put(8, new Object[] { "Sound quality is average.", 3, 4 }); // PUT
        }

        @Test
        @Order(1)
        public void testGetProducts() throws Exception {
                mockMvc.perform(get("/products")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].productId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$[0].price", Matchers.equalTo(products.get(1)[1])))

                                .andExpect(jsonPath("$[1].productId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$[1].price", Matchers.equalTo(products.get(2)[1])))

                                .andExpect(jsonPath("$[2].productId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$[2].price", Matchers.equalTo(products.get(3)[1])));
        }

        @Test
        @Order(2)
        public void testGetReviews() throws Exception {
                mockMvc.perform(get("/products/reviews")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(6)))

                                .andExpect(jsonPath("$[0].reviewId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].reviewContent", Matchers.equalTo(reviews.get(1)[0])))
                                .andExpect(jsonPath("$[0].rating", Matchers.equalTo(reviews.get(1)[1])))
                                .andExpect(jsonPath("$[0].product.productId", Matchers.equalTo(reviews.get(1)[2])))
                                .andExpect(jsonPath("$[0].product.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$[0].product.price", Matchers.equalTo(products.get(1)[1])))

                                .andExpect(jsonPath("$[1].reviewId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].reviewContent", Matchers.equalTo(reviews.get(2)[0])))
                                .andExpect(jsonPath("$[1].rating", Matchers.equalTo(reviews.get(2)[1])))
                                .andExpect(jsonPath("$[1].product.productId", Matchers.equalTo(reviews.get(2)[2])))
                                .andExpect(jsonPath("$[1].product.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$[1].product.price", Matchers.equalTo(products.get(1)[1])))

                                .andExpect(jsonPath("$[2].reviewId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].reviewContent", Matchers.equalTo(reviews.get(3)[0])))
                                .andExpect(jsonPath("$[2].rating", Matchers.equalTo(reviews.get(3)[1])))
                                .andExpect(jsonPath("$[2].product.productId", Matchers.equalTo(reviews.get(3)[2])))
                                .andExpect(jsonPath("$[2].product.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$[2].product.price", Matchers.equalTo(products.get(2)[1])))

                                .andExpect(jsonPath("$[3].reviewId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].reviewContent", Matchers.equalTo(reviews.get(4)[0])))
                                .andExpect(jsonPath("$[3].rating", Matchers.equalTo(reviews.get(4)[1])))
                                .andExpect(jsonPath("$[3].product.productId", Matchers.equalTo(reviews.get(4)[2])))
                                .andExpect(jsonPath("$[3].product.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$[3].product.price", Matchers.equalTo(products.get(2)[1])))

                                .andExpect(jsonPath("$[4].reviewId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].reviewContent", Matchers.equalTo(reviews.get(5)[0])))
                                .andExpect(jsonPath("$[4].rating", Matchers.equalTo(reviews.get(5)[1])))
                                .andExpect(jsonPath("$[4].product.productId", Matchers.equalTo(reviews.get(5)[2])))
                                .andExpect(jsonPath("$[4].product.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$[4].product.price", Matchers.equalTo(products.get(3)[1])))

                                .andExpect(jsonPath("$[5].reviewId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].reviewContent", Matchers.equalTo(reviews.get(6)[0])))
                                .andExpect(jsonPath("$[5].rating", Matchers.equalTo(reviews.get(6)[1])))
                                .andExpect(jsonPath("$[5].product.productId", Matchers.equalTo(reviews.get(6)[2])))
                                .andExpect(jsonPath("$[5].product.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$[5].product.price", Matchers.equalTo(products.get(3)[1])));
        }

        @Test
        @Order(3)
        public void testGetProductNotFound() throws Exception {
                mockMvc.perform(get("/products/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(4)
        public void testGetProductById() throws Exception {
                mockMvc.perform(get("/products/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(1)[1])));

                mockMvc.perform(get("/products/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(2)[1])));

                mockMvc.perform(get("/products/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(3)[1])));
        }

        @Test
        @Order(5)
        public void testGetReviewNotFound() throws Exception {
                mockMvc.perform(get("/products/reviews/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(6)
        public void testGetReviewById() throws Exception {
                mockMvc.perform(get("/products/reviews/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(1)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(1)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(reviews.get(1)[2])))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(1)[1])));

                mockMvc.perform(get("/products/reviews/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(2)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(2)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(reviews.get(2)[2])))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(1)[1])));

                mockMvc.perform(get("/products/reviews/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(3)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(3)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(reviews.get(3)[2])))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(2)[1])));

                mockMvc.perform(get("/products/reviews/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(4)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(4)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(reviews.get(4)[2])))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(2)[1])));

                mockMvc.perform(get("/products/reviews/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(5)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(5)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(reviews.get(5)[2])))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(3)[1])));

                mockMvc.perform(get("/products/reviews/6")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(6)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(6)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(reviews.get(6)[2])))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(3)[1])));
        }

        @Test
        @Order(7)
        public void testPostProduct() throws Exception {
                String content = "{\"productName\": \"" + products.get(4)[0] + "\", \"price\": "
                                + products.get(4)[1] + "}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(4)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(4)[1])));
        }

        @Test
        @Order(8)
        public void testAfterPostProduct() throws Exception {
                mockMvc.perform(get("/products/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(4)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(4)[1])));
        }

        @Test
        @Order(9)
        public void testDbAfterPostProduct() throws Exception {
                Product product = productJpaRepository.findById(4).get();

                assertEquals(product.getProductId(), 4);
                assertEquals(product.getProductName(), products.get(4)[0]);
                assertEquals(product.getPrice(), products.get(4)[1]);
        }

        @Test
        @Order(10)
        public void testPostReview() throws Exception {
                String content = "{\"reviewContent\": \"" + reviews.get(7)[0] + "\", \"rating\": " + reviews.get(7)[1]
                                + ", \"product\": {"
                                + "\"productId\": " + reviews
                                                .get(7)[2]
                                + "}}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products/reviews")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(7)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(7)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(reviews.get(7)[2])))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(4)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(4)[1])));
        }

        @Test
        @Order(11)
        public void testAfterPostReview() throws Exception {
                mockMvc.perform(get("/products/reviews/7")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(7)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(7)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(reviews.get(7)[2])))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(4)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(4)[1])));
        }

        @Test
        @Order(12)
        public void testDbAfterPostReview() throws Exception {
                Review review = reviewJpaRepository.findById(7).get();

                assertEquals(review.getReviewId(), 7);
                assertEquals(review.getReviewContent(), reviews.get(7)[0]);
                assertEquals(review.getRating(), reviews.get(7)[1]);
                assertEquals(review.getProduct().getProductId(), 4);
                assertEquals(review.getProduct().getProductName(), products.get(4)[0]);
                assertEquals(review.getProduct().getPrice(), products.get(4)[1]);
        }

        @Test
        @Order(13)
        public void testPutProductNotFound() throws Exception {
                String content = "{\"productName\": \"" + products.get(5)[0] + "\", \"price\": "
                                + products.get(5)[1] + "}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/products/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(14)
        public void testPutProduct() throws Exception {
                String content = "{\"productName\": \"" + products.get(5)[0] + "\", \"price\": "
                                + products.get(5)[1] + "}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/products/4")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(5)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(5)[1])));
        }

        @Test
        @Order(15)
        public void testAfterPutProduct() throws Exception {
                mockMvc.perform(get("/products/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(5)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(5)[1])));
        }

        @Test
        @Order(16)
        public void testDbAfterPutProduct() throws Exception {
                Product product = productJpaRepository.findById(4).get();

                assertEquals(product.getProductId(), 4);
                assertEquals(product.getProductName(), products.get(5)[0]);
                assertEquals(product.getPrice(), products.get(5)[1]);
        }

        @Test
        @Order(17)
        public void testPutReviewNotFound() throws Exception {
                String content = "{\"reviewContent\": \"" + reviews.get(8)[0] + "\", \"rating\": " + reviews.get(8)[1]
                                + ", \"product\": {"
                                + "\"productId\": " + reviews
                                                .get(8)[2]
                                + "}}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/products/reviews/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(18)
        public void testPutReview() throws Exception {
                String content = "{\"reviewContent\": \"" + reviews.get(8)[0] + "\", \"rating\": " + reviews.get(8)[1]
                                + ", \"product\": {"
                                + "\"productId\": " + reviews
                                                .get(8)[2]
                                + "}}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/products/reviews/7")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(8)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(8)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(5)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(5)[1])));
        }

        @Test
        @Order(19)
        public void testAfterPutReview() throws Exception {

                mockMvc.perform(get("/products/reviews/7")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.reviewId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.reviewContent", Matchers.equalTo(reviews.get(8)[0])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(reviews.get(8)[1])))
                                .andExpect(jsonPath("$.product.productId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.product.productName", Matchers.equalTo(products.get(5)[0])))
                                .andExpect(jsonPath("$.product.price", Matchers.equalTo(products.get(5)[1])));
        }

        @Test
        @Order(20)
        public void testDbAfterPutReview() throws Exception {
                Review review = reviewJpaRepository.findById(7).get();

                assertEquals(review.getReviewId(), 7);
                assertEquals(review.getReviewContent(), reviews.get(8)[0]);
                assertEquals(review.getRating(), reviews.get(8)[1]);
                assertEquals(review.getProduct().getProductId(), 4);
                assertEquals(review.getProduct().getProductName(), products.get(5)[0]);
                assertEquals(review.getProduct().getPrice(), products.get(5)[1]);
        }

        @Test
        @Order(21)
        public void testDeleteReviewNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/products/reviews/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(22)
        public void testDeleteReview() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/products/reviews/7");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(23)
        public void testAfterDeleteReview() throws Exception {
                mockMvc.perform(get("/products/reviews")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(6)))

                                .andExpect(jsonPath("$[0].reviewId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].reviewContent", Matchers.equalTo(reviews.get(1)[0])))
                                .andExpect(jsonPath("$[0].rating", Matchers.equalTo(reviews.get(1)[1])))
                                .andExpect(jsonPath("$[0].product.productId", Matchers.equalTo(reviews.get(1)[2])))
                                .andExpect(jsonPath("$[0].product.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$[0].product.price", Matchers.equalTo(products.get(1)[1])))

                                .andExpect(jsonPath("$[1].reviewId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].reviewContent", Matchers.equalTo(reviews.get(2)[0])))
                                .andExpect(jsonPath("$[1].rating", Matchers.equalTo(reviews.get(2)[1])))
                                .andExpect(jsonPath("$[1].product.productId", Matchers.equalTo(reviews.get(2)[2])))
                                .andExpect(jsonPath("$[1].product.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$[1].product.price", Matchers.equalTo(products.get(1)[1])))

                                .andExpect(jsonPath("$[2].reviewId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].reviewContent", Matchers.equalTo(reviews.get(3)[0])))
                                .andExpect(jsonPath("$[2].rating", Matchers.equalTo(reviews.get(3)[1])))
                                .andExpect(jsonPath("$[2].product.productId", Matchers.equalTo(reviews.get(3)[2])))
                                .andExpect(jsonPath("$[2].product.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$[2].product.price", Matchers.equalTo(products.get(2)[1])))

                                .andExpect(jsonPath("$[3].reviewId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].reviewContent", Matchers.equalTo(reviews.get(4)[0])))
                                .andExpect(jsonPath("$[3].rating", Matchers.equalTo(reviews.get(4)[1])))
                                .andExpect(jsonPath("$[3].product.productId", Matchers.equalTo(reviews.get(4)[2])))
                                .andExpect(jsonPath("$[3].product.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$[3].product.price", Matchers.equalTo(products.get(2)[1])))

                                .andExpect(jsonPath("$[4].reviewId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].reviewContent", Matchers.equalTo(reviews.get(5)[0])))
                                .andExpect(jsonPath("$[4].rating", Matchers.equalTo(reviews.get(5)[1])))
                                .andExpect(jsonPath("$[4].product.productId", Matchers.equalTo(reviews.get(5)[2])))
                                .andExpect(jsonPath("$[4].product.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$[4].product.price", Matchers.equalTo(products.get(3)[1])))

                                .andExpect(jsonPath("$[5].reviewId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].reviewContent", Matchers.equalTo(reviews.get(6)[0])))
                                .andExpect(jsonPath("$[5].rating", Matchers.equalTo(reviews.get(6)[1])))
                                .andExpect(jsonPath("$[5].product.productId", Matchers.equalTo(reviews.get(6)[2])))
                                .andExpect(jsonPath("$[5].product.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$[5].product.price", Matchers.equalTo(products.get(3)[1])));
        }

        @Test
        @Order(24)
        public void testDeleteProductNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/products/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(25)
        public void testDeleteProduct() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/products/4");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(26)
        public void testAfterDeleteProduct() throws Exception {
                mockMvc.perform(get("/products")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].productId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$[0].price", Matchers.equalTo(products.get(1)[1])))

                                .andExpect(jsonPath("$[1].productId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$[1].price", Matchers.equalTo(products.get(2)[1])))

                                .andExpect(jsonPath("$[2].productId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$[2].price", Matchers.equalTo(products.get(3)[1])));
        }

        @Test
        @Order(27)
        public void testGetProductByReviewId() throws Exception {
                mockMvc.perform(get("/reviews/1/product")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(1)[1])));

                mockMvc.perform(get("/reviews/2/product")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(1)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(1)[1])));

                mockMvc.perform(get("/reviews/3/product")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(2)[1])));

                mockMvc.perform(get("/reviews/4/product")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(2)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(2)[1])));

                mockMvc.perform(get("/reviews/5/product")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(3)[1])));

                mockMvc.perform(get("/reviews/6/product")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.productId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.productName", Matchers.equalTo(products.get(3)[0])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(products.get(3)[1])));
        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table review");
                jdbcTemplate.execute("drop table product");
        }

}