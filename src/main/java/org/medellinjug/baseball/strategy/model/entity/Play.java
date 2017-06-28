package org.medellinjug.baseball.strategy.model.entity;

/**
 * Created by Amy on 24/06/17.
 */
public class Play {
    public enum Type {PITCH, HIT}

    private Long id;
    private String code;
    private String name;
    private Type type;
    private String color;

    public Play() {
        super();

            this.id= 0L;
            this.code = "";
            this.name = "";
            this.type = Type.HIT;
            this.color = "#FFFFF";


    }

    public Play(Long id, String code, String name, Type type, String color) {
        this.id= id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.color = color;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Play{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
