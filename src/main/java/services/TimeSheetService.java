package services;

import java.util.List;

import actions.views.TimeSheetConverter;
import actions.views.TimeSheetView;
import constants.JpaConst;
import models.TimeSheet;

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
    /*public List<String> create(TimeSheetView tv) {
        List<String> errors = TimeSheetValidator.validate(tv);
        if(errors.size() == 0) {
            String ldt = String.now();
            tv.setStartTime(ldt);
            tv.setFinishTime(ldt);
            createInternal(tv);
        }

        // バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }*/

    /**
     * 画面から入力されたタイムシートの登録内容を元に、タイムシートデータを更新する
     * @param tv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    /*public List<String> update(TimeSheetView tv) {

        // バリデーションを行う
        List<String> errors = TimeSheetValidator.validate(tv);

        if (errors.size() == 0) {


        }
    }*/

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
    private void create(TimeSheetView tv) {

        em.getTransaction().begin();
        em.persist(TimeSheetConverter.toModel(tv));
        em.getTransaction().commit();
    }

    /**
     * タイムシートデータを更新する
     * @param tv タイムシートデータ
     */
    private void update(TimeSheetView tv) {

        em.getTransaction().begin();
        TimeSheet t = findOneInternal(tv.getId());
        TimeSheetConverter.copyViewToModel(t, tv);
        em.getTransaction().commit();
    }

}
