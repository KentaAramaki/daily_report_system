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
    public List<TimeSheetView> getPerPage(int page) {
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
        TimeSheet t = findOneInternal(id);
        return TimeSheetConverter.toView(t);
        //return TimeSheetConverter.toView(findOneInternal(id));

    }

    /**
     * 画面から入力されたタイムシートの登録内容を元にデータを1件作成し、タイムシートテーブルに登録する
     * @param tv タイムシートの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(TimeSheetView tv) {
        List<String> errors = TimeSheetValidator.validate(tv);
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
     * 画面から入力された従業員の更新内容を元にデータを1件作成し、タイムシートテーブルを更新する
     * @param tv 画面から入力されたタイムシートの登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(TimeSheetView tv) {

        //更新内容についてバリデーションを行う
        List<String> errors = TimeSheetValidator.validate(tv);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            updateInternal(tv);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件に従業員データを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得する
        TimeSheetView savedTim = findOne(id);

        //論理削除フラグをたてる
        savedTim.setDeleteFlag(JpaConst.TIM_DEL_TRUE);

        //更新処理を行う
        updateInternal(savedTim);

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
