package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * タイムシートデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_TIM)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_TIM_GET_ALL,
            query = JpaConst.Q_TIM_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_TIM_COUNT,
            query = JpaConst.Q_TIM_COUNT_DEF),
    //@NamedQuery(
            //name = JpaConst.Q_TIM_GET_ALL_MINE,
            //query = JpaConst.Q_TIM_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_TIM_COUNT_ALL_MINE,
            query = JpaConst.Q_TIM_COUNT_ALL_MINE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class TimeSheet {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.TIM_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /*
     * 日報を登録した従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TIM_COL_EMP, nullable = false)
    private Employee employee;


    /**
     * 出勤日時
     */
    @Column(name = JpaConst.TIM_COL_START_TIME, nullable = false)
    private String startTime;

    /**
     * 退勤日時
     */
    @Column(name = JpaConst.TIM_COL_FINISH_TIME, nullable = false)
    private String finishTime;

    /**
     * 残業理由
     */
    @Lob
    @Column(name = JpaConst.TIM_COL_OVERTIME_REASON, nullable = false)
    private String overtimeReason;

    /*
     * 削除された従業員かどうか（現役：0、削除済み：1）
     */
    @Column(name = JpaConst.TIM_COL_DELETE_FLAG, nullable = false)
    private Integer deleteFlag;

}
