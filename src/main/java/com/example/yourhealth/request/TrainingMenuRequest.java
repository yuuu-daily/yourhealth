package com.example.yourhealth.request;
import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
/**
 * メニュー情報 リクエストデータ
 */
@Data
public class TrainingMenuRequest implements Serializable {
  /**
   * 
   * カテゴリー
   */
  @NotEmpty(message = "カテゴリーを入力してください")
  @Size(max = 100, message = "カテゴリーは100文字以内で入力してください")
  private String category;
  /**
   * タイトル
   */
  @Size(max = 100, message = "タイトルは100文字以内で入力してください")
  private String title;
  /**
   * メニュー内容
   */
  @Size(max = 1000, message = "1000文字以内で入力してください")
  private String description;
}