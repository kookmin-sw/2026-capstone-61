package dev.mvc.dog_dbti;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DogDbtiVO {
    // DogVO와 이름을 맞춰서 dogno(소문자)로 변경하는 것이 좋습니다.
    private int dogno;          
    private int ePer;          
    private int sPer;          
    private int aPer;          
    private int oPer;          
    private String updateDate; 

    /**
     * 최종 4글자 유형 반환
     */
    public String getDbtiType() {
        return (ePer >= 50 ? "E" : "I") +
               (sPer >= 50 ? "S" : "N") +
               (aPer >= 50 ? "A" : "T") +
               (oPer >= 50 ? "O" : "U");
    }

    public String getEnergyLabel() { return ePer >= 50 ? "활동적" : "차분함"; }
    public String getSocialLabel() { return sPer >= 50 ? "사교적" : "독립적"; }
    public String getAffectionLabel() { return aPer >= 50 ? "애교많음" : "의젓함"; }
    public String getObedienceLabel() { return oPer >= 50 ? "순종적" : "자기주관확실"; }
}