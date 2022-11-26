package com.example.yourhealth.request;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * メニュー情報更新リクエストデータ
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TrainingMenuUpdateRequest extends TrainingMenuRequest implements Serializable {
  /**
   * メニューID
   */
  @NotNull
  private Long id;
}