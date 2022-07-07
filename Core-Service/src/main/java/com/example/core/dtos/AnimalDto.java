package com.example.core.dtos;

public class AnimalDto {
    private Long id;
    private String username;
    private String viewAnimal;
    private String dateBorn;
    private String gender;
    private String nickName;

    public AnimalDto(Long id, String username, String viewAnimal, String dateBorn, String gender, String nickName) {
        this.id = id;
        this.username = username;
        this.viewAnimal = viewAnimal;
        this.dateBorn = dateBorn;
        this.gender = gender;
        this.nickName = nickName;
    }

    public AnimalDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getViewAnimal() {
        return viewAnimal;
    }

    public void setViewAnimal(String viewAnimal) {
        this.viewAnimal = viewAnimal;
    }

    public String getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(String dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
