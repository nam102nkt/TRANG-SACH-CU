package model;

import java.math.BigDecimal; // Import lớp này

public class Book {
    private int id;
    private String title;
    private String author;
    private BigDecimal price; // Dùng BigDecimal cho tiền tệ
    private String description;
    private String imageUrl;

    // Constructors (để trống)
    public Book() {}

    // Getters and Setters
    // (Hãy dùng Eclipse: Chuột phải > Source > Generate Getters and Setters... > Select All)
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}