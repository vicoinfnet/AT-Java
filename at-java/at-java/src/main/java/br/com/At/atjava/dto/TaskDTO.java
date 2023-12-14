package br.com.At.atjava.dto;

import lombok.Data;
import java.util.List;

@Data
public class TaskDTO {
    private String description;
    private boolean completed;
    private List<String> tags;
}
