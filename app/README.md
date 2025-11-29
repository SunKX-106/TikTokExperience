📱 TikTokExperience — 仿抖音体验流页面 Demo

本项目为 Android 应用的一个功能模块 Demo，主要实现 仿抖音体验频道的瀑布流内容展示。
系统使用 Jetpack 组件、MVVM 架构与 Glide 实现基本的内容展示、刷新、加载更多等交互。

项目仅包含体验页相关内容，用于课程作业演示，不包含完整业务流程。

📌 功能概述
✔ 1. 内容瀑布流展示（仿抖音“体验”频道）

·使用 StaggeredGridLayoutManager 实现 1 / 2 列切换
·自适应卡片高度，支持图片裁剪与圆角展示
·卡片包含：封面图、标题、用户头像、用户名、点赞数

✔ 2. 下拉刷新 + 滑动到底自动加载更多

·下拉重新生成一批内容（Mock 数据）
·自动滑到底部时生成下一页内容

✔ 3. 模拟网络图片加载

·Glide 加载随机封面图 picsum.photos
·用户头像采用本地图标（因 Cloudflare 限制真实 API 无法在模拟器访问）

✔ 4. 布局切换功能（附加项）

·点击右上角按钮在 “单列模式 / 双列模式” 动态切换
·切换时保持 RecyclerView 平滑更新

🏗 技术栈与架构
| 类型      | 技术                              |
| -------  | ------------------------------- |
| 架构模式    | **MVVM + Repository**           |
| UI 框架   | Android View + RecyclerView     |
| 数据流     | LiveData                        |
| 图片加载    | Glide                           |
| 布局      | StaggeredGridLayoutManager（瀑布流） |
| Mock 数据 | 本地生成                            |

com.example.tiktokexperience
│
├── data
│   ├── mock                 # Mock 数据生成
│   │   └── MockDataSource
│   ├── model                # 数据模型
│   │   └── ExperienceItem
│   └── repository           # 数据仓库（可扩展真实网络）
│       └── ExperienceRepository
│
├── ui
│   ├── experience           # 体验页 UI
│   │   ├── ExperienceActivity
│   │   ├── ExperienceAdapter
│   │   └── ExperienceViewModel
│   └── main                 # 入口 Activity
│       └── MainActivity
│
└── resources
├── drawable/            # 背景、图标、圆角样式、SVG 图标
└── layout/
└── item_experience.xml
└── activity_experience.xml

🎨 UI 设计说明

深色主题，适配抖音视觉风格
卡片圆角 + 阴影
图标采用 SVG（menu / search / switch / heart）
统一间距 ItemDecoration，UI 更整齐

🚀 运行本项目
1. 克隆代码
   git clone https://github.com/<你的账号>/TikTokExperience.git
2. 打开 Android Studio
支持 Android Studio Giraffe 及以上版本
3. 运行到模拟器或真机即可展示瀑布流体验页

