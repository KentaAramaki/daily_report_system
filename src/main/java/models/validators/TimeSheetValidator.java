package models.validators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import actions.views.TimeSheetView;
import constants.MessageConst;

/**
 * 従業員インスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class TimeSheetValidator {
    /**
     * 従業員インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param tv TimeSheetViewのインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(TimeSheetView tv) {
            List<String> errors = new ArrayList<String>();


            // 出勤日時のチェック
            String startTimeError = validateStartTime(tv.getStartTime());
            if (!startTimeError.equals("")) {
                errors.add(startTimeError);
            }

            // 退勤日時のチェック
            String finishTimeError = validateFinishTime(tv.getFinishTime());
            if (!finishTimeError.equals("")) {
                errors.add(finishTimeError);
            }

            // 残業理由のチェック
            String overtimeReasonError = validateOvertimeReason(tv.getOvertimeReason());
            if (!overtimeReasonError.equals("")) {
                errors.add(overtimeReasonError);
            }

            return errors;
    }

    /**
     * 出勤日時に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param start_time 出勤日時
     * @return エラーメッセージ
     */
    private static  String validateStartTime(String startTime) {

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            df.setLenient(false);
            String st= df.format(df.parse(startTime));
            System.out.println(st);
        } catch (Exception e) {
            return MessageConst.E_NOSTART_TIME.getMessage();
        }
        return "";
    }

       /* if (startTime == null || startTime.equals("") || startTime != ZonedDateTime.parse("startTime" ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) ) {
            return MessageConst.E_NOSTART_TIME.getMessage();
        }

        // 入力値がある場合は空文字を返却
        return "";
    }*/

    /**
     * 退勤日時に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param finish_time 退勤日時
     * @return エラーメッセージ
     */
    private static  String validateFinishTime(String finishTime) {

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            df.setLenient(false);
            String ft= df.format(df.parse(finishTime));
            System.out.println(ft);
        } catch (Exception e) {
            return MessageConst.E_NOSTART_TIME.getMessage();
        }
        return "";
    }

        /*if(finishTime == null || finishTime.equals("") ) {
            return MessageConst.E_NOFINISH_TIME.getMessage();
        }

        // 入力値がある場合は空文字を返却
        return "";
    }*/

    /**
     * 残業理由に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param overtime_reason 残業理由
     * @return エラーメッセージ
     */
    private static String validateOvertimeReason(String overtimeReason) {
        if(overtimeReason == null || overtimeReason.equals("")) {
            return MessageConst.E_NOOVERTIME_REASON.getMessage();
        }

        // 入力値がある場合は空文字を返却
        return "";
    }




}
