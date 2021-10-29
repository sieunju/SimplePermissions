> [![](https://jitpack.io/v/sieunju/SimplePermissions.svg)](https://jitpack.io/#sieunju/SimplePermissions)   
> 권한 API가 AppCompat 1.3.0 이후로 변경이 되면서 그에 맞게 궈한 라이브러리를 만들었습니다.   

---


1. #### 사양
    - Min SDK Version 21
    - Target SDK Version 30

2. #### 라이브러리 추가 하는 방법
    - *Project Gradle*
    ~~~
    allprojects {
	    repositories {
		    ...
		    maven { url 'https://jitpack.io' }
	    }
    }
    ~~~
    - *App Module Gradle*   
    Latest Versions [![](https://jitpack.io/v/sieunju/SimplePermissions.svg)](https://jitpack.io/#sieunju/SimplePermissions)
    ~~~
    dependencies {
    	implementation 'com.github.sieunju:SimplePermissions:$version'
    }
    ~~~

3. #### 사용 예
    - *간단 설명*
        - builder 패턴으로 구성되어 있고, 요청한 권한 중 거부를 선택한 로직을 리턴합니다.
        - 함수
            - requestPermissions(vararg permissions: String)
                - 요청하고 싶은 권한들
            - negativeDialogTitle(@StringRes id: Int or text: String)
                - 권한 거부시 나타내는 팝업에 대한 제목
            - negativeDialogContents(@StringRes id: Int or text: String)
                - 권한 거부시 나타내는 팝업에 대한 내용
            - negativeDialogLeftButton(@StringRes id: Int or text: String)
                - 권한 거부시 나타내는 팝업에 대한 왼쪽(거절) 버튼
            - negativeDialogRightButton(@StringRes id: Int or text: String)
                - 권한 거부시 나타내는 팝업에 대한 오른쪽(승인) 버튼
            - negativeDialogUiConfig(config: PermissionsDialogUiModel)
                - 권한 거부시 나타내는 팝업에 대한 Ui 설정   
                ___PermissionsDialogUiModel 기본값 참고___
            - negativeDialogPermissionsSetting(which: Int)
                - 권한 거부시 앱 정보 페이지로 이동처리하는 함수   
            
    - *example*
        ~~~
        SimplePermissions(this)
            .requestPermissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.BLUETOOTH
            )
            .build { isAllGranted, negativePermissions ->
                // isAllGranted -> 모든 권한이 승인된 상태 유무
                // negativePermissions -> 권한 거부된 권한 리스트
            }
        ~~~
        - 권한 거부시 나타내는 팝업을 꽉찬 화면의 양쪽 여백을 고정으로 주고 싶을때 
        ~~~
        .negativeDialogUiConfig(PermissionsDialogUiModel(
            dialogBg = R.drawable.bg_permissions_dialog
        ))
        ~~~
        - bg_permissions_dialog.xml
        ~~~
        <?xml version="1.0" encoding="utf-8"?>
        <inset xmlns:android="http://schemas.android.com/apk/res/android"
            android:insetLeft="20dp"
            android:insetRight="20dp">
            <shape android:shape="rectangle">
                <solid android:color="@android:color/white" />
                <corners android:radius="20dp" />
            </shape>
        </inset>
        ~~~
