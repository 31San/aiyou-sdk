package org.eu.miraikan.aiyou.types;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Content {

    String role;





    //txt type, string or base64
    List<Part> parts;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    //used by json
    public Content() {
    }


    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public Content(String role, List<Part> parts) {
        this.role = role;
        this.parts = parts;
    }
}
