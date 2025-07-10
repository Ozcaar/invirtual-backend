package com.ozcaar.invirtual.invitation.dto.create;

public class InvitationCreateDTO {
    private Integer invitation_type_id;
    // private Integer music_id;
    // private Integer sign_book_id;
    private String name;
    private Integer max_people;
    
    // Getters & setters

    public Integer getInvitation_type_id() {
        return invitation_type_id;
    }
    public void setInvitation_type_id(Integer invitation_type_id) {
        this.invitation_type_id = invitation_type_id;
    }
    // public Integer getMusic_id() {
    //     return music_id;
    // }
    // public void setMusic_id(Integer music_id) {
    //     this.music_id = music_id;
    // }
    // public Integer getSign_book_id() {
    //     return sign_book_id;
    // }
    // public void setSign_book_id(Integer sign_book_id) {
    //     this.sign_book_id = sign_book_id;
    // }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getMax_people() {
        return max_people;
    }
    public void setMax_people(Integer max_people) {
        this.max_people = max_people;
    }
}
