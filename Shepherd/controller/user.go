package controller

import (
	"Shepherd/helper"
	"Shepherd/model"
	"Shepherd/util"
	"github.com/gin-gonic/gin"
	"github.com/gin-gonic/gin/binding"
	"time"
)

// Binding from Register JSON
type register struct {
	Username string `json:"username" binding:"required"`
	Password string `json:"password" binding:"required"`
}

// @Summary 添加用户
// @Description register user by username,password
// @Accept  json
// @Produce  json
// @Param username query string true "Username"
// @Param password query string false "Password"
// @Success 200 {string} json "{"time": 1561513181, "code": 200, "msg": "成功", "data" : {}}"
// @Failure 400 {string} json "{"time": 1561513181, "code": 400, "msg": "msg"}"
// @Router /v1/register/ [post]
func Register(c *gin.Context) {
	register := new(register)
	if err := c.ShouldBindWith(&register, binding.JSON); err != nil {
		helper.ResponseWithJsonError(c, err.Error())
		return
	}
	isUserExist, err := model.IsUserExist(register.Username)
	if err != nil {
		helper.ResponseWithJsonError(c, err.Error())
		return
	}
	if isUserExist {
		helper.ResponseWithJsonError(c, "用户<"+register.Username+">已注册")
		return
	}
	token, err := util.CreateToken(register.Username)
	if err != nil {
		helper.ResponseWithJsonError(c, err.Error())
		return
	}
	user := new(model.User)
	user.Username = register.Username
	user.Password = util.GetScryptPasswordBase64(register.Password)
	user.CreateTime = time.Now().Unix()
	if err := model.AddUser(user); err == nil {
		user.Password = ""
		user.Token = token
		helper.ResponseWithJsonData(c, user)
	} else {
		helper.ResponseWithJsonError(c, err.Error())
	}
}

// @Summary 获取用户
func GetUser(c *gin.Context) {
	username, ok := c.Get("username")
	if ok {
		user, err := model.GetUserByUsername(username.(string))
		if err != nil {
			helper.ResponseWithJsonError(c, err.Error())
		} else {
			helper.ResponseWithJsonData(c, user)
		}
	} else {
		helper.ResponseWithJsonError(c, "Token异常，用户名不存在")
	}
}

// @Summary 获取用户
//func GetUsers(c *gin.Context) {
//	list := make([]*model.User, 0)
//	list, err := model.GetAllUser()
//	if err != nil {
//		helper.ResponseWithJsonError(c, err.Error())
//		return
//	}
//	c.JSON(http.StatusOK, list)
//}

// Binding from Login JSON
type login struct {
	Username string `json:"username" binding:"required"`
	Password string `json:"password" binding:"required"`
}

// @Summary 用户登录
// @Description login user by username,password
// @Accept  json
// @Produce  json
// @Param username query string true "Username"
// @Param password query string false "Password"
// @Success 200 {string} json "{"time": 1561513181, "code": 200, "msg": "成功", "data" : {}}"
// @Failure 400 {string} json "{"time": 1561513181, "code": 400, "msg": "msg"}"
// @Router /v1/login/ [post]
func Login(c *gin.Context) {
	login := new(login)
	if err := c.ShouldBindWith(&login, binding.JSON); err != nil {
		helper.ResponseWithJsonError(c, err.Error())
		return
	}
	user, err := model.GetUserByUsername(login.Username)
	if err != nil {
		helper.ResponseWithJsonError(c, err.Error())
		return
	}
	if user.Password == util.GetScryptPasswordBase64(login.Password) {
		token, err := util.CreateToken(user.Username)
		if err != nil {
			helper.ResponseWithJsonError(c, err.Error())
			return
		}
		user.Password = ""
		user.Token = token
		helper.ResponseWithJsonData(c, user)
	} else {
		helper.ResponseWithJsonError(c, "账户或密码错误")
	}
}

// @Summary 删除用户
//func DelUser(c *gin.Context) {
//	id := c.Param("id")
//	intId, err := strconv.Atoi(id)
//	if err != nil {
//		helper.ResponseWithJsonError(c, "输入删除用户id非法")
//		return
//	}
//	err = model.DeleteUser(intId)
//	if err != nil {
//		helper.ResponseWithJsonError(c, err.Error())
//		return
//	}
//	c.JSON(http.StatusOK, "ok")
//}
