package cn.neday.sheep.model

data class User(
    // 用户ID 主键
    val id: Int?,
    // 手机号 11位 主键
    val mobile: String?,
    /* 用户密码 仅提交使用 永远不返回 password字段加密可行方案：客户端提交 md5(password) 密码，
     服务端数据库通过 md5(salt+md5(password)) 的规则存储密码，该 salt 仅存储在服务端，且在每次存储密码时都随机生成，
     密码被 md5() 提交到服务端之后，可通过 md5(salt + form['password']) 与数据库密码比对
     另防止 replay 攻击（请求被重新发出一次即可能通过验证）的问题，由服务端颁发并验证一个带有时间戳的可信 token （或一次性的）
     传输过程 HTTPS 加持 */
    val password: String?,
    // 用户昵称
    val nickname: String?,
    // 用户头像URL
    val avatar: String?,
    // 口袋币 即等级 0=0 1>=100 2>=200 3>=500 4>=1000 5>=2000 6>=5000 7>=15000 8>=50000 9>=100000 10>=200000
    val credit: Int?,
    // Token
    val token: String?
)