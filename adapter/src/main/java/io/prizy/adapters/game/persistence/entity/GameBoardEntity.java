package io.prizy.adapters.game.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:01
 */


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GameBoard")
@Table(name = "game_boards")
@TypeDefs({
  @TypeDef(
    name = "int-array",
    typeClass = IntArrayType.class
  )
})
public class GameBoardEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column
  private String name;
  @Type(type = "int-array")
  @Column(columnDefinition = "integer[]")
  private Integer[] cells;
  @Column(name = "row_size")
  private Integer rowSize;

}
