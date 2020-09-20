## Locker Robot

### 目标：
我们要开发一个新的Locker Robot存取包系统，其中Locker/Robot/Manager可以帮助顾客存取包。
### 背景：
随着互联网智能时代的快速发展，华顺超市也准备将之前的人工存取包变得更加智能化，可以让小樱(前台服务员) 一个人就可以搞定大量的存取包服务。所以特聘请你来为他们开发这个LockerRobot存取包系统。

### 业务需求如下：
华顺超市准备购买三种型号的储物柜，分别为S，M，L（S < M < L）。当顾客来存包的时候只需要将包交给小樱，之后的一系列存包会由小樱来完成。
小樱在存包之前先会拿到包裹的尺寸标签，根据不同的尺寸标签决定是直接存入Locker还是找对应Robot存包。当包裹尺寸为S时，小樱会直接存入S号的Locker中；当包裹尺寸为M时，找PrimaryLockerRobot存包；当包裹尺寸为L时，找SuperLockerRobot存包。存包成功后小樱会将票据交给顾客。存包的时候，小樱从不犯糊涂，她一定能找对目标。

当普通顾客拿着票据来取包的时候，只要把票据交给小樱，小樱会找对应的Robot或者Locker取包。
当VIP顾客来存取包时，可以直接通过VIP通道找LockerRobotManager提供专门的存取包服务。

### 业务规则
1. Locker可以存包取包
2. PrimaryLockerRobot 按照Locker顺序存，它只管理M号Locker，暂且不用考虑管理其它型号的Locker
3. SuperLockerRobot 将包存入空置率(可用容量/容量)最大的Locker，它只管理L号Locker，暂且不用考虑管理其它型号的Locker
4. 目前由于业务量比较小，LockerRobotManager只管理一个Locker（S号）、一个PrimaryLockerRobot（管理一个Locker）和SuperLockerRobot（管理一个Locker），但也不排除后期随着业务增长，LockerRobotManager会管理更多的Locker或者Robot
5. LockerRobotManager可以委派Robot存包取包，也可以自己存包取包，委派顺序没有要求
6. LockerRobotManager管理的Locker和Robot不会直接对外提供服务
7. 不同型号Locker产生的票据不通用，当用不同的型号票取包时，系统要提示票的型号不对
8. 超市管理员在配置Robot和Manager的时候，只要Locker的型号选择不对，Robot和Manager将无法正常使用

### 常见问题

1. 不存在容量为0的Locker，Robot至少要管理一个Locker
2. M，L号的Locker不对外提供服务，只能通过PrimaryLockerRobot或者SuperLockerRobot进行使用
3. 小樱会在线下对票据进行区分找不同的robot或者Locker进行取包，但她难免也有犯糊涂的时候。
4. 对于非VIP顾客找LockerRobotManager进行存取包，是线下验证还是系统验证？
5. VIP通道非VIP顾客是没法进入的。
6. LockerRobotManager管理的robot的locker可以和其他robot的locker是相同的吗？
   
    >不能相同，如果相同，则配置无效，将无法正常使用。
7. 小樱能区分不同类型的票据，那能够区分伪造的票据吗？
   
    >从实际场景出发，小樱不能够区分伪造票
8. 小樱代理用户取完包后，会回收票据吗？
   
    >小樱会回收，但她自己取包的时候难免也有犯糊涂的时候。

### Tasking

- 普通用户

  1. Given：一个 S 号的包，一个 S 号的 Locker ， Locker 有剩余空间

     When：小樱存包

     Then：存包成功，产生票据

  2. Given：一个 S 号的包，一个 S 号的 Locker ， Locker 没有剩余空间

     When：小樱存包

     Then：存包失败，提示箱子已满

  3. Given：一个经由小樱存进去的 S 号包的有效票据
     
     When：小樱去 Locker 取包
     
     Then：取包成功
     
  4. Given：小樱存了一个 S 号包进去，一张无效票据

     When：小樱去 Locker 取包

     Then：取包失败，票据无效

  5. Given：一个 M 号的包，一个 PrimaryLockerRobot ，管理两个有剩余空间的 M 号 Locker
     when：小樱存包
     then：成功存包到第一个非空 Locker，产生票据

  6. Given：一个 M 号的包，一个 PrimaryLockerRobot ，管理一个无剩余空间的 M 号 Locker 
     when：小樱存包
     then：存包失败，提示箱子已满

  7. Given：一个经由小樱存进去的 M 号包的有效票据

     When：小樱去 PrimaryLockerRobot 取包

     Then：取包成功

  8. Given：小樱存了一个 M 号包进去，一张无效票据

     When：小樱去 PrimaryLockerRobot 取包

     Then：取包失败，票据无效

  9. Given：一个 L 号的包，一个 SuperLockerRobot ，管理两个有剩余空间的 L 号 Locker 
     When：小樱存包
     Then：成功存包到空置率最高的 Locker ，返回票据

  10. Given：一个 L 号的包，一个 SuperLockerRobot ，管理一个无剩余空间的 L 号 Locker
     When：小樱存包
     Then：存包失败，提示箱子已满

  11. Given：一个经由小樱存进去的 L 号包的有效票据

      When：小樱去 SuperLockerRobot 取包

      Then：取包成功

  12. Given：小樱存了一个 L 号包进去，一张无效票据

      When：小樱去 SuperLockerRobot 取包

      Then：取包失败，票据无效

  13. Given：小樱存了一个 S 号包进去，一个经由小樱存进去的 M 号包的有效票据

      When：小樱去 Locker 取包

      Then：取包失败，票据型号不匹配

  14. Given：小樱存了一个 M 号包进去，一个经由小樱存进去的 S 号包的有效票据

      When：小樱去 PrimaryLockerRobot 取包

      Then：取包失败，票据型号不匹配

  15. Given：小樱存了一个 L 号包进去，一个经由小樱存进去的 M 号包的有效票据

      When：小樱去 SuperLockerRobot 取包

      Then：取包失败，票据型号不匹配

- VIP 用户存包

  1. Given：一个 S 号的包，LockerRobotManager 管理一个 S 号的 Locker ， Locker 有剩余空间

     When：LockerRobotManager 存包

     Then：存包成功，产生票据

  2. Given：一个 S 号的包，LockerRobotManager 管理一个 S 号的 Locker ， Locker 没有剩余空间

     When：LockerRobotManager 存包

     Then：存包失败，提示箱子已满

  3. Given：一个经由 LockerRobotManager 存进去的 S 号包的有效票据

     When：LockerRobotManager 取包

     Then：取包成功

  4. Given：LockerRobotManager 存了一个 S 号包进去，一张无效票据

     When：LockerRobotManager 取包

     Then：取包失败，票据无效

  5. Given：一个 M 号的包，LockerRobotManager 管理一个有剩余空间的 S 号 Locker 和一个仅管理一个有剩余空间的 M 号 Locker 的 PrimaryLockerRobot
     when：LockerRobotManager 存包
     then：存包成功，产生票据

  6. Given：一个 M 号的包，LockerRobotManager 管理一个有剩余空间的 S 号 Locker 和一个仅管理一个无剩余空间的 M 号 Locker 的 PrimaryLockerRobot
     when：LockerRobotManager 存包
     then：存包失败，提示箱子已满

  7. Given：一个经由 LockerRobotManager 存进去的 M 号包的有效票据

     When：LockerRobotManager 取包

     Then：取包成功

  8. Given：LockerRobotManager 存了一个 M 号包进去，一张无效票据

     When：LockerRobotManager 取包

     Then：取包失败，票据无效

  9. Given：一个 L 号的包，LockerRobotManager 管理一个有剩余空间的 S 号 Locker ，一个仅管理一个有剩余空间的 M 号 Locker 的 PrimaryLockerRobot 和一个仅管理一个有剩余空间的 L 号 Locker 的 SuperLockerRobot
     When：LockerRobotManager 存包
     Then：存包成功，返回票据

  10. Given：一个 L 号的包，LockerRobotManager 管理一个有剩余空间的 S 号 Locker，一个仅管理一个有剩余空间的 M 号 Locker 的 PrimaryLockerRobot 和一个管理一个无剩余空间的 L 号 Locker 的 SuperLockerRobot
      When：LockerRobotManager 存包
      Then：存包失败，提示箱子已满

  11. Given：一个经由 LockerRobotManager 存进去的 L 号包的有效票据

      When：LockerRobotManager 取包

      Then：取包成功

  12. Given：LockerRobotManager 存了一个 L 号包进去，一张无效票据

      When：LockerRobotManager 取包

      Then：取包失败，票据无效

- 超市管理员配置 Robot 和 Manager 

  1. Given：一个 S 号的 Locker

     When：配置 Locker 到 LockerRobotManager

     Then：配置成功

  2. Given：一个 M 号的 Locker，一个 L 号的 Locker

     When：配置 Locker 到 LockerRobotManager

     Then：配置失败

  3. Given：一个 M 号的 Locker

     When：配置 Locker 到 PrimaryLockerRobot

     Then：配置成功

  4. Given：一个 S 号的 Locker，一个 L 号的 Locker

     When：配置 Locker 到 PrimaryLockerRobot

     Then：配置失败

  5. Given：一个 L 号的 Locker

     When：配置 Locker 到 SuperLockerRobot

     Then：配置成功

  6. Given：一个 S 号的 Locker，一个 M 号的 Locker

     When：配置 Locker 到 SuperLockerRobot

     Then：配置失败





 
