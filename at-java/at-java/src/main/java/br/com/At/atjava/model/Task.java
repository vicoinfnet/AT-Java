package br.com.At.atjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Task {
    private Long id;
    private String description;
    private boolean completed;
    private List<String> tags;
}
