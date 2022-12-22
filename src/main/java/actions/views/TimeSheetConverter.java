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
     * @param tv TimeSheetViewのインスタンス
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
     * @param t TimeSheetのインスタンス
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
     * @param t DTOモデル(コピー先)
     * @param tv Viewモデル(コピー元)
     */
    public static void copyViewToModel(TimeSheet t, TimeSheetView tv) {

        System.out.println("@@@@@@@@@@@@確認11");
        System.out.println(t.getId());
        System.out.println(t.getStartTime());
        System.out.println(t.getFinishTime());
        System.out.println(tv.getId());
        System.out.println(tv.getStartTime());
        System.out.println(tv.getFinishTime());

        t.setId(tv.getId());
        t.setEmployee(EmployeeConverter.toModel(tv.getEmployee()));
        t.setStartTime(tv.getStartTime());
        t.setFinishTime(tv.getFinishTime());
        t.setOvertimeReason(tv.getOvertimeReason());
        t.setDeleteFlag(tv.getDeleteFlag());

        System.out.println("@@@@@@@@@@@@確認12");
        System.out.println(t.getId());
        System.out.println(t.getStartTime());
        System.out.println(t.getFinishTime());
        System.out.println(tv.getId());
        System.out.println(tv.getStartTime());
        System.out.println(tv.getFinishTime());

    }

}
