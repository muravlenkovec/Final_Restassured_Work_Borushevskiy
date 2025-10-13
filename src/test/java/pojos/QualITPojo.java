package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties

@Data

public class QualITPojo {

    private String name;
    private String type;
    private boolean exotic;

}