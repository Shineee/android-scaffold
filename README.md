# Android Scaffold

拿来即写业务代码的 Android 脚手架：Compose + 离线优先 + Retrofit + Room + DataStore + Koin，附带首页/设置两个可运行示例页。

架构遵循 Google 官方《应用架构指南》：**单 Activity + Compose、UI 层 / 数据层（+ 可选领域层）分层、单向数据流（UDF）、数据库作为唯一数据源（离线优先）、依赖注入**；依赖版本均为当前最新稳定版。

**[↓ 下载签名 Release APK（v1.0.0）](https://github.com/Shineee/android-scaffold/releases/download/v1.0.0/scaffold-v1.0.0-release.apk)** · [全部 Release](https://github.com/Shineee/android-scaffold/releases)

## 技术栈

| 类别 | 选型 | 版本 |
| --- | --- | --- |
| 语言 / 构建 | Kotlin（AGP 内置 Kotlin）、KSP、Gradle Wrapper | 2.4.21 / 2.3.12 / 9.8.0 |
| AGP | com.android.application | 9.4.1 |
| UI | Jetpack Compose + Material3 + 底部导航 | BOM 2026.09.00 |
| 导航 | Navigation Compose | 2.10.2 |
| AndroidX 基础 | Core-KTX / Lifecycle / Activity | 1.19.1 / 2.11.0 / 1.13.0 |
| 架构 | MVVM + UDF + Repository + 离线优先（Room 为唯一数据源） | — |
| 依赖注入 | Koin | 4.2.2 |
| 网络 | Retrofit / OkHttp / kotlinx-serialization | 3.0.0 / 5.5.1 / 1.12.0 |
| 本地存储 | Room / DataStore Preferences | 2.8.5 / 1.3.0 |
| 后台任务 | WorkManager（含 SyncWorker 示例） | 2.12.0 |
| 图片 | Coil 3 | 3.6.3 |
| 权限 | Accompanist Permissions（预留） | 0.37.3 |
| 测试 | JUnit4 / androidx-junit / coroutines-test / Espresso | 4.13.2 / 1.3.0 / 1.11.0 / 3.7.0 |

依赖仓库配置了阿里云镜像，国内构建更快。

## 目录结构

```
app/src/main/java/com/scaffold/app/
├── ScaffoldApp.kt          # Application：Koin 启动
├── MainActivity.kt         # 单 Activity：edge-to-edge + 主题 + 导航
├── core/UiState.kt         # 加载/成功/失败 界面状态模型
├── di/AppModule.kt         # Koin 模块：网络、数据库、仓库、ViewModel
├── sync/SyncWorker.kt      # WorkManager 后台同步示例（按需调度）
├── domain/                 # 业务层：模型、仓库接口（不依赖 Android）
│   ├── model/
│   └── repository/
├── data/                   # 数据层实现
│   ├── local/              #   Room + DataStore
│   ├── remote/             #   Retrofit API + DTO
│   └── repository/         #   仓库实现（网络→落库→Flow）
└── ui/
    ├── theme/              # Color / Type / Theme（支持动态取色 + 三态深色模式）
    ├── navigation/         # Routes + 底部导航 + NavHost
    ├── components/         # Loading / Error / Empty 通用视图
    ├── home/               # 示例：离线优先列表页
    └── settings/           # 示例：DataStore 持久化设置页
```

## 快速开始

1. **Android Studio** 打开本目录，等待 Gradle Sync 完成即可运行。
2. 命令行：
   ```bash
   ./gradlew assembleDebug     # 调试包
   ./gradlew testDebugUnitTest # 单元测试
   ./gradlew assembleRelease   # 混淆发布包
   ```

## 改成你的项目

### 1. 改包名 / 应用名

全局替换 `com.scaffold.app`（目录名也一并改），再改 `app/build.gradle.kts` 里的 `namespace` 与 `applicationId`，最后改 `strings.xml` 的 `app_name`。

### 2. 接后端

- `data/remote/ApiConfig.kt`：改 `BASE_URL`
- `data/remote/` 下新建 `XxxApi.kt` 接口 + `XxxDto.kt`（`@Serializable`）
- `di/AppModule.kt` 已配好 Retrofit/OkHttp/Json，`single<XxxApi> { get<Retrofit>().create(...) }` 即可注入

### 3. 加一个页面

1. `ui/xxx/` 下新建 `XxxScreen.kt` + `XxxViewModel.kt`
2. `di/AppModule.kt` 中 `viewModel { XxxViewModel(get()) }`
3. `navigation/AppNavHost.kt`：`Routes` 加常量、`composable(...)` 注册、`bottomTabs` 按需加 tab

### 4. release 签名（可选）

根目录新建 `keystore.properties`（已被 gitignore）：

```properties
storeFile=../keystore.jks
storePassword=******
keyAlias=your-key
keyPassword=******
```

存在该文件时 release 自动使用它签名；不存在则产出未签名 APK。

## 示例功能说明

- **首页（离线优先）**：`UI → PostRepository.posts(Room Flow) ← refresh() ← Retrofit`。断网时展示缓存，冷启动无缓存时展示错误页可重试；列表图片演示 Coil 加载。
- **设置页**：DataStore 持久化深色模式三态（跟随系统/浅色/深色），实时生效。
- **SyncWorker**：网络可用时后台刷新，默认不调度，调用 `SyncWorker.schedule(context)` 启用，失败自动重试 3 次。
- **日志**：OkHttp LoggingInterceptor 仅在 debug 构建输出请求体。

## 测试

- `app/src/test`：`HomeViewModelTest` 演示「假仓库 + MainDispatcherRule + runTest」的 ViewModel 测试写法。
- `app/src/androidTest`：仪器测试骨架。

## 说明与取舍

- 单 module 起步，业务变大时可按 `domain/data/ui` 拆 `:core:network`、`:feature:xxx` 等多模块。
- 深色模式默认开启动态取色（Android 12+），`AppTheme(dynamicColor = false)` 可关闭改用品牌色。
- 依赖版本集中在 `gradle/libs.versions.toml`，升级时统一改 `[versions]` 即可。
