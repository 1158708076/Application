# 介绍：
这是一个拥有栈管理的baseApplication

# 使用方法：
1、在project gradle里添加
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

2、在模块的 gradle里添加
dependencies {
	        implementation 'com.github.1158708076:Application:v1.0'
	}
  
