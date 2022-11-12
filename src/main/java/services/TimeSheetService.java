package services;

import java.util.List;

import actions.views.TimeSheetConverter;
import actions.views.TimeSheetView;
import constants.JpaConst;
import models.TimeSheet;
import models.validators.TimeSheetValidator;

/**
 * タイムシートテーブルの操作に関わる処理を行うクラス
 */
public class TimeSheetService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、TimeSheetViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<TimeSheetView> getMinePerPage(int page) {
        List<TimeSheet> timeSheets = em.createNamedQuery(JpaConst.Q_TIM_GET_ALL, TimeSheet.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return TimeSheetConverter.toViewList(timeSheets);
    }

    /**
     * タイムシートテーブルのデータの件数を取得し、返却する
     * @return タイムシートテーブルのデータの件数
     */
    public long countAll() {
        long timCount = (long) em.createNamedQuery(JpaConst.Q_TIM_COUNT, Long.class)
                .getSingleResult();

        return timCount;
    }

    /**
     * idを条件に取得したデータをTimeSheetViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public TimeSheetView findOne(int id) {
        //TimeSheet t = findOneInternal(id);
        //return TimeSheetConverter.toView(t);
        return TimeSheetConverter.toView(findOneInternal(id));

    }

    /**
     * 画面から入力されたタイムシートの登録内容を元にデータを1件作成し、タイムシートテーブルに登録する
     * @param tv タイムシートの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(TimeSheetView tv) {
        List<String> errors = TimeSheetValidator.validate(this, tv, true, true);
        if(errors.size() == 0) {
            //String ldt = String.now();
            //tv.setStartTime(ldt);
            //tv.setFinishTime(ldt);
            createInternal(tv);
        }

        // バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力されたタイムシートの登録内容を元に、タイムシートデータを更新する
     * @param tv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(TimeSheetView tv) {

        // バリデーションを行う
        List<String> errors = TimeSheetValidator.validate(this, tv, true, true);

        if (errors.size() == 0) {

            updateInternal(tv);

        }

      //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された従業員の更新内容を元にデータを1件作成し、従業員テーブルを更新する
     * @param ev 画面から入力された従業員の登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    /*public List<String> update(TimeSheetView tv) {

        //idを条件に登録済みの従業員情報を取得する
        TimeSheetView savedTim = findOne(tv.getId());

        boolean validateCode = false;
        if (!savedTim.getCode().equals(tv.getCode())) {
            //社員番号を更新する場合

            //社員番号についてのバリデーションを行う
            validateCode = true;
            //変更後の社員番号を設定する
            savedEmp.setCode(ev.getCode());
        }

        boolean validatePass = false;
        if (ev.getPassword() != null && !ev.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリデーションを行う
            validatePass = true;

            //変更後のパスワードをハッシュ化し設定する
            savedEmp.setPassword(
                    EncryptUtil.getPasswordEncrypt(ev.getPassword(), pepper));
        }

        //savedTim.setName(tv.getName()); //変更後の氏名を設定する
        //savedTim.setAdminFlag(ev.getAdminFlag()); //変更後の管理者フラグを設定する

        //更新日時に現在時刻を設定する
        //LocalDateTime today = LocalDateTime.now();
        //savedEmp.setUpdatedAt(today);

        //更新内容についてバリデーションを行う
        List<String> errors = TimeSheetValidator.validate(this, savedTim, true, true);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedTim);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }*/

    /**
     * idを条件に従業員データを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得する
        TimeSheetView tv = findOne(id);

        //更新日時に現在時刻を設定する
        //LocalDateTime today = LocalDateTime.now();
        //savedEmp.setUpdatedAt(today);

        //論理削除フラグをたてる
        tv.setDeleteFlag(JpaConst.TIM_DEL_TRUE);

        //更新処理を行う
        update(tv);

    }


    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private TimeSheet findOneInternal(int id) {
        TimeSheet t = em.find(TimeSheet.class, id);

        return t;
    }

    /**
     * タイムシートデータを1件登録する
     * @param tv タイムシートデータ
     */
    private void createInternal(TimeSheetView tv) {

        em.getTransaction().begin();
        em.persist(TimeSheetConverter.toModel(tv));
        em.getTransaction().commit();
    }

    /**
     * タイムシートデータを更新する
     * @param tv タイムシートデータ
     */
    private void updateInternal(TimeSheetView tv) {

        em.getTransaction().begin();
        TimeSheet t = findOneInternal(tv.getId());
        TimeSheetConverter.copyViewToModel(t, tv);
        em.getTransaction().commit();
    }

}
