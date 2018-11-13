# TaskMiniKotlin

### appモジュール
* アプリケーションのエントリーポイント
* 当TaskMiniアプリで唯一稼働するActivity(=EntryPointActivity)を持つ。
* tasksview/taskwriterview/trashviewモジュールのviewsパッケージ配下にあるFragmentで画面詳細を表現する。
* 画面詳細用のFragmentは、いちFragmentごとに別モジュールを作成。接尾辞をviewとするモジュール命名規約を採用。
* アプリの画面構成は、1Activity3Fragmentとなる。
* 画面遷移処理は、AndroidStudio3.2より試験的に導入されているAACのNavigationを利用。
* navigation_graph.xmlに遷移関係を定義。
* タスク一覧表示用のTasksFragmentを、Navigationのスタートアップに設定。
* 遷移パターンその1 TasksFragment(一覧表示) ⇒ TaskWriterFragment(新規登録)
* 遷移パターンその2 TasksFragment(一覧表示) ⇒ TaskWriterFragment(更新登録) ※新規更新TaskWriterFragmentを共用。
* 遷移パターンその3 TasksFragment(一覧表示) ⇒ TrashFragment(完了済一覧表示)
* 遷移処理によりFragmentが切り替わったタイミングで、EntryPointActivity.Toobar上の画面項目の可視制御を行っている。Navigationのイベントリスナー内で可視制御を記述。

### behaviorモジュール
* FatActivity/FatFragment対策のために作成されたモジュール。
* 当モジュールで決められた仕様を継承するBehavior実装クラスに、画面の処理記述を移譲する。
* 実装クラスは、view系モジュールのbehaviorsパッケージ配下に作成する。
* Activity/Fragmentのイベントリスナーを定義し、Commandパターンを通じてViewModelに処理を依頼。結果を、ViewModelが公開しているLiveDataで受け取る。(LiveData.observe)

### commandモジュール