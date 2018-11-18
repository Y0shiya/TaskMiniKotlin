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
* 遷移処理によりFragmentが切り替わったタイミングで、EntryPointActivity.Toolbar上の画面項目の可視制御を行っている。Navigationのイベントリスナー内で可視制御を記述。

### behaviorモジュール
* FatActivity/FatFragment対策のために作成されたモジュール。
* 当モジュールで決められた仕様を継承するBehavior実装クラスに、画面の処理記述を移譲する。
* 実装クラスは、view系モジュールのbehaviorsパッケージ配下に作成する。
* Activity/Fragmentのイベントリスナーを定義し、Commandパターンを通じてViewModelに処理を依頼。結果を、ViewModelが公開しているLiveDataで受け取る。(LiveData.observe)
* MVVMパターン上のView層に位置づけられると認識。

### commandモジュール
* Xamarin.FormsやWPFなどのMVVMアプリの世界で利用されているCommandパターンの簡易版を導入。
* View層からViewModel層への処理依頼を、「処理が実行可能か？(canExecute)」、「処理実行(execute)」の二通りに抽象化したもの。
* view系モジュール/viewmodelsパッケージ配下のViewModelクラスで実装する。

### diモジュール
* オレオレ依存性注入の仕様を持つモジュール。
* インターフェースと実装が、1対1となるような穏便な利用を想定。
* model/restdao/realmdaoモジュール機能の呼び出し時に、利用。

### entityモジュール
* 未完タスクをTaskクラス、完了済みタスクをTrashクラスとして扱う。
* 双方向DataBindingの利用を想定しているため、BaseObservableを継承。
* Gsonライブラリにより、Jsonシリアライズ/デシリアライズ処理をする際にも当モジュールのエンティティクラスを利用。

### modelモジュール
* MVVM/Model層の役割を担うインターフェースと実装クラスからなるモジュール。
* インターフェースと実装クラスは、1対1。
* インターフェースの命名規約は、I〇〇Model。※〇〇は、要件名。接頭辞の「I」は、Interfaceの頭文字。
* インターフェースは、interfacesパッケージ配下に作成する。
* インターフェースのアクセスレベルは、public。
* 実装クラスの命名規約は、〇〇Model。※〇〇は、要件名。
* 実装クラスは、implsパッケージ配下に作成する。※impls = Implementsの略。
* 実装クラスのアクセスレベルは、モジュール外部からの直接参照を禁止するためinternalとする。
* dependenciesパッケージ配下にあるDependencyクラスを通じて依存性注入し、実装クラスの機能を呼び出す。
* ViewModelに公開するメソッドのシグニチャは原則的に次の項目を満たすよう作成。suspendであること/特定の戻り値を返さないFuturePromiseなDeferred(Unit)であること/await可能であること/AsyncAwaitなメソッドであることを呼び出し元に知らせるため、メソッド名の末尾をAsyncとすること/呼び出し元から処理キャンセル可能な親Jobをコンテキストに設定すること。
* Modelの処理結果は、メソッドの戻り値として返すのではなく、LiveData/MutableLiveData.postValueを通じて返す。行きと戻りの処理の単一方向性を確保する意図。

### navigationモジュール
* AAC Navigation(NavController)のラッパーモジュール。
* 親Activity(EntryPointActivity)で生成した遷移管理機能を、画面詳細の子Fragment(TasksFragment/TaskWriterFragment/TrashFragment)からリフレクションアクセスし共有利用するための機能を提供する。
* 子Fragmentにラッパーモジュールを通じて遷移処理させることにより、AACのNavigationライブラリへの明示的な依存をappモジュールのみに限定させている。

### realmdaoモジュール
* 現在、実行時に稼働していない永続化処理用モジュール。
* バックエンドを実装するまでの間、暫定的にデータ永続化の動作確認をアプリ側だけで行えるよう作成利用したモジュール。
* SQLを記述せずに、一貫してひとつの言語(Kotlin)で、永続化処理まで書けるためRealmを採用。
* IDaoインタフェース(DAO/CRUDパターン)を通じて、Model層(modelモジュール)に実処理(RealmDao)を提供。
* DAO詳細機能への直接参照を禁止するため、RealmDaoはinternalとしている。
* dependenciesパッケージ配下にあるDependencyクラスを通じて依存性注入する。
* ジェネリクス/リフレクションプログラミングにより、エンティティクラスとRealm管理下データ(RealmObject)のマッピング処理を抽象化しており、ベタ書きによるマッピング処理を排除している。

### restdaoモジュール
* REST Web APIに対するデータの読み書きを、DAO/CRUDパターンでラップしたモジュール。
* IDaoインタフェースを通じて、実処理(RestDao)を提供する。
* DAO詳細機能への直接参照を禁止するため、RestDaoはinternalとしている。
* Retrofit2/OKHttp/Gsonライブラリを利用。
* Dao/createメソッドを、HTTP/POSTメソッドに対応させている。
* Dao/readメソッドを、HTTP/GETメソッドに対応させている。
* Dao/updateメソッドを、HTTP/PUTメソッドに対応させている。
* Dao/deleteメソッドを、HTTP/DELETEメソッドに対応させている。
* OKHttp/HttpLoggingInterceptorにより、Logcatへの通信ログ出力を設定している。
* Gsonにより、エンティティクラスからJsonへのシリアライズ処理を行っている。
* Gsonにより、Jsonからエンティティクラスへのデシリアライズ処理を行っている。
* 当モジュールからの問い合わせは、WebAPI/TaskMiniBackendプロジェクト/Controllersフォルダ配下のコントローラークラスにルーティングされる。

### tasksviewモジュール
* 未完了タスク一覧を表示するためのviewモジュール。
* MVVM上のView層とViewModel層、Behavior実装から構成されている。
* EntryPointActivity/TasksFragment(View層) ⇔ TasksViewModel(ViewModel層) ⇔ ITasksModel/TasksModel(Model層) ⇔ IDao/RestDao(永続化層)。
* TasksFragmentは、EntryPointActivityが持つAAC.NavControllerによる遷移管理下にある。
* 詳細用Fragmentの命名規約を、〇〇Fragmentとしている。※〇〇は、要件名。
* ViewModelの命名規約を、〇〇ViewModelとしている。※〇〇は、要件名。

### taskwriterviewモジュール
* 未完了タスクを新規登録、編集登録するためのviewモジュール。
* EntryPointActivity/TaskWriterFragment(View層) ⇔ TaskWriterViewModel(ViewModel層) ⇔ ITaskWriterModel/TaskWriterModel(Model層) ⇔ IDao/RestDao(永続化層)

### trashviewモジュール
* 完了済みタスク一覧を表示するためのviewモジュール。
* 完了済みタスクを、未完了タスク一覧に戻すRevert機能を持つ。
* 完了済みタスクを、個別or全件完全削除するDelete/Delete all機能を持つ。
* EntryPointActivity/TrashFragment(View層) ⇔ TrashViewModel(ViewModel層) ⇔ ITrashModel/TrashModel(Model層) ⇔ IDao/RestDao(永続化層)

### utilityモジュール
* 各モジュールで、頻繁利用する共通処理が、作成配置されるモジュール。