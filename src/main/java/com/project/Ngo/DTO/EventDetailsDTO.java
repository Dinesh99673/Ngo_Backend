package com.project.Ngo.DTO;

import com.project.Ngo.model.EventSchedule;
import com.project.Ngo.model.Ngo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class EventDetailsDTO {
    private Ngo ngo;

    private String title;
    private String description;
    private String location_link;
    private String venue;
    private BigDecimal fees;
    private MultipartFile poster;
<<<<<<< HEAD

//    private EventSchedule[] schedule;

=======
    private Date date;
>>>>>>> 8740dce9e7e799c70903df3db332859efb41c72f
}
