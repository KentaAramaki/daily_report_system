package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.TimeSheetView;
import constants.MessageConst;
import services.TimeSheetService;

/**
 * 従業員インスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class TimeSheetValidator {
    /**
     * 従業員インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param ev EmployeeViewのインスタンス
     * @param codeDuplicateCheckFlag 社員番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
                TimeSheetService service, TimeSheetView tv, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
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
    private static String validateStartTime(String startTime) {
        if (startTime == null || startTime.equals("")) {
            return MessageConst.E_NOSTART_TIME.getMessage();
        }

        // 入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 退勤日時に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param finish_time 退勤日時
     * @return エラーメッセージ
     */
    private static String validateFinishTime(String finishTime) {
        if(finishTime == null || finishTime.equals("")) {
            return MessageConst.E_NOFINISH_TIME.getMessage();
        }

        // 入力値がある場合は空文字を返却
        return "";
    }

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