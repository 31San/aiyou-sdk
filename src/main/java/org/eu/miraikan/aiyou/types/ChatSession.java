package org.eu.miraikan.aiyou.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Saving history.
 * For txt, only one choice of each should be saved
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSession<T> {
    List<T> contents= new ArrayList<T>();

    public void append(T content) {
        contents.add(content);
    }




}
