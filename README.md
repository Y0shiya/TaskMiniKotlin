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
* �J�ڏ����ɂ��Fragment���؂�ւ�����^�C�~���O�ŁAEntryPointActivity.Toobar��̉�ʍ��ڂ̉�������s���Ă���BNavigation�̃C�x���g���X�i�[���ŉ�������L�q�B

### behavior���W���[��
* FatActivity/FatFragment�΍�̂��߂ɍ쐬���ꂽ���W���[���B
* �����W���[���Ō��߂�ꂽ�d�l���p������Behavior�����N���X�ɁA��ʂ̏����L�q���ڏ�����B
* �����N���X�́Aview�n���W���[����behaviors�p�b�P�[�W�z���ɍ쐬����B
* Activity/Fragment�̃C�x���g���X�i�[���`���ACommand�p�^�[����ʂ���ViewModel�ɏ������˗��B���ʂ��AViewModel�����J���Ă���LiveData�Ŏ󂯎��B(LiveData.observe)

### command���W���[��
* Xamarin.Forms��WPF�Ȃǂ�MVVM�A�v���̐��E�ŗ��p����Ă���Command�p�^�[���𓱓��B
* View�w����ViewModel�w�ւ̏����˗����A�u���������s�\���H(canExecute)�v�A�u�������s(execute)�v�̓�ʂ�ɒ��ۉ��������́B
* view�n���W���[��/viewmodels�p�b�P�[�W�z����ViewModel�N���X�Ŏ�������B

### di���W���[��
* �I���I���ˑ��������̎d�l�������W���[���B
* �C���^�[�t�F�[�X�Ǝ������A1��1�ƂȂ�悤�ȉ��ւȗ��p��z��B
* model/restdao/realmdao���W���[���@�\�̌Ăяo�����ŁA���p�B

### entity���W���[��
* �����^�X�N��Task�N���X�A�����ς݃^�X�N��Trash�N���X�Ƃ��Ĉ����B
* �o����DataBinding�̗��p��z�肵�Ă��邽�߁ABaseObservable���p���B
* Gson���C�u�����ɂ��AJson�V���A���C�Y/�f�V���A���C�Y����������ۂɂ������W���[���̃G���e�B�e�B�𗘗p�B

### model���W���[��