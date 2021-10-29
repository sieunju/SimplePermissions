> 권한 API가 AppCompat 1.3.0 이후로 변경이 되면서 그에 맞게 궈한 라이브러리를 만들었습니다.   
[![](https://jitpack.io/v/sieunju/SimplePermissions.svg)](https://jitpack.io/#sieunju/SimplePermissions)

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
    ~~~
    dependencies {
    	Latest Version: 0.0.1
    	implementation 'com.github.sieunju:SimplePermissions:$version'
    }
    ~~~

3. #### 사용 예
    - *간단 설명*
        - build 함수를 호출하면 요청한 권한중 거부 상태의 권한에 대해서 로직을 수행하게 됩니다.
        - 함수
            - incrementProgressBy(diff: Int)
                - diff -> 증가율
            - currentProgress : Int
                - 현재 진행률
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
