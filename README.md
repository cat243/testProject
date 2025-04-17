javaWeb课设-电影管理系统管理员端

## 模板功能

本项目后端为spirngboot+springmvc基础项目
使用mybatis和mybatisPlus进行数据库的交互
使用阿里云oss进行图片服务的存储

页面见前端frontend,前端通过axios发送请求给后端获取数据进行数据的展示

1.电影管理 (详细从 MoviesController)
-- 添加电影
-- 修改电影
-- 删除电影
-- 查询电影
2.影院管理 (详细见 CinemaController)
-- 查询影院及每个影院上架的影片
3.订单管理 (详细见 OrderController)
-- 查看用户订单
-- 搜索用户订单
4.用户管理 (详细见 UserController)
-- 查询用户
-- 对用户进行拉黑和恢复白名单的操作