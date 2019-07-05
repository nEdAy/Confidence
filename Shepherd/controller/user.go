package controller

import (
	"Shepherd/model"
	"Shepherd/pkg/helper"
	"Shepherd/util"
	"github.com/gin-gonic/gin"
)

// Binding from RegisterOrLogin JSON
type register struct {
	Username   string `json:"username" binding:"required"`
	SmsCode    string `json:"smsCode"`
	Password   string `json:"password"`
	InviteCode string `json:"inviteCode"`
}

// @Summary 用户注册 用户登录(密码) 用户登录（短信验证码）
// @Description register or login user by username,password,smsCode
// @Accept  json
// @Produce  json
// @Param username query string true "Username"
// @Param password query string false "Password"
// @Param smsCode query string false "SmsCode"
// @Param inviteCode query string false "InviteCode"
// @Success 200 {string} json "{"time": 1561513181, "code": 200, "msg": "成功", "data" : {}}"
// @Failure 400 {string} json "{"time": 1561513181, "code": 400, "msg": "msg"}"
// @Router /v1/registerOrLogin/ [post]
func RegisterOrLogin(c *gin.Context) {
	var param register
	if err := c.ShouldBindJSON(&param); err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
		return
	}
	user, err := model.GetUserByUsername(param.Username)
	if err != nil {
		// （未注册）进行注册流程
		var scryptPassword string
		if len(param.Password) > 0 { // 如果存在密码，则加密存储
			scryptPassword = util.GetScryptPasswordBase64(param.Password)
		}
		// Verify SmsCode
		err = util.VerifySMS(param.Username, param.SmsCode)
		if err != nil {
			helper.ResponseErrorWithMsg(c, err.Error())
			return
		}
		// Create Token
		token, err := util.CreateToken(param.Username)
		if err != nil {
			helper.ResponseErrorWithMsg(c, err.Error())
			return
		}
		user := new(model.User)
		user.Username = param.Username
		user.Password = scryptPassword
		user.Token = token
		// Creat User DB
		if err := model.AddUser(user); err == nil {
			helper.ResponseJsonWithData(c, user)
		} else {
			helper.ResponseErrorWithMsg(c, err.Error())
		}
	} else {
		// （已注册）进行登录流程
		if len(param.Password) > 0 { // login via password
			if user.Password != util.GetScryptPasswordBase64(param.Password) {
				helper.ResponseErrorWithMsg(c, "账户或密码错误")
				return
			}
			// Create Token
			token, err := util.CreateToken(user.Username)
			if err != nil {
				helper.ResponseErrorWithMsg(c, err.Error())
				return
			}
			user.Token = token
			helper.ResponseJsonWithData(c, user)
		} else if len(param.SmsCode) > 0 { // login via smsCode
			err = util.VerifySMS(param.Username, param.SmsCode)
			if err != nil {
				helper.ResponseErrorWithMsg(c, err.Error())
				return
			}
			// Create Token
			token, err := util.CreateToken(user.Username)
			if err != nil {
				helper.ResponseErrorWithMsg(c, err.Error())
				return
			}
			user.Token = token
			helper.ResponseJsonWithData(c, user)
		} else {
			helper.ResponseErrorWithMsg(c, "参数缺少密码或短信验证码，无法登录")
		}
	}
}

// @Summary 获取用户
func GetUser(c *gin.Context) {
	username, ok := c.Get("username")
	if ok {
		user, err := model.GetUserByUsername(username.(string))
		if err != nil {
			helper.ResponseErrorWithMsg(c, err.Error())
		} else {
			helper.ResponseJsonWithData(c, user)
		}
	} else {
		helper.ResponseErrorWithMsg(c, "Token异常，用户名不存在")
	}
}

// @Summary 获取用户
//func GetUsers(c *gin.Context) {
//	list := make([]*model.User, 0)
//	list, err := model.GetAllUser()
//	if err != nil {
//		helper.ResponseErrorWithMsg(c, err.Error())
//		return
//	}
//	c.JSON(http.StatusOK, list)
//}

// @Summary 删除用户
//func DelUser(c *gin.Context) {
//	id := c.Param("id")
//	intId, err := strconv.Atoi(id)
//	if err != nil {
//		helper.ResponseErrorWithMsg(c, "输入删除用户id非法")
//		return
//	}
//	err = model.DeleteUser(intId)
//	if err != nil {
//		helper.ResponseErrorWithMsg(c, err.Error())
//		return
//	}
//	c.JSON(http.StatusOK, "ok")
//}
