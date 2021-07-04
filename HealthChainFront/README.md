# healthchainfront

###项目介绍

整个系统的web前端，供系统的管理员和机构用户使用。

### 安装相关依赖

```
npm install
```

### 启动项目
```
npm run serve
```

### 目录结构描述
├─public                       
│  ├─api                         //测试时的接口数据
│  │  ├─admin              //管理员
│  │  ├─third                 //第三方机构
│  │  └─user                 //个人用户
│  └─layuiadmin          //layui相关文件
│      ├─json
│      │  ├─console
│      │  ├─content
│      │  ├─forum
│      │  ├─layer
│      │  ├─message
│      │  ├─table
│      │  ├─upload
│      │  ├─user
│      │  ├─useradmin
│      │  └─workorder
│      ├─layui                               //ui文件
│      │  ├─css                             //相关css样式
│      │  │  └─modules
│      │  │      ├─laydate
│      │  │      │  └─default
│      │  │      └─layer
│      │  │          └─default
│      │  ├─font
│      │  └─lay
│      │      └─modules
│      ├─lib
│      │  └─extend
│      ├─modules
│      ├─style
│      │  └─res
│      │      └─template
│      └─tpl
│          ├─layim
│          └─system
└─src                                 //项目资源文件
    ├─assets
    │  └─js                        
    ├─common
    ├─components
    │  ├─common
    │  └─content
    ├─router                      //路由，存放每个页面的接口
    └─views                       //存放不同角色的组件
        ├─admin                 //管理端页面各个组件
        ├─login                    //登录注册组件
        └─organization      //机构页面各个组件
