package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskImportDTO {

    @DecimalMin(value = "1")
    @XmlElement
    @NotNull
    private BigDecimal price;

    @XmlElement
    @NotNull
    private String date;

    @XmlElement
    @NotNull
    private CarIdDTO car;

    @XmlElement
    @NotNull
    private MechanicFNameDTO mechanic;

    @XmlElement
    @NotNull
    private PartIdDTO part;
}
