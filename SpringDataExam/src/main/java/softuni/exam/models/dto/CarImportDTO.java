package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.CarType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportDTO {

    @Size(min = 2, max = 30)
    @XmlElement
    private String carMake;

    @Size(min = 2, max = 30)
    @XmlElement
    private String carModel;

    @Min(value = 1)
    @XmlElement
    private Integer year;

    @Size(min = 2, max = 30)
    @XmlElement
    private String plateNumber;

    @Min(value = 1)
    @XmlElement
    private Integer kilometers;

    @Min(value = 1)
    @XmlElement
    private Double engine;

    @Enumerated(EnumType.STRING)
    @XmlElement
    private CarType carType;
}
