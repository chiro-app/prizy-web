package io.prizy.adapters.asset.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 10:24
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Asset")
@Table(name = "assets")
public class AssetEntity {

  @Id
  private String id;
  @Column(name = "original_name")
  private String originalName;
  @Column
  private String path;
  @Column
  private String type;
  @Column
  private Long size;

}