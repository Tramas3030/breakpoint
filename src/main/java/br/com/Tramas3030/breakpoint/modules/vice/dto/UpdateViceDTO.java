package br.com.Tramas3030.breakpoint.modules.vice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateViceDTO {

  private String description;
  private String addictionImpact;
  private String impactCost;
  private List<String> criticalHours;

}
