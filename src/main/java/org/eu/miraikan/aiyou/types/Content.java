package org.eu.miraikan.aiyou.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Content {

    String role;


    /**
     *   txt type, string or base64
     */
    List<Part> parts;

    /**
     * Build Content with single part
     * @param role
     * @param part
     */
    public Content(String role, Part part){
        this.role = role;
        this.parts = List.of(part);
    }

}
