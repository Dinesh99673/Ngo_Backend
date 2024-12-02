package com.project.Ngo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.Ngo.model.EventSchedule;
import com.project.Ngo.model.Ngo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class EventDetailsDTO {
    private Long ngo_id;
    private String title;
    private String description;
    private String location_link;
    private String venue;
    private BigDecimal fees;
    private MultipartFile poster;
    private String schedule; // Receive as raw JSON string

    // Getters and Setters

    public List<EventSchedule> getDeserializedSchedule() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(schedule, new TypeReference<List<EventSchedule>>() {});
    }

}
