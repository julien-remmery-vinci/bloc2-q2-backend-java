package be.vinci;

public class Text {
    private int id;
    private String content;
    private String level;
    public Text(){
    }
    public Text(int id, String content, String level) {
        this.id = id;
        this.content = content;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getLevel() {
        return level;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
