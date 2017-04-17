package sen.wedding.com.weddingsen.component.refresh;


public interface TipsHelper {

    void showEmpty();

    void hideEmpty();

    void showLoading(boolean firstPage);

    void hideLoading();

    void showError(boolean firstPage, Throwable error);

    void hideError();

    void showHasMore();

    void hideHasMore();
}