package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.TimeSheet;

/**
 * 従業員データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class TimeSheetConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param ev TimeSheetViewのインスタンス
     * @return TimeSheetのインスタンス
     */
    public static TimeSheet toModel(TimeSheetView tv) {

        return new TimeSheet(
                tv.getId(),
                EmployeeConverter.toModel(tv.getEmployee()),
                tv.getStartTime(),
                tv.getFinishTime(),
                tv.getOvertimeReason(),
                tv.getDeleteFlag() == null
                ? null
                : tv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                    ? JpaConst.TIM_DEL_TRUE
                    : JpaConst.TIM_DEL_FALSE);

    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e TimeSheetのインスタンス
     * @return TimeSheetViewのインスタンス
     */
    public static TimeSheetView toView(TimeSheet t) {

        if(t == null) {
            return null;
        }


        return new TimeSheetView(
                t.getId(),
                EmployeeConverter.toView(t.getEmployee()),
                t.getStartTime(),
                t.getFinishTime(),
                t.getOvertimeReason(),
                t.getDeleteFlag() == null
                    ? null
                    : t.getDeleteFlag() == JpaConst.TIM_DEL_TRUE
                        ? AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        : AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

      }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<TimeSheetView> toViewList(List<TimeSheet> list) {
        List<TimeSheetView> tvs = new ArrayList<>();

        for (TimeSheet t : list) {
            tvs.add(toView(t));
        }

        return tvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(TimeSheet t, TimeSheetView tv) {
        t.setId(tv.getId());
        t.setStartTime(tv.getStartTime());
        t.setFinishTime(tv.getFinishTime());
        t.setOvertimeReason(tv.getOvertimeReason());

    }

}
