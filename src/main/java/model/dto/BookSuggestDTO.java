
package model.dto;

public class BookSuggestDTO {

    private int id;
    private String title;

    public BookSuggestDTO(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
