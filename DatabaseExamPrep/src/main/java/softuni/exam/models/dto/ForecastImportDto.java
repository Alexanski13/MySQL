package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.DayOfWeek;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {

    @NotNull
    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    @NotNull
    @XmlElement(name = "min_temperature")
    private Double minTemperature;

    @DecimalMin(value = "-20")
    @DecimalMax(value = "60")
    @NotNull
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;

    @NotNull
    @XmlElement
    private String sunset;

    @NotNull
    @XmlElement
    private String sunrise;

    @NotNull
    @XmlElement
    private Long city;
}
