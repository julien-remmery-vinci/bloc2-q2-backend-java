package be.vinci;

import java.util.Objects;

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
        if(level != null && !level.equals("easy") && !level.equals("medium") && !level.equals("hard"))
            this.level = null;
        else
            this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return id == text.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
