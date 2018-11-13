# TaskMiniKotlin

### appモジュール
* アプリケーションのエントリーポイント
* 当TaskMiniアプリで唯一稼働するActivity(=EntryPointActivity)を持つ。
* tasksview/taskwriterview/trashviewモジュールのviewsパッケージ配下にあるFragmentで画面詳細を表現する。
* 1Activity3Fragment構成。
* 画面遷移処理は、AndroidStudio3.2より試験的に導入されているAACのNavigationを利用。
* navigation_graph.xmlに遷移関係を定義。
* タスク一覧表示用のTasksFragmentを、Navigationのスタートアップに設定。
* 遷移パターンその1 TasksFragment(一覧表示) ⇒ TaskWriterFragment(新規登録)
* 遷移パターンその2 TasksFragment(一覧表示) ⇒ TaskWriterFragment(更新登録) ※新規更新TaskWriterFragmentを共用。
