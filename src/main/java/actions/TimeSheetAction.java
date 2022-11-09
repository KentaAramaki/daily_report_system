package actions;

/**
 * タイムシートに関わる処理を行うActionクラス
 *
 */
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.TimeSheetView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.TimeSheetService;

public class TimeSheetAction extends ActionBase {

    private TimeSheetService service;

    /*
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new TimeSheetService();

        // メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        // 指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<TimeSheetView> timeSheets = service.getMinePerPage(page);

        // 全てのタイムシートデータの件数を取得
        long timeSheetCount = service.countAll();

        putRequestScope(AttributeConst.TIMSHEETS, timeSheets); // 取得したタイムシートデータ
        putRequestScope(AttributeConst.TIM_COUNT, timeSheetCount ); // 全てのタイムシートデータの件数
        putRequestScope(AttributeConst.PAGE, page); // ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); // 1ページに表示するレコードの数

        // セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        // 一覧画面を表示
        forward(ForwardConst.FW_TIM_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); // CSRF対策用トークン
        putRequestScope(AttributeConst.TIMESHEET, new TimeSheetView()); // 空のタイムシートインスタンス

        // 新規登録画面を表示
        forward(ForwardConst.FW_TIM_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        // CSRF対策 tokenのチェック
        if (checkToken()) {

            // セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            // パラメータの値を元にタイムシート情報のインスタンスを作成する
            TimeSheetView tv = new TimeSheetView(
                    null,
                    ev,
                    getRequestParam(AttributeConst.TIM_START_TIME),
                    getRequestParam(AttributeConst.TIM_FINISH_TIME),
                    getRequestParam(AttributeConst.TIM_OVERTIME_REASON),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            // タイムシート情報登録
            /*List<String> errors = service.create(tv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, tv);//入力されたタイムシート情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_TIM_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TIM, ForwardConst.CMD_INDEX);

        }*/
       }

    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        // idを条件に従業員データを取得
        TimeSheetView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TIM_ID)));

        if(tv == null ) {
            // 該当のタイムシートデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.REPORT, tv); // 取得したタイムシートデータ

            // 詳細画面を表示
            forward(ForwardConst.FW_TIM_SHOW);
        }
    }



    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

        }

        // idを条件にタイムシートデータを取得する
        TimeSheetView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TIM_ID)));

        /*if (tv == null || tv.getId() != tv.getTimeSheet().getId()) {
            // 該当のタイムシートデータが存在しない、または
            // ログインしている従業員がタイムシートの作成者ではない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); // CSRF対策用トークン
            putRequestScope(AttributeConst.REPORT, tv); // 取得したタイムシートデータ

            // 編集画面を表示
            forward(ForwardConst.FW_TIM_EDIT);
        }*/

        if (tv == null || tv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.TIMESHEET, tv); //取得した従業員情報

        //編集画面を表示する
        forward(ForwardConst.FW_TIM_EDIT);
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

      // CSRF対策 tokenのチェック
        if (checkToken()) {

            // idを条件に日報データを取得する
            TimeSheetView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TIM_ID)));

            // 入力されたタイムシート内容を設定する
            tv.setStartTime(getRequestParam(AttributeConst.TIM_START_TIME));
            tv.setFinishTime(getRequestParam(AttributeConst.TIM_FINISH_TIME));
            tv.setOvertimeReason(getRequestParam(AttributeConst.TIM_OVERTIME_REASON));

            /*if (errors.size() > 0) {
                // 更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); // CSRF対策用トークン
                putRequestScope(AttributeConst.TIMESHEET, tv); // 入力されたタイムシート情報
                putRequestScope(AttributeConst.ERR, errors); // エラーのリスト

              //編集画面を再表示
                forward(ForwardConst.FW_REP_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);

            }*/

        }
    }

}