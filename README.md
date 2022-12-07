> [![](https://jitpack.io/v/sieunju/SimplePermissions.svg)](https://jitpack.io/#sieunju/SimplePermissions)   
> 권한 API가 AppCompat 1.3.0 이후로 변경이 되면서 그에 맞게 권한 라이브러리를 만들었습니다.
---

![AndroidMinSdkVersion](https://img.shields.io/badge/minSdkVersion-21-green.svg) ![AndroidTargetSdkVersion](https://img.shields.io/badge/targetSdkVersion-32-brightgreen.svg)

#### 라이브러리 추가 하는 방법
*Project Gradle*
```groovy
allprojects {
	    repositories {
		    ...
		    maven { url 'https://jitpack.io' }
	    }
}
```

- App Module Gradle

```groovy
dependencies {
    	implementation 'com.github.sieunju:SimplePermissions:core:$latestVersion'
}
```

## 유의사항
- 혹시나 머티리얼을 사용하시거나 프로젝트에 사용중인 라이브러리랑 충돌이 일어나는 경우에는 아래와 같이 사용해주시면 됩니다. 🙇‍♂️
- A.K.A exclude
```groovy
implementation("com.github.sieunju.SimplePermissions:core:$lateversion") {
        exclude("androidx.appcompat:appcompat")
    }
implementation("com.github.sieunju.SimplePermissions:dialog:$lateversion") {
        exclude("com.google.android.material")
        exclude("androidx.appcompat:appcompat")
        exclude("androidx.constraintlayout")
    }
```
- 현재 1.1.0 버전에는 'com.github.sieunju.SimplePermissions:dialog' 사용이 불가 합니다. 사유는 CustomDialog 에서 이슈가 있습니다. 현재 공사중에 있습니다..(이미 core 모듈에서 아주 심플하게 권한 체크를 해서 권한 거부일때 Dialog 처리에 대해서는 각자 비즈니스 로직으로 처리하는것이 좋아보입니다 😊 )

#### 사용 예
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

- *example*
     ~~~
     SPermissions(this) // Context, Activity, Fragment 지원합니다.
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
