package logiless.common.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "API_CODE_MASTER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeMasterEntity {
    @Id
    private String categoryCd;
    private String categoryName;
    private String outputCd;
    private String outputName;
}
