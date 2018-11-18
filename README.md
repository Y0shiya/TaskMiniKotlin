# TaskMiniKotlin

### app���W���[��
* �A�v���P�[�V�����̃G���g���[�|�C���g
* ��TaskMini�A�v���ŗB��ғ�����Activity(=EntryPointActivity)�����B
* tasksview/taskwriterview/trashview���W���[����views�p�b�P�[�W�z���ɂ���Fragment�ŉ�ʏڍׂ�\������B
* ��ʏڍחp��Fragment�́A����Fragment���Ƃɕʃ��W���[�����쐬�B�ڔ�����view�Ƃ��郂�W���[�������K����̗p�B
* �A�v���̉�ʍ\���́A1Activity3Fragment�ƂȂ�B
* ��ʑJ�ڏ����́AAndroidStudio3.2��莎���I�ɓ�������Ă���AAC��Navigation�𗘗p�B
* navigation_graph.xml�ɑJ�ڊ֌W���`�B
* �^�X�N�ꗗ�\���p��TasksFragment���ANavigation�̃X�^�[�g�A�b�v�ɐݒ�B
* �J�ڃp�^�[������1 TasksFragment(�ꗗ�\��) �� TaskWriterFragment(�V�K�o�^)
* �J�ڃp�^�[������2 TasksFragment(�ꗗ�\��) �� TaskWriterFragment(�X�V�o�^) ���V�K�X�VTaskWriterFragment�����p�B
* �J�ڃp�^�[������3 TasksFragment(�ꗗ�\��) �� TrashFragment(�����ψꗗ�\��)
* �J�ڏ����ɂ��Fragment���؂�ւ�����^�C�~���O�ŁAEntryPointActivity.Toolbar��̉�ʍ��ڂ̉�������s���Ă���BNavigation�̃C�x���g���X�i�[���ŉ�������L�q�B

### behavior���W���[��
* FatActivity/FatFragment�΍�̂��߂ɍ쐬���ꂽ���W���[���B
* �����W���[���Ō��߂�ꂽ�d�l���p������Behavior�����N���X�ɁA��ʂ̏����L�q���ڏ�����B
* �����N���X�́Aview�n���W���[����behaviors�p�b�P�[�W�z���ɍ쐬����B
* Activity/Fragment�̃C�x���g���X�i�[���`���ACommand�p�^�[����ʂ���ViewModel�ɏ������˗��B���ʂ��AViewModel�����J���Ă���LiveData�Ŏ󂯎��B(LiveData.observe)
* MVVM�p�^�[�����View�w�Ɉʒu�Â�����ƔF���B

### command���W���[��
* Xamarin.Forms��WPF�Ȃǂ�MVVM�A�v���̐��E�ŗ��p����Ă���Command�p�^�[���̊ȈՔł𓱓��B
* View�w����ViewModel�w�ւ̏����˗����A�u���������s�\���H(canExecute)�v�A�u�������s(execute)�v�̓�ʂ�ɒ��ۉ��������́B
* view�n���W���[��/viewmodels�p�b�P�[�W�z����ViewModel�N���X�Ŏ�������B

### di���W���[��
* �I���I���ˑ��������̎d�l�������W���[���B
* �C���^�[�t�F�[�X�Ǝ������A1��1�ƂȂ�悤�ȉ��ւȗ��p��z��B
* model/restdao/realmdao���W���[���@�\�̌Ăяo�����ɁA���p�B

### entity���W���[��
* �����^�X�N��Task�N���X�A�����ς݃^�X�N��Trash�N���X�Ƃ��Ĉ����B
* �o����DataBinding�̗��p��z�肵�Ă��邽�߁ABaseObservable���p���B
* Gson���C�u�����ɂ��AJson�V���A���C�Y/�f�V���A���C�Y����������ۂɂ������W���[���̃G���e�B�e�B�N���X�𗘗p�B

### model���W���[��
* MVVM/Model�w�̖�����S���C���^�[�t�F�[�X�Ǝ����N���X����Ȃ郂�W���[���B
* �C���^�[�t�F�[�X�Ǝ����N���X�́A1��1�B
* �C���^�[�t�F�[�X�̖����K��́AI�Z�ZModel�B���Z�Z�́A�v�����B�ړ����́uI�v�́AInterface�̓������B
* �C���^�[�t�F�[�X�́Ainterfaces�p�b�P�[�W�z���ɍ쐬����B
* �C���^�[�t�F�[�X�̃A�N�Z�X���x���́Apublic�B
* �����N���X�̖����K��́A�Z�ZModel�B���Z�Z�́A�v�����B
* �����N���X�́Aimpls�p�b�P�[�W�z���ɍ쐬����B��impls = Implements�̗��B
* �����N���X�̃A�N�Z�X���x���́A���W���[���O������̒��ڎQ�Ƃ��֎~���邽��internal�Ƃ���B
* dependencies�p�b�P�[�W�z���ɂ���Dependency�N���X��ʂ��Ĉˑ����������A�����N���X�̋@�\���Ăяo���B
* ViewModel�Ɍ��J���郁�\�b�h�̃V�O�j�`���͌����I�Ɏ��̍��ڂ𖞂����悤�쐬�Bsuspend�ł��邱��/����̖߂�l��Ԃ��Ȃ�FuturePromise��Deferred(Unit)�ł��邱��/await�\�ł��邱��/AsyncAwait�ȃ��\�b�h�ł��邱�Ƃ��Ăяo�����ɒm�点�邽�߁A���\�b�h���̖�����Async�Ƃ��邱��/�Ăяo�������珈���L�����Z���\�ȐeJob���R���e�L�X�g�ɐݒ肷�邱�ƁB
* Model�̏������ʂ́A���\�b�h�̖߂�l�Ƃ��ĕԂ��̂ł͂Ȃ��ALiveData/MutableLiveData.postValue��ʂ��ĕԂ��B�s���Ɩ߂�̏����̒P����������m�ۂ���Ӑ}�B

### navigation���W���[��
* AAC Navigation(NavController)�̃��b�p�[���W���[���B
* �eActivity(EntryPointActivity)�Ő��������J�ڊǗ��@�\���A��ʏڍׂ̎qFragment(TasksFragment/TaskWriterFragment/TrashFragment)���烊�t���N�V�����A�N�Z�X�����L���p���邽�߂̋@�\��񋟂���B
* �qFragment�Ƀ��b�p�[���W���[����ʂ��đJ�ڏ��������邱�Ƃɂ��AAAC��Navigation���C�u�����ւ̖����I�Ȉˑ���app���W���[���݂̂Ɍ��肳���Ă���B

### realmdao���W���[��
* ���݁A���s���ɉғ����Ă��Ȃ��i���������p���W���[���B
* �o�b�N�G���h����������܂ł̊ԁA�b��I�Ƀf�[�^�i�����̓���m�F���A�v���������ōs����悤�쐬���p�������W���[���B
* SQL���L�q�����ɁA��т��ĂЂƂ̌���(Kotlin)�ŁA�i���������܂ŏ����邽��Realm���̗p�B
* IDao�C���^�t�F�[�X(DAO/CRUD�p�^�[��)��ʂ��āAModel�w(model���W���[��)�Ɏ�����(RealmDao)��񋟁B
* DAO�ڍ׋@�\�ւ̒��ڎQ�Ƃ��֎~���邽�߁ARealmDao��internal�Ƃ��Ă���B
* dependencies�p�b�P�[�W�z���ɂ���Dependency�N���X��ʂ��Ĉˑ�����������B
* �W�F�l���N�X/���t���N�V�����v���O���~���O�ɂ��A�G���e�B�e�B�N���X��Realm�Ǘ����f�[�^(RealmObject)�̃}�b�s���O�����𒊏ۉ����Ă���A�x�^�����ɂ��}�b�s���O������r�����Ă���B

### restdao���W���[��
* REST Web API�ɑ΂���f�[�^�̓ǂݏ������ADAO/CRUD�p�^�[���Ń��b�v�������W���[���B
* IDao�C���^�t�F�[�X��ʂ��āA������(RestDao)��񋟂���B
* DAO�ڍ׋@�\�ւ̒��ڎQ�Ƃ��֎~���邽�߁ARestDao��internal�Ƃ��Ă���B
* Retrofit2/OKHttp/Gson���C�u�����𗘗p�B
* Dao/create���\�b�h���AHTTP/POST���\�b�h�ɑΉ������Ă���B
* Dao/read���\�b�h���AHTTP/GET���\�b�h�ɑΉ������Ă���B
* Dao/update���\�b�h���AHTTP/PUT���\�b�h�ɑΉ������Ă���B
* Dao/delete���\�b�h���AHTTP/DELETE���\�b�h�ɑΉ������Ă���B
* OKHttp/HttpLoggingInterceptor�ɂ��ALogcat�ւ̒ʐM���O�o�͂�ݒ肵�Ă���B
* Gson�ɂ��A�G���e�B�e�B�N���X����Json�ւ̃V���A���C�Y�������s���Ă���B
* Gson�ɂ��AJson����G���e�B�e�B�N���X�ւ̃f�V���A���C�Y�������s���Ă���B
* �����W���[������̖₢���킹�́AWebAPI/TaskMiniBackend�v���W�F�N�g/Controllers�t�H���_�z���̃R���g���[���[�N���X�Ƀ��[�e�B���O�����B

### tasksview���W���[��
* �������^�X�N�ꗗ��\�����邽�߂�view���W���[���B
* MVVM���View�w��ViewModel�w�ABehavior��������\������Ă���B
* EntryPointActivity/TasksFragment(View�w) �� TasksViewModel(ViewModel�w) �� ITasksModel/TasksModel(Model�w) �� IDao/RestDao(�i�����w)�B
* TasksFragment�́AEntryPointActivity������AAC.NavController�ɂ��J�ڊǗ����ɂ���B
* �ڍחpFragment�̖����K����A�Z�ZFragment�Ƃ��Ă���B���Z�Z�́A�v�����B
* ViewModel�̖����K����A�Z�ZViewModel�Ƃ��Ă���B���Z�Z�́A�v�����B

### taskwriterview���W���[��
* �������^�X�N��V�K�o�^�A�ҏW�o�^���邽�߂�view���W���[���B
* EntryPointActivity/TaskWriterFragment(View�w) �� TaskWriterViewModel(ViewModel�w) �� ITaskWriterModel/TaskWriterModel(Model�w) �� IDao/RestDao(�i�����w)

### trashview���W���[��
* �����ς݃^�X�N�ꗗ��\�����邽�߂�view���W���[���B
* �����ς݃^�X�N���A�������^�X�N�ꗗ�ɖ߂�Revert�@�\�����B
* �����ς݃^�X�N���A��or�S�����S�폜����Delete/Delete all�@�\�����B
* EntryPointActivity/TrashFragment(View�w) �� TrashViewModel(ViewModel�w) �� ITrashModel/TrashModel(Model�w) �� IDao/RestDao(�i�����w)

### utility���W���[��
* �e���W���[���ŁA�p�ɗ��p���鋤�ʏ������A�쐬�z�u����郂�W���[���B