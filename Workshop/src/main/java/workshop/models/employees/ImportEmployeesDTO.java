package workshop.models.employees;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportEmployeesDTO {

    @XmlElement(name = "employee")
    private List<ImportEmployeeDTO> employeeDTOs;

    public ImportEmployeesDTO() {
    }

    public List<ImportEmployeeDTO> getEmployeeDTOs() {
        return employeeDTOs;
    }

    public void setEmployeeDTOs(List<ImportEmployeeDTO> employeeDTOs) {
        this.employeeDTOs = employeeDTOs;
    }
}
