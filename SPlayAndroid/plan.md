- MainActivity -> Navigation
- WelcomeFragment
- HomeFragment
- LoginFragment
- 知识体系/导航
- SearchFragment
- Banner

HomeScreen：

- 搜索入口、消息入口
- 每日一问
- 首页、banner
- 广场 文章类别：
- 网格（艺术字做图标） 项目：
- 项目分类
- 项目列表 文章详情：
- H5

注：

- 为什么不使用单Activity模式？ 因为单Activity模式ViewModel无法销毁，很多无用的逻辑ViewModel会保存在内存中；如果不使用ViewModel的话，很多逻
  辑需要在UI层实现，这样会导致UI层显得臃肿。

- 为什么