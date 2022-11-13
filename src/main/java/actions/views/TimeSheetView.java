 package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class TimeSheetView {

    /**
     * id
     */
    private Integer id;

    /**
     * 日報を登録した従業員
     */
    private EmployeeView employee;

    /**
     * 出勤日時
     */
    //private String startTime;
    private  LocalDateTime startTime;

    /**
     * 退勤日時
     */
    //private String finishTime;
    private  LocalDateTime finishTime;

    /**
     * 残業理由
     */
    private String overtimeReason;

    /*
     * 削除された従業員かどうか（現役：0、削除済み：1）
     */
    private Integer deleteFlag;

}
